package com.vaadin.components.grid;

import static com.google.gwt.query.client.GQuery.$;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.Properties;
import com.google.gwt.query.client.js.JsUtils;
import com.google.gwt.query.client.plugins.widgets.WidgetsUtils;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.data.AbstractRemoteDataSource;
import com.vaadin.client.data.DataSource;
import com.vaadin.client.renderers.Renderer;
import com.vaadin.client.widget.grid.RendererCellReference;
import com.vaadin.client.widget.grid.selection.SelectionEvent;
import com.vaadin.client.widget.grid.selection.SelectionHandler;
import com.vaadin.client.widgets.Grid;
import com.vaadin.client.widgets.Grid.HeaderCell;
import com.vaadin.client.widgets.Grid.HeaderRow;
import com.vaadin.client.widgets.Grid.SelectionMode;
import com.vaadin.components.common.util.DOMUtils;
import com.vaadin.components.common.util.Elements;
import com.vaadin.components.grid.config.JS;
import com.vaadin.components.grid.config.JSArray;
import com.vaadin.components.grid.config.JSColumn;
import com.vaadin.components.grid.config.JSHeaderCell;
import com.vaadin.components.grid.config.JSHeaderCell.Format;
import com.vaadin.components.grid.data.GridDataSource;
import com.vaadin.components.grid.data.GridDomTableDataSource;
import com.vaadin.components.grid.data.GridJsFuncDataSource;
import com.vaadin.components.grid.data.GridJsObjectDataSource;
import com.vaadin.shared.ui.grid.HeightMode;

@JsNamespace(Elements.VAADIN_JS_NAMESPACE)
@JsExport
@JsType
public class GridComponent implements SelectionHandler<JsArrayMixed> {

    private Grid<JsArrayMixed> grid;
    public JSArray<JSColumn> cols;
    private int size = 0;

    private GQuery lightDom;

    private boolean updating = false;

    private int headerDefaultRowIndex = 0;


    public GridComponent() {
        // FIXME: If there is no default constructor JsInterop does not export
        // anything
    }

    public Element getGridElement() {
        return grid.getElement();
    }

    public void setLightDom(TableElement tableElement) {
        lightDom = $(tableElement);
    }

    public void created() {
        setColumns(JS.createArray());
        grid = new Grid<JsArrayMixed>();
        grid.addSelectionHandler(this);
    }

