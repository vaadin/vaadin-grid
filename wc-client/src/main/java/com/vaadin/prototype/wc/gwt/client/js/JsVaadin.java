package com.vaadin.prototype.wc.gwt.client.js;

import static com.google.gwt.query.client.GQuery.console;
import static com.google.gwt.query.client.GQuery.window;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.query.client.builders.JsniBundle;
import com.google.gwt.query.client.js.JsUtils;

/**
 * Load 3 party libraries
 */
public abstract class JsVaadin implements JsniBundle {

    private static JsVaadin instance;
    private static boolean ngLoaded = false;
    private static boolean pfLoaded = false;

    static boolean isAngularLoaded() {
        return JsUtils.prop(window, "angular") != null;
    }

    static JsVaadin instance() {
        if (instance == null) {
            console.log("Initialized JsVaadin.");
            instance = GWT.<JsVaadin>create(JsVaadin.class);
        }
        return instance;
    }

    // FIXME: does not work because
    public static void loadNgVaadin() {
        if (!ngLoaded) {
            ngLoaded = true;
            GWT.runAsync(new RunAsyncCallback() {
                public void onSuccess() {
                    console.log("Initialized Angular Vaadin X directives.");
                    instance().angular();
                }
                public void onFailure(Throwable reason) {
                }
            });
        }
    }

    // FIXME: does not work because gwt jsni does not support syntax:
    //   { get whatever(){}, set whatever(){} }
    //   foo.delete()
    public static void loadPolyfill() {
        if (!pfLoaded) {
            pfLoaded = true;
            if (JsUtils.prop(window, "WebComponents") == null) {
                GWT.runAsync(new RunAsyncCallback() {
                    public void onSuccess() {
                        console.log("WWW" + JsUtils.prop(window, "WebComponents"));
                        if (JsUtils.prop(window, "WebComponents") == null) {
                            console.log("Initialized polyfill.");
                            instance().webcomponents();
                        }
                    }
                    public void onFailure(Throwable reason) {
                    }
                });
            }
        }
    }

    @LibrarySource("ng-vaadin.js")
    protected abstract void angular();

    @LibrarySource("https://raw.githubusercontent.com/webcomponents/webcomponentsjs/master/webcomponents.js")
    protected abstract void webcomponents();
}
