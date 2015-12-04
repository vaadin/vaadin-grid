package com.vaadin.elements.grid.config;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.google.gwt.query.client.js.JsUtils;
import com.vaadin.client.widget.grid.RowReference;
import com.vaadin.elements.common.js.JS;
import com.vaadin.elements.grid.data.GridDataSource;

public interface JSRow {

    static JavaScriptObject create(RowReference<Object> row, Element container) {
        JavaScriptObject jsRow = JS.createJsObject();
        JS.definePropertyAccessors(jsRow, "index", null,
                () -> row.getRowIndex());
        JS.definePropertyAccessors(jsRow, "data", null,
                () -> GridDataSource.extractDataItem(row.getRow()));
        JS.definePropertyAccessors(jsRow, "element", null,
                () -> row.getElement());
        JsUtils.prop(jsRow, "grid", container);

        return jsRow;
    }

}
