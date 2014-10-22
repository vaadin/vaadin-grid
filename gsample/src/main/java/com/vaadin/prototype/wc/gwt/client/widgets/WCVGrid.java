package com.vaadin.prototype.wc.gwt.client.widgets;

import static com.google.gwt.query.client.GQuery.$;
import static com.google.gwt.query.client.GQuery.Widgets;
import static com.google.gwt.query.client.GQuery.console;
import static com.google.gwt.query.client.GQuery.window;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayInteger;
import com.google.gwt.core.client.JsArrayMixed;
import com.google.gwt.core.client.js.JsExport;
import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.query.client.GQ;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.js.JsUtils;
import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
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
import com.vaadin.prototype.wc.gwt.client.WC;
import com.vaadin.prototype.wc.gwt.client.html.HTMLElement;
import com.vaadin.prototype.wc.gwt.client.html.HTMLEvents;
import com.vaadin.prototype.wc.gwt.client.html.HTMLShadow;
import com.vaadin.prototype.wc.gwt.client.html.HTMLTableElement;
import com.vaadin.prototype.wc.gwt.client.widgets.grid.GData;
import com.vaadin.prototype.wc.gwt.client.widgets.grid.GData.GColumn;
import com.vaadin.prototype.wc.gwt.client.widgets.grid.GData.GColumn.GHeader;
import com.vaadin.prototype.wc.gwt.client.widgets.grid.GData.GColumn.GHeader.Format;
import com.vaadin.prototype.wc.gwt.client.widgets.grid.GDataSource;
import com.vaadin.shared.ui.grid.GridState;
import com.vaadin.shared.ui.grid.HeightMode;

