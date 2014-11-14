package com.vaadin.prototype.webcomponentwrapper;

import com.vaadin.prototype.webcomponentwrapper.element.Document;
import com.vaadin.prototype.webcomponentwrapper.element.Element;
import com.vaadin.prototype.webcomponentwrapper.template.TemplateDefinition;

/**
 * This interface and its implementations and uses should be considered
 * implementation detail, not a publicly accessible API.
 * 
 * @author mprzepiora
 *
 */
public interface WebComponentUISupport {
    Document getDocumentRoot();

    TemplateDefinition<? extends Element> getTemplateDefinition(String tag);

    void addTemplateDefinition(TemplateDefinition<? extends Element> td);
}
