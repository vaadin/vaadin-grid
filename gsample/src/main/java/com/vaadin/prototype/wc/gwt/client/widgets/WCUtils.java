package com.vaadin.prototype.wc.gwt.client.widgets;

import com.google.gwt.core.client.js.JsExport;
import com.google.gwt.i18n.client.NumberFormat;
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
}
