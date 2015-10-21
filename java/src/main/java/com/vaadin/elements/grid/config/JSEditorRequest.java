package com.vaadin.elements.grid.config;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.dom.client.Element;

/**
 * This class is a JsInterop wrapper for the JS object editor handler request.
 */
@JsType
public interface JSEditorRequest {
    @JsProperty
    int getRowIndex();

    @JsProperty
    void setRowIndex(int rowIndex);

    @JsProperty
    Object getDataItem();

    @JsProperty
    void setDataItem(Object dataItem);

    @JsProperty
    Element getGrid();

    @JsProperty
    void setGrid(Element grid);

    @JsProperty
    JavaScriptObject getSuccess();

    @JsProperty
    void setSuccess(JavaScriptObject success);

    @JsProperty
    JavaScriptObject getFailure();

    @JsProperty
    void setFailure(JavaScriptObject failure);

    @JsProperty
    JavaScriptObject getGetCellEditor();

    @JsProperty
    void setGetCellEditor(JavaScriptObject getCellEditor);

}
