package com.vaadin.components.common.js;

import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayMixed;
import com.google.gwt.query.client.Function;
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
    public static final String VAADIN_JS_NAMESPACE = "vaadin";

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

    public static void definePropertyAccessors(Object jso, String propertyName,
            Setter setter, Getter getter) {
        JavaScriptObject setterJSO = setter != null ? JsUtils
                .wrapFunction(new Function() {
                    @Override
                    public void f() {
                        setter.setValue(arguments(0));
                    }
                }) : null;

        JavaScriptObject getterJSO = getter != null ? JsUtils
                .wrapFunction(new Function() {
                    @Override
                    public Object f(Object... args) {
                        JSArray<Object> array = JS.createArray();
                        array.push(getter.getValue());
                        return array;
                    }
                }) : null;
        definePropertyAccessors((JavaScriptObject) jso, propertyName,
                setterJSO, getterJSO);
    }

    private static native void definePropertyAccessors(
            JavaScriptObject jsObject, String propertyName,
            JavaScriptObject setter, JavaScriptObject getter)
    /*-{
      var _value = jsObject[propertyName];

      Object.defineProperty(jsObject, propertyName, {
        get: function() {
            if (getter) {
                return getter()[0];
            }
            return _value;
        },
        set: function(value) {
            if (setter){
                setter(value);
            }
            _value = value;
        }
      });

      if (_value !== undefined){
          jsObject[propertyName] = _value;
      }
    }-*/;

    public interface Setter {
        void setValue(Object value);
    }

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

    public static native JavaScriptObject getError()
    /*-{
        return new Error();
    }-*/;

    public static native JavaScriptObject getUndefined()
    /*-{
        return undefined;
    }-*/;
}
