package com.vaadin.components.grid;

import static com.google.gwt.query.client.GQuery.$;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsArrayInteger;
import com.google.gwt.core.client.JsArrayMixed;
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
import com.vaadin.client.data.AbstractRemoteDataSource;
import com.vaadin.client.data.DataSource;
import com.vaadin.client.widget.grid.selection.SelectionEvent;
import com.vaadin.client.widget.grid.selection.SelectionHandler;
import com.vaadin.client.widget.grid.sort.SortOrder;
import com.vaadin.client.widgets.Grid;
import com.vaadin.client.widgets.Grid.Column;
import com.vaadin.client.widgets.Grid.SelectionMode;
import com.vaadin.components.common.util.DOMUtils;
import com.vaadin.components.common.util.Elements;
import com.vaadin.components.grid.config.JS;
import com.vaadin.components.grid.config.JSArray;
import com.vaadin.components.grid.config.JSColumn;
import com.vaadin.components.grid.config.JSEnums;
import com.vaadin.components.grid.config.JSSortOrder;
import com.vaadin.components.grid.data.GridDataSource;
import com.vaadin.components.grid.data.GridDomTableDataSource;
import com.vaadin.components.grid.data.GridJsFuncDataSource;
import com.vaadin.components.grid.data.GridJsObjectDataSource;
import com.vaadin.components.grid.head.GridColumn;
import com.vaadin.components.grid.head.GridDomTableHead;
import com.vaadin.components.grid.utils.Redraw;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.shared.ui.grid.ScrollDestination;

/**
 * Class to export Vaadin Grid to JS.
 */
