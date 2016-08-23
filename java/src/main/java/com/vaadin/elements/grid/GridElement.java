package com.vaadin.elements.grid;

import static com.google.gwt.query.client.GQuery.$;
import static com.google.gwt.query.client.GQuery.browser;

import com.google.gwt.core.client.JavaScriptException;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.TableElement;
import com.google.gwt.query.client.js.JsUtils;
import com.google.gwt.query.client.plugins.widgets.WidgetsUtils;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.SimplePanel;

import com.vaadin.client.widget.grid.DataAvailableEvent;
import com.vaadin.client.widget.grid.DetailsGenerator;
import com.vaadin.client.widget.grid.events.ColumnReorderEvent;
import com.vaadin.client.widget.grid.events.ColumnReorderHandler;
import com.vaadin.client.widget.grid.events.SelectAllEvent;
import com.vaadin.client.widget.grid.events.SelectAllHandler;
import com.vaadin.client.widget.grid.selection.SelectionEvent;
import com.vaadin.client.widget.grid.selection.SelectionHandler;
import com.vaadin.client.widget.grid.selection.SelectionModel;
import com.vaadin.client.widget.grid.sort.SortEvent;
import com.vaadin.client.widget.grid.sort.SortHandler;
import com.vaadin.client.widget.grid.sort.SortOrder;
import com.vaadin.client.widgets.Grid.Column;
import com.vaadin.elements.common.js.JS;
import com.vaadin.elements.common.js.JSArray;
import com.vaadin.elements.common.js.JSEnums;
import com.vaadin.elements.common.js.JSFunction;
import com.vaadin.elements.common.js.JSFunction2;
import com.vaadin.elements.common.js.JSPromise;
import com.vaadin.elements.common.js.JSValidate;
import com.vaadin.elements.grid.config.JSCell;
import com.vaadin.elements.grid.config.JSColumn;
import com.vaadin.elements.grid.config.JSDataRequest;
import com.vaadin.elements.grid.config.JSRow;
import com.vaadin.elements.grid.config.JSSortOrder;
import com.vaadin.elements.grid.data.GridDataSource;
import com.vaadin.elements.grid.data.GridDomTableDataSource;
import com.vaadin.elements.grid.data.GridJsFuncDataSource;
import com.vaadin.elements.grid.selection.IndexBasedSelectionMode;
import com.vaadin.elements.grid.selection.IndexBasedSelectionModel;
import com.vaadin.elements.grid.selection.IndexBasedSelectionModelMulti;
import com.vaadin.elements.grid.selection.IndexBasedSelectionModelSingle;
import com.vaadin.elements.grid.selection.MultiSelectModeChangedEvent;
import com.vaadin.elements.grid.selection.MultiSelectModeChangedHandler;
import com.vaadin.elements.grid.table.GridColumn;
import com.vaadin.elements.grid.table.GridLightDomTable;
import com.vaadin.elements.grid.table.GridStaticSection;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.shared.ui.grid.ScrollDestination;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import jsinterop.annotations.JsIgnore;
import jsinterop.annotations.JsType;

/**
 * Class to export Vaadin Grid to JS.
 */
