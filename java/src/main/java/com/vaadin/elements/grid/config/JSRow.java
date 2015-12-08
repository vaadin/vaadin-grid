package com.vaadin.elements.grid.config;

import jsinterop.annotations.JsIgnore;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

import com.google.gwt.dom.client.Element;
import com.vaadin.client.widget.grid.CellReference;
import com.vaadin.client.widget.grid.RowReference;
import com.vaadin.elements.common.js.JS;
import com.vaadin.elements.grid.data.GridDataSource;

@JsType(namespace = JS.NAMESPACE_API)
@SuppressWarnings("rawtypes")
public class JSRow {

    private final CellReference cell;
    private final RowReference row;
    public final Element grid;

    @JsIgnore
    public JSRow(RowReference row, Element container) {
        this.row = row;
        this.cell = null;
        this.grid = container;
    }

    @JsIgnore
    public JSRow(CellReference cell, Element container) {
        this.row = null;
        this.cell = cell;
        this.grid = container;
    }

    @JsProperty
    int getIndex() {
        return cell != null ? cell.getRowIndex() : row.getRowIndex();
    }

    @JsProperty
    Object getData() {
        return GridDataSource.extractDataItem(cell != null ? cell.getRow()
                : row.getRow());
    }

    @JsProperty
    Element getElement() {
        return cell != null ? cell.getElement().getParentElement() : row
                .getElement();
    }
}
