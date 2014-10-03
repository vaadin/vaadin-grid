package com.vaadin.prototype.wc.gwt.client.widgets;

import static com.google.gwt.query.client.GQuery.$;
import static com.google.gwt.query.client.GQuery.Widgets;

import java.util.ArrayList;
import java.util.logging.Logger;

import com.google.gwt.core.client.js.JsExport;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.ui.grid.Grid;
import com.vaadin.client.ui.grid.GridColumn;
import com.vaadin.client.ui.grid.datasources.ListDataSource;
import com.vaadin.client.ui.grid.renderers.TextRenderer;
import com.vaadin.prototype.wc.gwt.client.WC;
import com.vaadin.prototype.wc.gwt.client.html.HTMLElement;
import com.vaadin.prototype.wc.gwt.client.html.HTMLShadow;

@JsExport
@JsType
public class WCVHGrid extends HTMLElement.Prototype implements
        HTMLElement.LifeCycle.Created, HTMLElement.LifeCycle.Attached,
        HTMLElement.LifeCycle.Changed, Handler {

    private static final Logger log = Logger.getLogger(WCVHGrid.class.getName());

    public static final String TAG = "v-h-grid";

    private Grid<Element> grid;

    private HTMLElement container;
    private HTMLElement style;
    private Panel shadowPanel;
    private boolean initialized = false;
    private String theme = "reindeer";

    private GQuery children;
    private GQuery head;
    private GQuery body;

    public WCVHGrid() {
    }

    @Override
    public void createdCallback() {
        style = WC.create("style");
        style.setAttribute("language", "text/css");

        grid = new Grid<Element>();

        container = WC.create("div");
    }

    private void initWidgetSystem() {
        if (!initialized) {
            initialized = true;
            Widget panel = $(this).as(Widgets).panel().widget();
            if (panel == null) {
                panel = $(this).as(Widgets).panel().widget();
            }
            panel.addAttachHandler(this);

            HTMLShadow shadow = createShadowRoot();
            shadow.appendChild(style);
            shadow.appendChild(container);

            shadowPanel = $(container).as(Widgets).panel().widget();
            shadowPanel.add(grid);
        }

        if (children == null) {
            children = GQuery.$(this);
        }
        if (children != null) {
            if (head == null) {
                head = children.children("thead");
                head.hide();
            }
            if (body == null) {
                body = children.children("tbody");
                body.hide();
            }
        }
        updateWidgetStateFromContent();
    }

    @Override
    public void attachedCallback() {
        initWidgetSystem();
    }

    @Override
    public void attributeChangedCallback() {
        updateWidgetStateFromContent();
    }

    private void updateWidgetStateFromContent() {
        if (!initialized) {
            return;
        }
        theme = getAttrValue("theme", "reindeer");
//        style.innerText("@import url('" + GWT.getModuleBaseURL()
//                + "../../themes/" + theme + "/styles.css')");
        style.innerText("@import url('VAADIN/themes/" + theme + "/styles.css')");

        container.setAttribute("class", theme);

        if (body != null && head != null) {
            GQuery rows = body.children("tr");
            log.info("rows: " + rows.size());
            ArrayList<Element> list = new ArrayList<Element>(rows.size());
            for (int i = 0; i < rows.size(); ++i) {
                list.add(rows.get(i));
            }
            ListDataSource<Element> data = new ListDataSource<Element>(list);
            grid.setDataSource(data);

            GQuery columns = head.children("tr").children("th");
            log.info("columns: " + columns.size());
            for (int i = 0; i < columns.size(); ++i) {
                final int columnIndex = i;
                GridColumn<String, Element> column = new GridColumn<String, Element>(
                        new TextRenderer()) {
                    @Override
                    public String getValue(Element row) {
                        log.info("getValue: "
                                + GQuery.$(row).children("td").get(columnIndex)
                                        .getInnerText());
                        return GQuery.$(row).children("td").get(columnIndex)
                                .getInnerText();
                    }
                };
                grid.addColumn(column);
                log.info("column " + columnIndex + " header: "
                        + columns.get(i).getInnerText());
                grid.getHeader().getDefaultRow().getCell(column)
                        .setText(columns.get(i).getInnerText());
            }
        }
    }

    // TODO: Make this part of the API of a utils class.
    private String getAttrValue(String attr, String def) {
        String val = getAttribute(attr);
        return val == null || val.isEmpty() ? def : val;
    }

    @Override
    public void onAttachOrDetach(AttachEvent event) {
        // TODO Auto-generated method stub
    }

}