@JsType(namespace = JS.NAMESPACE_GRID)
public class GridElement implements SelectionHandler<Object>,
        SortHandler<Object>, SelectAllHandler<Object>,
        ColumnReorderHandler<Object>,
        MultiSelectModeChangedHandler {

    private final ViolatedGrid grid;
    private int visibleRows = -1;

    public boolean updating = true;
    private GridLightDomTable lightDom;
    private final GridStaticSection staticSection;

    private Element container;
    private Element measureObject;
    private JSArray<JSColumn> cols;

    private JSFunction<String, JSRow> rowClassGenerator;
    private JSFunction<String, JSCell> cellClassGenerator;
    private JSFunction<Object, Object> rowDetailsGenerator;

    @JsIgnore
    public static final int MAX_AUTO_ROWS = 10;

    private static final String SELECTION_MODE_CHANGED_EVENT = "selection-mode-changed";
    private static final String COLUMN_ORDER_CHANGED_EVENT = "column-order-changed";

    public GridElement() {
        grid = new ViolatedGrid();
        grid.setSelectionModel(new IndexBasedSelectionModelSingle());
        grid.addSelectionHandler(this);
        grid.addSortHandler(this);
        grid.addSelectAllHandler(this);
        grid.addColumnReorderHandler(this);
        grid.addHandler(this, MultiSelectModeChangedEvent.eventType);
        grid.getElement().getStyle().setHeight(0, Unit.PX);
        setColumns(JS.createArray());
        staticSection = new GridStaticSection(this);
        grid.setStylePrimaryName("vaadin-grid style-scope vaadin-grid");
    }

    public void setColumnReorderingAllowed(boolean isAllowed){
        grid.setColumnReorderingAllowed(isAllowed);
    }

    public Element getGridElement() {
        return grid.getElement();
    }

    public void setSortOrder(JSArray<JSSortOrder> jsOrders) {
        List<SortOrder> newOrder = mapToSortOrders(jsOrders);

        grid.setSortOrder(newOrder);
    }

    private List<SortOrder> mapToSortOrders(JSArray<JSSortOrder> jsOrders) {
        List<SortOrder> order = new ArrayList<SortOrder>();

        List<GridColumn> dataColumns = getDataColumns();
        for (JSSortOrder jsOrder : jsOrders.asList()) {
            Column<?, ?> column = dataColumns.get(jsOrder.getColumn());
            SortDirection direction = JSEnums.Direction.val(jsOrder
                    .getDirection());
            jsOrder.setDirection(JSEnums.Direction.val(direction));
            order.add(new SortOrder(column, direction));
        }

        return order;
    }
    
    private void updateOrder() {
        cols.setLength(0); // Clear.
        
        // Apply the column order from the Grid to cols.
        for (GridColumn dataCol : getDataColumns()) {
            cols.add(dataCol.getJsColumn());
        }
    }

    public ViolatedGrid getGrid() {
        return grid;
    }

    public void getItem(Double rowIndex,
            JSFunction2<JavaScriptObject, Object> callback, boolean onlyCached) {
        getDataSource().getItem(rowIndex, callback, onlyCached);
    }

    public Element getContainer() {
        return container;
    }

    public void init(Element container, Element gridContainer,
            Element measureObject) {
        if (this.container == null) {
            this.container = container;
            this.measureObject = measureObject;
            gridContainer.appendChild(grid.getElement());
            WidgetsUtils.attachWidget(grid, null);
        }

        updating = false;
        Scheduler.get().scheduleFinally(() -> updateHeight());
    }

    public void setLightDomTable(TableElement lightDomElement) {
        lightDom = new GridLightDomTable(lightDomElement, this);

        GridDataSource ds = getDataSource();
        if (ds == null) {
            // Check if we have the data in the DOM
            ds = GridDomTableDataSource.createInstance(lightDomElement, this);
        }
        if (ds != null) {
            grid.setDataSource(ds);
            getSelectionModel().reset();
        }
    }

    public void setHeaderHeight(double d) {
        grid.getEscalator().getHeader().setDefaultRowHeight(d);
    }

    public void setFooterHeight(double d) {
        grid.getEscalator().getFooter().setDefaultRowHeight(d);
    }

    public void setBodyHeight(double d) {
        grid.getEscalator().getBody().setDefaultRowHeight(d);
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

    @JsIgnore
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
        setColumns(cols);
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
        grid.setFrozenColumnCount(JSValidate.Integer.val(frozenColumn, 0, 0));
    }

    public void scrollToRow(int index, String scrollDestination) {
        if (grid.getEscalator().getBody().getRowCount() > 0) {
            if (scrollDestination != null) {
                grid.scrollToRow(index, ScrollDestination.valueOf(scrollDestination.toUpperCase()));
            } else {
                grid.scrollToRow(index);
            }
        }
    }

    public void scrollToStart() {
        scrollToRow(0, "start");
    }

    public void scrollToEnd() {
        scrollToRow(grid.getEscalator().getBody().getRowCount() - 1, "end");
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

    @JsIgnore
    @Override
    public void onSelect(SelectionEvent<Object> ev) {
        updateSelectAllCheckBox();
        if (!updating) {
            triggerEvent("selected-items-changed");
        }
    }

    private void triggerEvent(String eventName) {
        if (container != null) {
            NativeEvent event = Document.get().createHtmlEvent(eventName,
                    false, true);
            container.dispatchEvent(event);
        }
    }

    public void setHeight(String height) {
        grid.setHeight(height);
    }

    public void setDataSource(
            JSFunction2<JSDataRequest, JSFunction2<JSArray<?>, Double>> jsFunction) {
        if (getDataSource() instanceof GridJsFuncDataSource) {
            ((GridJsFuncDataSource) getDataSource()).setJSFunction(jsFunction);
        } else {
            grid.setDataSource(new GridJsFuncDataSource(jsFunction, this));
        }
        updateHeight();
    }

    public GridDataSource getDataSource() {
        return (GridDataSource) grid.getDataSource();
    }

    public IndexBasedSelectionModel getSelectionModel() {
        return (IndexBasedSelectionModel) grid.getSelectionModel();
    }

    public void setColumns(JSArray<JSColumn> columns) {
        this.cols = columns;

        // Add all missing columns to grid
        Collection<JSColumn> currentColumns = new ArrayList<JSColumn>();
        for (GridColumn c : getDataColumns()) {
            currentColumns.add(c.getJsColumn());
        }

        for (Object object : columns.asList()) {
            if (!currentColumns.contains(object)) {
                // We handle either JS objects or JSColumns, if column is an
                // Object, it's promoted to a JSColumn so as it has the
                // appropriate
                // prototype for handling set/get properties.
                GridColumn.createColumn(object, this);
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

        if (getDataSource() != null) {
            getDataSource().refreshItems();
        }
    }

    /**
     * Returns all the columns that display data. On multi-select mode
     * grid.getColumns() will contain the selection column as the first item so
     * it's excluded from the result.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @JsIgnore
    public List<GridColumn> getDataColumns() {
        List result = grid.getColumns();
        if (getSelectionModel() instanceof SelectionModel.Multi) {
            result = result.subList(1, result.size());
        }
        return result;
    }

    public void setSelectionMode(String selectionMode) {
        IndexBasedSelectionMode newMode = JSEnums.Selection.val(selectionMode);
        if (getSelectionModel().getMode() != newMode) {
            if (getSelectionModel().supportsMode(newMode)) {
                getSelectionModel().setMode(newMode);
                getSelectionModel().reset();
            } else {
                grid.setSelectionModel(newMode.createModel());
                updateWidth();

                selectionModeChanged();
            }
        }
    }

    public String getSelectionMode() {
        return getSelectionModel().getMode().name().toLowerCase();
    }

    public void setRowClassGenerator(JSFunction<String, JSRow> generator) {
        grid.setRowStyleGenerator(JS.isUndefinedOrNull(generator) ? null
                : row -> generator.f(new JSRow(row, container)));
        rowClassGenerator = generator;
    }

    public JSFunction<String, JSRow> getRowClassGenerator() {
        return rowClassGenerator;
    }

    public void setCellClassGenerator(JSFunction<String, JSCell> generator) {
        grid.setCellStyleGenerator(JS.isUndefinedOrNull(generator) ? null
                : cell -> generator.f(new JSCell(cell, container)));
        cellClassGenerator = generator;
    }

    public JSFunction<String, JSCell> getCellClassGenerator() {
        return cellClassGenerator;
    }

    // The method should only be called on init/attach
    private boolean resetSizesFromDomCalled = false;

    private final Timer sizeUpdater = new Timer() {
        @Override
        public void run() {
            if (!resetSizesFromDomCalled && isGridVisible()) {
                grid.resetSizesFromDom();
                then(o -> {
                    grid.resetSizesFromDom();
                    return null;
                });
                resetSizesFromDomCalled = true;
            }
            updateWidth();
            updateHeight();
        }

    };

    /*
     * This method checks whether the vaadin-grid element is actually visible in
     * the DOM (ie. its parent elements or itself aren't set display: none).
     */
    private boolean isGridVisible() {
        // We currently implement the feature by checking the client height of
        // measure object because it has 1px bottom padding and
        // thus has client height of 1 if it's visible in the DOM.
        return measureObject != null && measureObject.getClientHeight() > 0;
    }

    public void updateSize() {
        sizeUpdater.schedule(50);
    }

    @JsIgnore
    public void updateWidth() {
        grid.setWidth("100%");
        Scheduler.get().scheduleDeferred(() -> grid.recalculateColumnWidths());
    }

    @JsIgnore
    public void updateHeight() {
        if (!updating) {
            grid.setHeight("100%");

            if (container.getClientHeight() == 0) {
                if (visibleRows > 0) {
                    grid.setHeightByRows(visibleRows);
                } else {
                    GridDataSource ds = getDataSource();
                    if (ds != null) {
                        grid.setHeightByRows(Math.min(ds.size(), MAX_AUTO_ROWS));
                    } else {
                        grid.setHeightByRows(0);
                    }
                }
            }

            if (isGridVisible()) {
                measureObject.getStyle().setProperty("height", "");
            } else {
                // Changing the measureObject's size while invisible makes it
                // fire a resize event when it becomes visible again
                measureObject.getStyle().setHeight(1, Unit.PX);
            }
        }
    }

    public int getVisibleRows() {
        return visibleRows;
    }

    public void setVisibleRows(int visibleRows) {
        this.visibleRows = JSValidate.Integer.val(visibleRows, -1, -1);
        updateHeight();
    }

    private JSArray<JSSortOrder> mapToJSSortOrders(List<SortOrder> sortOrders) {
        JSArray<JSSortOrder> jsSortOrders = JSArray.createArray().cast();
        jsSortOrders.setLength(0);

        List<GridColumn> dataColumns = getDataColumns();

        for (SortOrder order : sortOrders) {
            JSSortOrder sortOrder = JS.createJsObject();
            sortOrder.setColumn(dataColumns.indexOf(order.getColumn()));
            sortOrder.setDirection(JSEnums.Direction.val(order.getDirection()));
            jsSortOrders.push(sortOrder);
        }

        return jsSortOrders;
    }

    @Override
    public void sort(SortEvent<Object> event) {
        if (event.isUserOriginated()) {
            JsUtils.prop(container, "sortOrder",
                    mapToJSSortOrders(event.getOrder()));
        }

        getSelectionModel().reset();
        clearDataSourceCache();
    }

    private void clearDataSourceCache() {
        GridDataSource dataSource = getDataSource();
        if (dataSource != null) {
            dataSource.refreshItems();
        }
    }

    public boolean isWorkPending() {
        return grid.getDataSource() != null
                && ((GridDataSource) grid.getDataSource()).isWaitingForData()
                || grid.isWorkPending() || sizeUpdater.isRunning()
                || columnsPending();
    }

    // This is needed because the col Observer is not synchronous in iOS
    private boolean columnsPending() {
        boolean result = getDataColumns().size() != cols.size();
        if (!result) {
            for (GridColumn col : getDataColumns()) {
                if (cols.indexOf(col.getJsColumn()) == -1) {
                    result = true;
                }
            }
        }
        return result;
    }

    public void onReady(JSFunction<?, ?> jsFunction) {
        Scheduler.get().scheduleFixedPeriod(new RepeatingCommand() {
            @Override
            public boolean execute() {
                if (!isWorkPending()) {
                    jsFunction.f(null);
                    return false;
                }
                return true;
            }
        }, 30);
    }

    public Object then(JSFunction<Object, Object> jsFunction) {
        // IE does not have support for native promises.
        if (browser.msie
                // FIXME: static initializers in exported classes cause
                // unexpected errors
                || browser.mozilla
                && Window.Navigator.getUserAgent().toLowerCase()
                        .contains("trident")) {
            JSPromise p = new JSPromise();
            onReady(o -> {
                try {
                    p.dfd.resolve(jsFunction.f(null));
                } catch (JavaScriptException e) {
                    p.dfd.reject(e.getThrown());
                }
                return null;
            });
            return p;
        } else {
            return nativePromise(jsFunction);
        }
    }

    private native JavaScriptObject nativePromise(JSFunction<?, ?> jsFunction)
    /*-{
        return new Promise(function(resolve, reject) {
          this.onReady(resolve);
        }.bind(this)).then(jsFunction);
    }-*/;

    @JsIgnore
    @Override
    public void onSelectAll(SelectAllEvent<Object> event) {
        if (getSelectAllCheckBox().getValue()) {
            getSelectionModel().selectAll();
        } else {
            getSelectionModel().clear();
        }
    }

    @JsIgnore 
    @Override
    public void onColumnReorder(ColumnReorderEvent<Object> event) {
        updateOrder();
        triggerEvent(COLUMN_ORDER_CHANGED_EVENT);
    }

    @JsIgnore
    @Override
    public void onMultiSelectModeChanged() {
        selectionModeChanged();
    }

    private void selectionModeChanged() {
        triggerEvent(SELECTION_MODE_CHANGED_EVENT);
        updateSelectAllCheckBox();
    }

    private void updateSelectAllCheckBox() {
        CheckBox selectAllCheckBox = getSelectAllCheckBox();
        if (selectAllCheckBox != null) {
            $(selectAllCheckBox).children().addClass("vaadin-grid",
                    "style-scope");
            IndexBasedSelectionModelMulti model = (IndexBasedSelectionModelMulti) getSelectionModel();
            $(selectAllCheckBox).find("input").prop("indeterminate",
                    model.isIndeterminate());
            selectAllCheckBox.setValue(model.isChecked(), false);
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

    @JsIgnore
    public void setLoadingDataClass(boolean loadingData) {
        String loadingDataClassName = "vaadin-grid-loading-data";

        if (loadingData) {
            getGridElement().addClassName(loadingDataClassName);
        } else {
            getGridElement().removeClassName(loadingDataClassName);
        }
    }

    public void setRowDetailsGenerator(JSFunction<Object, Object> generator) {
        grid.setDetailsGenerator(JS.isUndefinedOrNull(generator) ? DetailsGenerator.NULL
                : rowIndex -> {
                    Object details = generator.f(rowIndex);
                    return JS.isUndefinedOrNull(details) ? null
                            : new SimplePanel((Element) details) {
                            };
                });
        rowDetailsGenerator = generator;
    }

    public JSFunction<Object, Object> getRowDetailsGenerator() {
        return rowDetailsGenerator;
    }

    public void setRowDetailsVisible(int rowIndex, Object visible) {
        then(o -> {
            Integer validatedRowIndex = JSValidate.Integer.val(rowIndex, null,
                    null);
            Boolean validatedVisible = JSValidate.Boolean.val(visible, true,
                    true);
            if (!DetailsGenerator.NULL.equals(grid.getDetailsGenerator())
                    && validatedRowIndex != null) {
                grid.setDetailsVisible(validatedRowIndex, validatedVisible);
            }
            return null;
        });
    }

    public void sizeChanged(int size, int oldSize) {
        GridDataSource ds = getDataSource();
        if (ds != null) {
            // Resize existing data source row data
            if (oldSize < size) {
                ds.insertRowData(oldSize, size - oldSize);
            } else if (oldSize > size) {
                ds.removeRowData(size, oldSize - size);
            }
        }

        if (size == 0) {
            // Grid gets stuck when the data source size is 0. It won't
            // request new data but isWorkPending will still return true.
            // This releases the state (grid.dataIsBeingFetched gets set
            // false).
            grid.fireEvent(new DataAvailableEvent(null));
        } else if (oldSize == 0 && ds != null) {
            // Grid stops calling requestRows when size is 0, if
            // size changes we have to re-attach the data-source so
            // that grid starts calling requestRows again
            grid.setDataSource(ds);
        }

        updateHeight();
    }
}
