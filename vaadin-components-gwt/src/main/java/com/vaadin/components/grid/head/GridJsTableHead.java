package com.vaadin.components.grid.head;

import static com.google.gwt.query.client.GQuery.console;

import com.google.gwt.core.client.JavaScriptObject;
import com.vaadin.client.widgets.Grid;
import com.vaadin.components.common.js.JSArray;
import com.vaadin.components.grid.GridComponent;
import com.vaadin.components.grid.config.JSColumn;

/**
 * This class represents a grid header configuration based on a JSO array of
 * JsColumns
 */
public class GridJsTableHead {

    protected Grid<Object> grid;
    private final GridComponent gridComponent;

    protected JSArray<JSColumn> jsColumns = JSArray.createArray().cast();

    public GridJsTableHead(GridComponent grid) {
        this.gridComponent = grid;
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

        if (defaultRow >= 0) {
            if (grid.getColumnCount() < defaultRow) {
                console.error("GridJsTableHead [complex headers BUG]: grid with a defaultRow: "
                        + defaultRow
                        + " value greater than number of columns: "
                        + grid.getColumnCount());
                return;
            }
            grid.setDefaultHeaderRow(grid.getHeaderRow(defaultRow));
        }
    }
}
