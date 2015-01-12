package com.vaadin.prototype.wc.gwt.client.widgets.grid;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayMixed;
import com.google.gwt.query.client.js.JsUtils;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.vaadin.prototype.wc.gwt.client.widgets.WCVGrid;

/**
 * Datasource where requestRows() is delegated to a js native function
 *
 * @author manolo
 */
public class GJsFuncDataSource extends GDataSource {
    private JavaScriptObject f;

    public GJsFuncDataSource(JavaScriptObject jso, int rows, WCVGrid grid) {
        super(grid);
        assert JsUtils.isFunction(jso);
        f = jso;
        size = rows;
    }

    @Override
    protected void requestRows(
            int firstRowIndex,
            int numberOfRows,
            com.vaadin.client.data.AbstractRemoteDataSource.RequestRowsCallback<JsArrayMixed> callback) {
        JavaScriptObject o = exec(f, firstRowIndex, numberOfRows,
                new AsyncCallback<JavaScriptObject>() {
                    public void onFailure(Throwable caught) {
                    }

                    public void onSuccess(JavaScriptObject result) {
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

    private native JavaScriptObject exec(JavaScriptObject f, int idx,
            int count, AsyncCallback<JavaScriptObject> cb) /*-{
                                                           return f(idx, count, function(r) {
                                                           cb.@com.google.gwt.user.client.rpc.AsyncCallback::onSuccess(*)(r);
                                                           });
                                                           }-*/;
}