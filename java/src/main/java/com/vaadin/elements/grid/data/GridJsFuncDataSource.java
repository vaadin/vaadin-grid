package com.vaadin.elements.grid.data;

import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.js.JsUtils;
import com.vaadin.elements.common.js.JS;
import com.vaadin.elements.grid.GridElement;
import com.vaadin.elements.grid.config.JSDataRequest;

/**
 * Datasource where requestRows() is delegated to a js native function
 */
public class GridJsFuncDataSource extends GridDataSource {

    private JavaScriptObject jsFunction;
    private boolean initialRowSetReceived;

    public GridJsFuncDataSource(JavaScriptObject jso, GridElement grid) {
        super(grid);
        assert JsUtils.isFunction(jso);
        jsFunction = jso;

        // Grid size might be 0 so we'll check it here and make an initial empty
        // data request to query for the size iff no size is given.
        Scheduler.get().scheduleFinally(() -> {
            if (size() == 0) {
                refreshItems();
            }
        });
    }

    public void setJSFunction(JavaScriptObject jso) {
        jsFunction = jso;
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
        JsUtils.jsni(jsFunction, "call", jsFunction, jsDataRequest,
                wrapCallback(callback));
    }

    private JavaScriptObject wrapCallback(
            final RequestRowsCallback<Object> callback) {
        return JsUtils.wrapFunction(new Function() {
            @Override
            public void f() {
                List<Object> list = JS.asList(arguments(0));
                Double totalSize = arguments(1);

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
            }
        });
    }

}
