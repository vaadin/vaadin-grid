package com.vaadin.components.common.html;

import com.google.gwt.core.client.js.JsType;

@JsType(prototype = "HTMLTableElement")
public interface HTMLTableElement extends HTMLElement {

    // @PrototypeOfJsType
    public static class Prototype extends HTMLElement.Prototype implements HTMLTableElement {
    }

}
