package com.vaadin.prototype.wc.gwt.client.widgets.grid;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsArrayMixed;
import com.google.gwt.query.client.GQ;
import com.google.gwt.query.client.Properties;
import com.google.gwt.query.client.js.JsUtils;
import com.vaadin.client.data.AbstractRemoteDataSource;
import com.vaadin.prototype.wc.gwt.client.widgets.WCVGrid;
import com.vaadin.prototype.wc.gwt.client.widgets.grid.GData.GAjaxConf.GAjaxResponse.GAjaxColumn;
import com.vaadin.prototype.wc.gwt.client.widgets.grid.GData.GColumn;
import com.vaadin.prototype.wc.gwt.client.widgets.grid.GData.GColumn.GHeader;
import com.vaadin.prototype.wc.gwt.client.widgets.grid.GData.GColumn.GHeader.Format;

public abstract class GDataSource extends
        AbstractRemoteDataSource<JsArrayMixed> {
    protected int size = 300;

    protected final WCVGrid wcGrid;

    public GDataSource(WCVGrid grid) {
        this.wcGrid = grid;
    }

    protected List<GColumn> configColumsFromAjaxResponse(List<GAjaxColumn> cfgs) {
        List<GColumn> colList = new ArrayList<GColumn>();
        for (GAjaxColumn c : cfgs) {
            List<GHeader> l = new ArrayList<GHeader>();
            l.add(GQ.create(GHeader.class)
                    .setContent(c.title())
                    .setFormat(Format.HTML)
                    .setColSpan(1));

            colList.add(GQ.create(GColumn.class)
                    .setValue(c.name())
                    .setHeaderData(l));
        }
        if (wcGrid != null) {
            wcGrid.setCols(colList);
            wcGrid.initGrid();
        }
        return colList;
    }

    private List<GColumn> configColumnsFromFirstDataRow(Properties p) {
        List<GColumn> colList = new ArrayList<GColumn>();
        for (String k : p.keys()) {
            List<GHeader> l = new ArrayList<GHeader>();
            l.add(GQ.create(GHeader.class)
                    .setContent(k)
                    .setFormat(Format.HTML)
                    .setColSpan(1));

            colList.add(GQ.create(GColumn.class)
                    .setValue(k)
                    .setHeaderData(l));
        }
        if (wcGrid != null) {
            wcGrid.setCols(colList);
            wcGrid.initGrid();
        }
        return colList;
    }

    protected void setRowData(int idx, JavaScriptObject array) {
        GData g = GQ.create(GData.class).set("values", array);
        super.setRowData(idx, g.values());
    }

    @Override
    public Object getRowKey(JsArrayMixed row) {
        return row.toString();
    }

    @Override
    public int size() {
        return size;
    }

    public void refresh() {
        resetDataAndSize(size());
    }
    
    private native JavaScriptObject slice(JsArray<JavaScriptObject> data, int idx, int count) /*-{
        return data.slice(idx, idx + count);
    }-*/;

    protected List<GColumn> setRowDataFromJs(final int idx, int count, List<GColumn> cols, JsArray<JavaScriptObject> data) {
        if (data.length() > 0) {
            if (!JsUtils.isArray(data.get(0)) && (cols == null || cols.isEmpty())) {
                cols = configColumnsFromFirstDataRow(data.get(0).<Properties>cast());
            }
            setRowData(idx, slice(data, idx, count));
        }
        return cols;
    }
}