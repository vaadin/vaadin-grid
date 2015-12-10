package com.vaadin.elements.grid.data;

import java.util.List;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.query.client.js.JsUtils;
import com.vaadin.elements.common.js.JS;
import com.vaadin.elements.common.js.JSArray;
import com.vaadin.elements.common.js.JSFunction2;
import com.vaadin.elements.grid.GridElement;
import com.vaadin.elements.grid.config.JSDataRequest;

/**
 * Datasource where requestRows() is delegated to a js native function
 */
public class GridJsFuncDataSource extends GridDataSource {

    private JSFunction2<JSDataRequest, JSFunction2<JSArray<?>, Double>> jsFunction;
    private boolean initialRowSetReceived;

    public GridJsFuncDataSource(
            JSFunction2<JSDataRequest, JSFunction2<JSArray<?>, Double>> jsFunction,
            GridElement grid) {
        super(grid);
        this.jsFunction = jsFunction;

        // Grid size might be 0 so we'll check it here and make an initial empty
        // data request to query for the size iff no size is given.
        Scheduler.get().scheduleFinally(() -> {
            if (size() == 0) {
                refreshItems();
            }
        });
    }

    public void setJSFunction(
            JSFunction2<JSDataRequest, JSFunction2<JSArray<?>, Double>> jsFunction) {
        this.jsFunction = jsFunction;
        refreshItems();
        gridElement.getSelectionModel().reset();
    }

    @Override
    protected void requestRows(final int firstRowIndex, final int numberOfRows,
            final RequestRowsCallback<Object> callback) {

        JSDataRequest jsDataRequest = JS.createJsObject();
        jsDataRequest.setIndex(firstRowIndex);
        jsDataRequest.setCount(numberOfRows);
        jsDataRequest.setSortOrder(JsUtils.prop(gridElement.getContainer(),
                "sortOrder"));

        gridElement.setLoadingDataClass(true);

        jsFunction.f(jsDataRequest, (array, totalSize) -> {
            List<Object> list = JS.asList(array);
            for (int i = 0; i < list.size(); i++) {
                if (JS.isPrimitiveType(list.get(i))) {
                    list.set(i, new DataItemContainer(list.get(i)));
                }
            }

            if (totalSize != null) {
                setSize(totalSize.intValue());
            }
            if (callback != null) {
                callback.onResponse(list, size());
            }

            gridElement.setLoadingDataClass(false);

            if (!initialRowSetReceived && !list.isEmpty()) {
                initialRowSetReceived = true;
                gridElement.updateWidth();
            }
        });
    }
}
