package com.vaadin.prototype.wc.gwt.client;

import static com.google.gwt.query.client.GQuery.$;
import static com.google.gwt.query.client.GQuery.document;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.vaadin.prototype.wc.gwt.client.html.HTMLElement;
import com.vaadin.prototype.wc.gwt.client.widgets.WCVGrid;
import com.vaadin.prototype.wc.gwt.client.widgets.WCVProgress;
import com.vaadin.prototype.wc.gwt.client.widgets.WCVSlider;

/**
 * Example code for a GwtQuery application
 */
public class Demo implements EntryPoint {

    public void onModuleLoad() {
        $("body").css("font-family", "arial");
        demoHeikki();

        WC.register(WCVGrid.TAG, WCVGrid.class);
        WC.register(WCVProgress.TAG, WCVProgress.class);
        WC.register(WCVSlider.TAG, WCVSlider.class);

    }

    void demoHeikki() {
        GQuery gridElement = $("<v-grid></v-grid>");
        gridElement
                .append("<thead><tr><th>Name</th><th>Value</th><th>Progress</th></tr></thead><tbody id='append-test-body'><tr><td>Grid</td><td>10000</td><td>0.8</td></tr><tr><td>Vaadin X </td><td>999999</td><td>0.8</td></tr></tbody></v-grid>");

        WCVGrid grid = (WCVGrid) (HTMLElement) gridElement.appendTo(document)
                .get(0);

        Button appendButton = new Button("append data", new ClickHandler() {
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
        RootPanel.get().add(appendButton);
    }

}
