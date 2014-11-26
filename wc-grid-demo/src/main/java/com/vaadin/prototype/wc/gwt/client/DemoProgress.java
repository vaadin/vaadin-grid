package com.vaadin.prototype.wc.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.vaadin.prototype.wc.gwt.client.util.Elements;
import com.vaadin.prototype.wc.gwt.client.widgets.WCVProgress;

public class DemoProgress implements EntryPoint {
    public void onModuleLoad() {
        Elements.registerElement(WCVProgress.TAG, new WCVProgress());
    }
}
