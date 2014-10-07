package com.vaadin.prototype.wc.gwt.client.widgets;

import static com.google.gwt.query.client.GQuery.$;
import static com.google.gwt.query.client.GQuery.Widgets;
import static com.google.gwt.query.client.GQuery.console;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsArrayMixed;
import com.google.gwt.core.client.js.JsExport;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.query.client.GQ;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.js.JsUtils;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.data.AbstractRemoteDataSource;
import com.vaadin.client.ui.grid.FlyweightCell;
import com.vaadin.client.ui.grid.Grid;
import com.vaadin.client.ui.grid.GridColumn;
import com.vaadin.client.ui.grid.Renderer;
import com.vaadin.client.ui.grid.GridHeader.HeaderCell;
import com.vaadin.client.ui.grid.GridHeader.HeaderRow;
import com.vaadin.client.ui.grid.datasources.ListDataSource;
import com.vaadin.client.ui.grid.selection.SelectionChangeEvent;
import com.vaadin.client.ui.grid.selection.SelectionChangeHandler;
import com.vaadin.prototype.wc.gwt.client.WC;
import com.vaadin.prototype.wc.gwt.client.html.HTMLElement;
import com.vaadin.prototype.wc.gwt.client.html.HTMLEvents;
import com.vaadin.prototype.wc.gwt.client.html.HTMLShadow;
import com.vaadin.prototype.wc.gwt.client.widgets.grid.GData;
import com.vaadin.prototype.wc.gwt.client.widgets.grid.GData.GColumn;
import com.vaadin.prototype.wc.gwt.client.widgets.grid.GData.GColumn.GHeader;
import com.vaadin.prototype.wc.gwt.client.widgets.grid.GData.GColumn.GHeader.Format;
import com.vaadin.shared.ui.grid.GridState;
import com.vaadin.shared.ui.grid.HeightMode;

