package com.vaadin.components.grid.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.vaadin.client.widgets.Grid.SelectionMode;
import com.vaadin.shared.data.sort.SortDirection;

/**
 * A collection of java enums which can be mapped to JS strings and
 * validated through methods in this class.
 */
public enum JSEnums {
    Direction("sort direction", SortDirection.values(), "asc", "asc", "desc"),
    Format("format", JSHeaderCell.Format.values(), "html"),
    Selection("selection mode", SelectionMode.values(), "single");

    public String def;
    private Enum<?>[] enums;
    private List<String> vals;
    private String name;

    @SuppressWarnings("unchecked")
    public <T> T val(String s) {
        s = s == null || s.isEmpty() ? def : s.toLowerCase();
        if (!vals.contains(s)) {
            throw new RuntimeException("Invalid " + name + ", valid options are: " + vals);
        }
        if (enums != null) {
            for (Enum<?> e : enums) {
                if (val(e).equals(s)) {
                    return (T)e;
                }
            }
        }
        return null;
    }

    public String val(Enum<?> e) {
        for (String s : vals) {
            if (e.toString().toLowerCase().startsWith(s.toLowerCase())) {
                return s;
            }
        }
        return def;
    }

    JSEnums(String message, Enum<?>[] enumVals, String defaultStr, String... strVals) {
        this.name = message;
        this.enums = enumVals;
        this.def = defaultStr;
        if (strVals.length == 0) {
            List<String> l = new ArrayList<String>();
            for (Enum<?> e : enumVals) {
                l.add(e.toString().toLowerCase());
            }
            this.vals = l;
        } else {
            this.vals = Arrays.asList(strVals);
        }
    }
}
