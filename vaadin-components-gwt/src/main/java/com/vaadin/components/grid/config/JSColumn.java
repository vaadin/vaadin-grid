package com.vaadin.components.grid.config;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;

/**
 * This class is a JsInterop object for the JS object representing a grid column
 * configuration.
 */
@JsType
public interface JSColumn {

    @JsProperty
    String name();

    @JsProperty
    JSColumn setName(String s);

    @JsProperty
    String minWidth();

    @JsProperty
    JSColumn setMinWidth(String s);

    @JsProperty
    String maxWidth();

    @JsProperty
    JSColumn setMaxWidth(String s);

    @JsProperty
    String width();

    @JsProperty
    JSColumn setWidth(String s);

    @JsProperty
    int flex();

    @JsProperty
    JSColumn setFlex(int i);

    @JsProperty
    boolean sortable();

    @JsProperty
    JSColumn setSortable(boolean b);

    @JsProperty
    boolean readOnly();

    @JsProperty
    JSColumn setReadOnly(boolean b);

    @JsProperty
    JavaScriptObject renderer();

    @JsProperty
    JSColumn setRenderer(JavaScriptObject o);

    @JsProperty
    JavaScriptObject generatedValue();

    @JsProperty
    JSColumn setGeneratedValue(JavaScriptObject o);

    @JsProperty
    String headerHtml();

    @JsProperty
    JSColumn setHeaderHtml(String s);
}
