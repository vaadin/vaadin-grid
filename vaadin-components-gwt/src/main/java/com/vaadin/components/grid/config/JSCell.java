package com.vaadin.components.grid.config;

import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.dom.client.Element;

/**
 * This class is a JsInterop wrapper for the JS object representing a cell
 * object passed to cell style generator.
 */
@JsType
public interface JSCell {
    @JsProperty
    String getColumnName();

    @JsProperty
    void setColumnName(String columnName);

    @JsProperty
    Element getElement();

    @JsProperty
    void setElement(Element element);

    @JsProperty
    JSRow getRow();

    @JsProperty
    void setRow(JSRow row);

}
