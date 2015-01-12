package com.vaadin.prototype.wc.gwt.client.widgets.grid;

import static com.google.gwt.query.client.GQuery.console;

import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQ;
import com.google.gwt.query.client.plugins.ajax.Ajax;
import com.google.gwt.user.client.Window;
import com.vaadin.prototype.wc.gwt.client.widgets.WCVGrid;
import com.vaadin.prototype.wc.gwt.client.widgets.grid.GData.GAjaxConf;
import com.vaadin.prototype.wc.gwt.client.widgets.grid.GData.GAjaxConf.GAjaxResponse.GAjaxColumn;
import com.vaadin.prototype.wc.gwt.client.widgets.grid.GData.GColumn;

public class GRestDataSource extends GDataSource {

    private GAjaxConf ajaxCfg;

    public GRestDataSource(JavaScriptObject cfg, WCVGrid grid) {
        super(grid);
        ajaxCfg = GQ.create(GAjaxConf.class).load(cfg);
        requestRows(0, 0, null);
    }

    @Override
    protected void requestRows(
            int firstRowIndex,
            int numberOfRows,
            com.vaadin.client.data.AbstractRemoteDataSource.RequestRowsCallback<com.google.gwt.core.client.JsArrayMixed> callback) {
        final String url = ajaxCfg.getUrl()
                .replace("{START}", "" + firstRowIndex)
                .replace("{LENGTH}", "" + numberOfRows);
        console.log(url);

        Ajax.post(url, null).done(new Function() {
            @Override
            public void f() {
                String json = arguments(0);
                GAjaxConf.GAjaxResponse r = GQ.create(
                        GAjaxConf.GAjaxResponse.class).parse(json, false);
                size = r.size();

                List<GAjaxColumn> cfgs = r.columns();
                List<GColumn> cols = wcGrid.getCols();
                if (!cfgs.isEmpty() && (cols == null || cols.isEmpty())) {
                    cols = configColumsFromAjaxResponse(cfgs);
                }

                if (numberOfRows == 0) {
                    wcGrid.getGrid().setDataSource(GRestDataSource.this);
                } else {
                    JsArray<JavaScriptObject> data = r.data();
                    setRowDataFromJs(firstRowIndex, numberOfRows, cols, data);
                }
                wcGrid.adjustHeight();
            }
        }).fail(new Function() {
            @Override
            public void f() {
                Window.alert("Error getting datasources " + url);
            }
        });
    }

}