package com.vaadin.prototype.wc.gwt.client.widgets;

import static com.google.gwt.query.client.GQuery.$;
import static com.google.gwt.query.client.GQuery.console;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.js.JsExport;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.user.client.EventListener;
import com.vaadin.prototype.wc.gwt.client.html.HTMLElement;

@JsExport
public class WCUtils {
    @JsExport
    public static String formatCurrency(double val) {
        NumberFormat fmt = NumberFormat.getCurrencyFormat();
        return fmt.format(val);
    }

    public static int getAttrIntValue(HTMLElement el, String attr, int def) {
        return (int)getAttrFloatValue(el, attr, def);
    }

    public static double getAttrDoubleValue(HTMLElement el, String attr, double def) {
        return (double)getAttrFloatValue(el, attr, (float)def);
    }

    public static String getAttrValue(HTMLElement el, String attr, String def) {
        String val = el.getAttribute(attr);
        return val == null || val.isEmpty() ? def : val;
    }

    public static float getAttrFloatValue(HTMLElement el, String attr, float def) {
        try {
            return Float.valueOf(getAttrValue(el, attr, String.valueOf(def)));
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static void loadVaadinTheme(HTMLElement container, HTMLElement el, HTMLElement style, String def) {
        String theme = getAttrValue(el, "theme", def);
        if ($(theme).text().contains(theme)) {
            return;
        }
        GQuery l = $("link[href*='vaadin-x.html']");
        String base = GWT.getModuleBaseURL();
        if (!l.isEmpty()) {
            base = l.attr("href").replace("vaadin-x.html", "");
        } else if (base.contains("VAADIN/widgetsets")) {
            base += "../../../";
        }
        base += "VAADIN/themes/";
        $(style).text("@import url('" + base + theme + "/styles.css')");
        container.setAttribute("class", theme);
    }

    public static native void observe(JavaScriptObject jso, EventListener ev) /*-{
        var fnc = function(changes) {
          ev.@com.google.gwt.user.client.EventListener::onBrowserEvent(*)(null);
        };
        jso.__fnc = fnc;
        Object.observe(jso, fnc);
    }-*/;

    public static native void unobserve(JavaScriptObject jso) /*-{
        if (jso && jso.__fnc) {
          Object.unobserve(jso, jso.__fnc);
          jso.__fnc = undefined;
        }
    }-*/;
}
