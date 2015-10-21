package com.vaadin.elements.grid.table;

import static com.google.gwt.query.client.GQuery.$;

import java.util.List;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Element;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.js.JsUtils;
import com.google.gwt.query.client.plugins.observe.Observe;
import com.google.gwt.query.client.plugins.observe.Observe.Changes.MutationRecord;
import com.google.gwt.query.client.plugins.observe.Observe.MutationListener;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.HTML;
import com.vaadin.client.widgets.Grid;
import com.vaadin.client.widgets.Grid.StaticSection.StaticCell;
import com.vaadin.client.widgets.Grid.StaticSection.StaticRow;
import com.vaadin.elements.common.js.JS;
import com.vaadin.elements.common.js.JSArray;
import com.vaadin.elements.common.js.JSValidate;
import com.vaadin.elements.grid.GridElement;
import com.vaadin.elements.grid.config.JSColumn;
import com.vaadin.elements.grid.config.JSSortOrder;
import com.vaadin.elements.grid.config.JSStaticCell;

/**
 * This class represents a grid header configuration based on a DOM structure.
 */
public class GridLightDomTable implements MutationListener {

    private String lastConfigString = null;
    private final GQuery $light;
    private GQuery $thead;
    private GQuery $tfoot;
    private GQuery $head_tr;
    private GQuery $foot_tr;
    private GQuery $cols;
    private final Grid<Object> grid;
    private final GridElement gridElement;
    private int defaultHeaderRow, numberHeaderRows, numberColumns,
            numberFooterRows;

    public GridLightDomTable(Element tableElement, GridElement gridElement) {
        this.gridElement = gridElement;
        grid = gridElement.getGrid();

        $light = $(tableElement);

        parseDom();

        $light.as(Observe.Observe).mutation(
                Observe.createMutationInit().attributes(true).childList(true)
                        .subtree(true), this);
    }

    public void parseDom() {
        $thead = $light.find("thead");
        $tfoot = $light.find("tfoot");
        $cols = $light.find("colgroup");
        String txt = $thead.toString() + $cols.toString() + $tfoot.toString();
        if (!txt.equals(lastConfigString)) {
            lastConfigString = txt;

            $head_tr = $thead.find("tr");
            $foot_tr = $tfoot.find("tr");
            numberHeaderRows = $head_tr.size();
            numberFooterRows = $foot_tr.size();

            // Create the jsColumns array
            configureColumns();

            if (numberColumns > 0) {
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
        // TODO: remove timer when #17326 is fixed
        new Timer() {
            @Override
            public void run() {
                if (!$thead.isEmpty()) {
                    grid.setHeaderVisible(!(boolean) JSValidate.Boolean.attr(
                            $thead, "hidden"));
                }
                if (!$tfoot.isEmpty()) {
                    grid.setFooterVisible(!(boolean) JSValidate.Boolean.attr(
                            $tfoot, "hidden"));
                }
            }
        }.schedule(0);
        gridElement.updateSize();
    }

    private void configureColumns() {
        for (int i = 0, max = 0; i < numberHeaderRows; i++) {
            GQuery $ths = $head_tr.eq(i).children("th, td");
            max = Math.max(max, $ths.size());

            // The default row should be the last row in the header which has
            // the largest number of th elements, or the last header row which
            // contains a th element with the sortable attribute
            if ($ths.size() == max || !$ths.filter("[sortable]").isEmpty()) {
                defaultHeaderRow = i;
            }
        }

        if (!$cols.isEmpty()) {
            configureColumnsFromDom($cols.children("col"));
        }
    }

    private void configureColumnsFromDom(GQuery columns) {
        JSArray<JSColumn> jsColumns = gridElement.getColumns();
        jsColumns.setLength(0);
        numberColumns = columns.size();

        JSArray<JSSortOrder> sortOrders = JS.createArray();

        for (int i = 0; i < numberColumns; i++) {
            GQuery $th = columns.eq(i);
            JSColumn column = JS.createJsType(JSColumn.class);
            jsColumns.add(column);
            column.setSortable(JSValidate.Boolean.attr($th, "sortable"));
            Scheduler.get().scheduleDeferred(
                    () -> column.setHidable(JSValidate.Boolean.attr($th,
                            "hidable")));
            column.setHidden(JSValidate.Boolean.attr($th, "hidden"));
            column.setFlex(JSValidate.Integer.attr($th, "flex", 1, -1));

            Double width = JSValidate.Pixel.attr($th, "width");
            if (width != null) {
                column.setWidth(width);
            }
            Double minWidth = JSValidate.Pixel.attr($th, "min-width");
            if (minWidth != null) {
                column.setMinWidth(minWidth);
            }
            Double maxWidth = JSValidate.Pixel.attr($th, "max-width");
            if (maxWidth != null) {
                column.setMaxWidth(maxWidth);
            }

            column.setName(JSValidate.String.attr($th, "name"));
            String direction = JSValidate.String.attr($th, "sort-direction");
            if (!direction.isEmpty()) {
                JSSortOrder jsSortOrder = JS.createJsType(JSSortOrder.class);
                jsSortOrder.setDirection(direction);
                jsSortOrder.setColumn(i);
                sortOrders.add(jsSortOrder);
            }

            column.setHidingToggleText(JSValidate.String.attr($th,
                    "hiding-toggle-text", null, null));

            String headerHtml = JSValidate.String.attr($th, "header-text",
                    $th.html(), column.getName());
            if (headerHtml != null) {
                column.setHeaderContent(headerHtml);
            }
        }
        gridElement.setColumns(jsColumns);

        if (!sortOrders.isEmpty()) {
            gridElement.then(JsUtils.wrapFunction(new Function() {
                @Override
                public void f() {
                    JsUtils.prop(gridElement.getContainer(), "sortOrder", sortOrders);
                }

                ;
            }));
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
            StaticRow<?> row = isHeader ? grid.getHeaderRow(i) : grid
                    .getFooterRow(i);
            List<GridColumn> dataColumns = gridElement.getDataColumns();
            GQuery $tr = $rows.eq(i);
            GQuery $ths = $tr.children("th, td");
            String className = JSValidate.String.attr($tr, "class");
            if (!className.isEmpty()) {
                row.setStyleName(className);
            }

            for (int j = 0, colIndex = 0; j < $ths.size() && j < numberColumns; j++) {
                final GQuery $th = $ths.eq(j);
                StaticCell cell = row.getCell(dataColumns.get(colIndex));
                JSStaticCell js = gridElement.getStaticSection()
                        .obtainJSStaticCell(cell);

                className = JSValidate.String.attr($th, "class");
                if (!className.isEmpty()) {
                    js.setClassName(className);
                }

                int colspan = JSValidate.Integer.attr($th, "colspan", 1, 1);
                // TODO: for some reason this not work without a timeout
                new Timer() {
                    @Override
                    public void run() {
                        js.setContent(new HTML($th.html()).getElement());
                        js.setColspan(colspan);
                    }
                }.schedule(0);
                colIndex += colspan;
            }
        }

        if (isHeader) {
            gridElement.getStaticSection().setDefaultHeader(defaultHeaderRow);
        }
    }

    @Override
    public void onMutation(List<MutationRecord> mutations) {
        parseDom();
        gridElement.getDataSource().refresh();
    }
}
