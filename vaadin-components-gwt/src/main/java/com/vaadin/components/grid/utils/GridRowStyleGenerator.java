package com.vaadin.components.grid.utils;

import com.google.gwt.dom.client.Element;
import com.vaadin.client.widget.grid.RowReference;
import com.vaadin.client.widget.grid.RowStyleGenerator;
import com.vaadin.components.common.js.JS;
import com.vaadin.components.grid.config.JSRow;
import com.vaadin.components.grid.config.JSRowClassName;

/**
 * A GWT/JavaScript bridge for Grid RowStyleGenerator.
 */
public class GridRowStyleGenerator implements RowStyleGenerator<Object> {

    private JSRowClassName rowClassName;
    private Element container;

    public GridRowStyleGenerator(JSRowClassName rowClassName, Element container) {
        this.rowClassName = rowClassName;
        this.container = container;
    }

    public JSRowClassName getRowClassName() {
        return rowClassName;
    }

    @Override
    public String getStyle(RowReference<Object> rowReference) {
        JSRow row = JS.createJsType(JSRow.class);
        row.setIndex(rowReference.getRowIndex());
        row.setData(rowReference.getRow());
        row.setGrid(container);
        row.setElement(rowReference.getElement());
        return rowClassName.getStyle(row);
    }
}
