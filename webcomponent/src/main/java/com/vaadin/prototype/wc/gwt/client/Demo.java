package com.vaadin.prototype.wc.gwt.client;

import static com.google.gwt.query.client.GQuery.$;
import static com.google.gwt.query.client.GQuery.console;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;

/**
 * Example code for a GwtQuery application
 */
public class Demo implements EntryPoint {

    public void onModuleLoad() {
        $("body").css("font-family", "arial");
        
        console.log(GWT.getHostPageBaseURL());
        console.log(GWT.getModuleBaseURL());

        new DemoWrapWC().onModuleLoad();
        new DemoExportWC().onModuleLoad();
    }

}
