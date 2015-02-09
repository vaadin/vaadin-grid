package com.vaadin.prototype.wc.gwt.client.util;

import static com.google.gwt.query.client.GQuery.$;
import static com.google.gwt.query.client.GQuery.$$;
import static com.google.gwt.query.client.GQuery.document;
import static com.google.gwt.query.client.GQuery.lazy;

import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.Properties;

/**
 * Just a Toast Popup made with gQuery.
 */
public class Toast {
    private static Properties showAnimation = $$("opacity: show, left: 10px");
    private static Properties hideAnimation = $$("opacity: hide, left: -450px");
    private static Properties initialCss = 
            $$("position: fixed; left: -450px; bottom: 10px; width: 400px; background: black; border-radius: 4px; color: white; display: none; padding: 10px");
    
    private static GQuery g = $("<div>").appendTo(document).css(initialCss);
    
    public void text(String s) {
        g.text(s);
        show();
    }
    public void show() {
        g.stop(true, true).animate(showAnimation).delay(1200, lazy().animate(hideAnimation).done());
    }
    public void hide() {
        if (g.isVisible()) {
            g.animate(hideAnimation);
        }
    }
}