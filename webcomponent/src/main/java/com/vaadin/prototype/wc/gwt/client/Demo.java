package com.vaadin.prototype.wc.gwt.client;

import static com.google.gwt.query.client.GQuery.$;

import com.google.gwt.core.client.EntryPoint;

/**
 * Example code for a GwtQuery application
 */
public class Demo implements EntryPoint {

    public void onModuleLoad() {
        $("body").css("font-family", "arial");

        new DemoWrapWC().onModuleLoad();
        new DemoExportWC().onModuleLoad();
    }

}
