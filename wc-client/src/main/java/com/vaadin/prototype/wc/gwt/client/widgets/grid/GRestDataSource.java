package com.vaadin.prototype.wc.gwt.client.widgets.grid;

import static com.google.gwt.query.client.GQuery.console;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsArrayMixed;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQ;
import com.google.gwt.query.client.Properties;
import com.google.gwt.query.client.js.JsCache;
import com.google.gwt.query.client.js.JsObjectArray;
import com.google.gwt.query.client.js.JsUtils;
import com.google.gwt.query.client.plugins.ajax.Ajax;
import com.google.gwt.user.client.Window;
import com.vaadin.client.data.AbstractRemoteDataSource;
import com.vaadin.prototype.wc.gwt.client.widgets.WCVGrid;
import com.vaadin.prototype.wc.gwt.client.widgets.grid.GData.GAjax;
import com.vaadin.prototype.wc.gwt.client.widgets.grid.GData.GColumn;
import com.vaadin.prototype.wc.gwt.client.widgets.grid.GData.GColumn.GHeader;
import com.vaadin.prototype.wc.gwt.client.widgets.grid.GData.GColumn.GHeader.Format;
import com.vaadin.prototype.wc.gwt.client.widgets.grid.GData.GResponse;
import com.vaadin.prototype.wc.gwt.client.widgets.grid.GData.GResponse.GCfg;

public class GRestDataSource extends
        AbstractRemoteDataSource<JsArrayMixed> {
    private JavaScriptObject f;
    private int size = 300;
    
    GAjax ajaxCfg;
    WCVGrid wcGrid;

    public GRestDataSource(JavaScriptObject cfg, WCVGrid grid) {
        ajaxCfg = GQ.create(GAjax.class).load(cfg);
        this.wcGrid = grid;
        requestRows(0, 0);
    }

    @Override
    protected void requestRows(final int idx, final int count) {
        final String url = ajaxCfg.getUrl().replace("{START}", "" + idx).replace("{LENGTH}", "" + count);
        console.log("Request" + url);
        
        Ajax.post(url, null).done(new Function(){
            public void f() {
                String json = arguments(0);
                GResponse r = GQ.create(GResponse.class).parse(json, false);
                size = r.size();

                List<GCfg> cfgs = r.columns();
                List<GColumn> cols = wcGrid.getCols();
                if (!cfgs.isEmpty() && (cols == null || cols.isEmpty())) {
                    cols = configureColumns(cfgs);
                }
                
                if (count == 0) {
                    wcGrid.getGrid().setDataSource(GRestDataSource.this);
                } else {
                    JsArray<JavaScriptObject> data = r.data();
                    console.log(data.length());
                    if (data.length() > 0) {
                        if (JsUtils.isArray(data.get(0))) {
                            try {
                                setRowData(idx, data);
                            } catch (Exception e) {
                                console.log(e);
                            }
                        } else {
                            if (cols == null || cols.isEmpty()) {
                                cols = configureColumns(data.get(0).<Properties>cast());
                            }                            
                            List<JsArrayMixed> rowData = new ArrayList<JsArrayMixed>();
                            for (int i = 0; i < data.length(); i++) {
                                Properties c = data.get(i).cast();
                                JsObjectArray<Object> row = JsCache.createArray().cast();
                                for (int j = 0; j < cols.size(); j++) {
                                    GColumn g = cols.get(j);
                                    String name = g.name() != null ? g.name() : c.keys()[j];
                                    row.add(c.get(name));
                                }
                                rowData.add(row.<JsArrayMixed>cast());
                            }
                            setRowData(idx, rowData);
                        }
                    }
                }
                wcGrid.adjustHeight();
            }
        }).fail(new Function(){
            public void f() {
                Window.alert("Error getting datasources " + url);
            }
        });
    }
    
    private List<GColumn> configureColumns(List<GCfg> cfgs) {
        List<GColumn> colList = new ArrayList<GColumn>();
        for (GCfg c : cfgs) {
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
    
    private List<GColumn> configureColumns(Properties p) {
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
}