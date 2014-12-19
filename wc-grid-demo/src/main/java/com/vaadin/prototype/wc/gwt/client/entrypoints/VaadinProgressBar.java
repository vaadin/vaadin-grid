package com.vaadin.prototype.wc.gwt.client.entrypoints;

import com.google.gwt.core.client.EntryPoint;
import com.vaadin.prototype.wc.gwt.client.util.Elements;
import com.vaadin.prototype.wc.gwt.client.widgets.WCUtils;
import com.vaadin.prototype.wc.gwt.client.widgets.WCVProgress;

/**
 * Exports only the Vaadin progress bar web component
 */
public class VaadinProgressBar implements EntryPoint {
    public void onModuleLoad() {
        WCUtils.loadVaadinGlobalTheme();
        Elements.registerElement(WCVProgress.TAG, new WCVProgress());
    }
}
