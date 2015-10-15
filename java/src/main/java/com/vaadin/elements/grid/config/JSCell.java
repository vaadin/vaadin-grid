package com.vaadin.elements.grid.config;

import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.dom.client.Element;
import com.vaadin.client.widget.grid.CellReference;
import com.vaadin.elements.common.js.JS;
import com.vaadin.elements.grid.table.GridColumn;

/**
 * This class is a JsInterop wrapper for the JS object representing a cell
 * object passed to cell style generator.
 */
@JsType
public interface JSCell {

    static JSCell create(CellReference<Object> cell, Element container) {
        JSCell jsCell = JS.createJsType(JSCell.class);
        GridColumn column = (GridColumn) cell.getColumn();
        JS.definePropertyAccessors(jsCell, "element", null,
                () -> cell.getElement());
        JS.definePropertyAccessors(jsCell, "index", null,
                () -> cell.getColumnIndex());
        JS.definePropertyAccessors(jsCell, "columnName", null, () -> column
                .getJsColumn().getName());
        JS.definePropertyAccessors(jsCell, "data", null,
                () -> column.getValue(cell.getRow()));

        JSRow row = JS.createJsType(JSRow.class);
        JS.definePropertyAccessors(row, "index", null, () -> cell.getRowIndex());
        JS.definePropertyAccessors(row, "data", null, () -> cell.getRow());
        JS.definePropertyAccessors(row, "element", null, () -> cell
                .getElement().getParentElement());
        row.setGrid(container);
        jsCell.setRow(row);

        return jsCell;
    }

    @JsProperty
    String getColumnName();

    @JsProperty
    void setColumnName(String columnName);

    @JsProperty
    Element getElement();

    @JsProperty
    void setElement(Element element);

    @JsProperty
    JSRow getRow();

    @JsProperty
    void setRow(JSRow row);

    @JsProperty
    Object getData();

    @JsProperty
    void setData(Object data);

    @JsProperty
    int getIndex();

    @JsProperty
    void setIndex(int index);

}