@JsNamespace(Elements.VAADIN_JS_NAMESPACE)
@JsExport
@JsType
public class GridComponent implements SelectionHandler<JsArrayMixed>,
        EventListener {

    private Grid<JsArrayMixed> grid;
    private JSArray<JSSortOrder> jsSort;

    private int size = 0;
    private boolean updating = false;
    private GridDomTableHead head;
    private Redraw redrawer;

    private Element container;
    private JSArray<JSColumn> cols;

    public GridComponent() {
        grid = new Grid<JsArrayMixed>();
        grid.addSelectionHandler(this);
        cols = JS.createArray();
        observeColumnArray();
    }

    public Element getGridElement() {
        return grid.getElement();
    }

    public JavaScriptObject getSortOrder() {
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

    public Grid<JsArrayMixed> getGrid() {
        return grid;
    }

    public void init(Element container, TableElement lightDomElement,
            Element gridContainer) {
        this.container = container;
        redrawer = new Redraw(grid, container);

        if (head == null) {
            head = new GridDomTableHead(lightDomElement, this);
        } else {
            head.setLightDom(lightDomElement);
        }
        if (lightDomElement != null) {
            setColumns(head.loadHeaders());
        }

        gridContainer.appendChild(grid.getElement());
        WidgetsUtils.attachWidget(grid, null);

        if (lightDomElement != null) {
            // If the wrapped DOM table has TR elements, we use it as data
            // source
            DataSource<JsArrayMixed> dataSource = GridDomTableDataSource
                    .createInstance(lightDomElement, this);
            if (dataSource != null) {
                grid.setDataSource(dataSource);
                redraw();
            }
        }
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
        if (indexOrName.matches("\\d+")) {
            int parsedInt = Integer.parseInt(indexOrName);
            if (parsedInt < cols.length()) {
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

    public void onMutation() {
        setColumns(head.loadHeaders());
        refresh();
    }

    public void setDisabled(boolean disabled) {
        grid.setEnabled(!disabled);
    }

    public void setEditable(boolean editable) {
        // TODO: Currently missing an editor handler
        grid.setEditorEnabled(editable);
    }

    public void setFrozenColumn(String frozenColumn) {
        int index = getColumnIndexByIndexOrName(frozenColumn);
        grid.setFrozenColumnCount(index + 1);
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

    public void setRowCount(int size) {
        this.size = size;
    }

    @JsNoExport
    @Override
    public void onSelect(SelectionEvent<JsArrayMixed> ev) {
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

    public void setDataSource(JavaScriptObject jso) {
        if (JsUtils.isFunction(jso)) {
            grid.setDataSource(new GridJsFuncDataSource(jso, size, this));
        } else if (JsUtils.isArray(jso)) {
            setColumns(head.loadHeaders());
            grid.setDataSource(new GridJsObjectDataSource(jso
                    .<JsArray<JavaScriptObject>> cast(), this));
        } else {
            throw new RuntimeException("Unknown jso: " + jso);
        }
        redraw();
    }

    public void refresh() {
        if ((grid.getDataSource() instanceof GridDataSource)) {
            final JsArrayInteger a = getSelectedRows();
            ((GridDataSource) grid.getDataSource()).refresh();
            if (a.length() > 0) {
                $(container).delay(5, new Function() {
                    @Override
                    public void f() {
                        setSelectedRows(a);
                    }
                });
            }
        } else if (grid.getDataSource() != null) {
            grid.setDataSource(grid.getDataSource());
        }
    }

    public void setColumns(JSArray<JSColumn> columns) {
        if (this.cols != null) {
            DOMUtils.unobserve(this.cols);
        }

        Collection<JSColumn> gCols = new ArrayList<JSColumn>();
        for (Column<?, JsArrayMixed> gCol : grid.getColumns()) {
            gCols.add(((GridColumn) gCol).getJsColumn());
        }

        // Add all missing columns to grid
        for (JSColumn column : columns.asList()) {
            if (!gCols.contains(column)) {
                GridColumn.addColumn(column, this);
            }
        }
        // Remove all non-included columns from grid
        for (Column<?, JsArrayMixed> column : grid.getColumns()) {
            if (columns.indexOf(((GridColumn) column).getJsColumn()) == -1) {
                grid.removeColumn(column);
            }
        }

        // Fix column order
        GridColumn[] array = grid.getColumns().toArray(new GridColumn[0]);
        Arrays.sort(array, new Comparator<GridColumn>() {
            @Override
            public int compare(GridColumn o1, GridColumn o2) {
                return columns.indexOf(o1.getJsColumn()) > columns.indexOf(o2
                        .getJsColumn()) ? 1 : -1;
            }
        });
        grid.setColumnOrder(array);

        this.cols = columns;
        observeColumnArray();
    }

    private void observeColumnArray() {
        DOMUtils.observe(this.cols, new EventListener() {
            @Override
            public void onBrowserEvent(Event event) {
                setColumns(GridComponent.this.cols);
            }
        });
    }

    public void setSelectionMode(String selectionMode) {
        // TODO: this randomly raises an asynchronous exception
        // The selection column cannot be modified after init
        grid.setSelectionMode(JSEnums.Selection
                .<SelectionMode> val(selectionMode));
        grid.getDefaultHeaderRow().getCell(grid.getColumn(0)).setText("");
    }

    @Override
    public void onBrowserEvent(Event event) {
        refresh();
    }

    public void setSelectedRows(JsArrayInteger selectedJso) {
        updating = true;
        grid.getSelectionModel().reset();
        for (int i = 0, l = selectedJso.length(); i < l; i++) {
            int selectedIndex = selectedJso.get(i);
            if (selectedIndex >= 0
                    && selectedIndex < grid.getDataSource().size()) {
                grid.select(grid.getDataSource().getRow(selectedIndex));
            }
        }
        updating = false;
        onSelect(null);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public JsArrayInteger getSelectedRows() {
        JsArrayInteger selectedJso = JsArrayInteger.createArray().cast();
        selectedJso.setLength(0);
        Collection<JsArrayMixed> c = grid.getSelectedRows();
        for (Iterator<JsArrayMixed> i = c.iterator(); i.hasNext();) {
            selectedJso.push(((AbstractRemoteDataSource) grid.getDataSource())
                    .indexOf(i.next()));
        }
        return selectedJso;
    }

    // TODO: remove this when grid resizes appropriately on container
    // and data changes.
    public void redraw() {
        redrawer.redraw();
    }
}
