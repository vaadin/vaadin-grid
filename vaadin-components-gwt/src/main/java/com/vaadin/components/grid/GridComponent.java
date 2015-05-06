package com.vaadin.components.grid;

import static com.google.gwt.query.client.GQuery.$;
import static com.google.gwt.query.client.GQuery.browser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.google.gwt.core.client.JavaScriptException;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.core.client.js.JsExport;
import com.google.gwt.core.client.js.JsNamespace;
import com.google.gwt.core.client.js.JsNoExport;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.TableElement;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.js.JsUtils;
import com.google.gwt.query.client.js.JsUtils.JsFunction;
import com.google.gwt.query.client.plugins.widgets.WidgetsUtils;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.CheckBox;
import com.vaadin.client.widget.grid.events.SelectAllEvent;
import com.vaadin.client.widget.grid.events.SelectAllHandler;
import com.vaadin.client.widget.grid.selection.SelectionEvent;
import com.vaadin.client.widget.grid.selection.SelectionHandler;
import com.vaadin.client.widget.grid.selection.SelectionModel;
import com.vaadin.client.widget.grid.sort.SortEvent;
import com.vaadin.client.widget.grid.sort.SortHandler;
import com.vaadin.client.widget.grid.sort.SortOrder;
import com.vaadin.client.widgets.Grid.Column;
import com.vaadin.components.common.js.JS;
import com.vaadin.components.common.js.JSArray;
import com.vaadin.components.common.js.JSEnums;
import com.vaadin.components.common.js.JSPromise;
import com.vaadin.components.common.js.JSValidate;
import com.vaadin.components.grid.config.JSCell;
import com.vaadin.components.grid.config.JSColumn;
import com.vaadin.components.grid.config.JSRow;
import com.vaadin.components.grid.config.JSSortOrder;
import com.vaadin.components.grid.data.GridDataSource;
import com.vaadin.components.grid.data.GridDomTableDataSource;
import com.vaadin.components.grid.data.GridJsFuncDataSource;
import com.vaadin.components.grid.selection.IndexBasedSelectionMode;
import com.vaadin.components.grid.selection.IndexBasedSelectionModel;
import com.vaadin.components.grid.selection.IndexBasedSelectionModelSingle;
import com.vaadin.components.grid.table.GridColumn;
import com.vaadin.components.grid.table.GridLightDomTable;
import com.vaadin.components.grid.table.GridStaticSection;
import com.vaadin.components.grid.utils.Redraw;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.shared.ui.grid.ScrollDestination;

/**
 * Class to export Vaadin Grid to JS.
 */
