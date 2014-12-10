package com.vaadin.prototype.wc.gwt.client;

import static com.google.gwt.query.client.GQuery.console;

import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
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
            this.size = jso.length();
        }

        @Override
        protected void requestRows(final int idx, final int count) {
             gcols = setRowDataFromJs(idx, count, gcols, jso);
             console.log(gcols);
        }
    }

    public void onModuleLoad() {

//        Ajax.get("rest/list.json").done(new Function() {
//            public void f() {
//                String json = arguments(0);
//
//                Properties p = (Properties)GQ.create(json);
//                JavaScriptObject jso = p.getJavaScriptObject("heros");
//
//                GridColumn<Object, JsArrayMixed> column = new GridColumn<Object, JsArrayMixed>(){
//                    public Object getValue(JsArrayMixed row) {
//                        return row.<Properties>cast().getObject("name");
//                    }
//                };
//
//
//
//
//                Grid g = new Grid<JsArrayMixed>();
//                RootPanel.get().add(g);
//                MySource s = new MySource(jso.<JsArray<JavaScriptObject>>cast());
//                s.requestRows(0, 0);
//                for (int i = 0, l = s.gcols.size(); i < l; i++) {
//                    GColumn c = s.gcols.get(i);
//                    GridColumn<Object, JsArrayMixed> gridColumn = WCVGrid.createGridColumn(c, i);
//                    g.addColumn(gridColumn);
//                }
//                g.setDataSource(s);
//            };
//        });



        Elements.registerElement(WCVGrid.TAG, new WCVGrid());
        Elements.registerElement(WCVProgress.TAG, new WCVProgress());
        Elements.registerElement(WCVSlider.TAG, new WCVSlider());
//        JsUtils.runJavascriptFunction(window, "onVaadinX");
//        console.log(">> " + HData.MockData.createRandomData(new Random()));
    }
}
