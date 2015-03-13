package com.vaadin.components.grid.config;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.query.client.js.JsUtils;

/**
 * GWT API for the rowClassName JavaScript function.
 */
public final class JSRowClassName extends JavaScriptObject {

    protected JSRowClassName() {
    }

    public String getStyle(JSRow row) {
        return JsUtils.jsni(this, "call", this, row);
    }
}
