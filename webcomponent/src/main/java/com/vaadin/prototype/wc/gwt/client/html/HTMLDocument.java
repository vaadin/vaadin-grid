package com.vaadin.prototype.wc.gwt.client.html;

import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.dom.client.Document;

@JsType(prototype = "Document", isNative = true)
public interface HTMLDocument {
    static HTMLDocument $doc = (HTMLDocument)Document.get();

    @JsProperty HTMLElement head();
    @JsProperty HTMLElement body();
    HTMLElement createElement(String tagName);
    void register(String tag, Object jso);
}