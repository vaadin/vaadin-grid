package com.vaadin.elements.grid.table;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jsinterop.annotations.JsIgnore;
import jsinterop.annotations.JsType;

import com.vaadin.client.widgets.Grid.StaticSection.StaticCell;
import com.vaadin.client.widgets.Grid.StaticSection.StaticRow;
import com.vaadin.elements.common.js.JS;
import com.vaadin.elements.common.js.JSArray;
import com.vaadin.elements.grid.GridElement;
import com.vaadin.elements.grid.ViolatedGrid;
import com.vaadin.elements.grid.config.JSStaticCell;
@JsType(namespace = JS.NAMESPACE_API)
public class GridStaticSection {

    private final GridElement gridElement;
    private final ViolatedGrid grid;
    private final Map<StaticCell, JSStaticCell> cells = new HashMap<>();

    public GridStaticSection(GridElement gridElement) {
        this.gridElement = gridElement;
        this.grid = gridElement.getGrid();
    }

    @JsIgnore
    public JSStaticCell obtainJSStaticCell(StaticCell cell) {
        if (!cells.containsKey(cell)) {
            cells.put(cell, new JSStaticCell(gridElement, cell));
        }
        return cells.get(cell);
    }

    public JSStaticCell getHeaderCell(int rowIndex, Object columnId) {
        GridColumn column = getColumnById(columnId);
        return getHeaderCellByColumn(rowIndex, column);
    }

    @JsIgnore
    public JSStaticCell getHeaderCellByColumn(int rowIndex, GridColumn column) {
        StaticCell cell = grid.getHeaderRow(rowIndex).getCell(column);
        return obtainJSStaticCell(cell);
    }

    private GridColumn getColumnById(Object columnId) {
        return gridElement.getDataColumns().get(
                gridElement.getColumnIndexByIndexOrName(columnId));
    }

    public JSStaticCell getFooterCell(int rowIndex, Object columnId) {
        GridColumn column = getColumnById(columnId);
        StaticCell cell = grid.getFooterRow(rowIndex).getCell(column);
        return obtainJSStaticCell(cell);
    }

    public void addHeader(Object rowIndex, JSArray<?> cellContent) {
        int index = getInteger(rowIndex, grid.getHeaderRowCount());
        StaticRow<?> row = grid.addHeaderRowAt(index);
        if (cellContent != null) {
            setStaticRowCellContent(row, cellContent);
        }

        grid.refreshHeader();
        gridElement.updateHeight();
    }

    public void addFooter(Object rowIndex, JSArray<?> cellContent) {
        int index = getInteger(rowIndex, grid.getFooterRowCount());
        StaticRow<?> row = grid.addFooterRowAt(index);
        if (cellContent != null) {
            setStaticRowCellContent(row, cellContent);
        }

        grid.refreshFooter();
        gridElement.updateHeight();
    }

    public int getFooterRowCount() {
        return grid.getFooterRowCount();
    }

    public int getHeaderRowCount() {
        return grid.getHeaderRowCount();
    }

    private int getInteger(Object value, int defaultValue) {
        return value != null ? Integer.parseInt(String.valueOf(value))
                : defaultValue;
    }

    private void setStaticRowCellContent(StaticRow<?> row,
            JSArray<?> cellContent) {
        List<GridColumn> dataColumns = gridElement.getDataColumns();
        for (int i = 0; i < dataColumns.size(); i++) {
            GridColumn column = dataColumns.get(i);
            if (i < cellContent.size()) {
                StaticCell cell = row.getCell(column);
                obtainJSStaticCell(cell).setContent(cellContent.get(i));
            }
        }
    }

    public void removeHeader(int rowIndex) {
        grid.removeHeaderRow(rowIndex);
        grid.refreshHeader();
        gridElement.updateHeight();
    }

    public void removeFooter(int rowIndex) {
        grid.removeFooterRow(rowIndex);
        grid.refreshFooter();
        gridElement.updateHeight();
    }

    public void setHeaderRowClassName(int rowIndex, String styleName) {
        grid.getHeaderRow(rowIndex).setStyleName(styleName);
        grid.refreshHeader();
    }

    public void setFooterRowClassName(int rowIndex, String styleName) {
        grid.getFooterRow(rowIndex).setStyleName(styleName);
        grid.refreshFooter();
    }

    public void setDefaultHeader(int rowIndex) {
        grid.setDefaultHeaderRow(grid.getHeaderRow(rowIndex));
        grid.refreshHeader();
    }

    public int getDefaultHeader() {
        int result = 0;
        for (int rowIndex = 0; rowIndex < grid.getHeaderRowCount(); rowIndex++) {
            if (grid.getHeaderRow(rowIndex).isDefault()) {
                result = rowIndex;
                break;
            }
        }
        return result;
    }

    public boolean isHeaderHidden() {
        return !grid.isHeaderVisible();
    }

    public void setHeaderHidden(boolean hidden) {
        grid.setHeaderVisible(!hidden);
        grid.refreshHeader();
        gridElement.updateHeight();
    }

    public boolean isFooterHidden() {
        return !grid.isFooterVisible();
    }

    public void setFooterHidden(boolean hidden) {
        grid.setFooterVisible(!hidden);
        grid.refreshFooter();
        gridElement.updateHeight();
    }
}
