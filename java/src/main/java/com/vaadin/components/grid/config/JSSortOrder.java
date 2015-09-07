package com.vaadin.components.grid.config;

import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;

/**
 * This class is a JsInterop wrapper for the JS object representing a grid sort
 * order.
 */
@JsType
public interface JSSortOrder {
    @JsProperty
    int getColumn();

    @JsProperty
    void setColumn(int column);

    @JsProperty
    String getDirection();

    @JsProperty
    void setDirection(String direction);
}
