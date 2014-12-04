package com.vaadin.prototype.wc.gwt.client.widgets;

import static com.google.gwt.query.client.GQuery.$;
import static com.google.gwt.query.client.GQuery.Widgets;
import static com.google.gwt.query.client.GQuery.console;
import static com.google.gwt.query.client.GQuery.window;
import static com.vaadin.prototype.wc.gwt.client.widgets.WCUtils.getAttrIntValue;
import static com.vaadin.prototype.wc.gwt.client.widgets.WCUtils.getAttrValue;

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
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.JsArrayObject;
import com.vaadin.client.data.DataSource;
import com.vaadin.client.ui.grid.FlyweightCell;
import com.vaadin.client.ui.grid.Grid;
import com.vaadin.client.ui.grid.Grid.SelectionMode;
import com.vaadin.client.ui.grid.GridColumn;
import com.vaadin.client.ui.grid.GridHeader.HeaderCell;
import com.vaadin.client.ui.grid.GridHeader.HeaderRow;
import com.vaadin.client.ui.grid.Renderer;
import com.vaadin.client.ui.grid.datasources.ListDataSource;
import com.vaadin.client.ui.grid.selection.SelectionChangeEvent;
import com.vaadin.client.ui.grid.selection.SelectionChangeHandler;
import com.vaadin.client.ui.grid.selection.SelectionModel;
import com.vaadin.client.ui.grid.selection.SelectionModelMulti;
import com.vaadin.client.ui.grid.selection.SelectionModelSingle;
import com.vaadin.prototype.wc.gwt.client.html.HTMLElement;
import com.vaadin.prototype.wc.gwt.client.html.HTMLEvents;
import com.vaadin.prototype.wc.gwt.client.html.HTMLShadow;
import com.vaadin.prototype.wc.gwt.client.html.HTMLTableElement;
import com.vaadin.prototype.wc.gwt.client.util.Elements;
import com.vaadin.prototype.wc.gwt.client.widgets.grid.GData;
import com.vaadin.prototype.wc.gwt.client.widgets.grid.GData.GColumn;
import com.vaadin.prototype.wc.gwt.client.widgets.grid.GData.GColumn.GHeader;
import com.vaadin.prototype.wc.gwt.client.widgets.grid.GData.GColumn.GHeader.Format;
import com.vaadin.prototype.wc.gwt.client.widgets.grid.GDataSource;
import com.vaadin.prototype.wc.gwt.client.widgets.grid.GJsFuncDataSource;
import com.vaadin.prototype.wc.gwt.client.widgets.grid.GJsObjectDataSource;
import com.vaadin.prototype.wc.gwt.client.widgets.grid.GRestDataSource;
import com.vaadin.shared.ui.grid.GridState;
import com.vaadin.shared.ui.grid.HeightMode;

