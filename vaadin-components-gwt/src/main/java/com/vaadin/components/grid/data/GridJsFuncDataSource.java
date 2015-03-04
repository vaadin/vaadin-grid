package com.vaadin.components.grid.data;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayMixed;
import com.google.gwt.query.client.js.JsUtils;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.vaadin.components.grid.GridComponent;

/**
 * Datasource where requestRows() is delegated to a js native function
 *
 * @author manolo
 */
public class GridJsFuncDataSource extends GridDataSource {
    private JavaScriptObject f;

    public GridJsFuncDataSource(JavaScriptObject jso, int rows, GridComponent grid) {
        super(grid);
        assert JsUtils.isFunction(jso);
        f = jso;
        size = rows;
    }

    @Override
    protected void requestRows(
            final int firstRowIndex,
            final int numberOfRows,
            final com.vaadin.client.data.AbstractRemoteDataSource.RequestRowsCallback<JsArrayMixed> callback) {
        JsArrayMixed o = exec(f, firstRowIndex, numberOfRows,
                new AsyncCallback<JsArrayMixed>() {
                    public void onFailure(Throwable caught) {
                    }

                    public void onSuccess(JsArrayMixed result) {
                        setRowData(firstRowIndex, result);
                    }
                });
        if (o != null) {
            setRowData(firstRowIndex, o);
        }
    }

    @Override
    public Object getRowKey(JsArrayMixed row) {
        return JsUtils.JSON2String(row);
    }

    private native JsArrayMixed exec(JavaScriptObject f, int idx,
            int count, AsyncCallback<JsArrayMixed> cb)
   /*-{
       return f(idx, count, function(r) {
           cb.@com.google.gwt.user.client.rpc.AsyncCallback::onSuccess(*)(r);
       });
   }-*/;
}
