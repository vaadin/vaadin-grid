package com.vaadin.components.grid.data;

import com.google.gwt.core.client.js.JsExport;
import com.google.gwt.core.client.js.JsNamespace;
import com.google.gwt.core.client.js.JsType;
import com.vaadin.client.data.AbstractRemoteDataSource;
import com.vaadin.components.common.js.JS;
import com.vaadin.components.common.js.JSArray;
import com.vaadin.components.grid.GridComponent;

@JsNamespace(JS.VAADIN_JS_NAMESPACE)
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
        gridComponent.getSelectionModel().reset();
    }

    public void dataUpdated(int firstIndex, JSArray<Object> itemsData) {
        setRowData(firstIndex, itemsData.asList());
        gridComponent.getSelectionModel().reset();
    }

    public void dataRemoved(int firstRowIndex, int count) {
        removeRowData(firstRowIndex, count);
        gridComponent.getSelectionModel().reset();
    }

    public void dataAdded(int firstRowIndex, int count) {
        insertRowData(firstRowIndex, count);
        gridComponent.getSelectionModel().reset();
    }

    public void resetData(int newSize) {
        resetDataAndSize(newSize);
        gridComponent.getSelectionModel().reset();
    }

    @Override
    protected void resetDataAndSize(int newSize) {
        super.resetDataAndSize(newSize);

        // If selected rows contains values that are out of bounds, remove them.
        JSArray<Double> selectedRows = gridComponent.getSelectedRows().cast();
        boolean changed = false;
        for (int i = 0; i < selectedRows.length(); i++) {
            if (selectedRows.get(i) >= size) {
                selectedRows.remove(selectedRows.get(i--));
                changed = true;
            }
        }
        if (changed) {
            gridComponent.setSelectedRows(selectedRows.cast());
        }
    }

}
