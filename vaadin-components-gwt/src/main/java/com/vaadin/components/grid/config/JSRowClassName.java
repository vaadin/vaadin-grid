package com.vaadin.components.grid.config;

import com.google.gwt.core.client.JavaScriptObject;
import com.vaadin.components.common.js.JS;

/**
 * GWT API for the rowClassName JavaScript function.
 */
public final class JSRowClassName extends JavaScriptObject {

    protected JSRowClassName() {
    }

    public String getStyle(JSRow row) {
        return JS.exec(this, row);
    }
}
