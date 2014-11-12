package com.vaadin.prototype.webcomponentwrapper.template;

import com.vaadin.prototype.webcomponentwrapper.element.Element;
import com.vaadin.prototype.webcomponentwrapper.element.Elements;

public class SimpleTemplateInstance implements TemplateInstance<Element> {

    private Element myElement;

    public SimpleTemplateInstance(String templateId) {
        myElement = Elements.create("v-template-instance");
        myElement.setAttribute("template-id", templateId);
    }

    @Override
    public Element getElement() {
        return myElement;
    }

}
