package com.vaadin.prototype.wc.gwt.client.html;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.dom.client.Node;
import com.google.gwt.user.client.EventListener;

@JsType(prototype = "HTMLElement", isNative = true)
public interface HTMLElement {
    void addEventListener(String event, EventListener listener);
    void appendChild(HTMLElement element);
    void setAttribute(String name, String value);
    String getAttribute(String name);
    JavaScriptObject createShadowRoot();
    @JsProperty Node shadowRoot();
    @JsProperty String className();
    @JsProperty HTMLElement className(String string);
    @JsProperty HTMLStyle style();
    @JsProperty void innerHTML(String string);
    @JsProperty void innerText(String string);
}