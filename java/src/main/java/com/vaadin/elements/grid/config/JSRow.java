package com.vaadin.elements.grid.config;

import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

import com.google.gwt.dom.client.Element;
import com.vaadin.client.widget.grid.CellReference;
import com.vaadin.client.widget.grid.RowReference;
import com.vaadin.elements.common.js.JS;
import com.vaadin.elements.grid.data.GridDataSource;
import com.vaadin.elements.grid.table.GridColumn;

@JsType(namespace = JS.NAMESPACE_API)
@SuppressWarnings("rawtypes")
public class JSRow {

    private Element grid;
    private CellReference cell;
    private RowReference row;
    private GridColumn column;

    public JSRow(Object reference, Element container) {
        if (reference instanceof CellReference) {
            cell = (CellReference) reference;
            column = (GridColumn) cell.getColumn();
        }
        if (reference instanceof RowReference) {
            row = (RowReference) reference;
            column = (GridColumn) row.getGrid().getColumn(row.getRowIndex());
        }
        grid = container;
    }

    @JsProperty int getIndex() {
        return cell != null ? cell.getRowIndex() : row.getRowIndex();
    }

    @JsProperty Object getData() {
        return cell != null ? column.getValue(cell.getRow()) :
            GridDataSource.extractDataItem(row.getRow());
    }

    @JsProperty Element getElement() {
        return cell != null ? cell.getElement() : row.getElement();
    }

    @JsProperty Element getGrid() {
        return grid;
    }

    @JsProperty String getColumnName() {
        return column.getJsColumn().getName();
    }
}
