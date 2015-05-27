package com.vaadin.components.grid.data;

import java.util.List;

import com.google.gwt.core.client.js.JsExport;
import com.google.gwt.core.client.js.JsNamespace;
import com.google.gwt.core.client.js.JsNoExport;
import com.google.gwt.core.client.js.JsType;
import com.vaadin.client.data.AbstractRemoteDataSource;
import com.vaadin.client.widgets.Grid.Column;
import com.vaadin.components.common.js.JS;
import com.vaadin.components.common.js.JSValidate;
import com.vaadin.components.grid.GridComponent;
import com.vaadin.components.grid.table.GridColumn;
import com.vaadin.shared.ui.grid.Range;

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
                boolean wasUpdating = gridComponent.updating;
                gridComponent.updating = true;
                gridComponent.getGrid().setDataSource(this);
                gridComponent.updating = wasUpdating;
            } else if (heightByRows) {
                gridComponent.redraw(true);
            }
        }
    }

    public void refresh() {
        resetDataAndSize(size());
        gridComponent.getSelectionModel().reset();
    }

    public void clearCache(Double newSize) {
        resetDataAndSize(JSValidate.Integer.val(newSize, size, size));
        gridComponent.getSelectionModel().reset();
    }

    @Override
    protected void resetDataAndSize(int newSize) {
        super.resetDataAndSize(newSize);

        gridComponent.getSelectionModel().dataSizeUpdated(newSize);

    }
}
