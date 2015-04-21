package com.vaadin.components.grid.utils;

import com.google.gwt.dom.client.Element;
import com.vaadin.client.widget.grid.RowReference;
import com.vaadin.client.widget.grid.RowStyleGenerator;
import com.vaadin.components.grid.config.JSRow;
import com.vaadin.components.grid.config.JSRowClassName;

/**
 * A GWT/JavaScript bridge for Grid RowStyleGenerator.
 */
public class GridRowStyleGenerator implements RowStyleGenerator<Object> {

    private final JSRowClassName rowClassName;
    private final Element container;

    public GridRowStyleGenerator(JSRowClassName rowClassName, Element container) {
        this.rowClassName = rowClassName;
        this.container = container;
    }

    public JSRowClassName getRowClassName() {
        return rowClassName;
    }

    @Override
    public String getStyle(RowReference<Object> rowReference) {
        JSRow row = JSRow.create(rowReference, container);
        return rowClassName.getStyle(row);
    }
}
