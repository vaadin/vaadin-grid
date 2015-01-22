package com.vaadin.prototype.wc.gwt.client.entrypoints;

import static com.google.gwt.query.client.GQuery.$;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.js.JsUtils;
import com.vaadin.prototype.wc.gwt.client.html.HTMLElement;
import com.vaadin.prototype.wc.gwt.client.util.Elements;
import com.vaadin.prototype.wc.gwt.client.util.SuperDevModeUncaughtExceptionHandler;
import com.vaadin.prototype.wc.gwt.client.widgets.WCUtils;
import com.vaadin.prototype.wc.gwt.client.widgets.WCVGrid;
import com.vaadin.prototype.wc.gwt.client.widgets.WCVProgress;
import com.vaadin.prototype.wc.gwt.client.widgets.WCVSlider;

/**
 * Exports all vaadin web components.
 */
public class VaadinComponents implements EntryPoint {

    public void onModuleLoad() {
        GWT.setUncaughtExceptionHandler(new SuperDevModeUncaughtExceptionHandler());

        // load vaadin-theme if specified in body
        WCUtils.loadVaadinGlobalTheme(new Function() {
            public void f() {
                for (Element e : $("v-grid").elements()) {
                    ((WCVGrid)(HTMLElement)e).redraw();
                }
            }
        });

        WCVProgress progress = new WCVProgress();
        Elements.registerElement(WCVProgress.TAG, progress);
        Elements.registerElement(WCVProgress.TAG + "-bar", progress);
        Elements.registerElement(WCVSlider.TAG, new WCVSlider());
        Elements.registerElement(WCVGrid.TAG, new WCVGrid());

        // Call a vaadin callback function when vaadinX is ready.
        JsUtils.jsni("onVaadinX");
    }
}