@JsExport
@JsType
public class WCVGrid extends HTMLElement.Prototype implements
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
    private String dataSource;
    private List<GColumn> cols;
    private List<JsArrayMixed> vals;
    private boolean changed = true;
    private Timer deferRefresh;

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

    public JavaScriptObject columns(int n) {
        changed = true;
        deferRefresh.schedule(200);
        return cols.get(Math.min(cols.size() - 1, Math.max(0, n))).getDataImpl();
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
        if (cols != null) {
            for (int i = 0, l = cols.size(); i < l; i++) {
                final GColumn c = cols.get(i);
                GridColumn<String, JsArrayMixed> col;
                final int idx = i;
                col = new GridColumn<String, JsArrayMixed>(new Renderer<String>() {
                    public void render(FlyweightCell cell, String data) {
                        Object o = c.renderer();
                        Element elm = cell.getElement();
                        if (o instanceof JavaScriptObject) {
                            if (JsUtils.isFunction((JavaScriptObject)o)) {
                                JsUtils.runJavascriptFunction((JavaScriptObject)o, "call", o, elm, data, cell.getRow());
                            } else {
                                if ($(elm).data("init") == null) {
                                    $(elm).data("init", true);
                                    JsUtils.runJavascriptFunction((JavaScriptObject)o, "init", elm);
                                }
                                JsUtils.runJavascriptFunction((JavaScriptObject)o, "render", elm, data);
                            }
                        } else {
                            elm.setInnerHTML(data);
                        }
                    }
                }){
                    public String getValue(JsArrayMixed row) {
                        return row.getString(idx);
                    }
                };
                
                grid.addColumn(col);
                for (int j = 0; j < c.headerData().size(); j++) {
                    if (grid.getHeader().getRowCount() < c
                            .headerData().size()) {
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
        initValsFromContents();
        if (vals != null && !vals.isEmpty()) {
            ListDataSource<JsArrayMixed> dataSource = new ListDataSource<JsArrayMixed>(
                    vals);
            grid.setDataSource(dataSource);
        }
    }

    private void initValsFromContents() {
        GQuery body = GQuery.$(this).children("tbody");
        if (body == null || body.isEmpty()) {
            return;
        }
        GQuery rows = body.children("tr");

        if (rows != null && !rows.isEmpty()) {
            List<JsArrayMixed> list = new ArrayList<JsArrayMixed>();
            for (int i = 0; i < rows.size(); ++i) {
                JsArrayMixed array = (JsArrayMixed) JsArrayMixed
                        .createArray(cols.size());
                GQuery row = GQuery.$(rows.get(i)).children();
                for (int j = 0; j < row.size(); ++j) {
                    array.set(j, row.get(j).getInnerText());
                }
                list.add(array);
            }
            vals = list;
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
        adjustHeight(vals.size());
    }

    private String lastHeaders = null;

    private void loadHeaders() {
        GQuery $theadRows = $(this).find("thead tr");
        String txt = $theadRows.toString();
        if ($theadRows.isEmpty() || txt.equals(lastHeaders)) {
            return;
        }
        lastHeaders = txt;

        console.log("We have headers: " + $theadRows);
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
                initValsFromContents();
                if (vals != null && !vals.isEmpty()) {
                    ListDataSource<JsArrayMixed> dataSource = new ListDataSource<JsArrayMixed>(
                            vals);
                    grid.setDataSource(dataSource);
                }
            }
        }
    }

    public void setCols(List<GColumn> cols) {
        changed = true;
        this.cols = cols;
    }

    public void setVals(List<JsArrayMixed> vals) {
        changed = true;
        this.vals = vals;
    }

    public void dataSource(JavaScriptObject function) {
        assert JsUtils.isFunction(function);
        grid.setDataSource(new FunctionAbstractRemoteDataSource(function));
    }

    private GData parseJso(JavaScriptObject o) {
        GData r = GQ.create(GData.class);
        if (JsUtils.isArray(o)) {
            r.setValues(JsArrayToList(o));
        } else {
            r.load(o);
        }
        if (!r.values().isEmpty()) {
            setVals(r.values());
        }
        if (!r.columns().isEmpty()) {
            List<GColumn> colRows = r.columns();
            setCols(colRows);
        }
        initGrid();
        return r;
    }

    private List<JsArrayMixed> JsArrayToList(JavaScriptObject o) {
        List<JsArrayMixed> vals = new ArrayList<JsArrayMixed>();
        JsArray<JsArrayMixed> arr = o.cast();
        for (int i = 0; i < arr.length(); i++) {
            vals.add(arr.get(i));
        }
        return vals;
    }

    private int rowsize = 0;

    public void rowCount(double rows) {
        rowsize = (int) rows;
        adjustHeight(rowsize);
    }

    private void adjustHeight(int size) {
        grid.setHeightMode(HeightMode.ROW);
        grid.setHeightByRows(Math.min(size, GridState.DEFAULT_HEIGHT_BY_ROWS));
    }

    private native JavaScriptObject exec(JavaScriptObject f, int idx,
            int count, AsyncCallback<JavaScriptObject> cb) /*-{
        return f(idx, count, function(r) {
            cb.@com.google.gwt.user.client.rpc.AsyncCallback::onSuccess(*)(r);
        });
    }-*/;

    private class FunctionAbstractRemoteDataSource extends
            AbstractRemoteDataSource<JsArrayMixed> {
        private JavaScriptObject f;

        // TODO: make a feature request to grid team so as they implement indexOf(row)
        private HashMap<Object, Integer> idx = new HashMap<Object, Integer>();

        public FunctionAbstractRemoteDataSource(JavaScriptObject jso) {
            assert JsUtils.isFunction(jso);
            f = jso;
            setEstimatedSize(rowsize);
        }

        @Override
        protected void requestRows(final int idx, int count) {
            JavaScriptObject o = exec(f, idx, count,
                    new AsyncCallback<JavaScriptObject>() {
                        public void onFailure(Throwable caught) {
                        }

                        public void onSuccess(JavaScriptObject result) {
                            setRowData(idx, JsArrayToList(result));
                        }
                    });
            if (o != null) {
                setRowData(idx, JsArrayToList(o));
            }
        }

        @Override
        protected void setRowData(int firstRowIndex, List<JsArrayMixed> rowData) {
            for (int i = 0; i < rowData.size(); i++) {
                idx.put(getRowKey(rowData.get(i)), firstRowIndex + i);
            }
            super.setRowData(firstRowIndex, rowData);
        }

        @Override
        public Object getRowKey(JsArrayMixed row) {
            return row.toString();
        }

        public int indexOf(JsArrayMixed row) {
            return row == null ? -1 : idx.get(getRowKey(row));
        }

        @Override
        public JsArrayMixed getRow(int rowIndex) {
            return super.getRow(rowIndex);
        }
    }

    @Override
    public void onSelectionChange(SelectionChangeEvent<JsArrayMixed> ev) {
        dispatchEvent(selectEvent);
    }

    public int selectedRow() {
        return ((FunctionAbstractRemoteDataSource) grid.getDataSource())
                .indexOf(grid.getSelectedRow());
    }

    public void selectRow(int idx) {
       grid.select(grid.getDataSource().getRow(idx));
    }
}
