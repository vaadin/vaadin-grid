package com.vaadin.prototype.webcomponentwrapper.template;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.vaadin.prototype.webcomponentwrapper.UIWithRootDocument;
import com.vaadin.prototype.webcomponentwrapper.element.Document;
import com.vaadin.prototype.webcomponentwrapper.element.Element;
import com.vaadin.prototype.webcomponentwrapper.element.Elements;
import com.vaadin.prototype.webcomponentwrapper.element.Node;
import com.vaadin.prototype.webcomponentwrapper.element.Tag;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.JavaScript;
import com.vaadin.ui.UI;

public class Templates {

    public static TemplateInstance<? extends Element> instiantiate(
            String fileName) {

        TemplateDefinition<?> t = get(fileName);
        if (t == null) {
            t = bootstrapTemplateDef(fileName);
        }
        return t.instantiate();
    }

    public static <E extends Element> TemplateInstance<E> instantiate(
            Class<E> clazz) {
        String tag = getTagName(clazz);

        String fileName = getFileName(clazz);

        TemplateDefinition<E> t = get(tag);
        if (t == null) {
            t = bootstrapTemplateDef(tag, fileName, clazz);
        }

        return t.instantiate();
    }

    private static <T extends Element> String getTagName(Class<T> clazz) {
        Tag tagAnnotation = clazz.getAnnotation(Tag.class);

        if (tagAnnotation == null) {
            throw new IllegalArgumentException(
                    "No @Tag annotation found on this Element subclass: "
                            + clazz.getCanonicalName());
        }
        return tagAnnotation.value();
    }

    private static <T extends Element> String getFileName(Class<T> clazz) {
        Template templateAnnotation = clazz.getAnnotation(Template.class);
        if (templateAnnotation == null) {
            throw new IllegalArgumentException(
                    "No @Template annotation on this Element subcliass: "
                            + clazz.getCanonicalName());
        }
        return templateAnnotation.value();

    }

    private static TemplateDefinition<Element> bootstrapTemplateDef(
            String fileName) {
        String templateContents = readResource(fileName);

        String templateId = fileName;
        TemplateDefinition<Element> td = new SimpleTemplateDefinitionImpl(
                templateId, templateContents);

        put(templateId, td);

        addTemplateToHTMLBody(td);

        return td;
    }

    private static <E extends Element> TemplateDefinition<E> bootstrapTemplateDef(
            String tag, String fileName, Class<E> clazz) {
        String templateContent = readResource(fileName);

        TemplateDefinition<E> td = new ParametrizedTemplateDefinitionImpl<E>(
                tag, templateContent, clazz);
        put(tag, td);
        addCustomTagToHTMLBody(td);
        return td;
    }

    private static void addTemplateToHTMLBody(TemplateDefinition<Element> td) { // TODO:
                                                                                // effects
        // of this
        // method don't
        // survive
        // page-refreshing
        Element templateElement = Elements.create("template");
        templateElement.setAttribute("id", td.getId());
        templateElement.setInnerHtml(td.getInnerHTML());

        Document root = ((UIWithRootDocument) UI.getCurrent())
                .getDocumentRoot();
        root.appendChild(templateElement);
    }

    private static <E extends Element> void addCustomTagToHTMLBody(
            TemplateDefinition<E> td) {
        Element polymerElement = Elements.create("polymer-element");
        polymerElement.setAttribute("noscript", "true");
        polymerElement.setAttribute("name", td.getId());
        Element template = Elements.create("template");
        template.setInnerHtml(td.getInnerHTML());
        polymerElement.appendChild(template);

        String asHtml = polymerElement.asHtml();
        String script = "\ndocument.head.innerHTML += '" + asHtml + "'\n";
        JavaScript.eval(script); // required to let polymer do its magic
    }

    private static void put(String templateId,
            TemplateDefinition<? extends Element> td) {
        ensureTemplateDefMapExists();
        if (get(templateId) == null) {
            TemplateDefinitionMap map = VaadinSession.getCurrent()
                    .getAttribute(TemplateDefinitionMap.class);
            map.put(templateId, td);

        } else {
            throw new RuntimeException(
                    "Template already registered for this id: " + templateId);
        }
    }

    @SuppressWarnings("unchecked")
    private static <E extends Element> TemplateDefinition<E> get(
            String templateId) {
        TemplateDefinitionMap map = VaadinSession.getCurrent().getAttribute(
                TemplateDefinitionMap.class);
        if (map == null) {
            return null;
        }
        return map.get(templateId);
    }

    private static void ensureTemplateDefMapExists() {
        TemplateDefinitionMap map = VaadinSession.getCurrent().getAttribute(
                TemplateDefinitionMap.class);
        if (map == null) {
            map = new TemplateDefinitionMap();
            VaadinSession.getCurrent().setAttribute(
                    TemplateDefinitionMap.class, map);
        }

    }

    private static String readResource(String fileName) {
        ClassLoader cl = findClassloader();
        StringBuilder sb = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                cl.getResourceAsStream(fileName)))) {
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sb.toString();
    }

    private static ClassLoader findClassloader() {
        return Thread.currentThread().getContextClassLoader();
    }
}
