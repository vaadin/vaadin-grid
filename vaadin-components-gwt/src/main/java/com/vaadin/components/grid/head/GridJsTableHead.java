package com.vaadin.components.grid.head;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayMixed;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.widgets.Grid;
import com.vaadin.client.widgets.Grid.HeaderCell;
import com.vaadin.client.widgets.Grid.HeaderRow;
import com.vaadin.components.grid.GridComponent;
import com.vaadin.components.grid.config.JSArray;
import com.vaadin.components.grid.config.JSColumn;
import com.vaadin.components.grid.config.JSHeaderCell;
import com.vaadin.components.grid.config.JSHeaderCell.Format;

/**
 * This class represents a grid header configuration based on
 * a JSO array of JsColumns
 */
public class GridJsTableHead {

    protected Grid<JsArrayMixed> grid;

    protected JSArray<JSColumn> jsColumns = JSArray.createArray().cast();

    public GridJsTableHead(GridComponent grid) {
        this.grid = grid.getGrid();
    }

    public void setJsColumns(JavaScriptObject jso, int defaultRow) {
        this.jsColumns = jso.cast();
        updateGridColumns(defaultRow);
    }

    protected void updateGridColumns(int defaultRow) {
        while (grid.getColumnCount() > 1) {
            grid.removeColumn(grid.getColumn(1));
        }
        for (int i = 0, l = jsColumns.size(); i < l; i++) {
            JSColumn c = jsColumns.get(i);
            Grid.Column<Object, JsArrayMixed> col = new GridColumn(i, c);
            grid.addColumn(col);
            for (int j = 0; j < c.headerData().size(); j++) {
                if (grid.getHeaderRowCount() < c.headerData().size()) {
                    grid.appendHeaderRow();
                }
                JSHeaderCell header = c.headerData().get(j);
                int offset = 0;
                for (int k = 0; k <= j + offset; k++) {
                    HeaderRow row = grid.getHeaderRow(k);
                    if (i != 0
                            && row.getCell(grid.getColumn(i - 1))
                                    .getColspan() != 1) {
                        offset++;
                    }
                }
                HeaderCell cell = grid.getHeaderRow(j + offset)
                        .getCell(col);
                cell.setColspan(header.colSpan());
                Object content = header.content();
                switch (Format.valueOf(header.format())) {
                case HTML:
                    cell.setHtml((String) content);
                    break;
                case WIDGET:
                    cell.setWidget((Widget) content);
                    break;
                case TEXT:
                    cell.setText((String) content);
                    break;
                }
            }
        }
        if (defaultRow >= 0) {
            grid.setDefaultHeaderRow(grid.getHeaderRow(defaultRow));
        }
    }
}
