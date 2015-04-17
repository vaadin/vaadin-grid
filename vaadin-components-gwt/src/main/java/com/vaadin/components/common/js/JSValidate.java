package com.vaadin.components.common.js;

import com.google.gwt.dom.client.Element;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.js.JsUtils;
import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;

/**
 * A collection of validations for JS values.
 */
public enum JSValidate {
    //@formatter:off
    String("string value", "(.+)", String.class, "", ""),
    Pixel("pixel value", "([\\d\\.]+)(px)?$", String.class, "", ""),
    Integer("int value", "([+-]?\\d+)", Integer.class, 0, 0),
    Double("double value", "([+-]?[\\d\\.]+)", Double.class, 0, 0),
    Boolean("boolean value", "(|true|false)", Boolean.class, true, false);
    //@formatter:on

    private final String message;
    private final RegExp regex;
    private final Class<?> type;
    private final Object defaultIfEmpty;
    private final Object defaultIfNull;

    private JSValidate(String message, String regex, Class<?> type,
            Object defaultIfEmpty, Object defaultIfNull) {
        this.message = message;
        this.regex = RegExp.compile("^" + regex + "$", "i");
        this.type = type;
        this.defaultIfEmpty = defaultIfEmpty;
        this.defaultIfNull = defaultIfNull;
    }

    public <T> T attr(GQuery g, String s) {
        return attr(g.get(0), s, defaultIfEmpty, defaultIfNull);
    }

    public <T> T attr(GQuery g, String s, Object defaultIfEmpty,
            Object defaultIfNull) {
        return attr(g.get(0), s, defaultIfEmpty, defaultIfNull);
    }

    public <T> T attr(Element el, String s) {
        return val(
                el != null && JsUtils.hasAttribute(el, s) ? el.getAttribute(s)
                        : null, defaultIfEmpty, defaultIfNull);
    }

    public <T> T attr(Element el, String s, Object defaultIfEmpty,
            Object defaultIfNull) {
        return val(
                el != null && el.hasAttribute(s) ? el.getAttribute(s) : null,
                defaultIfEmpty, defaultIfNull);
    }

    public <T> T val(String s) {
        return val(s, defaultIfEmpty, defaultIfNull);
    }

    public <T> T val(Object o, Object defaultIfEmpty, Object defaultIfNull) {
        java.lang.String string = JS.isUndefinedOrNull(o) ? null
                : java.lang.String.valueOf(o);
        return val(string, defaultIfEmpty, defaultIfNull);
    }

    @SuppressWarnings("unchecked")
    public <T> T val(String s, Object defaultIfEmpty, Object defaultIfNull) {
        if (s == null || JS.isUndefinedOrNull(s)) {
            return (T) defaultIfNull;
        }
        if (s.trim().isEmpty()) {
            return (T) defaultIfEmpty;
        }
        MatchResult r = regex.exec(s);
        String v;
        if (r == null || (v = r.getGroup(1)) == null) {
            throw new RuntimeException("Invalid " + message + "(" + s
                    + "), valid format is " + regex.toString());
        }
        try {
            if (type == String.class) {
                return (T) v;
            }
            if (type == Integer.class) {
                return (T) java.lang.Integer.valueOf(v);
            }
            if (type == Double.class) {
                return (T) java.lang.Double.valueOf(v);
            }
            if (type == Boolean.class) {
                return (T) java.lang.Boolean.valueOf(v);
            }
            return (T) v;
        } catch (Exception e) {
            throw new RuntimeException("Invalid " + message
                    + ", valid format is " + regex.toString());
        }
    }
}
