package com.vaadin.components.grid.data;

import com.google.gwt.core.client.js.JsExport;
import com.google.gwt.core.client.js.JsNamespace;
import com.google.gwt.core.client.js.JsNoExport;
import com.google.gwt.core.client.js.JsType;
import com.vaadin.client.data.AbstractRemoteDataSource;
import com.vaadin.components.common.js.JS;
import com.vaadin.components.common.js.JSArray;
import com.vaadin.components.grid.GridComponent;

@JsNamespace(JS.VAADIN_JS_NAMESPACE)
@JsExport
@JsType
public abstract class GridDataSource extends AbstractRemoteDataSource<Object> {
    private int size = 0;
    private final int defaultRows;

    protected final GridComponent gridComponent;

    public GridDataSource(GridComponent gridComponent) {
        this.gridComponent = gridComponent;
        defaultRows = gridComponent.defaultHeightByRows;
    }

    @Override
    public Object getRowKey(Object row) {
        return row;
    }

    @Override
    public int size() {
        return size;
    }

    @JsNoExport
    public void size(int nsize) {
        if (nsize != size) {
            boolean isEmpty = size == 0;
            boolean heightByRows = nsize < defaultRows || size < defaultRows;
            size = nsize;
            if (isEmpty) {
                // Grid stops calling requestRows when size is 0, if
                // size changes we have to re-attach the data-source so
                // as grid starts calling requestRows again
                gridComponent.getGrid().setDataSource(this);
            } else if (heightByRows) {
                gridComponent.redraw(true);
            }
        }
    }

    public void refresh() {
        resetDataAndSize(size());
        gridComponent.getSelectionModel().reset();
    }

    public void dataUpdated(int firstIndex, JSArray<Object> itemsData) {
        setRowData(firstIndex, itemsData.asList());
        gridComponent.getSelectionModel().reset();
        refresh();
        gridComponent.redraw(false);
    }

    public void dataRemoved(int firstRowIndex, int count) {
        removeRowData(firstRowIndex, count);
        gridComponent.getSelectionModel().reset();
        refresh();
        gridComponent.redraw(true);
    }

    public void dataAdded(int firstRowIndex, int count) {
        // cover the case when size was 0
        size(size() + count);
        insertRowData(firstRowIndex, count);
        gridComponent.getSelectionModel().reset();
        refresh();
        gridComponent.redraw(true);
    }

    public void resetData(int newSize) {
        resetDataAndSize(newSize);
        gridComponent.getSelectionModel().reset();
    }

    @Override
    protected void resetDataAndSize(int newSize) {
        super.resetDataAndSize(newSize);

        gridComponent.getSelectionModel().dataSizeUpdated(newSize);

    }
}