@JsNamespace(JS.VAADIN_JS_NAMESPACE)
@JsExport
@JsType
public class GridComponent implements SelectionHandler<Object>, EventListener,
        SortHandler<Object>, SelectAllHandler<Object> {

    public final int defaultHeightByRows;

    private final ViolatedGrid grid;
    private JSArray<JSSortOrder> jsSort;

    public boolean updating = true;
    private GridLightDomTable lightDom;
    private final Redraw redrawer;
    private final GridEditor editor;
    private final GridStaticSection staticSection;

    private Element container;
    private JSArray<JSColumn> cols;

    private JavaScriptObject rowClassGenerator;
    private JavaScriptObject cellClassGenerator;

    public GridComponent() {
        grid = new ViolatedGrid();
        defaultHeightByRows = (int) grid.getHeightByRows();
        grid.setSelectionModel(new IndexBasedSelectionModelSingle());
        grid.addSelectionHandler(this);
        grid.addSortHandler(this);
        grid.addSelectAllHandler(this);

        cols = JS.createArray();
        redrawer = new Redraw(this);
        editor = new GridEditor(this);
        staticSection = new GridStaticSection(this);
    }

    public GridEditor getEditor() {
        return editor;
    }

    public Element getGridElement() {
        return grid.getElement();
    }

    public JSArray<JSSortOrder> getSortOrder() {
        return jsSort;
    }

    public void setSortOrder(JSArray<JSSortOrder> jsOrders) {
        List<SortOrder> order = new ArrayList<SortOrder>();
        List<GridColumn> dataColumns = getDataColumns();
        for (JSSortOrder jsOrder : jsOrders.asList()) {
            Column<?, ?> column = dataColumns.get(jsOrder.getColumn());
            SortDirection direction = JSEnums.Direction.val(jsOrder
                    .getDirection());
            jsOrder.setDirection(JSEnums.Direction.val(direction));
            order.add(new SortOrder(column, direction));
        }
        grid.setSortOrder(order);
        this.jsSort = jsOrders;
    }

    public ViolatedGrid getGrid() {
        return grid;
    }

    @JsNoExport
    public Element getContainer() {
        return container;
    }

    public void attached(Element container, TableElement lightDomElement,
            Element gridContainer) {
        if (this.container == null) {
            this.container = container;

            if (lightDomElement != null) {
                lightDom = new GridLightDomTable(lightDomElement, this);
                // Check if we have the data in the DOM
                GridDomTableDataSource ds = GridDomTableDataSource
                        .createInstance(lightDomElement, this);
                if (ds != null) {
                    grid.setDataSource(ds);
                }
            }

            gridContainer.appendChild(grid.getElement());
            WidgetsUtils.attachWidget(grid, null);

            editor.setContainer(container);
        }

        redrawer.setContainer(container);
        redraw(true);

        updating = false;
    }

    public JSColumn addColumn(JSColumn jsColumn, Object beforeColumnId) {
        int index = cols.length();
        if (beforeColumnId != null) {
            index = getColumnIndexByIndexOrName(beforeColumnId);
        }
        cols.add(jsColumn, index);
        setColumns(cols);
        return jsColumn;
    }

    @JsNoExport
    public int getColumnIndexByIndexOrName(Object indexOrName) {
        String stringId = String.valueOf(indexOrName);
        if (stringId.matches("[+-]?\\d+")) {
            int index = JSValidate.Integer.val(stringId);
            if (index >= 0 && index <= cols.size()) {
                return index;
            }
        } else {
            for (int i = 0; i < cols.length(); i++) {
                if (stringId.equals(cols.get(i).getName())) {
                    return i;
                }
            }
        }
        throw new IllegalArgumentException("Column not found.");
    }

    public void removeColumn(String columnId) {
        cols.remove(cols.get(getColumnIndexByIndexOrName(columnId)));
    }

    public void setDisabled(boolean disabled) {
        grid.setEnabled(!disabled);
    }

    public boolean isDisabled() {
        return !grid.isEnabled();
    }

    public int getFrozenColumns() {
        return grid.getFrozenColumnCount();
    }

    public void setFrozenColumns(int frozenColumn) {
        grid.setFrozenColumnCount(frozenColumn);
    }

    public void scrollToRow(int index, String scrollDestination) {
        if (scrollDestination != null) {
            grid.scrollToRow(index,
                    ScrollDestination.valueOf(scrollDestination.toUpperCase()));
        } else {
            grid.scrollToRow(index);
        }
    }

    public void scrollToStart() {
        grid.scrollToStart();
    }

    public void scrollToEnd() {
        grid.scrollToEnd();
    }

    public double getScrollTop() {
        return grid.getScrollTop();
    }

    public JSArray<JSColumn> getColumns() {
        return cols;
    }

    public GridStaticSection getStaticSection() {
        return staticSection;
    }

    @JsNoExport
    @Override
    public void onSelect(SelectionEvent<Object> ev) {
        updateSelectAllCheckBox();
        if (!updating) {
            $(container).trigger("select");
        }
    }

    public void setColumnWidth(int column, int widht) {
        grid.getColumn(column).setWidth(widht);
    }

    public String getHeightMode() {
        return grid.getHeightMode().toString();
    }

    public void setHeightMode(String mode) {
        grid.setHeightMode(HeightMode.valueOf(mode));
    }

    public void setHeight(String height) {
        grid.setHeight(height);
    }

    public void setDataSource(JavaScriptObject data) {
        if (JsUtils.isFunction(data)) {
            // We cannot attach ds to the grid until we get the async
            // response that brings the number of rows in the table
            // otherwise we set a size of zero and grid will never
            // request for more rows.
            new GridJsFuncDataSource(data, this);
        } else {
            throw new RuntimeException("Unknown data source type: " + data
                    + ". Arrays and Functions are supported only.");
        }
    }

    public GridDataSource getDataSource() {
        return (GridDataSource) grid.getDataSource();
    }

    public IndexBasedSelectionModel getSelectionModel() {
        return (IndexBasedSelectionModel) grid.getSelectionModel();
    }

    public void refresh() {
        updating = true;
        IndexBasedSelectionModel selectionModel = getSelectionModel();
        final JSArray<?> a = selectionModel.selected(null, null, null);
        if (getDataSource() != null) {
            getDataSource().refresh();
        }
        redraw(true);
        if (a.length() > 0) {
            $(container).delay(5, new Function() {
                @Override
                public void f() {
                    for (int i = 0; i < a.length(); i++) {
                        int value = JSValidate.Integer.val(a.get(i), -1, -1);
                        selectionModel.select(value, true);
                    }
                }
            });
        }
        updating = false;
    }

    public void setColumns(JSArray<JSColumn> columns) {
        // Add all missing columns to grid
        Collection<JSColumn> currentColumns = new ArrayList<JSColumn>();
        for (GridColumn c : getDataColumns()) {
            currentColumns.add(c.getJsColumn());
        }

        for (JSColumn column : columns.asList()) {
            if (!currentColumns.contains(column)) {
                GridColumn.addColumn(column, this);
            }
        }

        // Remove all non-included columns from grid
        for (GridColumn column : getDataColumns()) {
            if (columns.indexOf(column.getJsColumn()) == -1) {
                grid.removeColumn(column);
            }
        }

        // Fix column order
        GridColumn[] array = getDataColumns().toArray(new GridColumn[0]);
        Arrays.sort(array,
                (o1, o2) -> columns.indexOf(o1.getJsColumn()) > columns
                        .indexOf(o2.getJsColumn()) ? 1 : -1);
        if (array.length > 0) {
            grid.setColumnOrder(array);
        }
        this.cols = columns;

    }

    /**
     * Returns all the columns that display data. On multi-select mode
     * grid.getColumns() will contain the selection column as the first item so
     * it's excluded from the result.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @JsNoExport
    public List<GridColumn> getDataColumns() {
        List result = grid.getColumns();
        if (getSelectionModel() instanceof SelectionModel.Multi) {
            result = result.subList(1, result.size());
        }
        return result;
    }

    public void setSelectionMode(String selectionMode) {
        if (!getSelectionMode().equalsIgnoreCase(selectionMode)) {
            updating = true;
            IndexBasedSelectionMode mode = JSEnums.Selection.val(selectionMode);
            grid.setSelectionModel(mode.createModel());
            getSelectionModel().reset();
            updateSelectAllCheckBox();
            redraw();
            updating = false;
        }
    }

    public String getSelectionMode() {
        return getSelectionModel().getMode().name().toLowerCase();
    }

    @Override
    public void onBrowserEvent(Event event) {
        refresh();
    }

    public void setRowClassGenerator(JavaScriptObject generator) {
        grid.setRowStyleGenerator(JS.isUndefinedOrNull(generator) ? null
                : row -> JS.exec(generator, JSRow.create(row, container)));
        this.rowClassGenerator = generator;
    }

    public JavaScriptObject getRowClassGenerator() {
        return rowClassGenerator;
    }

    public void setCellClassGenerator(JavaScriptObject generator) {
        grid.setCellStyleGenerator(JS.isUndefinedOrNull(generator) ? null
                : cell -> JS.exec(generator, JSCell.create(cell, container)));
        this.cellClassGenerator = generator;
    }

    public JavaScriptObject getCellClassGenerator() {
        return cellClassGenerator;
    }

    // TODO: remove this when grid resizes appropriately on container
    // and data changes.
    public void redraw() {
        redraw(false);
    }

    @JsNoExport
    public void redraw(boolean force) {
        redrawer.redraw(force);
    }

    public int getRows() {
        return redrawer.getSize();
    }

    public void setRows(int rows) {
        redrawer.setSize(rows);
    }

    @Override
    public void sort(SortEvent<Object> event) {
        getSelectionModel().reset();
        if (jsSort == null) {
            jsSort = JSArray.createArray().cast();
        }
        jsSort.setLength(0);
        List<GridColumn> dataColumns = getDataColumns();
        for (SortOrder order : event.getOrder()) {
            JSSortOrder sortOrder = JS.createJsType(JSSortOrder.class);
            sortOrder.setColumn(dataColumns.indexOf(order.getColumn()));
            sortOrder.setDirection(JSEnums.Direction.val(order.getDirection()));
            jsSort.push(sortOrder);
        }
        $(container).trigger("sort");
        refresh();
    }

    public boolean isWorkPending() {
        return grid.getDataSource() == null
                || ((GridDataSource) grid.getDataSource()).isWaitingForData()
                || redrawer.isRunning() || grid.isWorkPending();
    }

    public void onReady(JavaScriptObject f) {
        onReady(new JsFunction(f));
    }

    @JsNoExport
    public void onReady(final Function f) {
        Scheduler.get().scheduleFixedPeriod(new RepeatingCommand() {
            @Override
            public boolean execute() {
                if (!isWorkPending()) {
                    f.f();
                    return false;
                }
                return true;
            }
        }, 30);
    }

    public Object then(JavaScriptObject f) {
        // IE does not have support for native promises.
        if (browser.msie
                // FIXME: static initializers in exported classes cause
                // unexpected errors
                || browser.mozilla
                && Window.Navigator.getUserAgent().toLowerCase()
                        .contains("trident")) {
            JSPromise p = new JSPromise();
            onReady(new Function() {
                @Override
                public void f() {
                    try {
                        Object v = JS.exec(f, null);
                        p.dfd.resolve(v);
                    } catch (JavaScriptException e) {
                        p.dfd.reject(e.getThrown());
                    }
                }
            });
            return p;
        } else {
            return nativePromise(f);
        }
    }

    private native JavaScriptObject nativePromise(JavaScriptObject f)
    /*-{
        var _this = this;
        return new Promise(function(resolve, reject) {
          _this.onReady(resolve);
        }).then(f);
    }-*/;

    @JsNoExport
    @Override
    public void onSelectAll(SelectAllEvent<Object> event) {
        if (!updating) {
            updating = true;
            if (event.getSelectionModel() != getSelectionModel()) {
                grid.setSelectionModel(event.getSelectionModel());
            } else {
                boolean all = getSelectAllCheckBox().getValue();
                setSelectionMode(all ? IndexBasedSelectionMode.ALL.name()
                        : IndexBasedSelectionMode.MULTI.name());
            }
            updateSelectAllCheckBox();
            updating = false;
            onSelect(null);
            getSelectionModel().reset();
        }
    }

    private void updateSelectAllCheckBox() {
        CheckBox selectAllCheckBox = getSelectAllCheckBox();
        if (selectAllCheckBox != null) {
            boolean checked = getSelectionModel().getMode() == IndexBasedSelectionMode.ALL;
            selectAllCheckBox.setValue(checked, false);

            Element input = selectAllCheckBox.getElement()
                    .getFirstChildElement();
            if (checked) {
                JsUtils.prop(input, "indeterminate", !getSelectionModel()
                        .deselected(null, null, null).isEmpty());
            } else {
                JsUtils.prop(input, "indeterminate", !getSelectionModel()
                        .selected(null, null, null).isEmpty());
            }
        }
    }

    private CheckBox getSelectAllCheckBox() {
        CheckBox result = null;
        if (getSelectionModel() instanceof SelectionModel.Multi) {
            result = (CheckBox) grid.getDefaultHeaderRow()
                    .getCell(grid.getColumn(0)).getWidget();
        }
        return result;
    }

}
