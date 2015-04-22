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
    double getMinWidth();

    @JsProperty
    void setMinWidth(double d);

    @JsProperty
    double getMaxWidth();

    @JsProperty
    void setMaxWidth(double d);

    @JsProperty
    double getWidth();

    @JsProperty
    void setWidth(double d);

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
    JavaScriptObject getValueGenerator();

    @JsProperty
    void setValueGenerator(JavaScriptObject o);

    @JsProperty
    Object getHeaderContent();

    @JsProperty
    void setHeaderContent(Object o);
}
