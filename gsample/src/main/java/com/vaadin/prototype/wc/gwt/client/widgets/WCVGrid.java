package com.vaadin.prototype.wc.gwt.client.widgets;

import static com.google.gwt.query.client.GQuery.$;
import static com.google.gwt.query.client.GQuery.Widgets;
import static com.google.gwt.query.client.GQuery.console;

import java.util.ArrayList;
import java.util.List;

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
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.data.AbstractRemoteDataSource;
import com.vaadin.client.ui.grid.Grid;
import com.vaadin.client.ui.grid.GridColumn;
import com.vaadin.client.ui.grid.datasources.ListDataSource;
import com.vaadin.client.ui.grid.renderers.HtmlRenderer;
import com.vaadin.client.ui.grid.renderers.TextRenderer;
import com.vaadin.prototype.wc.gwt.client.WC;
import com.vaadin.prototype.wc.gwt.client.html.HTMLElement;
import com.vaadin.prototype.wc.gwt.client.html.HTMLEvents;
import com.vaadin.prototype.wc.gwt.client.html.HTMLShadow;
import com.vaadin.prototype.wc.gwt.client.widgets.grid.GData;
import com.vaadin.prototype.wc.gwt.client.widgets.grid.GData.GColumn;
import com.vaadin.shared.ui.grid.GridState;
import com.vaadin.shared.ui.grid.HeightMode;

@JsExport
@JsType
public class WCVGrid extends HTMLElement.Prototype implements
        HTMLElement.LifeCycle.Created, HTMLElement.LifeCycle.Attached,
        HTMLElement.LifeCycle.Changed, ValueChangeHandler<Double>, Handler,
        EventListener {

    public static final String TAG = "v-grid";

    // FIXME: figure out a way to reuse grid.
    private Grid grid;
    private HTMLEvents changeEvent;
    private HTMLElement container;
    private HTMLElement style;
    private Panel shadowPanel;
    private boolean initialized = false;
    private String theme = "valo";
    private String dataSource;
    private List<GColumn> cols;
    private List<JsArrayMixed> vals;
    private boolean changed = true;

    public WCVGrid() {
        // FIXME: If there is no default constructor JsInterop does not export
        // anything
    }

    @Override
    public void createdCallback() {
        style = WC.create("style");
        style.setAttribute("language", "text/css");
        changeEvent = WC.document.createEvent("HTMLEvents");
        changeEvent.initEvent("change", false, false);
        container = WC.create("div");
        cols = new ArrayList<GColumn>();
        vals = new ArrayList<JsArrayMixed>();
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
        grid = new Grid();
        shadowPanel.add(grid);
        if (cols != null) {
            for (int i = 0, l = cols.size(); i < l; i++) {
                GColumn c = cols.get(i);
                GridColumn<String, JsArrayMixed> col;
                final int idx = i;
                col = new GridColumn<String, JsArrayMixed>(new HtmlRenderer()) {
                    @Override
                    public String getValue(JsArrayMixed row) {
                        return row.getString(idx);
                    }
                };
                col.setHeader(c.name());
                grid.addColumn(col);
            }
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
        console.log("readAttributes");
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
        GQuery $th = $(this).find("thead th");
        if (!$th.isEmpty()) {
            String txt = $th.toString();
            if (!txt.equals(lastHeaders)) {
                lastHeaders = txt;
                console.log("We have headers: " + $th.size());
                setCols(new ArrayList<GColumn>());
                for (int i = 0; i < $th.size(); i++) {
                    cols.add(GQ.create(GColumn.class).setName($th.eq(i).text()));
                }
            }
        }
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
            setCols(r.columns());
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
            AbstractRemoteDataSource {
        private JavaScriptObject f;

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
        public Object getRowKey(Object row) {
            return row.toString();
        }
    }
}
