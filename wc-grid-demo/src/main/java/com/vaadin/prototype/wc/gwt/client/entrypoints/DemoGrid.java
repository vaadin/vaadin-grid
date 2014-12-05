package com.vaadin.prototype.wc.gwt.client.entrypoints;

import com.google.gwt.core.client.EntryPoint;
import com.vaadin.prototype.wc.gwt.client.util.Elements;
import com.vaadin.prototype.wc.gwt.client.widgets.WCVGrid;

public class DemoGrid implements EntryPoint {
    public void onModuleLoad() {
        Elements.registerElement(WCVGrid.TAG, new WCVGrid());
    }
}
