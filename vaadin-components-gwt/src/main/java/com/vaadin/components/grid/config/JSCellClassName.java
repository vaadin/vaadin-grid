package com.vaadin.components.grid.config;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.query.client.js.JsUtils;

/**
 * GWT API for the cellClassName JavaScript function.
 */
public final class JSCellClassName extends JavaScriptObject {

    protected JSCellClassName() {
    }

    public String getStyle(JSCell cell) {
        return JsUtils.jsni(this, "call", this, cell);
    }
}
