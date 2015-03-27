package com.vaadin.components.grid.config;

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
    JSStaticCell setContent(Object content);

    @JsProperty
    int getColspan();

    @JsProperty
    JSStaticCell setColspan(int colspan);

    @JsProperty
    String getClassName();

    @JsProperty
    JSStaticCell setClassName(String s);
}
