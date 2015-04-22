package com.vaadin.components.grid.table;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.js.JsExport;
import com.google.gwt.core.client.js.JsNamespace;
import com.google.gwt.core.client.js.JsNoExport;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.dom.client.Element;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.js.JsUtils;
import com.google.gwt.user.client.ui.SimplePanel;
import com.vaadin.client.widgets.Grid.Column;
import com.vaadin.client.widgets.Grid.StaticSection.StaticCell;
import com.vaadin.client.widgets.Grid.StaticSection.StaticRow;
import com.vaadin.components.common.js.JS;
import com.vaadin.components.common.js.JSArray;
import com.vaadin.components.grid.GridComponent;
import com.vaadin.components.grid.ViolatedGrid;
import com.vaadin.components.grid.config.JSStaticCell;
import com.vaadin.shared.ui.grid.GridStaticCellType;

@JsNamespace(JS.VAADIN_JS_NAMESPACE)
@JsExport
@JsType
public class GridStaticSection {

    private final GridComponent gridComponent;
    private final ViolatedGrid grid;
    private final Map<StaticCell, JSStaticCell> cells = new HashMap<>();

    public GridStaticSection(GridComponent gridComponent) {
        this.gridComponent = gridComponent;
        this.grid = gridComponent.getGrid();
    }

    @JsNoExport
    public JSStaticCell assureJSStaticCell(StaticCell cell) {
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

            bind(jsCell, cell, "colspan", new Function() {
                @Override
                public void f() {
                    cell.setColspan(((Double) arguments(0)).intValue());
                }
            });
            bind(jsCell, cell, "content", new Function() {
                @Override
                public void f() {
                    Object content = arguments(0);
                    if (content == null) {
                        cell.setHtml(null);
                    } else if (JS.isPrimitiveType(content)
                            || content instanceof Number) {
                        cell.setHtml(String.valueOf(content));
                    } else if (JsUtils.isElement(content)) {
                        cell.setWidget(new SimplePanel((Element) content) {
                        });
                    }
                }
            });
            bind(jsCell, cell, "className", new Function() {
                @Override
                public void f() {
                    cell.setStyleName(arguments(0));
                }
            });

            cells.put(cell, jsCell);
        }

        return cells.get(cell);
    }

    private void bind(JSStaticCell cell, StaticCell staticCell,
            String propertyName, final Function function) {
        JS.definePropertyAccessors(cell, propertyName, v -> {
            function.f(v);
            gridComponent.redraw();
            grid.refreshStaticSection(staticCell);
        }, null);
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

        grid.refreshHeader();
        gridComponent.redraw();
    }

    public void addFooter(Object rowIndex, JSArray<?> cellContent) {
        int index = getInteger(rowIndex, grid.getFooterRowCount());
        StaticRow<?> row = grid.addFooterRowAt(index);
        if (cellContent != null) {
            setStaticRowCellContent(row, cellContent);
        }

        grid.refreshFooter();
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
        grid.refreshHeader();
        gridComponent.redraw();
    }

    public void removeFooter(int rowIndex) {
        grid.removeFooterRow(rowIndex);
        grid.refreshFooter();
        gridComponent.redraw();
    }

    public void setHeaderRowClassName(int rowIndex, String styleName) {
        grid.getHeaderRow(rowIndex).setStyleName(styleName);
        grid.refreshHeader();
        gridComponent.redraw();
    }

    public void setFooterRowClassName(int rowIndex, String styleName) {
        grid.getFooterRow(rowIndex).setStyleName(styleName);
        grid.refreshFooter();
        gridComponent.redraw();
    }

    public void setDefaultHeader(int rowIndex) {
        grid.setDefaultHeaderRow(grid.getHeaderRow(rowIndex));
        grid.refreshHeader();
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
        grid.refreshHeader();
        gridComponent.redraw(true);
    }

    public boolean isFooterHidden() {
        return !grid.isFooterVisible();
    }

    public void setFooterHidden(boolean hidden) {
        grid.setFooterVisible(!hidden);
        grid.refreshFooter();
        gridComponent.redraw(true);
    }
}
