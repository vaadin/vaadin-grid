package com.vaadin.prototype.wc.gwt.client;

import static com.google.gwt.query.client.GQuery.$;
import static com.google.gwt.query.client.GQuery.document;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.Window;
import com.vaadin.prototype.wc.gwt.client.html.HTMLElement;
import com.vaadin.prototype.wc.gwt.client.widgets.WCButton;

/**
 * Example code for a GwtQuery application
 */
public class DemoExportWC implements EntryPoint {
    public void onModuleLoad() {
        WC.register("x-button", WCButton.class);

        // We can create elements adding the tag with gQuery or Js
        final GQuery g = $("<x-button message='WebComponents Rock'/>")
                .appendTo(document);

        // The new element created has the HTMLElement prototype, hence we can
        // cast.
        final HTMLElement e = (HTMLElement) g.get(0);
        e.addEventListener("click", new EventListener() {
            public void onBrowserEvent(Event event) {
                Window.alert("Hello -> " + e.getAttribute("message"));
            }
        });

    }
}
