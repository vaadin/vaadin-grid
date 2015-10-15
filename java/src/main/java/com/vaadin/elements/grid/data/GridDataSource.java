package com.vaadin.elements.grid.data;

import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.js.JsExport;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.core.client.js.JsNamespace;
import com.google.gwt.core.client.js.JsNoExport;
import com.google.gwt.query.client.js.JsUtils;
import com.vaadin.client.data.AbstractRemoteDataSource;
import com.vaadin.elements.common.js.JS;
import com.vaadin.elements.common.js.JSValidate;
import com.vaadin.elements.grid.GridElement;
import com.vaadin.shared.ui.grid.Range;

@JsNamespace(JS.VAADIN_JS_NAMESPACE + ".grid._api")
@JsExport
@JsType
public abstract class GridDataSource extends AbstractRemoteDataSource<Object> {
    private int size = 0;

    protected final GridElement gridComponent;

    public GridDataSource(GridElement gridComponent) {
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

    @JsNoExport
    public void setSize(int nsize) {
        if (nsize != size) {
            boolean isEmpty = size == 0;
            size = nsize;
            if (isEmpty) {
                // Grid stops calling requestRows when size is 0, if
                // size changes we have to re-attach the data-source so
                // as grid starts calling requestRows again
                boolean wasUpdating = gridComponent.updating;
                gridComponent.updating = true;
                gridComponent.getGrid().setDataSource(this);
                gridComponent.updating = wasUpdating;
            }

            gridComponent.updateHeight();
        }
    }

    public void refresh() {
        resetDataAndSize(size());
        gridComponent.getSelectionModel().reset();
    }

    public void clearCache(Double newSize) {
        Integer intSize = JSValidate.Integer.val(newSize, size, size);
        if (intSize == size || size == 0) {
            Range range = getCachedRange();
            requestRows(range.getStart(), range.length(),
                    new RequestRowsCallback<Object>(this, range) {
                    });
        } else {
            resetDataAndSize(intSize);
        }
        gridComponent.getSelectionModel().reset();
    }

    @Override
    protected void resetDataAndSize(int newSize) {
        super.resetDataAndSize(newSize);

        gridComponent.getSelectionModel().dataSizeUpdated(newSize);

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
}
