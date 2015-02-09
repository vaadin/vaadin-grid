package com.vaadin.prototype.wc.gwt.client.html;

import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;

@JsType(prototype = "HTMLEvents", isNative = true)
public interface HTMLEvents {
    @JsProperty HTMLElement srcElement();
    @JsProperty void srcElement(HTMLElement e);
    @JsProperty HTMLElement target();
    @JsProperty HTMLElement currentTarget();
    void initEvent(String name, boolean bubbles, boolean cancelable);
}