package com.vaadin.prototype.wc.gwt.client;

import static com.google.gwt.query.client.GQuery.console;

import java.util.List;
import java.util.Random;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.user.client.Window;
import com.vaadin.prototype.wc.gwt.client.util.Elements;
import com.vaadin.prototype.wc.gwt.client.widgets.WCVGrid;
import com.vaadin.prototype.wc.gwt.client.widgets.WCVProgress;
import com.vaadin.prototype.wc.gwt.client.widgets.WCVSlider;
import com.vaadin.prototype.wc.gwt.client.widgets.grid.GData.GColumn;
import com.vaadin.prototype.wc.gwt.client.widgets.grid.GDataSource;

/**
 */
public class Hero implements EntryPoint {

    public class MySource extends GDataSource {
        private final JsArray<JavaScriptObject> jso;
        List<GColumn> gcols = null;

        public MySource(JsArray<JavaScriptObject> jso) {
            super(null);
            this.jso = jso;
            size = jso.length();
        }

        @Override
        protected void requestRows(
                int firstRowIndex,
                int numberOfRows,
                com.vaadin.client.data.AbstractRemoteDataSource.RequestRowsCallback<com.google.gwt.core.client.JsArrayMixed> callback) {
            gcols = setRowDataFromJs(firstRowIndex, numberOfRows, gcols, jso);
            console.log(gcols);
        }
    }

    public void onModuleLoad() {
        Elements.registerElement(WCVGrid.TAG, new WCVGrid());
        Elements.registerElement(WCVProgress.TAG, new WCVProgress());
        Elements.registerElement(WCVSlider.TAG, new WCVSlider());
        // Used to generate a list of heres
        if (Window.Location.getParameter("generate-heroes") != null) {
            HData data = HData.MockData.createRandomData(new Random());
            console.log(data);
        }
        // console.log(">> ", HData.MockData.createRandomData(new Random()));
    }
}
