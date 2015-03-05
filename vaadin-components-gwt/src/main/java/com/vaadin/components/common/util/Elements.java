package com.vaadin.components.common.util;

import com.vaadin.components.common.html.HTMLDocument;
import com.vaadin.components.common.html.HTMLElement;
import com.vaadin.components.common.html.HTMLWindow;

/**
 * This is the helper class to create, import or register web components.
 */
public abstract class Elements {

    // This has to match with the @JsNamespace of the package-info of exported components
    public static final String VAADIN_JS_NAMESPACE = "vaadin";

    public static HTMLWindow window;
    public static HTMLDocument document;
    public static HTMLElement head;
    public static HTMLElement body;

    static {
        window = HTMLWindow.window;
        document = window.document();
        head = document.head();
        body = document.body();
    }

    public static HTMLElement create(String tag) {
        return document.createElement(tag);
    }
}
