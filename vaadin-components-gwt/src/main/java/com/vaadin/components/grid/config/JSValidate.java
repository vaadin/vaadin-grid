package com.vaadin.components.grid.config;

import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;

/**
 * A collection of validations for JS values.
 */
public enum JSValidate {
    Pixel("pixel value", RegExp.compile("^([\\d\\.]+)px$", "i"), Integer.class),
    Int("int value", RegExp.compile("^(\\d+)"), Integer.class),
    Double("double value", RegExp.compile("^([\\d\\.]+)"), Double.class);

    private String message;
    private RegExp regex;
    private Class<?> type;

    @SuppressWarnings("unchecked")
    public <T> T val(String s) {
        MatchResult r = regex.exec(s);
        String v;
        if (r == null || (v = r.getGroup(1)) == null) {
            throw new RuntimeException("Invalid " + message + "(" + s
                    + "), valid format is " + regex.toString());
        }
        try {
            if (type == Integer.class) {
                return (T) Integer.valueOf(checkEmpty(v));
            }
            if (type == Double.class) {
                return (T) java.lang.Double.valueOf(checkEmpty(v));
            }
            return (T) v;
        } catch (Exception e) {
            throw new RuntimeException("Invalid " + message
                    + ", valid format is " + regex.toString());
        }
    }

    private String checkEmpty(String s) {
        return s == null || s.trim().isEmpty() ? "0" : s.trim();
    }

    private JSValidate(String message, RegExp regex, Class<?> type) {
        this.message = message;
        this.regex = regex;
        this.type = type;
    }
}
