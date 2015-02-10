package com.vaadin.prototype.wc.gwt.client.entrypoints;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.vaadin.prototype.wc.gwt.client.util.Elements;
import com.vaadin.prototype.wc.gwt.client.util.SuperDevModeUncaughtExceptionHandler;
import com.vaadin.prototype.wc.gwt.client.widgets.WCUtils;
import com.vaadin.prototype.wc.gwt.client.widgets.WCVGrid;

/**
 * Exports vaadin grid.
 */
public class VaadinGrid implements EntryPoint {

    public void onModuleLoad() {
        if (!GWT.isProdMode()) {
           GWT.setUncaughtExceptionHandler(new SuperDevModeUncaughtExceptionHandler());
        }
        // load vaadin-theme if specified in body
        WCUtils.loadVaadinGlobalTheme();
        Elements.registerElement(WCVGrid.TAG, new WCVGrid());
    }
}
