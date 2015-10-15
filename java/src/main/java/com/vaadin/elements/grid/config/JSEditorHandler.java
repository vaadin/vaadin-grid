package com.vaadin.elements.grid.config;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;

/**
 * This class is a JsInterop wrapper for the JS object editor handler.
 */
@JsType
public interface JSEditorHandler {

    @JsProperty
    JavaScriptObject getBind();

    @JsProperty
    void setBind(JavaScriptObject bind);

    @JsProperty
    JavaScriptObject getCancel();

    @JsProperty
    void setCancel(JavaScriptObject cancel);

    @JsProperty
    JavaScriptObject getSave();

    @JsProperty
    void setSave(JavaScriptObject save);

    @JsProperty
    JavaScriptObject getGetCellEditor();

    @JsProperty
    void setGetCellEditor(JavaScriptObject getCellEditor);

}
