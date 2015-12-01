package com.vaadin.elements.grid.config;

import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;


/**
 * This class is a JsInterop wrapper for the JS object representing a grid sort
 * order.
 */
@JsType(isNative=true)
public interface JSSortOrder {
    @JsProperty int getColumn();

    @JsProperty void setColumn(int column);

    @JsProperty String getDirection();

    @JsProperty void setDirection(String direction);
}