@JsExport
@JsType
public class WCVGrid extends HTMLTableElement.Prototype implements
        HTMLElement.LifeCycle.Created, HTMLElement.LifeCycle.Attached,
        HTMLElement.LifeCycle.Changed, ValueChangeHandler<Double>, Handler,
        EventListener, SelectionChangeHandler<JsArrayMixed> {

    public static final String TAG = "v-grid";

    // FIXME: figure out a way to reuse grid.
    private Grid<JsArrayMixed> grid;
    private HTMLEvents selectEvent;
    private HTMLElement container;
    private HTMLElement style;
    private Panel shadowPanel;
    private boolean initialized = false;
    private String theme = "valo";
    public List<GColumn> cols;
    private List<JsArrayMixed> vals;
    private boolean changed = true;
    private Timer deferRefresh;

    // TODO: we should set this from JS among the datasource.
    private int size = 0;

    private int headerDefaultRowIndex = 0;

    public WCVGrid() {
        // FIXME: If there is no default constructor JsInterop does not export
        // anything
    }

    @Override
    public void createdCallback() {
        style = WC.create("style");
        style.setAttribute("language", "text/css");
        selectEvent = WC.document.createEvent("HTMLEvents");
        selectEvent.initEvent("select", false, false);
        container = WC.create("div");
        cols = new ArrayList<GColumn>();
        vals = new ArrayList<JsArrayMixed>();
        deferRefresh = new Timer() {
            @Override
            public void run() {
                if (vals != null && !vals.isEmpty()) {
                    adjustHeight(vals != null ? vals.size() : 100);
                    initGrid();
                }
            }
        };
    }

    /*
     * TODO: common stuff for exporting other widgets
     */
    private void initWidgetSystem() {
        if (!initialized) {
            initialized = true;
            Widget elementWidget = $(this).widget();
            if (elementWidget == null) {
                elementWidget = $(this).as(Widgets).panel().widget();
            }
            elementWidget.addAttachHandler(this);

            HTMLShadow shadow = createShadowRoot();
            shadow.appendChild(style);
            shadow.appendChild(container);

            shadowPanel = $(container).as(Widgets).panel().widget();
        }
    }

    @Override
    public void attachedCallback() {
        initWidgetSystem();
        readAttributes();
        addEventListener("DOMSubtreeModified", this);
    }

    private void initGrid() {
        if (!changed) {
            return;
        }
        changed = false;

        if (grid != null) {
            grid.removeFromParent();
        }
        grid = new Grid<JsArrayMixed>();
        grid.addSelectionChangeHandler(this);
        shadowPanel.add(grid);

        if ($(this).attr("selectionMode").equals("multi")) {
            grid.setSelectionMode(SelectionMode.MULTI);
        } else {
            grid.setSelectionMode(SelectionMode.SINGLE);
        }
        if (cols != null) {
            for (int i = 0, l = cols.size(); i < l; i++) {
                final GColumn c = cols.get(i);
                GridColumn<String, JsArrayMixed> col;
                final int idx = i;
                col = new GridColumn<String, JsArrayMixed>(
                        new Renderer<String>() {
                            public void render(FlyweightCell cell, String data) {
                                Object o = c.renderer();
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
                                    elm.setInnerHTML(data);
                                }
                            }
                        }) {
                    @Override
                    public String getValue(JsArrayMixed row) {
                        Object o = c.value();
                        if (o instanceof JavaScriptObject) {
                            o = JsUtils.runJavascriptFunction(
                                    (JavaScriptObject) o, "call", o, row, idx);
                        } else if (o instanceof String) {
                            o = JsUtils.prop(row, o);
                        } else {
                            o = row.getObject(idx);
                        }
                        return String.valueOf(o);
                    }
                };

                grid.addColumn(col);
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
                    HeaderCell cell = grid.getHeader().getRow(j + offset)
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
            grid.getHeader().setDefaultRow(
                    grid.getHeader().getRow(headerDefaultRowIndex));
        }
        loadRows();
        if (vals != null && !vals.isEmpty()) {
            ListDataSource<JsArrayMixed> dataSource = new ListDataSource<JsArrayMixed>(
                    vals);
            grid.setDataSource(dataSource);
        }
    }

    @Override
    public void onValueChange(ValueChangeEvent<Double> ev) {
    }

    @Override
    public void attributeChangedCallback() {
        readAttributes();
    }

    private void readAttributes() {
        theme = getAttrValue("theme", "reindeer");
        // style.innerText("@import url('" + GWT.getModuleBaseURL() +
        // "../../themes/" + theme + "/styles.css')");
        style.innerText("@import url('VAADIN/themes/" + theme + "/styles.css')");
        container.setAttribute("class", theme);

        loadHeaders();
        loadRows();
        initGrid();
        parseAttributeDeclarations();

        adjustHeight(vals.size());

        setSelectedRow(getAttrIntValue("selectedRow", -1));
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
        GQuery $theadRows = $(this).find("thead tr");
        String txt = $theadRows.toString();
        if ($theadRows.isEmpty() || txt.equals(lastHeaders)) {
            return;
        }
        lastHeaders = txt;

        List<GColumn> colList = new ArrayList<GColumn>();

        Map<GColumn, List<GHeader>> contentsMap = new HashMap<GColumn, List<GHeader>>();

        headerDefaultRowIndex = $theadRows.index($(this).find("tr[default]")
                .get(0));
        if (headerDefaultRowIndex == -1) {
            headerDefaultRowIndex = 0;
        }
        for (int i = 0; i < $theadRows.size(); i++) {
            GQuery $th = $theadRows.eq(i).children("th");
            while (colList.size() < $th.size()) {
                GColumn column = GQ.create(GColumn.class);
                contentsMap.put(column, new ArrayList<GHeader>());
                colList.add(column);
            }
        }

        for (int i = 0; i < $theadRows.size(); i++) {
            GQuery $row = $theadRows.eq(i).children("th");

            int colOffset = 0;
            for (int j = 0; j < $row.size(); j++) {
                GColumn column = colList.get(j + colOffset);

                GHeader header = GQ.create(GHeader.class);

                GQuery $th = $row.eq(j);

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

        for (GColumn key : contentsMap.keySet()) {
            key.setHeaderData(contentsMap.get(key));
        }

        setCols(colList);
    }

    // TODO: Make this part of the API of a utils class.
    private int getAttrIntValue(String attr, int def) {
        return Integer.parseInt(getAttrValue(attr, String.valueOf(def)));
    }

    private void loadRows() {
        GQuery $tr = $(this).find("tbody tr");
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

    // TODO: Make this part of the API of a utils class.
    private double getAttrDoubleValue(String attr, double def) {
        return Double.valueOf(getAttrValue(attr, String.valueOf(def)));
    }

    // TODO: Make this part of the API of a utils class.
    private String getAttrValue(String attr, String def) {
        String val = getAttribute(attr);
        return val == null || val.isEmpty() ? def : val;
    }

    @Override
    public void onAttachOrDetach(AttachEvent event) {
        // TODO: Do something with shadowPanel, right now
        // gQuery creates a new root-panel so it does not
        // have any parent, but we should maintain the widget
        // hierarchy someway.
    }

    @Override
    public void onBrowserEvent(Event event) {
        console.log(event.getType());
        if (event.getType().equals("DOMSubtreeModified")) {
            readAttributes();
            if (grid != null) {
                loadRows();
                loadData();
            }
        }
    }

    private void loadData() {
        if (vals != null && !vals.isEmpty()) {
            grid.setDataSource(new ListDataSource<JsArrayMixed>(vals));
        }
    }

    private void setCols(List<GColumn> cols) {
        changed = true;
        this.cols = cols;
    }

    private void setVals(List<JsArrayMixed> vals) {
        changed = true;
        this.vals = vals;
    }

    private void adjustHeight(int size) {
        if (size > 0) {
            grid.setHeightMode(HeightMode.ROW);
            grid.setHeightByRows(Math.min(size,
                    GridState.DEFAULT_HEIGHT_BY_ROWS));
        }
    }

    @Override
    public void onSelectionChange(SelectionChangeEvent<JsArrayMixed> ev) {
        setAttribute("selectedRow", "" + getSelectedRow());
        dispatchEvent(selectEvent);
    }

    // TODO:
    // @JsProperty seems not exporting these methods right now.
    // We use a magic function name 'jsProperty...' to mark these methods as
    // JS properties when mixing the prototype in WC.
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
    public void setDataSource(JavaScriptObject function) {
        assert JsUtils.isFunction(function);
        grid.setDataSource(new GDataSource(function, size));
    }

    @JsProperty
    public JavaScriptObject getDataSource() {
        return JavaScriptObject.createFunction();
    }

    public void jsPropertyColumns() {
    };

    @JsProperty
    public JavaScriptObject getColumns() {
        // TODO: use observable
        deferRefresh.schedule(200);
        // Using GQuery data-binding magic to convert list to js arrays.
        return GQ.create(GData.class).setColumns(cols).get("columns");
    }

    @JsProperty
    public void setColumns(JavaScriptObject newCols) {
        cols = GQ.create(GData.class).<GData> set("columns", newCols).columns();
    }

    public void jsPropertySelectedRow() {
    };

    @JsProperty
    public int getSelectedRow() {
        return !(grid.getSelectionModel() instanceof SelectionModel.Single<?>)
                || grid.getSelectedRow() == null ? -1 : grid.getDataSource()
                .indexOf(grid.getSelectedRow());
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
    }

    public void jsPropertySelectedRows() {
    };

    @JsProperty
    public void setSelectedRows(JsArrayInteger arr) {
        grid.getSelectionModel().reset();
        for (int i = 0, l = arr.length(); i < l; i++) {
            grid.select(grid.getDataSource().getRow(arr.get(i)));
        }
    }

    @JsProperty
    public JsArrayInteger getSelectedRows() {
        JsArrayInteger ret = JsArrayInteger.createArray().cast();
        Collection<JsArrayMixed> c = grid.getSelectedRows();
        for (Iterator<JsArrayMixed> i = c.iterator(); i.hasNext();) {
            ret.push(grid.getDataSource().indexOf(i.next()));
        }
        // TODO: use observable
        deferRefresh.schedule(200);
        return ret;
    }
}
