package com.vaadin.prototype.wc.gwt.client;

import static com.google.gwt.query.client.GQuery.window;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.query.client.js.JsUtils;
import com.vaadin.prototype.wc.gwt.client.util.Elements;
import com.vaadin.prototype.wc.gwt.client.widgets.WCVGrid;
import com.vaadin.prototype.wc.gwt.client.widgets.WCVProgress;
import com.vaadin.prototype.wc.gwt.client.widgets.WCVSlider;

/**
 * Example code for a GwtQuery application
 */
public class Demo implements EntryPoint {

    public void onModuleLoad() {
        Elements.registerElement(WCVGrid.TAG, new WCVGrid());
        Elements.registerElement(WCVProgress.TAG, new WCVProgress());
        Elements.registerElement(WCVSlider.TAG, new WCVSlider());

        // TODO: When we figure out a way to add lazy directives
        // NgVaadin.loadNgVaadin();

        // Call a vaadin callback function when vaadinX is ready.
        JsUtils.runJavascriptFunction(window, "onVaadinX");
    }
}
