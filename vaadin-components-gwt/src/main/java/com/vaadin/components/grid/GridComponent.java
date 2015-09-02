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
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.TableElement;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.js.JsUtils;
import com.google.gwt.query.client.js.JsUtils.JsFunction;
import com.google.gwt.query.client.plugins.observe.Observe;
import com.google.gwt.query.client.plugins.observe.Observe.Changes.ChangeRecord;
import com.google.gwt.query.client.plugins.observe.Observe.ObserveListener;
import com.google.gwt.query.client.plugins.widgets.WidgetsUtils;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.UIObject;
import com.vaadin.client.widget.grid.DetailsGenerator;
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
import com.vaadin.components.grid.selection.IndexBasedSelectionModelMulti;
import com.vaadin.components.grid.selection.IndexBasedSelectionModelSingle;
import com.vaadin.components.grid.table.GridColumn;
import com.vaadin.components.grid.table.GridLightDomTable;
import com.vaadin.components.grid.table.GridStaticSection;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.shared.ui.grid.ScrollDestination;

/**
 * Class to export Vaadin Grid to JS.
 */
@JsNamespace(JS.VAADIN_JS_NAMESPACE)
@JsExport
@JsType
public class GridComponent implements SelectionHandler<Object>,
        SortHandler<Object>, SelectAllHandler<Object> {

    private final ViolatedGrid grid;
    private JSArray<JSSortOrder> jsSort;
    private int rows = -1;

    public boolean updating = true;
    private GridLightDomTable lightDom;
    private final GridEditor editor;
    private final GridStaticSection staticSection;

    private Element container;
    private JSArray<JSColumn> cols;

    private JavaScriptObject rowClassGenerator;
    private JavaScriptObject cellClassGenerator;
    private JavaScriptObject rowDetailsGenerator;

    public static final int MAX_AUTO_ROWS = 10;

    public GridComponent() {
        grid = new ViolatedGrid();
        grid.setSelectionModel(new IndexBasedSelectionModelSingle());
        grid.addSelectionHandler(this);
        grid.addSortHandler(this);
        grid.addSelectAllHandler(this);
        grid.getElement().getStyle().setHeight(0, Unit.PX);

        setColumns(JS.createArray());
        editor = new GridEditor(this);
        staticSection = new GridStaticSection(this);

        grid.setStylePrimaryName("vaadin-grid style-scope vaadin-grid");
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
        jsSort = jsOrders;
    }

    public ViolatedGrid getGrid() {
        return grid;
    }

    public void getItem(Double rowIndex, JavaScriptObject callback,
            boolean onlyCached) {
        getDataSource().getItem(rowIndex, callback, onlyCached);
    }

    @JsNoExport
    public Element getContainer() {
        return container;
    }

    public void init(Element container, TableElement lightDomElement,
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

        updating = false;

        Scheduler.get().scheduleFinally(() -> updateHeight());

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
        grid.setFrozenColumnCount(JSValidate.Integer.val(frozenColumn, 0, 0));
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
            triggerEvent("select");
        }
    }

    private void triggerEvent(String eventName) {
        NativeEvent event = Document.get().createHtmlEvent(eventName, false,
                true);
        container.dispatchEvent(event);
    }

    public void setHeight(String height) {
        grid.setHeight(height);
    }

    public void setDataSource(JavaScriptObject data) {
        if (JsUtils.isFunction(data)) {
            if (getDataSource() instanceof GridJsFuncDataSource) {
                ((GridJsFuncDataSource) getDataSource()).setJSFunction(data);
            } else {
                grid.setDataSource(new GridJsFuncDataSource(data, this));
            }
            updateHeight();
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

        if (cols != columns) {
            Observe.unobserve(cols);
            Observe.observe(cols = columns, new ObserveListener() {
                @Override
                public void onChange(List<ChangeRecord> changes) {
                    setColumns(cols);
                }
            });
        }

        if (getDataSource() != null) {
            getDataSource().refresh();
        }
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

    public JSArray<JSColumn> getVisibleColumns() {
        JSArray<JSColumn> result = JSArray.createArray().cast();
        for (int i = 0; i < cols.size(); i++) {
            if (!cols.get(i).getHidden()) {
                result.add(cols.get(i));
            }
        }
        return result;
    }

    public void setSelectionMode(String selectionMode) {
        setSelectionMode(selectionMode, false);
    }

    private void setSelectionMode(String selectionMode, boolean force) {
        if (force || !getSelectionMode().equalsIgnoreCase(selectionMode)) {
            updating = true;
            IndexBasedSelectionMode mode = JSEnums.Selection.val(selectionMode);

            boolean newModeIsAll = mode == IndexBasedSelectionMode.ALL;
            boolean newModeIsMulti = mode == IndexBasedSelectionMode.MULTI;
            boolean currentModelIsMulti = getSelectionModel() instanceof IndexBasedSelectionModelMulti;

            if (!(currentModelIsMulti && (newModeIsAll || newModeIsMulti))) {
                grid.setSelectionModel(mode.createModel());
                updateWidth();
            }
            if (newModeIsAll) {
                getSelectionModel().selectAll();
            } else {
                getSelectionModel().reset();
            }
            triggerEvent("selectionmodechange");
            updateSelectAllCheckBox();
            updating = false;
        }
    }

    public String getSelectionMode() {
        return getSelectionModel().getMode().name().toLowerCase();
    }

    public void setRowClassGenerator(JavaScriptObject generator) {
        grid.setRowStyleGenerator(JS.isUndefinedOrNull(generator) ? null
                : row -> JS.exec(generator, JSRow.create(row, container)));
        rowClassGenerator = generator;
    }

    public JavaScriptObject getRowClassGenerator() {
        return rowClassGenerator;
    }

    public void setCellClassGenerator(JavaScriptObject generator) {
        grid.setCellStyleGenerator(JS.isUndefinedOrNull(generator) ? null
                : cell -> JS.exec(generator, JSCell.create(cell, container)));
        cellClassGenerator = generator;
    }

    public JavaScriptObject getCellClassGenerator() {
        return cellClassGenerator;
    }

    // The method should only be called on init/attach
    private boolean resetSizesFromDomCalled = false;

    private final Timer sizeUpdater = new Timer() {
        @Override
        public void run() {
            if (!resetSizesFromDomCalled && UIObject.isVisible(container)) {
                grid.resetSizesFromDom();
                then(JsUtils.wrapFunction(new Function() {
                    @Override
                    public void f() {
                        grid.resetSizesFromDom();
                    };
                }));
                resetSizesFromDomCalled = true;
            }
            updateWidth();
            updateHeight();
        }
    };

    public void updateSize() {
        sizeUpdater.schedule(50);
    }

    @JsNoExport
    public void updateWidth() {
        grid.setWidth("100%");
        Scheduler.get().scheduleDeferred(() -> grid.recalculateColumnWidths());
    }

    @JsNoExport
    public void updateHeight() {
        if (!updating) {
            grid.setHeight("100%");

            if (container.getClientHeight() == 0) {
                if (rows > 0) {
                    grid.setHeightByRows(rows);
                } else {
                    GridDataSource ds = getDataSource();
                    if (ds != null) {
                        grid.setHeightByRows(Math.min(ds.size(), MAX_AUTO_ROWS));
                    } else {
                        grid.setHeightByRows(0);
                    }
                }
            }
        }
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = JSValidate.Integer.val(rows, -1, -1);
        updateHeight();
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
        triggerEvent("sort");

        clearDataSourceCache();
    }

    private void clearDataSourceCache() {
        GridDataSource dataSource = getDataSource();
        if (dataSource != null) {
            dataSource.clearCache(null);
        }
    }

    public boolean isWorkPending() {
        return grid.getDataSource() != null
                && ((GridDataSource) grid.getDataSource()).isWaitingForData()
                || grid.isWorkPending() || sizeUpdater.isRunning();
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
                triggerEvent("selectionmodechange");
                getSelectionModel().reset();
            } else {
                boolean all = getSelectAllCheckBox().getValue();
                setSelectionMode(all ? IndexBasedSelectionMode.ALL.name()
                        : IndexBasedSelectionMode.MULTI.name(), true);
            }
            updateSelectAllCheckBox();
            updating = false;
            onSelect(null);
        }
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

    @JsNoExport
    public void setLoadingDataClass(boolean loadingData) {
        String loadingDataClassName = "vaadin-grid-loading-data";

        if (loadingData) {
            getGridElement().addClassName(loadingDataClassName);
        } else {
            getGridElement().removeClassName(loadingDataClassName);
        }
    }

    public void setRowDetailsGenerator(JavaScriptObject generator) {
        grid.setDetailsGenerator(JS.isUndefinedOrNull(generator) ? DetailsGenerator.NULL
                : rowIndex -> {
                    Object details = JS.exec(generator, rowIndex);
                    return JS.isUndefinedOrNull(details) ? null
                            : new SimplePanel((Element) details) {
                            };
                });
        rowDetailsGenerator = generator;
    }

    public JavaScriptObject getRowDetailsGenerator() {
        return rowDetailsGenerator;
    }

    public void setRowDetailsVisible(int rowIndex, Object visible) {
        Integer validatedRowIndex = JSValidate.Integer
                .val(rowIndex, null, null);
        Boolean validatedVisible = JSValidate.Boolean.val(visible, true, true);
        if (!DetailsGenerator.NULL.equals(grid.getDetailsGenerator())
                && validatedRowIndex != null) {
            grid.setDetailsVisible(validatedRowIndex, validatedVisible);
        }
    }
}