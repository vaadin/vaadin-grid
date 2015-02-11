package com.vaadin.components.grid.data;

import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.query.client.js.JsUtils;
import com.vaadin.components.grid.GridComponent;
import com.vaadin.components.grid.data.GridData.GridColumn;

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
        List<GridColumn> cols = wcGrid.getCols();
        setRowDataFromJs(firstRowIndex, numberOfRows, cols, jso);
    }

}
