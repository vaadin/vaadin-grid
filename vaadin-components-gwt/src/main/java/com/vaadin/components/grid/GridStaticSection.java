package com.vaadin.components.grid;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.js.JsExport;
import com.google.gwt.core.client.js.JsNamespace;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.dom.client.Element;
import com.google.gwt.query.client.js.JsUtils;
import com.google.gwt.user.client.ui.SimplePanel;
import com.vaadin.client.widgets.Grid;
import com.vaadin.client.widgets.Grid.Column;
import com.vaadin.client.widgets.Grid.StaticSection.StaticCell;
import com.vaadin.client.widgets.Grid.StaticSection.StaticRow;
import com.vaadin.components.common.js.JS;
import com.vaadin.components.common.js.JS.PropertyValueSetter;
import com.vaadin.components.common.js.JSArray;
import com.vaadin.components.common.util.Elements;
import com.vaadin.components.grid.config.JSStaticCell;
import com.vaadin.shared.ui.grid.GridStaticCellType;

@JsNamespace(Elements.VAADIN_JS_NAMESPACE)
@JsExport
@JsType
public class GridStaticSection implements PropertyValueSetter {

    private final GridComponent gridComponent;
    private final Grid<Object> grid;
    private final Map<StaticCell, JSStaticCell> cells = new HashMap<>();

    public GridStaticSection(GridComponent gridComponent) {
        this.gridComponent = gridComponent;
        this.grid = gridComponent.getGrid();
    }

    private JSStaticCell assureJSStaticCell(StaticCell cell) {
        if (!cells.containsKey(cell)) {
            JSStaticCell jsCell = JS.createJsType(JSStaticCell.class);
            jsCell.setColspan(cell.getColspan());

            if (cell.getType() == GridStaticCellType.WIDGET) {
                jsCell.setContent(cell.getWidget().getElement());
            } else if (cell.getType() == GridStaticCellType.TEXT) {
                jsCell.setContent(cell.getText());
            } else if (cell.getType() == GridStaticCellType.HTML) {
                jsCell.setContent(cell.getHtml());
            }

            for (String propertyName : Arrays.asList("colspan", "content")) {
                JS.defineSetter(jsCell, cell, propertyName, this);
            }
            cells.put(cell, jsCell);
        }

        return cells.get(cell);
    }

    public JSStaticCell getHeaderCell(int rowIndex, Object columnId) {
        Column<?, ?> column = gridComponent
                .getColumnByJSColumnNameOrIndex(columnId);
        StaticCell cell = grid.getHeaderRow(rowIndex).getCell(column);
        return assureJSStaticCell(cell);
    }

    public JSStaticCell getFooterCell(int rowIndex, Object columnId) {
        Column<?, ?> column = gridComponent
                .getColumnByJSColumnNameOrIndex(columnId);
        StaticCell cell = grid.getFooterRow(rowIndex).getCell(column);
        return assureJSStaticCell(cell);
    }

    public void addHeader(Object rowIndex, JSArray<?> cellContent) {
        int index = getInteger(rowIndex, grid.getHeaderRowCount());
        StaticRow<?> row = grid.addHeaderRowAt(index);
        if (cellContent != null) {
            setStaticRowCellContent(row, cellContent);
        }
        gridComponent.redraw();
    }

    public void addFooter(Object rowIndex, JSArray<?> cellContent) {
        int index = getInteger(rowIndex, grid.getFooterRowCount());
        StaticRow<?> row = grid.addFooterRowAt(index);
        if (cellContent != null) {
            setStaticRowCellContent(row, cellContent);
        }
        gridComponent.redraw();
    }

    private int getInteger(Object value, int defaultValue) {
        return value != null ? Integer.parseInt(String.valueOf(value))
                : defaultValue;
    }

    private void setStaticRowCellContent(StaticRow<?> row,
            JSArray<?> cellContent) {
        List<Column<?, Object>> dataColumns = gridComponent.getDataColumns();
        for (int i = 0; i < dataColumns.size(); i++) {
            Column<?, Object> column = dataColumns.get(i);
            if (i < cellContent.size()) {
                StaticCell cell = row.getCell(column);
                assureJSStaticCell(cell).setContent(cellContent.get(i));
            }
        }
    }

    public void removeHeader(int rowIndex) {
        grid.removeHeaderRow(rowIndex);
        gridComponent.redraw();
    }

    public void removeFooter(int rowIndex) {
        grid.removeFooterRow(rowIndex);
        gridComponent.redraw();
    }

    public void setHeaderRowClassName(int rowIndex, String styleName) {
        grid.getHeaderRow(rowIndex).setStyleName(styleName);
        gridComponent.redraw();
    }

    public void setFooterRowClassName(int rowIndex, String styleName) {
        grid.getFooterRow(rowIndex).setStyleName(styleName);
        gridComponent.redraw();
    }

    public void setDefaultHeader(int rowIndex) {
        grid.setDefaultHeaderRow(grid.getHeaderRow(rowIndex));
        gridComponent.redraw();
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
        gridComponent.redraw();
    }

    public boolean isFooterHidden() {
        return !grid.isFooterVisible();
    }

    public void setFooterHidden(boolean hidden) {
        grid.setFooterVisible(!hidden);
        gridComponent.redraw();
    }

    @Override
    public void setValue(Object target, String propertyName,
            JavaScriptObject jso) {
        StaticCell cell = (StaticCell) target;
        switch (propertyName) {
        case "colspan":
            double value = JsUtils.prop(jso, VALUE);
            cell.setColspan((int) value);
            break;
        case "content":
            Object content = JsUtils.prop(jso, VALUE);
            if (content == null) {
                cell.setHtml(null);
            } else if (JS.isPrimitiveType(content) || content instanceof Number) {
                cell.setHtml(String.valueOf(content));
            } else if (JsUtils.isElement(content)) {
                cell.setWidget(new SimplePanel((Element) content) {
                });
            }
            break;
        default:
            break;
        }

        gridComponent.redraw();
    }

}
