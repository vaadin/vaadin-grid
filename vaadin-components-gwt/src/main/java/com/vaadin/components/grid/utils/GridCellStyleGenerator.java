package com.vaadin.components.grid.utils;

import com.google.gwt.dom.client.Element;
import com.vaadin.client.widget.grid.CellReference;
import com.vaadin.client.widget.grid.CellStyleGenerator;
import com.vaadin.components.grid.config.JSCell;
import com.vaadin.components.grid.config.JSCellClassName;

/**
 * A GWT/JavaScript bridge for Grid CellStyleGenerator.
 */
public class GridCellStyleGenerator implements CellStyleGenerator<Object> {

    private final JSCellClassName cellClassName;
    private final Element container;

    public GridCellStyleGenerator(JSCellClassName cellClassName,
            Element container) {
        this.cellClassName = cellClassName;
        this.container = container;
    }

    public JSCellClassName getCellClassName() {
        return cellClassName;
    }

    @Override
    public String getStyle(CellReference<Object> cellReference) {
        JSCell jsCell = JSCell.create(cellReference, container);
        return cellClassName.getStyle(jsCell);
    }

}
