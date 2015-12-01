package com.vaadin.elements.grid.config;

import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

/**
 * This class is a JsInterop wrapper for the JS object representing a grid
 * header/footer cell configuration
 */
@JsType(isNative=true)
public interface JSStaticCell {
    @JsProperty Object getContent();

    @JsProperty void setContent(Object content);

    @JsProperty int getColspan();

    @JsProperty void setColspan(int colspan);

    @JsProperty String getClassName();

    @JsProperty void setClassName(String s);
}
