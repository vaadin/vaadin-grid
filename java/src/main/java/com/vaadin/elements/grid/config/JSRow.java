package com.vaadin.elements.grid.config;

import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

import com.google.gwt.dom.client.Element;
import com.vaadin.client.widget.grid.RowReference;
import com.vaadin.elements.common.js.JS;
import com.vaadin.elements.grid.data.GridDataSource;

/**
 * This class is a JsInterop wrapper for the JS object representing a row object
 * passed to row style generators.
 */
@JsType(isNative=true)
public interface JSRow {
    @JsOverlay
    static JSRow create(RowReference<Object> row, Element container) {
        JSRow jsRow = JS.createJsObject();
        jsRow.setIndex(row.getRowIndex());
        jsRow.setData(GridDataSource.extractDataItem(row.getRow()));
        jsRow.setElement(row.getElement());
        jsRow.setGrid(container);
        return jsRow;
    }

    @JsProperty int getIndex();

    @JsProperty void setIndex(int index);

    @JsProperty Object getData();

    @JsProperty void setData(Object data);

    @JsProperty Element getGrid();

    @JsProperty void setGrid(Element grid);

    @JsProperty Element getElement();

    @JsProperty void setElement(Element element);
}
