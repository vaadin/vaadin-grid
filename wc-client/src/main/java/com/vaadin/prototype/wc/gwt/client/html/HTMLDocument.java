package com.vaadin.prototype.wc.gwt.client.html;

import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;

@JsType(prototype = "Document", isNative = true)
public interface HTMLDocument {
    Object registerElement(String tag, Object o);
    @JsProperty HTMLElement head();
    @JsProperty HTMLElement body();
    HTMLElement createElement(String tagName);
    HTMLEvents createEvent(String HTMLEvents);
    void dispatchEvent(HTMLEvents ev);
    void register(String tag, Object jso);
}