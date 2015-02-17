package com.vaadin.components.grid.data;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsArrayMixed;
import com.google.gwt.query.client.Properties;
import com.google.gwt.query.client.js.JsUtils;
import com.vaadin.client.data.AbstractRemoteDataSource;
import com.vaadin.components.grid.GridComponent;
import com.vaadin.components.grid.config.JS;
import com.vaadin.components.grid.config.JSArray;
import com.vaadin.components.grid.config.JSColumn;
import com.vaadin.components.grid.config.JSHeaderCell;
import com.vaadin.components.grid.config.JSHeaderCell.Format;

public abstract class GridDataSource extends
        AbstractRemoteDataSource<JsArrayMixed> {
    protected int size = 300;

    protected final GridComponent wcGrid;

    public GridDataSource(GridComponent grid) {
        this.wcGrid = grid;
    }

    private JSArray<JSColumn> configColumnsFromFirstDataRow(Properties p) {
        JSArray<JSColumn> colList = JS.createArray();
        for (String k : p.keys()) {
            JSArray<JSHeaderCell> l = JS.createArray();
            l.add(JS.createJsType(JSHeaderCell.class).setContent(k)
                    .setFormat(Format.HTML.name()).setColSpan(1));

            colList.add(JS.createJsType(JSColumn.class).setValue(k)
                    .setHeaderData(l));
        }
        if (wcGrid != null) {
            wcGrid.setCols(colList);
            wcGrid.initGrid();
        }
        return colList;
    }

    protected void setRowData(int idx, JavaScriptObject array) {
        super.setRowData(idx, JS.<JsArrayMixed> asList(array));
    }

    @Override
    public Object getRowKey(JsArrayMixed row) {
        return row;
    }

    @Override
    public int size() {
        return size;
    }

    public void refresh() {
        resetDataAndSize(size());
    }

    private native JavaScriptObject slice(JsArray<JavaScriptObject> data,
            int idx, int count) /*-{
                                return data.slice(idx, idx + count);
                                }-*/;

    protected JSArray<JSColumn> setRowDataFromJs(final int idx, int count,
            JSArray<JSColumn> cols, JsArray<JavaScriptObject> data) {
        if (data.length() > 0) {
            if (!JsUtils.isArray(data.get(0))
                    && (cols == null || cols.isEmpty())) {
                cols = configColumnsFromFirstDataRow(data.get(0)
                        .<Properties> cast());
            }
            setRowData(idx, slice(data, idx, count));
        }
        return cols;
    }
}
