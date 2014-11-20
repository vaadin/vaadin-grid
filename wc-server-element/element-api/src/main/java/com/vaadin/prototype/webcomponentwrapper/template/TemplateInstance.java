package com.vaadin.prototype.webcomponentwrapper.template;

import com.vaadin.prototype.webcomponentwrapper.element.Element;

public interface TemplateInstance<E extends Element> {

    E getElement();
    <EL extends Element> EL getElementById(String id);
    void setPropertyValue(String propertyName, String propertyValue);

}
