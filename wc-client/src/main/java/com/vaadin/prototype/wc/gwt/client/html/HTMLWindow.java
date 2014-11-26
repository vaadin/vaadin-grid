package com.vaadin.prototype.wc.gwt.client.html;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.ScriptInjector;
import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;

@JsType(prototype = "Window", isNative = true)
public interface HTMLWindow {
    // TODO: this makes IE fail with a CastException
    // static HTMLWindow window = (HTMLWindow)ScriptInjector.TOP_WINDOW;
    public static abstract class Utils {
        public static native HTMLWindow window() /*-{
            return $wnd;
        }-*/;
    }
    static HTMLWindow window = Utils.window();


    void alert(String msg);
    boolean confirm(String msg);
    void addEventListener(String event, JavaScriptObject listener);
    @JsProperty String getName();
    @JsProperty void setName(String name);
    @JsProperty HTMLDocument document();
}