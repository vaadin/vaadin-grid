package com.vaadin.prototype.wc.gwt.client;

import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.user.client.EventListener;

public abstract class JsInterfaces {
    
    @JsType(prototype = "Window", isNative = true)
    public interface Window {
        void alert(String msg);
        boolean confirm(String msg);
        void addEventListener(String event, EventListener listener);
        @JsProperty String getName();
        @JsProperty void setName(String name);
    }
}
