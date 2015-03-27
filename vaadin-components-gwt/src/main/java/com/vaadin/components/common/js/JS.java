package com.vaadin.components.common.js;

import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayMixed;

/**
 * Class with static utilities for @JsType
 *
 * TODO: revisit when JsInterop supports static or default methods in @JsType
 * interfaces
 *
 */
public abstract class JS {

    @SuppressWarnings("unchecked")
    public static <T> T createJsType(Class<T> clz) {
        if (clz == JsArrayMixed.class || clz == JSArray.class) {
            return (T) JavaScriptObject.createArray();
        }
        return (T) JavaScriptObject.createObject();
    }

    @SuppressWarnings("unchecked")
    public static <T> JSArray<T> createArray() {
        return createJsType(JSArray.class);
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

    public static native void defineSetter(Object jsObject,
            String propertyName, JavaScriptObject setter)
    /*-{
      var _value = jsObject[propertyName];

      Object.defineProperty(jsObject, propertyName, {
        get: function() {
            return _value;
        },
        set: function(value) {
            setter(value);
            _value = value;
        }
      });

      if (_value !== undefined){
          jsObject[propertyName] = _value;
      }
    }-*/;
}
