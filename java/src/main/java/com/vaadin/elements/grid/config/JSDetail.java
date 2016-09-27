package com.vaadin.elements.grid.config;

import com.vaadin.elements.common.js.JS;

import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

/**
 * This class is a JsInterop wrapper for the JS object representing detailed event.
 */
@JsType(namespace = JS.NAMESPACE_API)
public class JSDetail {
    @JsProperty
    public double row;
    @JsProperty
    public double column;
    @JsProperty
    public String section;
    @JsProperty
    public Object data;
}
