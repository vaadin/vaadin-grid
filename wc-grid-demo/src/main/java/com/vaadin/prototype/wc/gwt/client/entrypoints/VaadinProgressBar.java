package com.vaadin.prototype.wc.gwt.client.entrypoints;

import static com.google.gwt.query.client.GQuery.$;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Element;
import com.google.gwt.query.client.Function;
import com.vaadin.prototype.wc.gwt.client.html.HTMLElement;
import com.vaadin.prototype.wc.gwt.client.util.Elements;
import com.vaadin.prototype.wc.gwt.client.widgets.WCUtils;
import com.vaadin.prototype.wc.gwt.client.widgets.WCVGrid;
import com.vaadin.prototype.wc.gwt.client.widgets.WCVProgress;

/**
 * Exports only the Vaadin progress bar web component
 */
public class VaadinProgressBar implements EntryPoint {
    public void onModuleLoad() {
        WCUtils.loadVaadinGlobalTheme();
        WCVProgress progress = new WCVProgress();
        Elements.registerElement(WCVProgress.TAG, progress);
        Elements.registerElement(WCVProgress.TAG + "-bar", progress);
    }
}
