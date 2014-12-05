package com.vaadin.prototype.wc.gwt.client.widgets;

import static com.google.gwt.query.client.GQuery.$;
import static com.google.gwt.query.client.GQuery.Widgets;
import static com.google.gwt.query.client.GQuery.console;
import static com.google.gwt.query.client.GQuery.window;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.js.JsExport;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.StyleElement;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.query.client.js.JsUtils;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DatePicker;
import com.vaadin.prototype.wc.gwt.client.html.HTMLElement;
import com.vaadin.prototype.wc.gwt.client.html.HTMLEvents;
import com.vaadin.prototype.wc.gwt.client.html.HTMLShadow;
import com.vaadin.prototype.wc.gwt.client.util.Elements;

/**
 * Element class for exporting a GWT DateBox widget as a web element.
 *
 * @author manolo
 */
@JsExport
@JsType
public class WCDatepicker extends HTMLElement.Prototype implements
        HTMLElement.LifeCycle.Created,
        HTMLElement.LifeCycle.Attached,
        HTMLElement.LifeCycle.Changed,
        Handler, ValueChangeHandler<Date> {

    public static final String TAG = "x-date-picker";

    private DatePicker widget;
    private HTMLEvents changeEvent;
    private HTMLElement container;
    private StyleElement style;
    private boolean initialized = false;
    private String theme = "clean";

    public WCDatepicker() {
        // FIXME: If there is no default constructor JsInterop does not export anything
    }

    static int a = 0;
    @Override
    public void createdCallback() {
        style = Document.get().createStyleElement();

        widget = new DatePicker();
        widget.addValueChangeHandler(this);

        changeEvent = Elements.document.createEvent("HTMLEvents");
        changeEvent.initEvent("change", false, false);

        container = Elements.create("div");
        readAttributes();
        console.log("Created .... " + a++ + container);
    }

    /*
     * TODO: share this with other widgets in an abstract class
     */
    private void initWidgetSystem() {
        if (!initialized) {
            initialized = true;
            // If the attached element is not a widget yet, promote it to
            // a widget to respect gwt hierarchy
            Widget elementWidget = $(this).widget();
            if (elementWidget == null) {
                // Use gQuery to promote an element to a widget
                elementWidget = $(this).as(Widgets).panel().widget();
            }
            // handle gwt attach events
            elementWidget.addAttachHandler(this);

            HTMLShadow shadow = this.createShadowRoot();
            shadow.appendChild((HTMLElement)style);

            shadow.appendChild(container);

            // Promote the shadow element to a panel so as we can attach to it
            // the exported widget.
            Panel shadowPanel = $(container).as(Widgets).panel().widget();
            shadowPanel.add(widget);
        }
    }

    @Override
    public void attachedCallback() {
        initWidgetSystem();
    }

    @Override
    public void attributeChangedCallback() {
        readAttributes();
    }

    private void readAttributes() {
        JsUtils.prop(window, "ee", this);
        console.log(this);
        theme = getAttrValue("theme", "chrome");
        widget.setYearAndMonthDropdownVisible(Boolean.valueOf(getAttribute("dropdown")));

        String url = GWT.getModuleBaseForStaticFiles() + "gwt/" + theme + "/" + theme + ".css";
        String css = "@import url('" + url + "');";
        style.appendChild(Document.get().createTextNode(css));

        container.setAttribute("class", theme);
        // TODO: read value and parse it
    }

    private String getAttrValue(String attr, String def) {
        String val = getAttribute(attr);
        return val == null || val.isEmpty() ? def : val;
    }

    @Override
    public void onAttachOrDetach(AttachEvent event) {
        // TODO: Do something with shadowPanel
    }

    @Override
    public void onValueChange(ValueChangeEvent<Date> ev) {
        String val = ev.getValue().toString();
        if (!val.equals(getAttribute("value"))) {
            setAttribute("value", val);
            dispatchEvent(changeEvent);
        }
    }
}
