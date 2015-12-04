package com.vaadin.elements.grid.config;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.google.gwt.query.client.js.JsUtils;
import com.vaadin.client.widget.grid.CellReference;
import com.vaadin.elements.common.js.JS;
import com.vaadin.elements.grid.data.GridDataSource;
import com.vaadin.elements.grid.table.GridColumn;

public interface JSCell {

    static JavaScriptObject create(CellReference<Object> cell, Element container) {
        JavaScriptObject jsCell = JS.createJsObject();
        GridColumn column = (GridColumn) cell.getColumn();
        JS.definePropertyAccessors(jsCell, "element", null,
                () -> cell.getElement());
        JS.definePropertyAccessors(jsCell, "index", null,
                () -> cell.getColumnIndex());
        JS.definePropertyAccessors(jsCell, "columnName", null, () -> column
                .getJsColumn().getName());
        JS.definePropertyAccessors(jsCell, "data", null,
                () -> column.getValue(cell.getRow()));

        JSRow row = JS.createJsObject();
        JS.definePropertyAccessors(row, "index", null, () -> cell.getRowIndex());
        JS.definePropertyAccessors(row, "data", null,
                () -> GridDataSource.extractDataItem(cell.getRow()));
        JS.definePropertyAccessors(row, "element", null, () -> cell
                .getElement().getParentElement());
        JsUtils.prop((JavaScriptObject) row, "grid", container);
        JsUtils.prop(jsCell, "row", row);

        return jsCell;
    }

}
