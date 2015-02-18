package com.vaadin.components.grid;

import static com.google.gwt.query.client.GQuery.$;
import static com.google.gwt.query.client.GQuery.Widgets;
import static com.google.gwt.query.client.GQuery.console;
import static com.google.gwt.query.client.GQuery.window;
import static com.vaadin.components.common.util.DOMUtils.getAttrIntValue;
import static com.vaadin.components.common.util.DOMUtils.getAttrValue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsArrayInteger;
import com.google.gwt.core.client.JsArrayMixed;
import com.google.gwt.core.client.js.JsExport;
import com.google.gwt.core.client.js.JsNamespace;
import com.google.gwt.core.client.js.JsNoExport;
import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQ;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.Properties;
import com.google.gwt.query.client.js.JsUtils;
import com.google.gwt.query.client.plugin.Observe;
import com.google.gwt.query.client.plugin.Observe.MutationListener;
import com.google.gwt.query.client.plugin.Observe.MutationRecords.MutationRecord;
import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.data.AbstractRemoteDataSource;
import com.vaadin.client.data.DataSource;
import com.vaadin.client.renderers.Renderer;
import com.vaadin.client.widget.escalator.ColumnConfiguration;
import com.vaadin.client.widget.escalator.RowContainer;
import com.vaadin.client.widget.grid.RendererCellReference;
import com.vaadin.client.widget.grid.selection.SelectionEvent;
import com.vaadin.client.widget.grid.selection.SelectionHandler;
import com.vaadin.client.widget.grid.selection.SelectionModel;
import com.vaadin.client.widget.grid.selection.SelectionModelMulti;
import com.vaadin.client.widget.grid.selection.SelectionModelSingle;
import com.vaadin.client.widgets.Escalator;
import com.vaadin.client.widgets.Grid;
import com.vaadin.client.widgets.Grid.HeaderCell;
import com.vaadin.client.widgets.Grid.HeaderRow;
import com.vaadin.client.widgets.Grid.SelectionMode;
import com.vaadin.components.common.html.HTMLElement;
import com.vaadin.components.common.html.HTMLEvents;
import com.vaadin.components.common.html.HTMLShadow;
import com.vaadin.components.common.html.HTMLTableElement;
import com.vaadin.components.common.util.DOMUtils;
import com.vaadin.components.common.util.Elements;
import com.vaadin.components.grid.data.GridData;
import com.vaadin.components.grid.data.GridData.GridColumn;
import com.vaadin.components.grid.data.GridData.GridColumn.GridHeader;
import com.vaadin.components.grid.data.GridData.GridColumn.GridHeader.Format;
import com.vaadin.components.grid.data.GridDataSource;
import com.vaadin.components.grid.data.GridDomTableDataSource;
import com.vaadin.components.grid.data.GridJsFuncDataSource;
import com.vaadin.components.grid.data.GridJsObjectDataSource;
import com.vaadin.components.grid.data.GridRestDataSource;
import com.vaadin.shared.ui.grid.HeightMode;

