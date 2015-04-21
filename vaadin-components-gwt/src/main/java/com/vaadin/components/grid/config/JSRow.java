package com.vaadin.components.grid.config;

import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.dom.client.Element;
import com.google.gwt.query.client.Function;
import com.vaadin.client.widget.grid.RowReference;
import com.vaadin.components.common.js.JS;

/**
 * This class is a JsInterop wrapper for the JS object representing a row object
 * passed to row style generators.
 */
@JsType
public interface JSRow {

    static JSRow create(RowReference<Object> row, Element container) {
        JSRow jsRow = JS.createJsType(JSRow.class);
        JS.definePropertyAccessors(jsRow, "index", null, new Function() {
            @Override
            public Object f(Object... args) {
                return row.getRowIndex();
            }
        });
        JS.definePropertyAccessors(jsRow, "data", null, new Function() {
            @Override
            public Object f(Object... args) {
                return row.getRow();
            }
        });
        JS.definePropertyAccessors(jsRow, "element", null, new Function() {
            @Override
            public Object f(Object... args) {
                return row.getElement();
            }
        });
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
