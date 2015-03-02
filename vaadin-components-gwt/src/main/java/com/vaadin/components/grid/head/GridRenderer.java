package com.vaadin.components.grid.head;

import static com.google.gwt.query.client.GQuery.$;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.google.gwt.query.client.js.JsUtils;
import com.google.gwt.regexp.shared.RegExp;
import com.vaadin.client.renderers.Renderer;
import com.vaadin.client.widget.grid.RendererCellReference;
import com.vaadin.components.grid.config.JSColumn;

/**
 * Renders a cell based on a JSColumn configuration
 */
class GridRenderer implements Renderer<Object> {
    private final JSColumn jsColumn;
    // TODO: this hard-coded declarative case comes from 0.1.0
    private static final RegExp templateRegexp = RegExp.compile(
            "\\{\\{data\\}\\}", "ig");

    public GridRenderer(JSColumn jsColumn) {
        this.jsColumn = jsColumn;
    }

    public void render(RendererCellReference cell, Object data) {
        Object o = jsColumn.renderer();
        Element elm = cell.getElement();
        if (o instanceof JavaScriptObject) {
            if (JsUtils.isFunction((JavaScriptObject) o)) {
                JsUtils.jsni((JavaScriptObject) o, "call", o, elm, data,
                        cell.getRow());
            } else {
                if ($(elm).data("init") == null) {
                    $(elm).data("init", true);
                    JsUtils.jsni((JavaScriptObject) o, "init", elm);
                }
                JsUtils.jsni((JavaScriptObject) o, "render", elm, data);
            }
        } else {
            if (jsColumn.template() != null) {
                // FIXME: this implementation doesn't
                // reuse any of the possible HTML tags
                // included in the template.
                elm.setInnerHTML(templateRegexp.replace(jsColumn.template(),
                        String.valueOf(data)));
            } else {
                elm.setInnerHTML(String.valueOf(data));
            }
        }
    }
}