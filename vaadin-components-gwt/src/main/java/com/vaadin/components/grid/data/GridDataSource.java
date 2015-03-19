package com.vaadin.components.grid.data;

import com.google.gwt.core.client.js.JsExport;
import com.google.gwt.core.client.js.JsNamespace;
import com.google.gwt.core.client.js.JsType;
import com.vaadin.client.data.AbstractRemoteDataSource;
import com.vaadin.components.common.util.Elements;
import com.vaadin.components.grid.GridComponent;
import com.vaadin.components.common.js.JSArray;

@JsNamespace(Elements.VAADIN_JS_NAMESPACE)
@JsExport
@JsType
public abstract class GridDataSource extends AbstractRemoteDataSource<Object> {
    protected int size = 1;

    protected final GridComponent gridComponent;

    public GridDataSource(GridComponent gridComponent) {
        this.gridComponent = gridComponent;
    }

    @Override
    public Object getRowKey(Object row) {
        return row;
    }

    @Override
    public int size() {
        return size;
    }

    public void refresh() {
        resetDataAndSize(size());
    }

    public void dataUpdated(int firstIndex, JSArray<Object> itemsData) {
        setRowData(firstIndex, itemsData.asList());
    }

    public void dataRemoved(int firstRowIndex, int count) {
        removeRowData(firstRowIndex, count);
    }

    public void dataAdded(int firstRowIndex, int count) {
        insertRowData(firstRowIndex, count);
    }

    public void resetData(int newSize) {
        resetDataAndSize(newSize);
    }

}
