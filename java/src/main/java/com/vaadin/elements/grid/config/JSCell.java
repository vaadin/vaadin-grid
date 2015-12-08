package com.vaadin.elements.grid.config;

import jsinterop.annotations.JsIgnore;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

import com.google.gwt.dom.client.Element;
import com.vaadin.client.widget.grid.CellReference;
import com.vaadin.elements.common.js.JS;
import com.vaadin.elements.grid.table.GridColumn;

/**
 * This class is a JsInterop wrapper for the JS object representing a cell
 * object passed to cell style generator.
 */
@JsType(namespace = JS.NAMESPACE_API)
public class JSCell {

    private final CellReference<Object> cell;
    private final JSRow jsRow;
    private Element grid;

    @JsIgnore
    public JSCell(CellReference<Object> cellReference, Element container) {
        cell = cellReference;
        jsRow = new JSRow(cell, container);
    }

    @JsProperty
    Element getElement() {
        return cell.getElement();
    }

    @JsProperty
    int getIndex() {
        return cell.getRowIndex();
    }

    @JsProperty
    String getColumnName() {
        return ((GridColumn) cell.getColumn()).getJsColumn().getName();
    }

    @JsProperty
    Object getData() {
        return cell.getColumn().getValue(cell.getRow());
    }

    @JsProperty
    Element getGrid() {
        return grid;
    }

    @JsProperty
    JSRow getRow() {
        return jsRow;
    }
}
