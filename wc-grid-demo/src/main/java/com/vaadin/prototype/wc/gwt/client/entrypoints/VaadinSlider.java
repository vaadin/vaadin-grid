package com.vaadin.prototype.wc.gwt.client.entrypoints;

import static com.google.gwt.query.client.GQuery.$;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Element;
import com.google.gwt.query.client.Function;
import com.vaadin.prototype.wc.gwt.client.html.HTMLElement;
import com.vaadin.prototype.wc.gwt.client.util.Elements;
import com.vaadin.prototype.wc.gwt.client.widgets.WCUtils;
import com.vaadin.prototype.wc.gwt.client.widgets.WCVGrid;
import com.vaadin.prototype.wc.gwt.client.widgets.WCVSlider;

/**
 * Exports only the Vaadin slider web component
 */
public class VaadinSlider implements EntryPoint {
    public void onModuleLoad() {
        WCUtils.loadVaadinGlobalTheme(null);
        Elements.registerElement(WCVSlider.TAG, new WCVSlider());
    }
}
