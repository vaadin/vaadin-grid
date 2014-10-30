package com.vaadin.prototype.wc.gwt.client.js;

import static com.google.gwt.query.client.GQuery.console;
import static com.google.gwt.query.client.GQuery.window;

import com.google.gwt.core.client.GWT;
import com.google.gwt.query.client.builders.JsniBundle;
import com.google.gwt.query.client.js.JsUtils;

/**
 * Load angular Vaadin directives
 */
public abstract class NgVaadin implements JsniBundle {
    private static NgVaadin instance;

    static boolean isAngularLoaded() {
        return JsUtils.prop(window, "angular") != null;
    }

    public static void loadNgVaadin() {
        if (instance == null) {
            instance = GWT.<NgVaadin>create(NgVaadin.class);
            instance.initialize();
            console.log("Initialized Angular Vaadin X directives.");
        }
    }

    @LibrarySource("ng-vaadin.js")
    protected abstract void initialize();
}
