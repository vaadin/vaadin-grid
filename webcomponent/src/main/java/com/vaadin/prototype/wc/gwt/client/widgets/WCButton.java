package com.vaadin.prototype.wc.gwt.client.widgets;

import com.google.gwt.core.client.js.JsExport;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.vaadin.prototype.wc.gwt.client.html.HTMLElement;

@JsExport
@JsType
public class WCButton extends HTMLElement.Prototype implements
        HTMLElement.LifeCycle.Created, HTMLElement.LifeCycle.Attached {

    public WCButton() {
        // FIXME: If there is no default constructor JsInterop does not export anything
    }

    static int top = 0;

    @Override
    public void createdCallback() {
        innerHTML("<button>Click Me</button>");
        style().position("absolute").zIndex("400").padding("5px").right("0px").top(top + "px");
        top += 40;
    }

    @Override
    public void attachedCallback() {
        addEventListener("mouseenter", new EventListener() {
            public void onBrowserEvent(Event event) {
                style().backgroundColor("orange");
            }
        });
        addEventListener("mouseleave", new EventListener() {
            public void onBrowserEvent(Event event) {
                style().backgroundColor("inherit");
            }
        });
    }
}