@JsExport()
@JsNamespace("vaadin")
@JsType
public class GridComponent extends HTMLTableElement.Prototype implements
        HTMLElement.LifeCycle.Created, HTMLElement.LifeCycle.Attached,
        HTMLElement.LifeCycle.Changed, ValueChangeHandler<Double>, Handler,
        SelectionHandler<JsArrayMixed>, MutationListener {

    public static final String TAG = "v-grid";

    // FIXME: figure out a way to reuse grid.
    private Grid<JsArrayMixed> grid;
    private HTMLEvents selectEvent;
    private HTMLElement container;
    private HTMLElement style;
    private boolean initialized = false;
    public List<GridColumn> cols;
    private boolean changed = true;
    // FIXME: using columns name here make this fail in prod mode
    private List<Grid.Column<Object, JsArrayMixed>> gridColumns;
    // We save the original content of the Light-DOM because polyfills remove it
    private GQuery lightDom;

    // TODO: we should set this from JS among the datasource.
    private int size = 0;

    private int headerDefaultRowIndex = 0;

    public GridComponent() {
        // FIXME: If there is no default constructor JsInterop does not export
        // anything
    }

    @Override
    public void createdCallback() {
        style = Elements.create("style");
        style.setAttribute("language", "text/css");
        selectEvent = Elements.document.createEvent("HTMLEvents");
        selectEvent.initEvent("select", false, false);
        selectEvent.srcElement(this);

        container = Elements.create("div");
        cols = new ArrayList<GridColumn>();
        gridColumns = new ArrayList<>();

        grid = new Grid<JsArrayMixed>();
        grid.addSelectionHandler(this);
    }

    /*
     * TODO: common stuff for exporting other widgets
     */
    private void initWidgetSystem() {
        if (!initialized) {
            initialized = true;
            lightDom = $(this)
                    // this is the table inside the v-grid
                    .children()
                    // hide it, otherwise it's visible if shadow is not used
                    .hide();

            Widget elementWidget = $(this).widget();
            if (elementWidget == null) {
                elementWidget = $(this).as(Widgets).panel().widget();
            }
            elementWidget.addAttachHandler(this);

            if (DOMUtils.getAttrBooleanValue(this, "shadow", false)) {
                HTMLShadow shadow = createShadowRoot();
                shadow.appendChild(style);
                shadow.appendChild(container);
            } else {
                appendChild(style);
                appendChild(container);
            }
            Panel shadowPanel = $(container).as(Widgets).panel().widget();
            shadowPanel.add(grid);

            readAttributes();

            // After everything is initialized we listen to all mutations
            // in the dom table.
            lightDom.as(Observe.Observe).observe(Observe.createInit()
                    .attributes(true).characterData(true)
                    .childList(true).subtree(true), this);
        }
    }

    @Override
    public void attachedCallback() {
        initWidgetSystem();
    }

    @JsNoExport
    public void initGrid() {
        if (!changed) {
            return;
        }
        changed = false;
        DataSource<JsArrayMixed> dataSource = null;
        dataSource = grid.getDataSource();
        if (grid.getSelectionModel() instanceof SelectionModelSingle
                && $(this).attr("selectionMode").equals("multi")) {
            grid.setSelectionMode(SelectionMode.MULTI);
        } else if (grid.getSelectionModel() instanceof SelectionModelMulti
                && !$(this).attr("selectionMode").equals("multi")) {
            grid.setSelectionMode(SelectionMode.SINGLE);
        }
        while (gridColumns.size() > 0) {
            grid.removeColumn(gridColumns.remove(0));
        }
        if (cols != null) {
            for (int i = 0, l = cols.size(); i < l; i++) {
                GridColumn c = cols.get(i);
                Grid.Column<Object, JsArrayMixed> col;
                col = createGridColumn(c, i);
                grid.addColumn(col);
                gridColumns.add(col);
                for (int j = 0; j < c.headerData().size(); j++) {
                    if (grid.getHeaderRowCount() < c.headerData().size()) {
                        grid.appendHeaderRow();
                    }
                    GridHeader header = c.headerData().get(j);
                    int offset = 0;
                    for (int k = 0; k <= j + offset; k++) {
                        HeaderRow row = grid.getHeaderRow(k);
                        if (i != 0 &&
                                row.getCell(grid.getColumn(i-1)).getColspan() != 1) {
                            offset++;
                        }
                    }
                    HeaderCell cell = grid.getHeaderRow(j + offset)
                            .getCell(col);
                    cell.setColspan(header.colSpan());
                    Object content = header.content();
                    switch (header.format()) {
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
            grid.setDefaultHeaderRow(grid.getHeaderRow(headerDefaultRowIndex));
        }

        // If the wrapped DOM table has TR elements, we use it as data source
        dataSource = GridDomTableDataSource.createInstance(lightDom.get(0), this);
        if (dataSource != null) {
            grid.setDataSource(dataSource);
        }
    }

    public static Grid.Column<Object, JsArrayMixed> createGridColumn(
            final GridColumn gColumn, final int idx) {
        final RegExp templateRegexp = RegExp.compile("\\{\\{data\\}\\}", "ig");
        return new Grid.Column<Object, JsArrayMixed>(new Renderer<Object>() {
            public void render(RendererCellReference cell, Object data) {
                Object o = gColumn.renderer();
                Element elm = cell.getElement();
                if (o instanceof JavaScriptObject) {
                    if (JsUtils.isFunction((JavaScriptObject) o)) {
                        JsUtils.jsni((JavaScriptObject) o,
                                "call", o, elm, data, cell.getRow());
                    } else {
                        if ($(elm).data("init") == null) {
                            $(elm).data("init", true);
                            JsUtils.jsni((JavaScriptObject) o,
                                    "init", elm);
                        }
                        JsUtils.jsni((JavaScriptObject) o,
                                "render", elm, data);
                    }
                } else {
                    if (gColumn.template() != null) {
                        // FIXME: this implementation doesn't
                        // reuse any of the possible HTML tags
                        // included in the template.
                        elm.setInnerHTML(templateRegexp.replace(
                                gColumn.template(), String.valueOf(data)));
                    } else {
                        elm.setInnerHTML(String.valueOf(data));
                    }
                }
            }
        }) {
            @Override
            public Object getValue(JsArrayMixed row) {
                Object o = gColumn.value();
                if (o instanceof JavaScriptObject
                        && JsUtils.isFunction((JavaScriptObject) o)) {
                    o = JsUtils.jsni((JavaScriptObject) o,
                            "call", o, row, idx);
                } else if (o instanceof String) {
                    o = JsUtils.prop(row, o);
                } else {
                    if (JsUtils.isArray(row)) {
                        o = row.getObject(idx);
                    } else {
                        Properties p = row.cast();
                        o = p.getObject(p.keys()[idx]);
                    }
                }
                return o;
            }
        };
    }

    @Override
    public void onValueChange(ValueChangeEvent<Double> ev) {
    }

    @Override
    public void attributeChangedCallback() {
        readAttributes();
    }

    private void readAttributes() {
        if (refreshing) {
            return;
        }
        DOMUtils.loadVaadinTheme(container, this, style, null);
        loadHeaders();
        initGrid();
        parseAttributeDeclarations();
        setSelectedRow(getAttrIntValue(this, "selectedRow", -1));
        String type = getAttrValue(this, "type", null);
        String url = getAttrValue(this, "url", null);
        if ("ajax".equals(type) && url != null) {
            Properties p = Properties.create();
            p.set("url", url);
            setDataSource(p);
        }
        // TODO be able to change the selection mode if
        // attribute selectionMode change
    }

    private void parseAttributeDeclarations() {
        String dataPath = getAttribute("dataSource");
        RegExp regex = RegExp.compile("\\{\\{\\s*(\\w+)\\s*\\}\\}");
        MatchResult match = regex.exec(dataPath);
        if (match != null) {
            JavaScriptObject jso = JsUtils.prop(window, match.getGroup(1));
            if (JsUtils.isFunction(jso)) {
                String count = getAttribute("rowCount");
                match = regex.exec(count);
                if (match != null) {
                    count = "" + JsUtils.prop(window, match.getGroup(1));
                }
                if (count != null && count.matches("[\\d\\.\\+]+")) {
                    setRowCount(Integer.valueOf(count));
                }
                setDataSource(jso);
            } else if (JsUtils.isArray(jso)) {
                setDataSource(jso);
            } else {
                console.log("Unknown type of datasource: " + jso);
            }
        }
    }

    private String lastHeaders = null;

    private void loadHeaders() {
        GQuery $theadRows = lightDom.find("thead tr");
        String txt = $theadRows.toString();
        if ($theadRows.isEmpty() || txt.equals(lastHeaders)) {
            return;
        }
        lastHeaders = txt;

        List<GridColumn> colList = new ArrayList<GridColumn>();

        Map<GridColumn, List<GridHeader>> contentsMap = new HashMap<GridColumn, List<GridHeader>>();

        headerDefaultRowIndex = $theadRows.index(lightDom.find("tr[default]")
                .get(0));
        if (headerDefaultRowIndex == -1) {
            headerDefaultRowIndex = 0;
        }
        for (int i = 0; i < $theadRows.size(); i++) {
            GQuery $ths = $theadRows.eq(i).children("th");
            while (colList.size() < $ths.size()) {
                GridColumn column = GQ.create(GridColumn.class);
                contentsMap.put(column, new ArrayList<GridHeader>());
                colList.add(column);
            }
        }

        for (int i = 0; i < $theadRows.size(); i++) {
            GQuery $ths = $theadRows.eq(i).children("th");

            int colOffset = 0;
            for (int j = 0; j < $ths.size(); j++) {
                GridColumn column = colList.get(j + colOffset);
                GridHeader header = GQ.create(GridHeader.class);
                GQuery $th = $ths.eq(j);
                column.setValue($th.attr("name"));
                int colSpan = 1;
                String colString = $th.attr("colspan");
                if (!colString.isEmpty()) {
                    colSpan = Integer.parseInt(colString);
                    colOffset += colSpan - 1;
                }

                // FIXME: Assuming format to be HTML, should we detect
                // between simple text and HTML contents?
                header.setColSpan(colSpan).setContent($th.html())
                        .setFormat(Format.HTML);
                contentsMap.get(column).add(header);
            }
        }

        Iterator<GridColumn> iterator = contentsMap.keySet().iterator();
        // When we don't use shadow, sometimes the component could
        // be renderized previously.
        lightDom.find("div[v-wc-container]").remove();

        GQuery $templateRow = lightDom.find("tr[template] td");
        for (int i = 0; iterator.hasNext(); i++) {
            GridColumn column = iterator.next();
            column.setHeaderData(contentsMap.get(column));
            if (i < $templateRow.size()) {
                String html = $templateRow.eq(i).html();
                column.setTemplate(html);
            }
        }

        setCols(colList);
    }

    @Override
    public void onAttachOrDetach(AttachEvent event) {
        // TODO: Do something with shadowPanel, right now
        // gQuery creates a new root-panel so it does not
        // have any parent, but we should maintain the widget
        // hierarchy someway.
    }

    @Override
    public void onMutation(List<MutationRecord> mutations) {
        readAttributes();
        refresh();
    }

    @JsNoExport
    public void setCols(List<GridColumn> cols) {
        changed = true;
        this.cols = cols;
    }

    @JsNoExport
    public List<GridColumn> getCols() {
        return cols;
    }

    @JsNoExport
    private void adjustHeight(int size) {
        // TODO: fix this in Grid, it seems this only works with reindeer
        if (Window.Location.getParameter("resize") != null && size > 0) {
            this.size = size;
            grid.setHeightMode(HeightMode.ROW);
            grid.setHeightByRows(Math.min(size,
                    RowContainer.INITIAL_DEFAULT_ROW_HEIGHT));
        }
    }

    @JsNoExport
    public void adjustHeight() {
        size = grid.getDataSource().size();
        adjustHeight(size);
    }

    @JsNoExport
    public Grid<JsArrayMixed> getGrid() {
        if (grid == null) {
            changed = true;
            initGrid();
        }
        return grid;
    }

    @Override
    public void onSelect(SelectionEvent<JsArrayMixed> ev) {
        if (!refreshing) {
            refreshing = true;
            dispatchEvent(selectEvent);
            if(!getAttribute("selectedRow").matches("\\s*\\{\\{.+\\}\\}\\s*")) {
                setAttribute("selectedRow", ""
                        + (getSelectedRow() < 0 ? "" : getSelectedRow()));
            }
            refreshing = false;
        }
    }

    public void setColumnWidth(int column, int widht) {
        grid.getColumn(column).setWidth(widht);
    }

    // TODO:
    // @JsProperty seems not exporting these methods right now.
    // We use a magic function name 'jsProperty...' to mark these methods as
    // JS properties when mixing the prototype in Elements.
    public void jsPropertyRowCount() {
    };

    @JsProperty
    public void setRowCount(double rows) {
        size = (int) rows;
        adjustHeight(size);
    }

    @JsProperty
    public double getRowCount() {
        return size;
    }

    public void jsPropertyHeightMode() {
    };

    @JsProperty
    public String getHeightMode() {
        return grid.getHeightMode().toString();
    }

    @JsProperty
    public void setHeightMode(String mode) {
        grid.setHeightMode(HeightMode.valueOf(mode));
    }

    public void jsPropertyHeight() {
    };

    @JsProperty
    public void setHeight(String height) {
        grid.setHeight(height);
    }

    public void jsPropertyDataSource() {
    };

    @JsProperty
    public void setDataSource(JavaScriptObject jso) {
        if (JsUtils.isFunction(jso)) {
            grid.setDataSource(new GridJsFuncDataSource(jso, size, this));
        } else if (JsUtils.isArray(jso)) {
            loadHeaders();
            grid.setDataSource(new GridJsObjectDataSource(jso
                    .<JsArray<JavaScriptObject>> cast(), this));
        } else if (JsUtils.prop(jso, "url") != null) {
            loadHeaders();
            @SuppressWarnings("unused")
            GridRestDataSource d = new GridRestDataSource(jso, this);
        } else {
            throw new RuntimeException("Unknown jso: " + jso);
        }
    }

    boolean refreshing = false;

    public void refresh() {
        if ((grid.getDataSource() instanceof GridDataSource)) {
            final int a = getSelectedRow();
            ((GridDataSource) grid.getDataSource()).refresh();
            if (a > 0) {
                refreshing = true;
                $(this).delay(5, new Function() {
                    @Override
                    public void f() {
                        setSelectedRow(a);
                        refreshing = false;
                    }
                });
            }
        } else if (grid.getDataSource() != null) {
            grid.setDataSource(grid.getDataSource());
        }
    }

    @JsProperty
    public JavaScriptObject getDataSource() {
        return JavaScriptObject.createFunction();
    }

    public void jsPropertyColumns() {
    };

    // Array of JSO representing column configuration
    // used in JS to change renderers.
    private JsArray<JavaScriptObject> columnsJso;

    @JsProperty
    public JavaScriptObject getColumns() {
        // remove old observers
        if (columnsJso != null) {
            for (int i = 0, l = columnsJso.length(); i < l; i++) {
                DOMUtils.unobserve(columnsJso.get(i));
            }
        }
        // Using GQuery data-binding magic to convert list to js arrays.
        columnsJso = GQ.create(GridData.class).setColumns(cols).get("columns");

        // Add observers to any column configuration object so as
        for (int i = 0, l = columnsJso.length(); i < l; i++) {
            DOMUtils.observe(columnsJso.get(i), new EventListener() {
                public void onBrowserEvent(Event event) {
                    refresh();
                }
            });
        }
        return columnsJso;
    }

    @JsProperty
    public void setColumns(JavaScriptObject newCols) {
        changed = true;
        cols = GQ.create(GridData.class).<GridData> set("columns", newCols).columns();
    }

    public void jsPropertySelectedRow() {
    };

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @JsProperty
    public int getSelectedRow() {
        return grid == null
                || grid.getSelectionModel() == null
                || !(grid.getSelectionModel() instanceof SelectionModel.Single<?>)
                || grid.getSelectedRow() == null ? -1
                : ((AbstractRemoteDataSource) grid.getDataSource())
                        .indexOf(grid.getSelectedRow());
    }

    @JsProperty
    public void setSelectedRow(int idx) {
        if (idx < 0 || idx >= grid.getDataSource().size()) {
            if (getSelectedRow() >= 0) {
                JsArrayMixed row = grid.getDataSource().getRow(getSelectedRow());
                if (row != null) {
                    grid.deselect(row);
                }
            }
        } else {
            JsArrayMixed row = grid.getDataSource().getRow(idx);
            if (row != null) {
                grid.select(row);
            }
        }
        onSelect(null);
    }

    public void jsPropertySelectedRows() {
    };

    // Array of selected indexed returned to JS
    // We observe it so as when JS makes changes we update
    // grid selection.
    private JsArrayInteger selectedJso;
    private boolean selectedLock;

    @JsProperty
    public void setSelectedRows(JsArrayInteger arr) {
        if (arr != selectedJso) {
            DOMUtils.unobserve(selectedJso);
        }
        selectedJso = arr;
        selectedLock = true;
        grid.getSelectionModel().reset();
        for (int i = 0, l = selectedJso.length(); i < l; i++) {
            grid.select(grid.getDataSource().getRow(selectedJso.get(i)));
        }
        selectedLock = false;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @JsProperty
    public JsArrayInteger getSelectedRows() {
        if (!selectedLock) {
            if (selectedJso == null) {
                selectedJso = JsArrayInteger.createArray().cast();
            }
            selectedJso.setLength(0);
            Collection<JsArrayMixed> c = grid.getSelectedRows();
            for (Iterator<JsArrayMixed> i = c.iterator(); i.hasNext();) {
                selectedJso.push(((AbstractRemoteDataSource) grid.getDataSource()).indexOf(i.next()));
            }
            DOMUtils.unobserve(selectedJso);
            DOMUtils.observe(selectedJso, new EventListener() {
                public void onBrowserEvent(Event event) {
                    setSelectedRows(selectedJso);
                }
            });
        }
        return selectedJso;
    }

    public void jsPropertyTheme() {
    }

    @JsProperty
    public void setTheme(String value) {
        setAttribute("theme", value);
    }

    @JsProperty
    public String getTheme() {
        return getAttribute("theme");
    }

    public void redraw() {
        Escalator e = e(grid);
        c(e.getHeader());
        c(e.getFooter());
        c(e.getBody());
        ColumnConfiguration columnConfiguration = f(e);
        for (int i = 0; i < columnConfiguration.getColumnCount(); i++) {
            columnConfiguration.setColumnWidth(i, columnConfiguration.getColumnWidth(i));
        }
    }

    private static native Escalator e(Grid<?> g) /*-{
        return g.@com.vaadin.client.widgets.Grid::escalator;
    }-*/;

    private static native Escalator c(RowContainer r) /*-{
        r.@com.vaadin.client.widgets.Escalator.AbstractRowContainer::defaultRowHeightShouldBeAutodetected = true;
        r.@com.vaadin.client.widgets.Escalator.AbstractRowContainer::autodetectRowHeightLater()();
    }-*/;

    private static native ColumnConfiguration f(Escalator e) /*-{
        return e.@com.vaadin.client.widgets.Escalator::columnConfiguration;
    }-*/;
}