    public void initGrid() {
        WidgetsUtils.attachWidget(grid, null);
        loadHeaders();

        DataSource<JsArrayMixed> dataSource = grid.getDataSource();

        while (grid.getColumnCount() > 1) {
            grid.removeColumn(grid.getColumn(1));
        }
        if (cols != null) {
            for (int i = 0, l = cols.size(); i < l; i++) {
                JSColumn c = cols.get(i);
                Grid.Column<Object, JsArrayMixed> col;
                col = createGridColumn(c, i);
                grid.addColumn(col);
                for (int j = 0; j < c.headerData().size(); j++) {
                    if (grid.getHeaderRowCount() < c.headerData().size()) {
                        grid.appendHeaderRow();
                    }
                    JSHeaderCell header = c.headerData().get(j);
                    int offset = 0;
                    for (int k = 0; k <= j + offset; k++) {
                        HeaderRow row = grid.getHeaderRow(k);
                        if (i != 0
                                && row.getCell(grid.getColumn(i - 1))
                                        .getColspan() != 1) {
                            offset++;
                        }
                    }
                    HeaderCell cell = grid.getHeaderRow(j + offset)
                            .getCell(col);
                    cell.setColspan(header.colSpan());
                    Object content = header.content();
                    switch (Format.valueOf(header.format())) {
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
        dataSource = GridDomTableDataSource.createInstance(lightDom.get(0),
                this);
        if (dataSource != null) {
            grid.setDataSource(dataSource);
        }
        redraw();
    }

    public static Grid.Column<Object, JsArrayMixed> createGridColumn(
            final JSColumn gColumn, final int idx) {
        final RegExp templateRegexp = RegExp.compile("\\{\\{data\\}\\}", "ig");
        return new Grid.Column<Object, JsArrayMixed>(new Renderer<Object>() {
            @Override
            public void render(RendererCellReference cell, Object data) {
                Object o = gColumn.renderer();
                Element elm = cell.getElement();
                if (o instanceof JavaScriptObject) {
                    if (JsUtils.isFunction((JavaScriptObject) o)) {
                        JsUtils.jsni((JavaScriptObject) o, "call", o, elm,
                                data, cell.getRow());
                    } else {
                        if ($(elm).data("init") == null) {
                            $(elm).data("init", true);
                            JsUtils.jsni((JavaScriptObject) o, "init", elm);
                        }
                        JsUtils.jsni((JavaScriptObject) o, "render", elm, data);
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
                    o = JsUtils.jsni((JavaScriptObject) o, "call", o, row, idx);
                } else if (o instanceof String
                // For some reason JsInterop returns empty
                        && "" != o) {
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

    private String lastHeaders = null;

    private void loadHeaders() {
        GQuery $theadRows = lightDom.find("thead tr");
        String txt = $theadRows.toString();
        if ($theadRows.isEmpty() || txt.equals(lastHeaders)) {
            return;
        }
        lastHeaders = txt;

        JSArray<JSColumn> colList = JS.createArray();

        Map<JSColumn, JSArray<JSHeaderCell>> contentsMap = new HashMap<JSColumn, JSArray<JSHeaderCell>>();

        headerDefaultRowIndex = $theadRows.index(lightDom.find("tr[default]")
                .get(0));
        if (headerDefaultRowIndex == -1) {
            headerDefaultRowIndex = 0;
        }
        for (int i = 0; i < $theadRows.size(); i++) {
            GQuery $ths = $theadRows.eq(i).children("th");
            while (colList.size() < $ths.size()) {
                JSColumn column = JS.createJsType(JSColumn.class);
                contentsMap.put(column, JS.<JSHeaderCell> createArray());
                colList.add(column);
            }
        }

        for (int i = 0; i < $theadRows.size(); i++) {
            GQuery $ths = $theadRows.eq(i).children("th");

            int colOffset = 0;
            for (int j = 0; j < $ths.size(); j++) {
                JSColumn column = colList.get(j + colOffset);
                JSHeaderCell header = JS.createJsType(JSHeaderCell.class);
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
                        .setFormat(Format.HTML.name());
                contentsMap.get(column).add(header);
            }
        }

        Iterator<JSColumn> iterator = contentsMap.keySet().iterator();
        // When we don't use shadow, sometimes the component could
        // be renderized previously.
        lightDom.find("div[v-wc-container]").remove();

        GQuery $templateRow = lightDom.find("tr[template] td");
        for (int i = 0; iterator.hasNext(); i++) {
            JSColumn column = iterator.next();
            column.setHeaderData(contentsMap.get(column));
            if (i < $templateRow.size()) {
                String html = $templateRow.eq(i).html();
                column.setTemplate(html);
            }
        }

        setColumns(colList);
    }

    public void onMutation() {
        loadHeaders();
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
        Integer column = null;
        try {
            column = Integer.parseInt(frozenColumn);
        } catch (NumberFormatException e) {
            for (int i = 0; i < cols.length(); i++) {
                if (frozenColumn.equals(cols.get(i).headerData().get(0)
                        .content())) {
                    column = i + 1;
                    break;
                }
            }
        }
        if (column != null) {
            grid.setFrozenColumnCount(column);
        }
    }

    @JsNoExport
    public void setCols(JSArray<JSColumn> cols) {
        this.cols = cols;
    }

    @JsNoExport
    public JSArray<JSColumn> getCols() {
        return cols;
    }

    public void setRowCount(int size) {
        this.size = size;
    }

    @JsNoExport
    @Override
    public void onSelect(SelectionEvent<JsArrayMixed> ev) {
        if (!updating) {
            $(this).trigger("select");
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
            loadHeaders();
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
                $(this).delay(5, new Function() {
                    public void f() {
                        setSelectedRows(a);
                    }
                });
            }
        } else if (grid.getDataSource() != null) {
            grid.setDataSource(grid.getDataSource());
        }
    }

    public JavaScriptObject getColumns() {
        return cols;
    }

    public void setSelectionMode(String selectionMode) {
        // TODO: this randomly raises an asynchronous exception
        // The selection column cannot be modified after init
        grid.setSelectionMode(SelectionMode.valueOf(selectionMode.toUpperCase()));
        grid.getDefaultHeaderRow().getCell(grid.getColumn(0)).setText("");
    }

    public void setColumns(JavaScriptObject newCols) {
        if (cols != newCols) {
            if (cols != null) {
                for (int i = 0, l = cols.length(); i < l; i++) {
                    DOMUtils.unobserve(cols.get(i));
                }
            }
            cols = newCols.cast();
            for (int i = 0, l = cols.length(); i < l; i++) {
                DOMUtils.observe(cols.get(i), new EventListener() {
                    public void onBrowserEvent(Event event) {
                        refresh();
                    }
                });
            }
        }
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

    private Timer redrawTimer;
    // TODO: this method is a workaround to adjust sizes of the grid based on
    // the v-grid container. It should be done out-of-the-box by the grid though.
    public void redraw() {
        if (redrawTimer == null) {
            redrawTimer = new Timer() {
                public void run() {
                    // Setting grid to 100% makes it fit to our v-grid container
                    grid.setWidth("100%");
                    grid.resetSizesFromDom();
                    grid.recalculateColumnWidths();

                    // Let see if our container has a fixed css height
                    int vgridHeight = $(GridComponent.this).height();
                    int gridHeight = $(grid).height();
                    if (vgridHeight != gridHeight) {
                        // Use same height that v-grid
                        grid.setHeight(vgridHeight + "px");
                    } else {
                        // Check if data-source size is smaller than grid visible rows, and reduce height
                        // TODO: this should be done using setHeightByRows, but it has performance issues
                        GridDataSource ds = (GridDataSource)grid.getDataSource();
                        if (ds != null && ds.size() < grid.getHeightByRows()) {
                            int h = $(grid).find("tr td").height() + 2;
                            double s = h * (ds.size() + grid.getHeaderRowCount() + grid.getFooterRowCount());
                            grid.setHeight(s + "px");
                        }
                    }
                }
            };
        }
        // Scheduling it we avoid multiple redraw in very small time
        redrawTimer.schedule(50);
    }
}
