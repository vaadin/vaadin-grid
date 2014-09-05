package com.vaadin.prototype.wc.gwt.client;

import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.dom.client.BodyElement;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.HeadElement;
import com.google.gwt.user.client.EventListener;

public abstract class Native {
    
    @JsType(prototype = "Window", isNative = true)
    public interface Window {
        void alert(String msg);
        boolean confirm(String msg);
        void addEventListener(String event, EventListener listener);
        @JsProperty String getName();
        @JsProperty void setName(String name);
        @JsProperty Document document();
    }
    
    @JsType(prototype = "Document", isNative = true)
    public interface Document {
        @JsProperty HeadElement head();
        @JsProperty BodyElement body();
        HTMLElement createElement(String tagName);
    }
    
    @JsType(prototype = "HTMLElement", isNative = true)
    public interface HTMLElement {
        void addEventListener(String event, EventListener listener);
        void appendChild(HeadElement element);
    }
}
