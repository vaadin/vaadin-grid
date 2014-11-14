package com.vaadin.prototype.webcomponentwrapper.template;

import com.vaadin.prototype.webcomponentwrapper.element.Element;

public class TemplateInstanceImpl<E extends Element> implements TemplateInstance<E> {
    
    private E element;
    
    public TemplateInstanceImpl(E element) {
        this.element = element;
    }

    @Override
    public E getElement() {
        return element;
    }

}
