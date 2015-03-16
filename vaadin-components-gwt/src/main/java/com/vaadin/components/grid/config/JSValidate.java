package com.vaadin.components.grid.config;

import com.google.gwt.dom.client.Element;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.js.JsUtils;
import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;

/**
 * A collection of validations for JS values.
 */
public enum JSValidate {
    Pixel("pixel value", RegExp.compile("^([\\d\\.]+)(px)?$", "i"), String.class, "", ""),
    Integer("int value", RegExp.compile("^([+-]?\\d+)"), Integer.class, 0, 0),
    Flex("int value", RegExp.compile("^([+-]?\\d+)"), Integer.class, 0, 1),
    Double("double value", RegExp.compile("^([+-]?[\\d\\.]+)"), Double.class, 0, 0),
    Boolean("boolean value", RegExp.compile("^(|true|false)", "i"), Boolean.class, true, false);

    private final String message;
    private final RegExp regex;
    private final Class<?> type;
    private final Object defaultIfEmpty;
    private final Object defaultIfNull;

    private JSValidate(String message, RegExp regex, Class<?> type,
            Object defaultIfEmpty, Object defaultIfNull) {
        this.message = message;
        this.regex = regex;
        this.type = type;
        this.defaultIfEmpty = defaultIfEmpty;
        this.defaultIfNull = defaultIfNull;
    }

    public <T> T attr(GQuery g, String s) {
        return attr(g.get(0), s);
    }

    public <T> T attr(Element el, String s) {
        return val(el != null && JsUtils.hasAttribute(el, s) ? el.getAttribute(s) : null);
    }

    @SuppressWarnings("unchecked")
    public <T> T val(String s) {
        if (s == null) {
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
