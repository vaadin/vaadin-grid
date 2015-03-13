package com.vaadin.components.grid.config;

import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.dom.client.Element;

/**
 * This class is a JsInterop wrapper for the JS object representing a row object
 * passed to row style generators.
 */
@JsType
public interface JSRow {
    @JsProperty
    int getIndex();

    @JsProperty
    void setIndex(int index);

    @JsProperty
    Object getData();

    @JsProperty
    void setData(Object data);

    @JsProperty
    Element getGrid();

    @JsProperty
    void setGrid(Element grid);

    @JsProperty
    Element getElement();

    @JsProperty
    void setElement(Element element);

}
