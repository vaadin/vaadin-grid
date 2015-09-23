package com.vaadin.components.grid.config;

import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.dom.client.Element;
import com.vaadin.client.widget.grid.RowReference;
import com.vaadin.components.common.js.JS;
import com.vaadin.components.grid.data.GridDataSource;

/**
 * This class is a JsInterop wrapper for the JS object representing a row object
 * passed to row style generators.
 */
@JsType
public interface JSRow {

    static JSRow create(RowReference<Object> row, Element container) {
        JSRow jsRow = JS.createJsType(JSRow.class);
        JS.definePropertyAccessors(jsRow, "index", null,
                () -> row.getRowIndex());
        JS.definePropertyAccessors(jsRow, "data", null,
                () -> GridDataSource.extractDataItem(row.getRow()));
        JS.definePropertyAccessors(jsRow, "element", null,
                () -> row.getElement());
        jsRow.setGrid(container);
        return jsRow;
    }

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
