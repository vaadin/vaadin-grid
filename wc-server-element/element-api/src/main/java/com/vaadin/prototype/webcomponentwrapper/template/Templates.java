package com.vaadin.prototype.webcomponentwrapper.template;

import static com.vaadin.prototype.webcomponentwrapper.template.Utils.getAnnotationValue;
import static com.vaadin.prototype.webcomponentwrapper.template.Utils.readResource;

import com.vaadin.prototype.webcomponentwrapper.WebComponentUI;
import com.vaadin.prototype.webcomponentwrapper.element.Element;
import com.vaadin.prototype.webcomponentwrapper.element.Elements;
import com.vaadin.prototype.webcomponentwrapper.element.Tag;
import com.vaadin.ui.JavaScript;

public class Templates {

    public static <E extends Element> TemplateInstance<E> instantiate(
            Class<E> klazz) {
        String tag = getTag(klazz);
        String fileName = getFileName(klazz);
        TemplateDefinition<E> td = bootstrapTemplateDefinition(tag, fileName,
                klazz);
        return td.instantiate();
    }

    public static TemplateInstance<Element> instantiate(String fileName) {
        String tag = getTag(fileName);
        TemplateDefinition<Element> td = bootstrapTemplateDefinition(tag,
                fileName, Element.class);
        return td.instantiate();
    }

    private static String getTag(Class<? extends Element> klazz) {
        return getAnnotationValue(klazz, Tag.class);
    }

    private static String getTag(String fileName) {
        StringBuilder sb = new StringBuilder("v-");

        String fileNameFragment = fileName.replace('.', '-');
        sb.append(fileNameFragment);

        return sb.toString();
    }

    private static String getFileName(Class<? extends Element> klazz) {
        return getAnnotationValue(klazz, Template.class);
    }

    @SuppressWarnings("unchecked")
    private static <E extends Element> TemplateDefinition<E> bootstrapTemplateDefinition(
            String tag, String fileName, Class<E> klazz) {
        TemplateDefinition<? extends Element> templateDefinition = WebComponentUI
                .getCurrent().getTemplateDefinition(tag);
        if (templateDefinition != null) {
            return ((TemplateDefinition<E>) templateDefinition);
        }

        String htmlContent = readResource(fileName);
        TemplateDefinition<E> td = new TemplateDefinitionImpl<E>(tag,
                htmlContent, klazz);
        WebComponentUI.getCurrent().addTemplateDefinition(td);

        addToBrowserDOM(td);

        return td;

    }
    
    private static String polymerRegistrationScript = "\n"
            + "Polymer('${0}', {})\n"
            + "var el = document.createElement('div')\n"
            + "el.innerHTML = '${1}'\n" //watch out for special characters here :(
            + "document.body.appendChild(el)";

    // TODO: replace with <link> and resources serving
    private static <E extends Element> void addToBrowserDOM(
            TemplateDefinition<E> td) {
        Element polymerDefinition = Elements.create("polymer-element");
        polymerDefinition.setAttribute("name", td.getTag());
        Element template = Elements.create("template");
        template.setInnerHtml(td.getHtmlContent());
        polymerDefinition.appendChild(template);
        
        String script = polymerRegistrationScript.replace("${0}", td.getTag()).replace("${1}", polymerDefinition.asHtml());

        JavaScript.eval(script);
    }
}
