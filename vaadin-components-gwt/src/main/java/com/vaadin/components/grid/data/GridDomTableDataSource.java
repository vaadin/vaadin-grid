package com.vaadin.components.grid.data;

import static com.google.gwt.query.client.GQuery.$;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.JsArrayMixed;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.TableElement;
import com.google.gwt.query.client.GQuery;
import com.vaadin.components.grid.GridComponent;

/**
 * DataSource implementation based on a DOM table. Each TR tag in the TBODY
 * corresponds with a row in the Grid.
 */
public class GridDomTableDataSource extends GridDataSource {

    private final TableElement table;
    // We cache table rows collection until refresh is called.
    private GQuery tableRows;

    public GridDomTableDataSource(Element table, GridComponent grid) {
        super(grid);
        assert TableElement.is(table);
        this.table = table.cast();
        size = rows().size();
    }

    private GQuery rows() {
        if (tableRows == null || tableRows.isEmpty()) {
            tableRows = $(table).find("tbody tr:not([template])");
        }
        return tableRows;
    }

    @Override
    protected void requestRows(int firstRowIndex, int numberOfRows,
            RequestRowsCallback<Object> callback) {

        List<Object> list = new ArrayList<>();

        for (Element row : rows().gt(firstRowIndex - 1).lt(numberOfRows)
                .elements()) {
            JsArrayMixed values = JsArrayMixed.createArray().cast();
            for (Element e : $(row).find("td").elements()) {
                values.push($(e).html());
            }
            list.add(values);
        }

        setRowData(firstRowIndex, list);
    }

    @Override
    public void refresh() {
        // on refresh we reset the cache and recompute size.
        tableRows = null;
        size = rows().size();
        super.refresh();
    }

    /**
     * Return a new DOM DataSource, only in the case the table has elements.
     */
    public static GridDomTableDataSource createInstance(Element table,
            GridComponent grid) {
        GridDomTableDataSource dataSource = new GridDomTableDataSource(table,
                grid);
        return dataSource.size > 0 ? dataSource : null;
    }
}
