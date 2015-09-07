package com.vaadin.components.grid.config;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import com.vaadin.components.common.js.JSArray;

/**
 * This class is a JsInterop wrapper for the JS object editor handler request.
 */
@JsType
public interface JSDataRequest {
    @JsProperty
    int getIndex();

    @JsProperty
    void setIndex(int index);

    @JsProperty
    int getCount();

    @JsProperty
    void setCount(int count);

    @JsProperty
    JSArray<JSSortOrder> getSortOrder();

    @JsProperty
    void setSortOrder(JSArray<JSSortOrder> sortOrder);

    @JsProperty
    JavaScriptObject getSuccess();

    @JsProperty
    void setSuccess(JavaScriptObject success);

    @JsProperty
    JavaScriptObject getFailure();

    @JsProperty
    void setFailure(JavaScriptObject failure);
}
