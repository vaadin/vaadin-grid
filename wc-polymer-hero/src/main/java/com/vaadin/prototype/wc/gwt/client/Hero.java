package com.vaadin.prototype.wc.gwt.client;

import static com.google.gwt.query.client.GQuery.console;
import static com.google.gwt.query.client.GQuery.window;

import java.util.Random;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.query.client.js.JsUtils;
import com.vaadin.prototype.wc.gwt.client.util.Elements;
import com.vaadin.prototype.wc.gwt.client.widgets.WCVGrid;
import com.vaadin.prototype.wc.gwt.client.widgets.WCVProgress;
import com.vaadin.prototype.wc.gwt.client.widgets.WCVSlider;

/**
 */
public class Hero implements EntryPoint {
    public void onModuleLoad() {
        Elements.registerElement(WCVGrid.TAG, WCVGrid.class);
        Elements.registerElement(WCVProgress.TAG, WCVProgress.class);
        Elements.registerElement(WCVSlider.TAG, WCVSlider.class);
//        JsUtils.runJavascriptFunction(window, "onVaadinX");
//        console.log(">> " + HData.MockData.createRandomData(new Random()));
    }
}
