package com.vaadin.elements.common.js;

import java.util.List;

import jsinterop.annotations.JsFunction;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.query.client.js.JsUtils;

/**
 * Class with static utilities for @JsType
 *
 * TODO: revisit when JsInterop supports static or default methods in @JsType
 * interfaces
 *
 */
public abstract class JS {

    // This has to match with the @JsNamespace of the package-info of exported
    // components
    public static final String VAADIN_JS_NAMESPACE = "vaadin.elements";

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

    public static native void definePropertyAccessors(Object jsObject, String propertyName, Setter setter, Getter getter)
    /*-{
      var _value = jsObject[propertyName];

      Object.defineProperty(jsObject, propertyName, {
        get: function() {
            return typeof getter === 'function' ? getter() : _value;
        },
        set: function(value) {
            if (typeof setter === 'function'){
                setter(value);
            }
            _value = value;
        }
      });

      if (_value !== undefined){
          jsObject[propertyName] = _value;
      }
    }-*/;

    @JsFunction
    public interface Setter {
        void setValue(Object value);
    }

    @JsFunction
    public interface Getter {
        Object getValue();
    }

    public static <T> T exec(Object o, Object arg) {
        return JsUtils.jsni((JavaScriptObject) o, "call", (JavaScriptObject) o,
                arg);
    }

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
}
