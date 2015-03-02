package com.vaadin.components.grid.head;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayMixed;
import com.google.gwt.query.client.Properties;
import com.google.gwt.query.client.js.JsUtils;
import com.vaadin.client.widgets.Grid;
import com.vaadin.components.grid.config.JSColumn;

/**
 * Column configuration for Grid based on a JSColumn object.
 */
class GridColumn extends Grid.Column<Object, JsArrayMixed> {

    private final JSColumn gColumn;
    private final int idx;

    public GridColumn(int i, JSColumn col) {
        super(new GridRenderer(col));
        this.gColumn = col;
        this.idx = i;
    }

    public Object getValue(JsArrayMixed row) {
        Object o = gColumn.value();
        if (o instanceof JavaScriptObject
                && JsUtils.isFunction((JavaScriptObject) o)) {
            o = JsUtils.jsni((JavaScriptObject) o, "call", o, row, idx);
        } else if (o instanceof String
                // For some reason JsInterop returns empty
                && "" != o) {
            o = JsUtils.prop(row, o);
        } else {
            if (JsUtils.isArray(row)) {
                o = row.getObject(idx);
            } else {
                Properties p = row.cast();
                o = p.getObject(p.keys()[idx]);
            }
        }
        return o;
    }
}