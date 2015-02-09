package com.vaadin.prototype.wc.gwt.client.html;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;

@JsType(prototype = "Window", isNative = true)
public interface HTMLWindow {

    static class U {
        static native HTMLWindow $wnd() /*-{
            return $wnd;
        }-*/;
    }

    // FIXME: IE11 does not support this, it raises a Casting Exception
    // static HTMLWindow window = JsUtils.cast(ScriptInjector.TOP_WINDOW);
    static HTMLWindow window = U.$wnd();

    void alert(String msg);
    boolean confirm(String msg);
    void addEventListener(String event, JavaScriptObject listener);
    @JsProperty String getName();
    @JsProperty void setName(String name);
    @JsProperty HTMLDocument document();
}