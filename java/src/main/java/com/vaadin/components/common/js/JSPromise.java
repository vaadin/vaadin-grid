package com.vaadin.components.common.js;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.js.JsExport;
import com.google.gwt.core.client.js.JsNamespace;
import com.google.gwt.core.client.js.JsNoExport;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.Promise;
import com.google.gwt.query.client.Promise.Deferred;
import com.google.gwt.query.client.js.JsUtils.JsFunction;

/**
 * An exported version of the light weight gwtquery promises implementation.
 * Works with any browser, and exported methods allows that it can be chained
 * with native HTML5 promises.
 */
@JsNamespace(JS.VAADIN_JS_NAMESPACE + ".common")
@JsExport
@JsType
public class JSPromise {

    private Promise p;
    public Deferred dfd;

    @JsNoExport
    public JSPromise() {
        dfd = GQuery.Deferred();
        p = dfd.promise();
        defineCatch();
    }

    @JsNoExport
    public JSPromise(Promise then) {
        p = then;
        defineCatch();
    }

    public JSPromise always(JavaScriptObject f) {
        p.always(new JsFunction(f));
        return this;
    }

    public JSPromise done(JavaScriptObject f) {
        p.done(new JsFunction(f));
        return this;
    }

    public JSPromise fail(JavaScriptObject f) {
        p.fail(new JsFunction(f));
        return this;
    }

    public String state() {
        return p.state();
    }

    public JSPromise then(JavaScriptObject f) {
        return new JSPromise(p.then(new Function(){
            public Object f(Object... args) {
                return JS.exec(f, arguments(0));
            }
        }));
    }

    // Since catch is a reserved java word, we have to export this method using JS
    private native void defineCatch() /*-{
        this['catch'] = this.@com.vaadin.components.common.js.JSPromise::fail(*);
    }-*/;
}
