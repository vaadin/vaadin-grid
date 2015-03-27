package com.vaadin.components.grid.table;

import static com.google.gwt.query.client.GQuery.$;

import java.util.List;

import com.google.gwt.dom.client.Element;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.user.client.Timer;
import com.vaadin.client.widgets.Grid;
import com.vaadin.client.widgets.Grid.Column;
import com.vaadin.client.widgets.Grid.StaticSection.StaticCell;
import com.vaadin.client.widgets.Grid.StaticSection.StaticRow;
import com.vaadin.components.common.js.JS;
import com.vaadin.components.common.js.JSArray;
import com.vaadin.components.common.js.JSValidate;
import com.vaadin.components.grid.GridComponent;
import com.vaadin.components.grid.config.JSColumn;
import com.vaadin.components.grid.config.JSStaticCell;
import com.vaadin.components.grid.data.GridDomTableDataSource;

/**
 * This class represents a grid header configuration based on a DOM structure.
 */
public class GridLightDomTable {

    private String lastConfigString = null;
    private GQuery $light, $thead, $tfoot, $head_tr, $foot_tr;
    private final Grid<Object> grid;
    private final GridComponent gridComponent;
    protected final JSArray<JSColumn> jsColumns = JSArray.createArray().cast();
    int defaultHeaderRow, numberHeaderRows, numberColumns, numberFooterRows;
    private GridDomTableDataSource ds;

    public GridLightDomTable(Element tableElement, GridComponent grid) {
        this.gridComponent = grid;
        this.grid = grid.getGrid();

        ds = GridDomTableDataSource.createInstance(tableElement, gridComponent);
        $light = $(tableElement);

        parseDom();
    }

    public void parseDom() {
        $thead = $light.find("thead");
        if ($thead.isEmpty()) {
            $thead = $("<thead>").appendTo($light);
        }
        $tfoot = $light.find("tfoot");
        if ($tfoot.isEmpty()) {
            $tfoot = $("<tfoot>").appendTo($light);
        }
        String txt = $thead.toString() + $tfoot.toString();
        if (!txt.equals(lastConfigString)) {
            lastConfigString = txt;

            $head_tr = $thead.find("tr");
            $foot_tr = $tfoot.find("tr");
            numberHeaderRows = $head_tr.size();
            numberFooterRows = $foot_tr.size();

            // Create the jsColumns array
            configureColumns();

            // We need to set columns to the grid before configuring
            // headers or footers
            gridComponent.setColumns(jsColumns);

            if(numberColumns > 0) {
                if (numberHeaderRows > 0) {
                    // Configure Headers
                    configureHeadersFooters(true);
                }
                if (numberFooterRows > 0) {
                    // Configure Footers
                    configureHeadersFooters(false);
                }
            }
        }
    }

    private void configureColumns() {
        // Clear columns array
        jsColumns.setLength(0);
        for (int i = 0; i < numberHeaderRows; i++) {
            GQuery $ths = $head_tr.eq(i).children("th, td");

            JSArray<Object> contents = JSArray.createArray().cast();
            for (int j = 0; j < $ths.size(); j++) {
                if (jsColumns.size() <= j) {
                    JSColumn column = JS.createJsType(JSColumn.class);
                    jsColumns.add(column);
                }
                contents.add($ths.eq(j).html());
            }
            // The default row should be the last row in the header which has
            // the largest number of th elements, or the last header row which
            // contains a th element with the sortable attribute
            if (!$ths.filter("[sortable]").isEmpty()
                    || $ths.size() == jsColumns.size()) {
                defaultHeaderRow = i;
            }
        }

        numberColumns = jsColumns.length();
        if (numberColumns > 0) {
            GQuery $ths = $head_tr.eq(defaultHeaderRow).children("th");
            for (int i = 0; i < $ths.size(); i++) {
                GQuery $th = $ths.eq(i);
                JSColumn column = jsColumns.get(i);
                column.setSortable(JSValidate.Boolean.attr($th, "sortable"));
                column.setReadOnly(JSValidate.Boolean.attr($th, "read-only"));
                column.setFlex(JSValidate.Integer.attr($th, "flex", 0, 1));
                column.setWidth(JSValidate.Pixel.attr($th, "width"));
                column.setMinWidth(JSValidate.Pixel.attr($th, "min-width"));
                column.setMaxWidth(JSValidate.Pixel.attr($th, "max-width"));
                column.setHeaderHtml($th.html());
            }
        }
    }

    private void configureHeadersFooters(final boolean isHeader) {
        int n = isHeader ? grid.getHeaderRowCount() : grid.getFooterRowCount();
        int nrows = isHeader ? numberHeaderRows : numberFooterRows;
        GQuery $rows = isHeader ? $head_tr : $foot_tr;
        if (n != nrows) {
            for (int i = nrows; i < n; i++) {
                if (isHeader) {
                    grid.removeHeaderRow(i);
                } else {
                    grid.removeFooterRow(i);
                }
            }
            for (int i = n; i < nrows; i++) {
                if (isHeader) {
                    grid.addHeaderRowAt(i);
                } else {
                    grid.addFooterRowAt(i);
                }
            }
        }
        for (int i = 0; i < nrows; i++) {
            StaticRow<?> row = isHeader ? grid.getHeaderRow(i) : grid.getFooterRow(i);
            List<Column<?, Object>> dataColumns = gridComponent.getDataColumns();
            GQuery $tr = $rows.eq(i);
            GQuery $ths = $tr.children("th, td");
            String className = JSValidate.String.attr($tr, "class");
            if (!className.isEmpty()) {
                row.setStyleName(className);
            }

            for (int j = 0; j < $ths.size(); j++) {
                final GQuery $th = $ths.eq(j);
                Column<?, Object> column = dataColumns.get(j);
                StaticCell cell = row.getCell(column);
                JSStaticCell js = gridComponent.getStaticSection().assureJSStaticCell(cell);
                js.setContent($th.html());
                // TODO: for some reason this not work without a timeout
                new Timer() {
                    public void run() {
                        js.setColspan(JSValidate.Integer.attr($th, "colspan", 0, 1));
                    }
                }.schedule(0);
                className = JSValidate.String.attr($th, "class");
                if (!className.isEmpty()) {
                    js.setClassName(className);
                }
            }
        }
        // TODO: remove timer when #17327 is fixed
        new Timer() {
            public void run() {
                if (isHeader) {
                    gridComponent.getStaticSection().setDefaultHeader(defaultHeaderRow);
                    grid.setHeaderVisible(!(boolean)JSValidate.Boolean.attr($thead, "hidden"));
                } else {
                    if (!$foot_tr.isEmpty()) {
                      grid.setFooterVisible(!(boolean)JSValidate.Boolean.attr($tfoot, "hidden"));
                    }
                }
            }
        }.schedule(0);
    }

    public GridDomTableDataSource getDomDataSource() {
        return ds;
    }
}
