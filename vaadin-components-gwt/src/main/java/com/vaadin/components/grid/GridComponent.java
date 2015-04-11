package com.vaadin.components.grid;

import static com.google.gwt.query.client.GQuery.$;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayInteger;
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
import com.google.gwt.query.client.plugins.widgets.WidgetsUtils;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.vaadin.client.data.DataSource;
import com.vaadin.client.widget.grid.selection.SelectionEvent;
import com.vaadin.client.widget.grid.selection.SelectionHandler;
import com.vaadin.client.widget.grid.selection.SelectionModel;
import com.vaadin.client.widget.grid.selection.SelectionModelMulti;
import com.vaadin.client.widget.grid.selection.SelectionModelSingle;
import com.vaadin.client.widget.grid.sort.SortEvent;
import com.vaadin.client.widget.grid.sort.SortHandler;
import com.vaadin.client.widget.grid.sort.SortOrder;
import com.vaadin.client.widgets.Grid.Column;
import com.vaadin.client.widgets.Grid.SelectionMode;
import com.vaadin.components.common.js.JS;
import com.vaadin.components.common.js.JSArray;
import com.vaadin.components.common.js.JSEnums;
import com.vaadin.components.common.js.JSValidate;
import com.vaadin.components.grid.IndexBasedSelectionModel.IndexBasedSelectionModelMulti;
import com.vaadin.components.grid.IndexBasedSelectionModel.IndexBasedSelectionModelNone;
import com.vaadin.components.grid.IndexBasedSelectionModel.IndexBasedSelectionModelSingle;
import com.vaadin.components.grid.config.JSCellClassName;
import com.vaadin.components.grid.config.JSColumn;
import com.vaadin.components.grid.config.JSRowClassName;
import com.vaadin.components.grid.config.JSSortOrder;
import com.vaadin.components.grid.data.GridDataSource;
import com.vaadin.components.grid.data.GridDomTableDataSource;
import com.vaadin.components.grid.data.GridJsFuncDataSource;
import com.vaadin.components.grid.table.GridColumn;
import com.vaadin.components.grid.table.GridLightDomTable;
import com.vaadin.components.grid.table.GridStaticSection;
import com.vaadin.components.grid.utils.GridCellStyleGenerator;
import com.vaadin.components.grid.utils.GridRowStyleGenerator;
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
        SortHandler<Object> {

    private final ViolatedGrid grid;
    private JSArray<JSSortOrder> jsSort;

    private boolean updating = false;
    private GridLightDomTable lightDom;
    private final Redraw redrawer;
    private final GridEditor editor;
    private final GridStaticSection staticSection;

    private Element container;
    private JSArray<JSColumn> cols;

    public GridComponent() {
        grid = new ViolatedGrid();
        grid.setSelectionModel(new IndexBasedSelectionModelSingle());
        grid.addSelectionHandler(this);
        grid.addSortHandler(this);
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
        for (JSSortOrder jsOrder : jsOrders.asList()) {
            Column<?, ?> column = grid.getColumn(jsOrder.getColumn());
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

    public void init(Element container, TableElement lightDomElement,
            Element gridContainer) {
        this.container = container;

        if (lightDomElement != null) {
            lightDom = new GridLightDomTable(lightDomElement, this);
            // Check if we have the data in the DOM
            GridDomTableDataSource ds = lightDom.getDomDataSource();
            if (ds != null) {
                grid.setDataSource(ds);
            }
        }

        gridContainer.appendChild(grid.getElement());
        WidgetsUtils.attachWidget(grid, null);

        redrawer.setContainer(container);
        editor.setContainer(container);
    }

    public JSColumn addColumn(JSColumn jsColumn, String beforeColumn) {
        int index = grid.getColumnCount();
        if (beforeColumn != null) {
            index = getColumnIndexByIndexOrName(beforeColumn);
        }
        cols.add(jsColumn, index);

        return jsColumn;
    }

    private int getColumnIndexByIndexOrName(String indexOrName) {
        if (indexOrName.matches("[+-]?\\d+")) {
            int parsedInt = Integer.parseInt(indexOrName);
            if (parsedInt >= 0 && parsedInt < cols.length()) {
                return parsedInt;
            }
        } else {
            String idString = String.valueOf(indexOrName);
            for (int i = 0; i < cols.length(); i++) {
                JSColumn jsColumn = cols.get(i);
                if (idString.equals(jsColumn.name())) {
                    return i;
                }
            }
        }
        throw new IllegalArgumentException("Column not found.");
    }

    public void removeColumn(String id) {
        int index = getColumnIndexByIndexOrName(id);
        cols.remove(cols.get(index));
    }

    public void setDisabled(boolean disabled) {
        grid.setEnabled(!disabled);
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

    public void setScrollTop(double px) {
        grid.setScrollTop(px);
    }

    public JSArray<JSColumn> getColumns() {
        return cols;
    }

    public GridStaticSection getStaticSection() {
        return staticSection;
    }

    public Column<?, ?> getColumnByJSColumnNameOrIndex(Object columnId) {
        Column<?, ?> column = null;
        if (columnId instanceof Integer || JS.isPrimitiveType(columnId)) {
            int index = getColumnIndexByIndexOrName(String.valueOf(columnId));
            column = grid.getColumn(index);
        } else {
            for (Column<?, ?> gCol : getDataColumns()) {
                if (((GridColumn) gCol).getJsColumn() == columnId) {
                    column = gCol;
                    break;
                }
            }
        }
        return column;
    }

    @JsNoExport
    @Override
    public void onSelect(SelectionEvent<Object> ev) {
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
            grid.setDataSource(new GridJsFuncDataSource(data, this));
        } else {
            throw new RuntimeException("Unknown data source type: " + data
                    + ". Arrays and Functions are supported only.");
        }
    }

    public DataSource<Object> getDataSource() {
        return grid.getDataSource();
    }

    @JsNoExport
    public IndexBasedSelectionModel getSelectionModel() {
        return (IndexBasedSelectionModel) grid.getSelectionModel();
    }

    public void refresh() {
        final JsArrayInteger a = getSelectedRows();
        ((GridDataSource) grid.getDataSource()).refresh();
        redrawer.redraw(true);
        if (a.length() > 0) {
            $(container).delay(5, new Function() {
                @Override
                public void f() {
                    setSelectedRows(a);
                }
            });
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void setColumns(JSArray<JSColumn> columns) {
        Collection<JSColumn> gCols = new ArrayList<JSColumn>();
        for (Column<?, Object> gCol : getDataColumns()) {
            gCols.add(((GridColumn) gCol).getJsColumn());
        }

        // Add all missing columns to grid
        for (JSColumn column : columns.asList()) {
            if (!gCols.contains(column)) {
                GridColumn.addColumn(column, this);
            }
        }
        // Remove all non-included columns from grid
        for (Column<?, Object> column : getDataColumns()) {
            if (columns.indexOf(((GridColumn) column).getJsColumn()) == -1) {
                grid.removeColumn(column);
            }
        }

        // Fix column order
        Column[] array = getDataColumns().toArray(new Column[0]);
        Arrays.sort(array, new Comparator<Object>() {
            @Override
            public int compare(Object o1, Object o2) {
                return columns.indexOf(((GridColumn) o1).getJsColumn()) > columns
                        .indexOf(((GridColumn) o2).getJsColumn()) ? 1 : -1;
            }
        });
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
    @JsNoExport
    public List<Column<?, Object>> getDataColumns() {
        List<Column<?, Object>> result = grid.getColumns();
        if (getSelectionModel() instanceof IndexBasedSelectionModelMulti) {
            result = result.subList(1, result.size());
        }
        return result;
    }

    public void setSelectionMode(String selectionMode) {
        SelectionMode mode = JSEnums.Selection.val(selectionMode);
        if (!getSelectionMode().equalsIgnoreCase(mode.name())) {
            if (mode == SelectionMode.MULTI) {
                grid.setSelectionModel(new IndexBasedSelectionModelMulti());
                // Remove check-box for selecting all in the header
                grid.getDefaultHeaderRow().getCell(grid.getColumn(0))
                        .setText("");
            } else if (mode == SelectionMode.SINGLE) {
                grid.setSelectionModel(new IndexBasedSelectionModelSingle());
            } else {
                grid.setSelectionModel(new IndexBasedSelectionModelNone());
            }
        }
    }

    public String getSelectionMode() {
        SelectionModel<Object> selectionModel = grid.getSelectionModel();
        if (selectionModel instanceof SelectionModelMulti) {
            return SelectionMode.MULTI.name().toLowerCase();
        } else if (selectionModel instanceof SelectionModelSingle) {
            return SelectionMode.SINGLE.name().toLowerCase();
        } else {
            return SelectionMode.NONE.name().toLowerCase();
        }
    }

    @Override
    public void onBrowserEvent(Event event) {
        refresh();
    }

    public void setSelectedRows(JsArrayInteger selectedJso) {
        if (!(getSelectionModel() instanceof IndexBasedSelectionModelNone)) {
            updating = true;

            HashSet<Object> previouslySelected = new HashSet<Object>(
                    getSelectionModel().getSelectedIndexes());

            Set<Integer> selected = new HashSet<Integer>();
            for (int i = selectedJso.length() - 1; i >= 0; i--) {
                try {
                    int index = JSValidate.Integer.val(String
                            .valueOf(selectedJso.get(i)));
                    selected.add(index);
                    if (getSelectionModel() instanceof IndexBasedSelectionModelSingle) {
                        break;
                    }
                } catch (RuntimeException e) {
                    // NOP
                }
            }

            getSelectionModel().setSelectedIndexes(selected);
            updating = false;

            if (!previouslySelected.equals(getSelectionModel()
                    .getSelectedIndexes())) {
                onSelect(null);
            }
        }
    }

    public JsArrayInteger getSelectedRows() {
        JsArrayInteger selectedJso = JsArrayInteger.createArray().cast();
        selectedJso.setLength(0);

        for (Integer i : getSelectionModel().getSelectedIndexes()) {
            selectedJso.push(i);
        }

        return selectedJso;
    }

    public JSRowClassName getRowClassName() {
        JSRowClassName result = null;
        if (grid.getRowStyleGenerator() != null) {
            result = ((GridRowStyleGenerator) grid.getRowStyleGenerator())
                    .getRowClassName();
        }
        return result;
    }

    public void setRowClassName(JSRowClassName rowClassName) {
        if (rowClassName == null) {
            grid.setRowStyleGenerator(null);
        } else {
            grid.setRowStyleGenerator(new GridRowStyleGenerator(rowClassName,
                    container));
        }
    }

    public JSCellClassName getCellClassName() {
        JSCellClassName result = null;
        if (grid.getCellStyleGenerator() != null) {
            result = ((GridCellStyleGenerator) grid.getCellStyleGenerator())
                    .getCellClassName();
        }
        return result;
    }

    public void setCellClassName(JSCellClassName cellClass) {
        if (cellClass == null) {
            grid.setCellStyleGenerator(null);
        } else {
            grid.setCellStyleGenerator(new GridCellStyleGenerator(cellClass,
                    container));
        }
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
        for (SortOrder order : event.getOrder()) {
            int idx = grid.getColumns().indexOf(order.getColumn());
            jsSort.push(JS.createJsType(JSSortOrder.class).setColumn(idx)
                    .setDirection(JSEnums.Direction.val(order.getDirection())));
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
        Scheduler.get().scheduleFixedPeriod(new RepeatingCommand() {
            @Override
            public boolean execute() {
                if (!isWorkPending()) {
                    JS.exec(f, null);
                    return false;
                }
                return true;
            }
        }, 30);
    }
}
