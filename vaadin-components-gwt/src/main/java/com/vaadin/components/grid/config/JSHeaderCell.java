package com.vaadin.components.grid.config;

import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
/**
 * This class is a JsInterop wrapper for the JS object representing
 * a grid header cell configuration
 */
@JsType
public interface JSHeaderCell {
    // TODO: revisit JsInterop does not support enumerations yet
    public static enum Format {
        TEXT, HTML, WIDGET
    }

    @JsProperty
    Object content();
    @JsProperty
    JSHeaderCell setContent(Object content);
    @JsProperty
    String format();
    @JsProperty
    JSHeaderCell setFormat(String format);
    @JsProperty
    int colSpan();
    @JsProperty
    JSHeaderCell setColSpan(int colSpans);
}
