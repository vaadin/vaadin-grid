package com.vaadin.components.grid.config;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;

/**
 * This class is a JsInterop object for the JS object representing
 * a grid column configuration.
 */
@JsType
public interface JSColumn {
    @JsProperty
    Object content();
    @JsProperty
    JSColumn setContent(Object o);
    @JsProperty
    String type();
    @JsProperty
    JSColumn setType(String s);
    @JsProperty
    JavaScriptObject renderer();
    @JsProperty
    JSColumn setRenderer(JavaScriptObject o);
    @JsProperty
    Object value();
    @JsProperty
    JSColumn setValue(Object o);
    @JsProperty
    JSArray<JSHeaderCell> headerData();
    @JsProperty
    JSColumn setHeaderData(JSArray<JSHeaderCell> headers);
    @JsProperty
    String template();
    @JsProperty
    JSColumn setTemplate(String template);
}
