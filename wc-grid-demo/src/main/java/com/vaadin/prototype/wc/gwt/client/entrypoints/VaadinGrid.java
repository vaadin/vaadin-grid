package com.vaadin.prototype.wc.gwt.client.entrypoints;

import static com.google.gwt.query.client.GQuery.$;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Element;
import com.google.gwt.query.client.Function;
import com.vaadin.prototype.wc.gwt.client.html.HTMLElement;
import com.vaadin.prototype.wc.gwt.client.util.Elements;
import com.vaadin.prototype.wc.gwt.client.widgets.WCUtils;
import com.vaadin.prototype.wc.gwt.client.widgets.WCVGrid;

/**
 * Exports only the Vaadin grid web component
 */
public class VaadinGrid implements EntryPoint {
    public void onModuleLoad() {
        WCUtils.loadVaadinGlobalTheme(new Function() {
            public void f() {
                for (Element e : $("v-grid").elements()) {
                    ((WCVGrid)(HTMLElement)e).redraw();
                }
            }
        });
        Elements.registerElement(WCVGrid.TAG, new WCVGrid());
    }
}
