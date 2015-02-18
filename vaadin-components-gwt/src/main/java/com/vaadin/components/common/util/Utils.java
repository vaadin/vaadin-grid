package com.vaadin.components.common.util;

import com.google.gwt.core.client.js.JsExport;
import com.google.gwt.core.client.js.JsNamespace;
import com.google.gwt.i18n.client.NumberFormat;

@JsExport()
@JsNamespace(Elements.VAADIN_JS_NAMESPACE)
public class Utils {
    @JsExport
    public static String formatCurrency(double val) {
        NumberFormat fmt = NumberFormat.getCurrencyFormat();
        return fmt.format(val);
    }
}
