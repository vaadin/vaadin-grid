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
    String getName();

    @JsProperty
    void setName(String s);

    @JsProperty
    String getMinWidth();

    @JsProperty
    void setMinWidth(String s);

    @JsProperty
    String getMaxWidth();

    @JsProperty
    void setMaxWidth(String s);

    @JsProperty
    String getWidth();

    @JsProperty
    void setWidth(String s);

    @JsProperty
    int getFlex();

    @JsProperty
    void setFlex(int i);

    @JsProperty
    boolean getSortable();

    @JsProperty
    void setSortable(boolean b);

    @JsProperty
    boolean getReadOnly();

    @JsProperty
    void setReadOnly(boolean b);

    @JsProperty
    JavaScriptObject getRenderer();

    @JsProperty
    void setRenderer(JavaScriptObject o);

    @JsProperty
    JavaScriptObject getGeneratedValue();

    @JsProperty
    void setGeneratedValue(JavaScriptObject o);

    @JsProperty
    String getHeaderHtml();

    @JsProperty
    void setHeaderHtml(String s);
}
