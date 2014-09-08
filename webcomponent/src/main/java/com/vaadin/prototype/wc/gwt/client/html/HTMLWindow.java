package com.vaadin.prototype.wc.gwt.client.html;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.ScriptInjector;
import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;

@JsType(prototype = "Window", isNative = true)
public interface HTMLWindow {
    public static HTMLWindow window = (HTMLWindow)ScriptInjector.TOP_WINDOW;

    void alert(String msg);
    boolean confirm(String msg);
    void addEventListener(String event, JavaScriptObject listener);
    @JsProperty String getName();
    @JsProperty void setName(String name);
    @JsProperty HTMLDocument document();
}