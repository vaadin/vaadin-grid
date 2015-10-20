package com.vaadin.elements.grid.config;

import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;

/**
 * This class is a JsInterop wrapper for the JS object representing a grid
 * header/footer cell configuration
 */
@JsType
public interface JSStaticCell {
    @JsProperty
    Object getContent();

    @JsProperty
    void setContent(Object content);

    @JsProperty
    int getColspan();

    @JsProperty
    void setColspan(int colspan);

    @JsProperty
    String getClassName();

    @JsProperty
    void setClassName(String s);
}
