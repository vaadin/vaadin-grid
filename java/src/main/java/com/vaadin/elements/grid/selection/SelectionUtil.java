package com.vaadin.elements.grid.selection;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.js.JsUtils;
import com.vaadin.client.data.DataSource.RowHandle;
import com.vaadin.client.widgets.Grid;
import com.vaadin.elements.grid.data.GridDataSource;

public final class SelectionUtil {

    private SelectionUtil() {
    }

    static int getRowIndex(Grid<Object> grid, RowHandle<Object> rowHandle) {
        rowHandle.pin();
        int result = getRowIndexByRow(grid, rowHandle.getRow());
        rowHandle.unpin();
        return result;
    }

    static int getRowIndexByRow(Grid<Object> grid, Object row) {
        return ((GridDataSource) grid.getDataSource()).indexOf(row);
    }

    static JavaScriptObject verifyMapper(JavaScriptObject mapper) {
        JavaScriptObject result = mapper;
        if (result == null) {
            // Default mapper
            result = JsUtils.wrapFunction(new Function() {
                @Override
                public Object f(Object... args) {
                    return arguments[0];
                };
            });
        }
        return result;
    }
}
