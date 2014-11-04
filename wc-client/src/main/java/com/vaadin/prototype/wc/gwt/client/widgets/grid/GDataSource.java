package com.vaadin.prototype.wc.gwt.client.widgets.grid;

import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayMixed;
import com.google.gwt.query.client.GQ;
import com.google.gwt.query.client.js.JsUtils;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.vaadin.client.data.AbstractRemoteDataSource;

public class GDataSource extends
        AbstractRemoteDataSource<JsArrayMixed> {
    private JavaScriptObject f;
    private int size = 0;

    public GDataSource(JavaScriptObject jso, int rows) {
        assert JsUtils.isFunction(jso);
        f = jso;
        size = rows;
    }

    @Override
    protected void requestRows(final int idx, int count) {
        JavaScriptObject o = exec(f, idx, count,
                new AsyncCallback<JavaScriptObject>() {
                    public void onFailure(Throwable caught) {
                    }

                    public void onSuccess(JavaScriptObject result) {
                        setRowData(idx, result);
                    }
                });
        if (o != null) {
            setRowData(idx, o);
        }
    }
    
    private void setRowData(int idx, JavaScriptObject array) {
        GData g = GQ.create(GData.class).set("values", array);
        setRowData(idx, g.values());
    }

    @Override
    protected void setRowData(int firstRowIndex, List<JsArrayMixed> rowData) {
        super.setRowData(firstRowIndex, rowData);
    }

    @Override
    public Object getRowKey(JsArrayMixed row) {
        return row.toString();
    }

    @Override
    public int size() {
        return size;
    }
    
    private native JavaScriptObject exec(JavaScriptObject f, int idx,
            int count, AsyncCallback<JavaScriptObject> cb) /*-{
        return f(idx, count, function(r) {
            cb.@com.google.gwt.user.client.rpc.AsyncCallback::onSuccess(*)(r);
        });
    }-*/;        
}