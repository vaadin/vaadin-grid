package com.vaadin.components.grid.data;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.query.client.js.JsUtils;
import com.vaadin.components.grid.GridComponent;

public class GridJsObjectDataSource extends GridDataSource {

    private final JsArray<JavaScriptObject> jso;

    public GridJsObjectDataSource(JsArray<JavaScriptObject> jso, GridComponent grid) {
        super(grid);
        assert JsUtils.isArray(jso);
        this.jso = jso;
        size = jso.length();
    }

    @Override
    protected void requestRows(
            int firstRowIndex,
            int numberOfRows,
            com.vaadin.client.data.AbstractRemoteDataSource.RequestRowsCallback<com.google.gwt.core.client.JsArrayMixed> callback) {
        setRowDataFromJs(firstRowIndex, numberOfRows, wcGrid.getColumns(), jso);
    }

}
