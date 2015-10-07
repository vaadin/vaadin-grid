package com.vaadin.components.grid.data;

import java.util.Collections;
import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.js.JsUtils;
import com.vaadin.components.common.js.JS;
import com.vaadin.components.grid.GridComponent;
import com.vaadin.components.grid.config.JSDataRequest;

/**
 * Datasource where requestRows() is delegated to a js native function
 */
public class GridJsFuncDataSource extends GridDataSource {

    private JavaScriptObject jsFunction;
    private boolean initialRowSetReceived;

    public GridJsFuncDataSource(JavaScriptObject jso, GridComponent grid) {
        super(grid);
        assert JsUtils.isFunction(jso);
        jsFunction = jso;
        // We need to do a first query to DB in order to get the initial size
        // and then attach the data-source to the grid, otherwise the grid will
        // never call the requestRows method when size is zero.
        doRequest(0, 0, null);
    }

    public void setJSFunction(JavaScriptObject jso) {
        jsFunction = jso;
        clearCache(null);
        gridComponent.getSelectionModel().reset();
    }

    @Override
    protected void requestRows(final int firstRowIndex, final int numberOfRows,
            final RequestRowsCallback<Object> callback) {

        doRequest(firstRowIndex, numberOfRows, callback);
    }

    private void doRequest(int idx, int count, Object cb) {
        JSDataRequest jsDataRequest = JS.createJsType(JSDataRequest.class);
        jsDataRequest.setIndex(idx);
        jsDataRequest.setCount(count);
        jsDataRequest.setSortOrder(JsUtils.prop(gridComponent.getContainer(), "sortOrder"));
        jsDataRequest.setSuccess(JsUtils.wrapFunction(new Function() {
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

                if (cb != null) {
                    ((RequestRowsCallback) cb).onResponse(list, size());
                }

                gridComponent.setLoadingDataClass(false);

                if (!initialRowSetReceived && !list.isEmpty()) {
                    initialRowSetReceived = true;
                    gridComponent.updateWidth();
                }
            }
        }));
        if (cb != null) {
            jsDataRequest.setFailure(JsUtils.wrapFunction(new Function() {
                @Override
                public void f() {
                    ((RequestRowsCallback) cb).onResponse(
                            Collections.emptyList(), size());
                    gridComponent.setLoadingDataClass(false);
                }
            }));
        }
        gridComponent.setLoadingDataClass(true);
        JS.exec(jsFunction, jsDataRequest);
    }
}
