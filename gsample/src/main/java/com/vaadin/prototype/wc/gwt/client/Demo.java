package com.vaadin.prototype.wc.gwt.client;

import static com.google.gwt.query.client.GQuery.$;
import static com.google.gwt.query.client.GQuery.document;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.query.client.GQuery;
import com.vaadin.prototype.wc.gwt.client.html.HTMLElement;
import com.vaadin.prototype.wc.gwt.client.widgets.WCVGrid;
import com.vaadin.prototype.wc.gwt.client.widgets.WCVHGrid;
import com.vaadin.prototype.wc.gwt.client.widgets.WCVSlider;

/**
 * Example code for a GwtQuery application
 */
public class Demo implements EntryPoint {

    public void onModuleLoad() {
        $("body").css("font-family", "arial");
        demoHeikki();

        WC.register(WCVGrid.TAG, WCVGrid.class);
        WC.register(WCVSlider.TAG, WCVSlider.class);
    }
    
    void demoHeikki() {
        GQuery gridElement = $("<v-h-grid></v-h-grid>");
        gridElement.
          append("<table><thead id='testhead'><tr><th>Name</th><th>Value</th><th>Progress</th></tr></thead><tbody><tr><td>Grid</td><td>10000</td><td>0.8</td></tr><tr><td>Vaadin X </td><td>999999</td><td>0.8</td></tr></tbody></table>");
        
        WCVHGrid grid = (WCVHGrid) (HTMLElement) gridElement
                .appendTo(document).get(0);
        
    }
    
}
