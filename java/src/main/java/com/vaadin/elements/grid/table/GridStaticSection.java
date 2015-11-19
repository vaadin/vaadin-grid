package com.vaadin.elements.grid.table;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.js.JsExport;
import com.google.gwt.core.client.js.JsNamespace;
import com.google.gwt.core.client.js.JsNoExport;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.dom.client.Element;
import com.google.gwt.query.client.js.JsUtils;
import com.google.gwt.user.client.ui.SimplePanel;
import com.vaadin.client.widgets.Grid.StaticSection.StaticCell;
import com.vaadin.client.widgets.Grid.StaticSection.StaticRow;
import com.vaadin.elements.common.js.JS;
import com.vaadin.elements.common.js.JS.Setter;
import com.vaadin.elements.common.js.JSArray;
import com.vaadin.elements.grid.GridElement;
import com.vaadin.elements.grid.ViolatedGrid;
import com.vaadin.elements.grid.config.JSStaticCell;
import com.vaadin.shared.ui.grid.GridStaticCellType;

@JsNamespace(JS.VAADIN_JS_NAMESPACE + ".grid._api")
@JsExport
@JsType
public class GridStaticSection {

    private final GridElement gridElement;
    private final ViolatedGrid grid;
    private final Map<StaticCell, JSStaticCell> cells = new HashMap<>();
    private final static String CONTENT_WRAPPER = "<span style='overflow: hidden;text-overflow: ellipsis;'>%CONTENT%</span>";

    public GridStaticSection(GridElement gridElement) {
        this.gridElement = gridElement;
        this.grid = gridElement.getGrid();
    }

    @JsNoExport
    public JSStaticCell obtainJSStaticCell(StaticCell cell) {
        if (!cells.containsKey(cell)) {
            JSStaticCell jsCell = JS.createJsType(JSStaticCell.class);
            jsCell.setColspan(cell.getColspan());

            if (cell.getType() == GridStaticCellType.WIDGET) {
                jsCell.setContent(cell.getWidget().getElement());
            } else if (cell.getType() == GridStaticCellType.TEXT) {
                jsCell.setContent("".equals(cell.getText()) ? null : cell
                        .getText());
            } else if (cell.getType() == GridStaticCellType.HTML) {
                jsCell.setContent(cell.getHtml());
            }

            cells.put(cell, jsCell);
            bind(jsCell, cell, "colspan",
                    v -> cell.setColspan(((Double) v).intValue()));
            bind(jsCell, cell, "className", v -> cell.setStyleName((String) v));

            bind(jsCell, cell, "content", v -> contentChanged(v, cell));
        }

        return cells.get(cell);
    }

    private void contentChanged(Object content, StaticCell cell) {
        // "column" is non-null only if the given cell is on default header row
        GridColumn column = getColumnByDefaultHeaderCell(obtainJSStaticCell(cell));
        if (content == null) {
            contentCleared(cell, column);
        } else if (JS.isPrimitiveType(content) || content instanceof Number) {
            applyStringContent(cell, String.valueOf(content), column);
        } else if (JsUtils.isElement(content)) {
            applyElementContent(cell, (Element) content, column);
        }
    }

    private void contentCleared(StaticCell cell, GridColumn column) {
        if (column != null) {
            // Default header cell content is empty, use
            // column name as the header caption and cell html instead
            String name = column.getJsColumn().getName();
            name = name != null ? name : "";
            column.setHeaderCaption(name);
            cell.setHtml(CONTENT_WRAPPER.replace("%CONTENT%", name));
        } else {
            cell.setHtml(null);
        }
    }

    private void applyStringContent(StaticCell cell, String content,
            GridColumn column) {
        // Primitive content
        if (column != null) {
            column.setHeaderCaption(content);
        }
        cell.setHtml(CONTENT_WRAPPER.replace("%CONTENT%", content));
    }

    private void applyElementContent(StaticCell cell, Element content,
            GridColumn column) {
        if (column != null) {
            String name = column.getJsColumn().getName();
            if (name != null) {
                column.setHeaderCaption(name);
            } else {
                column.setHeaderCaption(String.valueOf(content));
            }
        }
        cell.setWidget(new SimplePanel(content) {
        });
    }

    private GridColumn getColumnByDefaultHeaderCell(JSStaticCell cell) {
        GridColumn result = null;
        for (GridColumn col : gridElement.getDataColumns()) {
            if (col.getDefaultHeaderCellReference() == cell) {
                result = col;
                break;
            }
        }
        return result;
    }

    private void bind(JSStaticCell cell, StaticCell staticCell,
            String propertyName, Setter setter) {
        JS.definePropertyAccessors(cell, propertyName, v -> {
            setter.setValue(v);
            gridElement.updateWidth();
            grid.refreshStaticSection(staticCell);
        }, null);
    }

    public JSStaticCell getHeaderCell(int rowIndex, Object columnId) {
        GridColumn column = getColumnById(columnId);
        return getHeaderCellByColumn(rowIndex, column);
    }

    @JsNoExport
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
