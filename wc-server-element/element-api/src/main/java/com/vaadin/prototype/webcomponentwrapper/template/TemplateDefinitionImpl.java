package com.vaadin.prototype.webcomponentwrapper.template;

import com.vaadin.prototype.webcomponentwrapper.element.Element;
import com.vaadin.prototype.webcomponentwrapper.element.Elements;

class TemplateDefinitionImpl<E extends Element> implements
        TemplateDefinition<E> {
    private String tag;
    private String html;
    private Class<E> elementClass;

    public TemplateDefinitionImpl(String tag, String htmlContent,
            Class<E> elementClass) {
        this.tag = tag;
        this.html = htmlContent;
        this.elementClass = elementClass;
    }

    public String getTag() {
        return tag;
    }

    public String getHtml() {
        return html;
    }

    public Class<E> getElementClass() {
        return elementClass;
    }

    @Override
    public TemplateInstance<E> instantiate() {
        TemplateInstance<E> ti = new TemplateInstanceImpl<>(this);
        return ti;
    }

}
