package com.vaadin.components.grid.data;

import static com.google.gwt.query.client.GQuery.console;

import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQ;
import com.google.gwt.query.client.plugins.ajax.Ajax;
import com.google.gwt.user.client.Window;
import com.vaadin.components.grid.GridComponent;
import com.vaadin.components.grid.data.GridData.GridAjaxConf;
import com.vaadin.components.grid.data.GridData.GridAjaxConf.GridAjaxResponse.GridAjaxColumn;
import com.vaadin.components.grid.data.GridData.GridColumn;

public class GridRestDataSource extends GridDataSource {

    private GridAjaxConf ajaxCfg;

    public GridRestDataSource(JavaScriptObject cfg, GridComponent grid) {
        super(grid);
        ajaxCfg = GQ.create(GridAjaxConf.class).load(cfg);
        requestRows(0, 0, null);
    }

    @Override
    protected void requestRows(
            final int firstRowIndex,
            final int numberOfRows,
            final com.vaadin.client.data.AbstractRemoteDataSource.RequestRowsCallback<com.google.gwt.core.client.JsArrayMixed> callback) {
        final String url = ajaxCfg.getUrl()
                .replace("{START}", "" + firstRowIndex)
                .replace("{LENGTH}", "" + numberOfRows);
        console.log(url);

        Ajax.post(url, null).done(new Function() {
            @Override
            public void f() {
                String json = arguments(0);
                GridAjaxConf.GridAjaxResponse r = GQ.create(
                        GridAjaxConf.GridAjaxResponse.class).parse(json, false);
                size = r.size();

                List<GridAjaxColumn> cfgs = r.columns();
                List<GridColumn> cols = wcGrid.getCols();
                if (!cfgs.isEmpty() && (cols == null || cols.isEmpty())) {
                    cols = configColumsFromAjaxResponse(cfgs);
                }

                if (numberOfRows == 0) {
                    wcGrid.getGrid().setDataSource(GridRestDataSource.this);
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
