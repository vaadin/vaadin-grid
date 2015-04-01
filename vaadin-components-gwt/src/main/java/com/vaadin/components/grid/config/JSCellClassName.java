package com.vaadin.components.grid.config;

import com.google.gwt.core.client.JavaScriptObject;
import com.vaadin.components.common.js.JS;

/**
 * GWT API for the cellClassName JavaScript function.
 */
public final class JSCellClassName extends JavaScriptObject {

    protected JSCellClassName() {
    }

    public String getStyle(JSCell cell) {
        return JS.exec(this, cell);
    }
}
