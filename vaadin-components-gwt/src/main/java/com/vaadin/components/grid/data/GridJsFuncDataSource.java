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

    private final JavaScriptObject jsFunction;

    public GridJsFuncDataSource(JavaScriptObject jso, GridComponent grid) {
        super(grid);
        assert JsUtils.isFunction(jso);
        jsFunction = jso;
    }

    @Override
    protected void requestRows(
            final int firstRowIndex,
            final int numberOfRows,
            final com.vaadin.client.data.AbstractRemoteDataSource.RequestRowsCallback<Object> callback) {

        JSDataRequest jsDataRequest = JS.createJsType(JSDataRequest.class);
        jsDataRequest.setIndex(firstRowIndex);
        jsDataRequest.setCount(numberOfRows);
        jsDataRequest.setSortOrder(gridComponent.getSortOrder());
        // jsDataRequest.setFilterData();
        jsDataRequest.setSuccess(JsUtils.wrapFunction(new Function() {
            @Override
            public void f() {
                List<Object> list = JS.asList(arguments(0));
                for (int i = 0; i < list.size(); i++) {
                    if (JS.isPrimitiveType(list.get(i))) {
                        list.set(i, new DataItemContainer(list.get(i)));
                    }
                }

                Object totalSize = arguments(1);
                if (totalSize != null) {
                    size = ((Double) totalSize).intValue();
                }
                callback.onResponse(list, size);
            }
        }));
        jsDataRequest.setFailure(JsUtils.wrapFunction(new Function() {
            @Override
            public void f() {
                callback.onResponse(Collections.emptyList(), size);
            }
        }));

        JsUtils.jsni(jsFunction, "call", jsFunction, jsDataRequest);
    }

}
