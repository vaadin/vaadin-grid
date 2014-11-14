package com.vaadin.prototype.webcomponentwrapper.template;

import com.vaadin.prototype.webcomponentwrapper.element.Element;
import com.vaadin.prototype.webcomponentwrapper.element.Elements;

class TemplateDefinitionImpl<E extends Element> implements
        TemplateDefinition<E> {
    private String tag;
    private String htmlContent;
    private Class<E> elementClass;

    public TemplateDefinitionImpl(String tag, String htmlContent,
            Class<E> elementClass) {
        this.tag = tag;
        this.htmlContent = htmlContent;
        this.elementClass = elementClass;
    }

    public String getTag() {
        return tag;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public Class<E> getElementClass() {
        return elementClass;
    }

    @Override
    public TemplateInstance<E> instantiate() {
        E element = null;
        if(elementClass.equals(Element.class)) {
            element = Elements.create(tag);
        } else {
            element = Elements.create(elementClass);
        }
        
        TemplateInstance<E> ti = new TemplateInstanceImpl<>(element);
        return ti;
    }

}