@JsExport
@JsType
public class WCVGrid extends HTMLTableElement.Prototype implements
        HTMLElement.LifeCycle.Created, HTMLElement.LifeCycle.Attached,
        HTMLElement.LifeCycle.Changed, ValueChangeHandler<Double>, Handler,
        SelectionChangeHandler<JsArrayMixed>, MutationListener {

    public static final String TAG = "v-grid";

    // FIXME: figure out a way to reuse grid.
    private Grid<JsArrayMixed> grid;
    private HTMLEvents selectEvent;
    private HTMLElement container;
    private HTMLElement style;
    private Panel shadowPanel;
    private boolean initialized = false;
    public List<GColumn> cols;
    private List<JsArrayMixed> vals;
    private boolean changed = true;
    // FIXME: using columns name here make this fail in prod mode
    private List<GridColumn<Object, JsArrayMixed>> gridColumns;
    // We save the original content of the Light-DOM because polyfills remove it
    private Observe lightDom;

    // TODO: we should set this from JS among the datasource.
    private int size = 0;

    private int headerDefaultRowIndex = 0;

    public WCVGrid() {
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
        cols = new ArrayList<GColumn>();
        vals = new ArrayList<JsArrayMixed>();
        gridColumns = new ArrayList<>();
    }

    /*
     * TODO: common stuff for exporting other widgets
     */
    private void initWidgetSystem() {
        if (!initialized) {
            initialized = true;
            lightDom = $(this).children().as(Observe.Observe);
            lightDom.observe(Observe.createInit().attributes(true)
                    .characterData(true).childList(true).subtree(true)
                    .attributeFilter("aaa"), this);

            Widget elementWidget = $(this).widget();
            if (elementWidget == null) {
                elementWidget = $(this).as(Widgets).panel().widget();
            }
            elementWidget.addAttachHandler(this);

            HTMLShadow shadow = createShadowRoot();
            shadow.appendChild(style);
            shadow.appendChild(container);

            shadowPanel = $(container).as(Widgets).panel().widget();
            grid = new Grid<JsArrayMixed>();
            grid.addSelectionChangeHandler(this);
            shadowPanel.add(grid);
        }
    }

    @Override
    public void attachedCallback() {
        initWidgetSystem();
        readAttributes();
    }

    @JsNoExport
    public void initGrid() {
        if (!changed) {
            return;
        }
        changed = false;
        DataSource<JsArrayMixed> dataSource = null;
        dataSource = grid.getDataSource();
        if (grid.getSelectionModel() instanceof SelectionModelSingle && $(this).attr("selectionMode").equals("multi")) {
            grid.setSelectionMode(SelectionMode.MULTI);
        } else if (grid.getSelectionModel() instanceof SelectionModelMulti && !$(this).attr("selectionMode").equals("multi")) {
            grid.setSelectionMode(SelectionMode.SINGLE);
        }
        while(gridColumns.size() > 0) {
            grid.removeColumn(gridColumns.remove(0));
        }
        if (cols != null) {
            for (int i = 0, l = cols.size(); i < l; i++) {
                GColumn c = cols.get(i);
                GridColumn<Object, JsArrayMixed> col;
                col = createGridColumn(c, i);
                grid.addColumn(col);
                gridColumns.add(col);
                for (int j = 0; j < c.headerData().size(); j++) {
                    if (grid.getHeader().getRowCount() < c.headerData().size()) {
                        grid.getHeader().appendRow();
                    }
                    GHeader header = c.headerData().get(j);
                    int offset = 0;
                    for (int k = 0; k <= j + offset; k++) {
                        HeaderRow row = grid.getHeader().getRow(k);
                        if (row.getCell(grid.getColumn(i)).getColspan() != 1) {
                            offset++;
                        }
                    }
                    HeaderCell cell = grid.getHeader().getRow(j + offset).getCell(col);
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
            grid.getHeader().setDefaultRow(
                    grid.getHeader().getRow(headerDefaultRowIndex));
        }

        loadRows();
        if (vals != null && !vals.isEmpty()) {
            dataSource = new ListDataSource<JsArrayMixed>(vals);
        }
        if (dataSource != null) {
            grid.setDataSource(dataSource);
        }
    }

    public static GridColumn<Object, JsArrayMixed> createGridColumn(final GColumn gColumn, final int idx) {
        final RegExp templateRegexp = RegExp.compile("\\{\\{data\\}\\}", "ig");
        return new GridColumn<Object, JsArrayMixed>(
                new Renderer<Object>() {
                    public void render(FlyweightCell cell, Object data) {
                        Object o = gColumn.renderer();
                        Element elm = cell.getElement();
                        if (o instanceof JavaScriptObject) {
                            if (JsUtils
                                    .isFunction((JavaScriptObject) o)) {
                                JsUtils.runJavascriptFunction(
                                        (JavaScriptObject) o, "call",
                                        o, elm, data, cell.getRow());
                            } else {
                                if ($(elm).data("init") == null) {
                                    $(elm).data("init", true);
                                    JsUtils.runJavascriptFunction(
                                            (JavaScriptObject) o,
                                            "init", elm);
                                }
                                JsUtils.runJavascriptFunction(
                                        (JavaScriptObject) o, "render",
                                        elm, data);
                            }
                        } else {
                            if (gColumn.template() != null) {
                                // FIXME: this implementation doesn't
                                // reuse any of the possible HTML tags
                                // included in the template.
                                elm.setInnerHTML(templateRegexp
                                        .replace(gColumn.template(), String.valueOf(data)));
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
                    o = JsUtils.runJavascriptFunction((JavaScriptObject) o,
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
        if (!refreshing) {
            readAttributes();
        }
    }

    private void readAttributes() {
        WCUtils.loadVaadinTheme(container, this, style, "reindeer");

        loadHeaders();
        loadRows();
        initGrid();
        parseAttributeDeclarations();

        adjustHeight(vals.size());
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
                vals = GQ.create(GData.class).<GData>set("values", jso).values();
                loadData();
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

        List<GColumn> colList = new ArrayList<GColumn>();

        Map<GColumn, List<GHeader>> contentsMap = new HashMap<GColumn, List<GHeader>>();

        headerDefaultRowIndex = $theadRows.index(lightDom.find("tr[default]")
                .get(0));
        if (headerDefaultRowIndex == -1) {
            headerDefaultRowIndex = 0;
        }
        for (int i = 0; i < $theadRows.size(); i++) {
            GQuery $ths = $theadRows.eq(i).children("th");
            while (colList.size() < $ths.size()) {
                GColumn column = GQ.create(GColumn.class);
                contentsMap.put(column, new ArrayList<GHeader>());
                colList.add(column);
            }
        }

        for (int i = 0; i < $theadRows.size(); i++) {
            GQuery $ths = $theadRows.eq(i).children("th");

            int colOffset = 0;
            for (int j = 0; j < $ths.size(); j++) {
                GColumn column = colList.get(j + colOffset);
                GHeader header = GQ.create(GHeader.class);
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

        Iterator<GColumn> iterator = contentsMap.keySet().iterator();
        GQuery $templateRow = lightDom.find("tr[template] td");
        for (int i = 0; iterator.hasNext(); i++) {
            GColumn column = iterator.next();
            column.setHeaderData(contentsMap.get(column));

            if (i < $templateRow.size()) {
                String html = $templateRow.eq(i).html();
                column.setTemplate(html);
            }
        }

        setCols(colList);
    }

    private void loadRows() {
        GQuery $tr = lightDom.find("tbody tr:not([template])");
        if (!$tr.isEmpty()) {
            setVals(new ArrayList<JsArrayMixed>());
            for (Element tr : $tr.elements()) {
                JsArrayMixed a = JsArrayMixed.createArray().cast();
                vals.add(a);
                GQuery $td = $(tr).find("td");
                for (int i = 0; i < $td.size(); i++) {
                    a.push($td.eq(i).html());
                }
            }
        }
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
        if (grid != null) {
            loadRows();
            loadData();
            $(grid).css().offset();
        }
    }

    private void loadData() {
        if (vals != null && !vals.isEmpty()) {
            grid.setDataSource(new ListDataSource<JsArrayMixed>(vals));
        }
    }

    @JsNoExport
    public void setCols(List<GColumn> cols) {
        changed = true;
        this.cols = cols;
    }

    @JsNoExport
    public List<GColumn> getCols() {
        return cols;
    }

    private void setVals(List<JsArrayMixed> vals) {
        changed = true;
        this.vals = vals;
    }

    @JsNoExport
    private void adjustHeight(int size) {
        if (size > 0) {
            grid.setHeightMode(HeightMode.ROW);
            grid.setHeightByRows(Math.min(size,
                    GridState.DEFAULT_HEIGHT_BY_ROWS));
        }
    }

    @JsNoExport
    public void adjustHeight() {
        int s = grid.getDataSource().size();
        adjustHeight(s);
    }

    @Override
    public void onSelectionChange(SelectionChangeEvent<JsArrayMixed> ev) {
        if (!refreshing) {
            refreshing = true;
            dispatchEvent(selectEvent);
            setAttribute("selectedRow", "" + (getSelectedRow() < 0 ? "" : getSelectedRow()));
            refreshing = false;
        }
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

    public void jsPropertyDataSource() {
    };

    @JsProperty
    public void setDataSource(JavaScriptObject jso) {
        console.log("ADSFAFAAFaaA");
        if (JsUtils.isFunction(jso)) {
            grid.setDataSource(new GJsFuncDataSource(jso, size, this));
        } else if (JsUtils.isArray(jso)) {
            loadHeaders();
            grid.setDataSource(new GJsObjectDataSource(jso.<JsArray<JavaScriptObject>>cast(), this));
        } else if (JsUtils.prop(jso, "url") != null) {
            loadHeaders();
            @SuppressWarnings("unused")
            GRestDataSource d = new GRestDataSource(jso, this);
        } else {
            throw new RuntimeException("Unknown jso: " + jso);
        }
    }

    boolean refreshing = false;
    public void refresh() {
        if ((grid.getDataSource() instanceof GDataSource)) {
            final int a = getSelectedRow();
            ((GDataSource)grid.getDataSource()).refresh();
            if (a > 0) {
                refreshing = true;
                $(this).delay(5, new Function() {
                    public void f() {
                        setSelectedRow(a);
                        refreshing = false;
                    }
                });
            }
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
    private JsArrayObject<JavaScriptObject> columnsJso;

    @JsProperty
    public JavaScriptObject getColumns() {
        // remove old observers
        if (columnsJso != null) {
            for (int i = 0, l = columnsJso.size(); i < l; i++) {
                WCUtils.unobserve(columnsJso.get(i));
            }
        }
        // Using GQuery data-binding magic to convert list to js arrays.
        columnsJso =  GQ.create(GData.class).setColumns(cols).get("columns");

        // Add observers to any column configuration object so as
        for (int i = 0, l = columnsJso.size(); i < l ; i++) {
            WCUtils.observe(columnsJso.get(i), new EventListener() {
                public void onBrowserEvent(Event event) {
                    // TODO: a better way to refresh?
                    grid.setDataSource(grid.getDataSource());
                }
            });
        }
        return columnsJso;
    }

    @JsProperty
    public void setColumns(JavaScriptObject newCols) {
        changed = true;
        cols = GQ.create(GData.class).<GData> set("columns", newCols).columns();
    }

    public void jsPropertySelectedRow() {
    };

    @JsProperty
    public int getSelectedRow() {
        return grid == null || grid.getSelectionModel() == null
                || !(grid.getSelectionModel() instanceof SelectionModel.Single<?>)
                || grid.getSelectedRow() == null ? -1
                        : grid.getDataSource().indexOf(grid.getSelectedRow());
    }

    @JsProperty
    public void setSelectedRow(int idx) {
        if (idx < 0 || idx >= grid.getDataSource().size()) {
            if (getSelectedRow() > 0) {
                grid.deselect(grid.getDataSource().getRow(getSelectedRow()));
            }
        } else {
            grid.select(grid.getDataSource().getRow(idx));
        }
        onSelectionChange(null);
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
            WCUtils.unobserve(selectedJso);
        }
        selectedJso = arr;
        selectedLock = true;
        grid.getSelectionModel().reset();
        for (int i = 0, l = selectedJso.length(); i < l; i++) {
            grid.select(grid.getDataSource().getRow(selectedJso.get(i)));
        }
        selectedLock = false;
    }

    @JsProperty
    public JsArrayInteger getSelectedRows() {
        if (!selectedLock) {
            if (selectedJso == null) {
                selectedJso = JsArrayInteger.createArray().cast();
            }
            selectedJso.setLength(0);
            Collection<JsArrayMixed> c = grid.getSelectedRows();
            for (Iterator<JsArrayMixed> i = c.iterator(); i.hasNext();) {
                selectedJso.push(grid.getDataSource().indexOf(i.next()));
            }
            WCUtils.unobserve(selectedJso);
            WCUtils.observe(selectedJso, new EventListener() {
                public void onBrowserEvent(Event event) {
                    setSelectedRows(selectedJso);
                }
            });
        }
        return selectedJso;
    }

    public Grid<JsArrayMixed> getGrid() {
        if (grid == null) {
            changed = true;
            initGrid();
        }
        return grid;
    }
}
