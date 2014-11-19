package com.vaadin.prototype.wc.gwt.client.widgets.grid;

import static com.google.gwt.query.client.GQuery.console;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsArrayMixed;
import com.google.gwt.query.client.GQ;
import com.google.gwt.query.client.Properties;
import com.google.gwt.query.client.js.JsCache;
import com.google.gwt.query.client.js.JsObjectArray;
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
                    .setName(c.name())
                    .setHeaderData(l));
        }
        wcGrid.setCols(colList);
        wcGrid.initGrid();
        return wcGrid.getCols();
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
                    .setName(k)
                    .setHeaderData(l));
        }
        wcGrid.setCols(colList);
        wcGrid.initGrid();
        return wcGrid.getCols();
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

    protected void setRowDataFromJs(final int idx, int count, List<GColumn> cols, JsArray<JavaScriptObject> data) {
        if (data.length() > 0) {
            if (JsUtils.isArray(data.get(0))) {
                try {
                    setRowData(idx, data);
                } catch (Exception e) {
                    console.log(e);
                }
            } else {
                if (cols == null || cols.isEmpty()) {
                    cols = configColumnsFromFirstDataRow(data.get(0).<Properties>cast());
                }
                List<JsArrayMixed> rowData = new ArrayList<JsArrayMixed>();
                for (int i = idx, l = Math.min(data.length(), idx + count); i < l; i++) {
                    Properties c = data.get(i).cast();
                    JsObjectArray<Object> row = JsCache.createArray().cast();
                    for (int j = 0; j < cols.size(); j++) {
                        GColumn g = cols.get(j);
                        String name = g.name() != null && !g.name().isEmpty() ? g.name() : c.keys()[j];
                        row.add(c.get(name));
                    }
                    rowData.add(row.<JsArrayMixed>cast());
                }
                setRowData(idx, rowData);
            }
        }
    }
}