package com.vaadin.prototype.webcomponentwrapper.element.elements;

import com.vaadin.prototype.webcomponentwrapper.element.Element;
import com.vaadin.prototype.webcomponentwrapper.element.Tag;

@Tag("input")
public interface InputElement extends Element {
    void setMaxLength(String maxLength);
}
