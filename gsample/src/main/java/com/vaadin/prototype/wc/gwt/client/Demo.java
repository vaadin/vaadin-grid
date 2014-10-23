package com.vaadin.prototype.wc.gwt.client;

import static com.google.gwt.query.client.GQuery.$;
import static com.google.gwt.query.client.GQuery.document;
import static com.google.gwt.query.client.GQuery.window;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.js.JsUtils;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.vaadin.prototype.wc.gwt.client.widgets.WCVGrid;
import com.vaadin.prototype.wc.gwt.client.widgets.WCVProgress;
import com.vaadin.prototype.wc.gwt.client.widgets.WCVSlider;

/**
 * Example code for a GwtQuery application
 */
public class Demo implements EntryPoint {

    public void onModuleLoad() {
        WC.register(WCVGrid.TAG, WCVGrid.class);
        WC.register(WCVProgress.TAG, WCVProgress.class);
        WC.register(WCVSlider.TAG, WCVSlider.class);

        // TODO: When we figure out a way to add lazy directives
        // NgVaadin.loadNgVaadin();

        // Call a vaadin callback function when vaadinX is ready.
        JsUtils.runJavascriptFunction(window, "onVaadinX");
    }

    void demoHeikki() {
        GQuery $grid = $("<v-grid></v-grid>");
        $grid
                .append("<table><thead>"
                        + "<tr><th><button>I'm a button</button></th><th colspan='2'>I'm spanned</th></tr>"
                        + "<tr><th colspan='2'>Double span</th><th>Whee</th></tr>"
                        + "<tr default><th>Name</th><th>Value</th><th>Progress</th></tr>"
                        + "<tr><th>1</th><th>2</th><th>3</th></tr></thead>"
                        + "<tbody id='append-test-body'>"
                        + "<tr><td>Grid</td><td>10000</td><td>0.8</td></tr>"
                        + "<tr><td>Vaadin X </td><td>999999</td><td>0.8</td></tr>"
                        + "</tbody></table>");

        $(document).append("<h3>Below grids inserted programatically</h3>").append($grid);

        Button button = new Button("append data", new ClickHandler() {
            int value = 0;
            @Override
            public void onClick(ClickEvent event) {
                GQuery row = $("<tr></tr>");
                row.append("<td>" + (value++) + "</td>");
                row.append("<td>" + (value++) + "</td>");
                row.append("<td>" + (value++) + "</td>");
                row.appendTo($("#append-test-body"));
            }
        });

        RootPanel.get().add(button);
    }

}
