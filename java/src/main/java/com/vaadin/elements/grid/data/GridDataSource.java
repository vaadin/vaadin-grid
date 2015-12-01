package com.vaadin.elements.grid.data;

import java.util.List;

import jsinterop.annotations.JsIgnore;
import jsinterop.annotations.JsType;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.js.JsNoExport;
import com.google.gwt.query.client.js.JsUtils;
import com.vaadin.client.data.AbstractRemoteDataSource;
import com.vaadin.elements.common.js.JS;
import com.vaadin.elements.common.js.JSValidate;
import com.vaadin.elements.grid.GridElement;
import com.vaadin.shared.ui.grid.Range;

@JsType(namespace = JS.VAADIN_JS_NAMESPACE + ".grid._api")
public abstract class GridDataSource extends AbstractRemoteDataSource<Object> {

    protected final GridElement gridElement;

    public GridDataSource(GridElement gridElement) {
        this.gridElement = gridElement;
    }

    @Override
    public Object getRowKey(Object row) {
        return row;
    }

    @Override
    public int size() {
        Object size = JsUtils.prop(gridElement.getContainer(), "size");
        return JSValidate.Integer.val(size, 0, 0);
    }

    @JsIgnore
    public void setSize(int size) {
        JsUtils.prop(gridElement.getContainer(), "size", size);
    }

    @JsNoExport
    public void refresh() {
        resetDataAndSize(size());
        gridElement.getSelectionModel().reset();
    }

    public void refreshItems() {
        Range range = getCachedRange();
        requestRows(range.getStart(), range.length(),
                new RequestRowsCallback<Object>(this, range) {
                });
    }

    @Override
    protected void resetDataAndSize(int newSize) {
        super.resetDataAndSize(newSize);

        gridElement.getSelectionModel().dataSizeUpdated(newSize);

    }

    public void getItem(Double rowIndex, JavaScriptObject callback,
            boolean onlyCached) {
        Integer index = JSValidate.Integer.val(rowIndex, -1, -1);
        if (index >= 0 && index < size()) {
            Object row = getRow(index);
            if (row != null) {
                JsUtils.jsni(callback, "call", callback, JS.getUndefined(),
                        extractDataItem(row));
            } else if (onlyCached) {
                JS.exec(callback,
                        JS.getError("Unable to retrieve row #" + index
                                + ", it has not been cached yet"));
            } else {
                Range range = Range.withOnly(index);
                requestRows(range.getStart(), range.length(),
                        new RequestRowsCallback<Object>(this, range) {
                            @Override
                            public void onResponse(List<Object> rowData,
                                    int totalSize) {
                                JsUtils.jsni(callback, "call", callback,
                                        JS.getUndefined(), rowData.get(0));
                            }
                        });
            }
        } else {
            JS.exec(callback,
                    JS.getError("Index value #" + index + " is out of range"));
        }
    }

    public static Object extractDataItem(Object itemOrContainer) {
        if (itemOrContainer instanceof DataItemContainer) {
            return ((DataItemContainer) itemOrContainer).getDataItem();
        }
        return itemOrContainer;
    }

    @JsNoExport
    @Override
    public void insertRowData(int firstRowIndex, int count) {
        super.insertRowData(firstRowIndex, count);
    }

    @JsNoExport
    @Override
    public void removeRowData(int firstRowIndex, int count) {
        // super.removeRowData(firstRowIndex, count);

        // FIXME: For some bloody reason the size needs to be reduced in small
        // batches instead or else we'll get an exception
        int tmpCount = count;
        while (tmpCount > 0) {
            int amount = Math.min(tmpCount, 10);
            tmpCount -= amount;
            super.removeRowData(firstRowIndex + tmpCount, amount);
        }
    }
}
