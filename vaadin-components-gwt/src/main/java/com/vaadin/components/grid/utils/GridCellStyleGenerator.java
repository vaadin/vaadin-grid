package com.vaadin.components.grid.utils;

import com.google.gwt.dom.client.Element;
import com.vaadin.client.widget.grid.CellReference;
import com.vaadin.client.widget.grid.CellStyleGenerator;
import com.vaadin.components.grid.config.JS;
import com.vaadin.components.grid.config.JSCell;
import com.vaadin.components.grid.config.JSCellClassName;
import com.vaadin.components.grid.config.JSRow;
import com.vaadin.components.grid.head.GridColumn;

/**
 * A GWT/JavaScript bridge for Grid CellStyleGenerator.
 */
public class GridCellStyleGenerator implements CellStyleGenerator<Object> {

    private JSCellClassName cellClassName;
    private Element container;

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
        JSCell cell = JS.createJsType(JSCell.class);
        String columnName = ((GridColumn) cellReference.getColumn())
                .getJsColumn().name();
        cell.setColumnName(columnName);
        cell.setElement(cellReference.getElement());

        JSRow row = JS.createJsType(JSRow.class);
        row.setIndex(cellReference.getRowIndex());
        row.setData(cellReference.getRow());
        row.setGrid(container);
        row.setElement(cellReference.getElement().getParentElement());
        cell.setRow(row);
        return cellClassName.getStyle(cell);
    }

}
