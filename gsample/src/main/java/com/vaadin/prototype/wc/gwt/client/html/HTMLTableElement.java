package com.vaadin.prototype.wc.gwt.client.html;

import com.google.gwt.core.client.js.JsType;

@JsType(prototype = "HTMLTableElement", isNative = true)
public interface HTMLTableElement extends HTMLElement {

    // @PrototypeOfJsType
    public static class Prototype extends HTMLElement.Prototype implements HTMLTableElement {
    }

}