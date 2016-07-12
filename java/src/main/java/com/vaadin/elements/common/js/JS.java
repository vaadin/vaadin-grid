package com.vaadin.elements.common.js;

import java.util.List;

import com.gargoylesoftware.htmlunit.javascript.host.dom.NodeList;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;

/**
 * Class with static utilities for @JsType
 *
 * TODO: revisit when JsInterop supports static or default methods in @JsType
 * interfaces
 *
 */
public abstract class JS {

    private static final String NAMESPACE_VAADIN = "vaadin.elements";
    public static final String NAMESPACE_API = NAMESPACE_VAADIN + "._api";
    public static final String NAMESPACE_GRID = NAMESPACE_VAADIN + ".grid";

    @SuppressWarnings("unchecked")
    public static <T> T createJsObject() {
        return (T) JavaScriptObject.createObject();
    }

    @SuppressWarnings("unchecked")
    public static <T> T createJsArray() {
        return (T) JavaScriptObject.createArray();
    }

    public static <T> JSArray<T> createArray() {
        return createJsArray();
    }

    /**
     * Box a native JS array in a Java List. It does not have any performance
     * penalty because we directly change the native array of super ArrayList
     * implementation.
     */
    public static native <T> List<T> asList(JavaScriptObject o)
    /*-{
        var l = @java.util.ArrayList::new()();
        l.@java.util.ArrayList::array = o;
        return l;
    }-*/;

    public static native boolean isPrimitiveType(Object dataItem)
    /*-{
        return Object(dataItem) !== dataItem;
    }-*/;

    /**
     * Promote a plain JSO to a GWT java class.
     */
    public static native <T> T promoteTo(Object o, Class<?> clz)
    /*-{
        var p = @java.lang.Class::getPrototypeForClass(*)(clz);
        if (o.__proto__ && o.__proto__ !== p) {
            o.__proto__ = p;
            o.__reassignPending = true;
        }
        return o;
    }-*/;

    /**
     * Loop over an object properties and call customized setters which are present in its prototype.
     * Useful after promoting a JSO to Java and make old values effective.
     */
    public static native void reassignProperties(Object o)
    /*-{
        if (o.__reassignPending) {
            delete o.__reassignPending;
            var p = o.__proto__;
            for (var i in o) if (o.hasOwnProperty(i) && p.hasOwnProperty(i)) {
                var v = o[i];
                delete o[i];
                o[i] = v;
            }
        }
    }-*/;

    public static native boolean isUndefinedOrNull(Object o)
    /*-{
      return o === undefined || o === null;
    }-*/;

    public static native boolean isObject(Object o)
    /*-{
      return typeof o === "object" && o !== null;
    }-*/;

    public static native JavaScriptObject getError(String msg)
    /*-{
        return new Error(msg || '');
    }-*/;

    public static native JavaScriptObject getUndefined()
    /*-{
        return undefined;
    }-*/;

    public static native Element querySelector(Element parent, String css)
    /*-{
       return parent.querySelector(css);
    }-*/;
}
