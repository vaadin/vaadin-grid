package com.vaadin.elements.grid.config;

import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

import com.vaadin.elements.common.js.JS;
import com.vaadin.elements.common.js.JSFunction;
import com.vaadin.elements.grid.GridElement;
import com.vaadin.elements.grid.table.GridColumn;
import com.vaadin.shared.ui.grid.GridConstants;

/**
 * This class is a JsInterop object for the JS object representing a grid column
 * configuration.
 */
@JsType(namespace = JS.NAMESPACE_API)
public class JSColumn {

    public static JSColumn promote(Object o) {
        return JS.promoteTo(o, JSColumn.class);
    }

    private GridColumn col;
    private GridElement grid;
    private String name;
    private JSFunction<?, JSCell> renderer;

    public void configure(GridElement gridElement, GridColumn column) {
        col = column;
        grid = gridElement;
        JS.reassignProperties(this);
    }

    @JsProperty
    public String getName() {
        return name;
    }

    @JsProperty
    public void setName(String s) {
        name = s;
        col.nameChanged(s);
    }

    @JsProperty
    public JSFunction<?, JSCell> getRenderer() {
        return renderer;
    }

    @JsProperty
    public void setRenderer(JSFunction<?, JSCell> o) {
        renderer = o;
        col.setRenderer((cell, data) -> {
            renderer.f(new JSCell(cell, grid.getContainer()));
        });
    }

    @JsProperty
    public String getHidingToggleText() {
        return col.getHidingToggleCaption();
    }

    @JsProperty
    public void setHidingToggleText(String s) {
        col.setHidingToggleCaption(s == null ? null : s.toString());
    }

    @JsProperty
    public int getFlex() {
        return col.getExpandRatio();
    }

    @JsProperty
    public void setFlex(int f) {
        col.setExpandRatio(f);
    }

    @JsProperty
    public boolean getSortable() {
        return col.isSortable();
    }

    @JsProperty
    public void setSortable(boolean b) {
        col.setSortable(b);
    }

    @JsProperty
    public boolean getHidable() {
        return col.isHidable();
    }

    @JsProperty
    public void setHidable(boolean b) {
        col.setHidable(b);
    }

    @JsProperty
    public boolean getReadonly() {
        return !col.isEditable();
    }

    @JsProperty
    public void setReadonly(boolean b) {
        col.setEditable(!b);
    }

    @JsProperty
    public double getMinWidth() {
        return col.getMinimumWidth();
    }

    @JsProperty
    public void setMinWidth(double d) {
        col.setMinimumWidth(JS.isUndefinedOrNull(d) ? GridConstants.DEFAULT_MIN_WIDTH
                : d);
    }

    @JsProperty
    public double getMaxWidth() {
        return col.getMaximumWidth();
    }

    @JsProperty
    public void setMaxWidth(double d) {
        col.setMaximumWidth(JS.isUndefinedOrNull(d) ? GridConstants.DEFAULT_MAX_WIDTH
                : d);
    }

    @JsProperty
    public double getWidth() {
        return col.getWidth();
    }

    @JsProperty
    public void setWidth(double d) {
        col.setWidth(JS.isUndefinedOrNull(d) ? GridConstants.DEFAULT_COLUMN_WIDTH_PX
                : d);
    }

    @JsProperty
    public boolean getHidden() {
        return col.isHidden();
    }

    @JsProperty
    public void setHidden(boolean b) {
        col.setHidden(b);
        grid.updateWidth();
    }
}
