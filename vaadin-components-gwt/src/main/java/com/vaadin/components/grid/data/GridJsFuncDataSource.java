package com.vaadin.components.grid.data;

import static com.google.gwt.query.client.GQuery.$;
import static com.google.gwt.query.client.GQuery.console;
import static com.google.gwt.query.client.GQuery.window;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayMixed;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.js.JsUtils;
import com.vaadin.components.grid.GridComponent;

/**
 * Datasource where requestRows() is delegated to a js native function
 */
public class GridJsFuncDataSource extends GridDataSource {

    private JavaScriptObject jsFunction;

    public GridJsFuncDataSource(JavaScriptObject jso, int rows, GridComponent grid) {
        super(grid);
        assert JsUtils.isFunction(jso);
        jsFunction = jso;
        size = rows;
    }

    @Override
    protected void requestRows(
            final int firstRowIndex,
            final int numberOfRows,
            final com.vaadin.client.data.AbstractRemoteDataSource.RequestRowsCallback<JsArrayMixed> callback) {

        JavaScriptObject jsCallback = JsUtils.wrapFunction(new Function() {
            public void f() {
                JsArrayMixed result = arguments(0);
                setRowData(firstRowIndex, result);
            }
        });

        // When we call the native function, it could return the array of rows directly
        // or via the provided callback function.
        JsArrayMixed result = JsUtils.jsni(jsFunction, "call", jsFunction, firstRowIndex, numberOfRows, jsCallback);
        if (result != null) {
            setRowData(firstRowIndex, result);
        }
    }

    @Override
    public Object getRowKey(JsArrayMixed row) {
        return JsUtils.JSON2String(row);
    }
}
