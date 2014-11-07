package com.vaadin.prototype.webcomponentwrapper.template;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.vaadin.prototype.webcomponentwrapper.UIWithRootDocument;
import com.vaadin.prototype.webcomponentwrapper.element.Document;
import com.vaadin.prototype.webcomponentwrapper.element.Element;
import com.vaadin.prototype.webcomponentwrapper.element.Elements;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;

public class Templates {

	public static TemplateInstance<? extends Element> instiantiate(
			String fileName) {

		TemplateDefinition t = get(fileName);
		if (t == null) {
			t = bootstrapTemplateDef(fileName);
		}
		return t.instantiate();

		// throw new UnsupportedOperationException("Not implemented yet");
	}

	private static TemplateDefinition bootstrapTemplateDef(String fileName) {
		ClassLoader cl = findClassloader();
		String templateContents = read(fileName, cl);

		String templateId = fileName;
		TemplateDefinition td = new SimpleTemplateDefinitionImpl(templateId,
				templateContents);

		put(templateId, td);
		
		addToHTMLBody(td);

		return td;
	}

	private static void addToHTMLBody(TemplateDefinition td) { //TODO: effects of this method don't survive page-refreshing
		Element templateElement = Elements.create("template");
		templateElement.setAttribute("id", td.getId());
		templateElement.setInnerHtml(td.getInnerHTML());
		
		Document root = ((UIWithRootDocument)UI.getCurrent()).getDocumentRoot();
		root.appendChild(templateElement);
	}

	private static void put(String templateId, TemplateDefinition td) {
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

	private static TemplateDefinition get(String templateId) {
		TemplateDefinitionMap map = VaadinSession.getCurrent().getAttribute(TemplateDefinitionMap.class);
		if(map == null) {
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

	private static String read(String fileName, ClassLoader cl) {
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
