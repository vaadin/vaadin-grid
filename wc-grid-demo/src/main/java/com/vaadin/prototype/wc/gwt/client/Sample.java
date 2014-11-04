package com.vaadin.prototype.wc.gwt.client;

import static com.google.gwt.query.client.GQuery.$;
import static com.google.gwt.query.client.GQuery.console;
import static com.google.gwt.query.client.GQuery.document;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.vaadin.prototype.wc.gwt.client.js.NgVaadin;

public class Sample implements EntryPoint {

    public void onModuleLoad() {
        console.log("Sample.onModuleLoad done.");
        GWT.runAsync(new RunAsyncCallback() {
            public void onSuccess() {
                $(document).append("<div>RunAsyncCallback OK</div>");
                NgVaadin.loadNgVaadin();
            }
            public void onFailure(Throwable reason) {
                $(document).append("<div>RunAsyncCallback ERR</div>");
            }
        });
    }
}
