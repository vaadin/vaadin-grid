package com.vaadin.prototype.wc.gwt.client.widgets;

import static com.google.gwt.query.client.GQuery.$;
import static com.google.gwt.query.client.GQuery.Widgets;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsDate;
import com.google.gwt.core.client.js.JsExport;
import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
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
public class WCDateBox extends HTMLElement.Prototype implements
        HTMLElement.LifeCycle.Created,
        HTMLElement.LifeCycle.Attached,
        HTMLElement.LifeCycle.Changed,
        Handler, ValueChangeHandler<Date> {

    public static final String TAG = "x-date-box";

    private DateBox widget;
    private HTMLEvents changeEvent;
    private HTMLElement container;
    private HTMLElement style;
    private boolean initialized = false;
    private String theme = "clean";

    public WCDateBox() {
        // FIXME: If there is no default constructor JsInterop does not export anything
    }

    @Override
    public void createdCallback() {
        style = Elements.create("style");
        style.setAttribute("language", "text/css");

        widget = new DateBox();
        widget.addValueChangeHandler(this);

        changeEvent = Elements.document.createEvent("HTMLEvents");
        changeEvent.initEvent("change", false, false);

        container = Elements.create("div");
        readAttributes();
    }

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
            shadow.appendChild(style);
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
        theme = getAttrValue("theme", "chrome");
        String url = GWT.getModuleBaseForStaticFiles() + "gwt/" + theme + "/" + theme + ".css";
        style.innerText("@import url('" + url + "')");
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

    private JsDate date;

    public void jsPropertyValue() {
    }

    @JsProperty public void setValue(JsDate date) {
        this.date = date;
        widget.setValue(date == null ? null : new Date((long)date.getTime()));
    }

    @JsProperty public JsDate getValue() {
        if (widget.getValue() == null) {
            return null;
        }
        if (date == null) {
            date = JsDate.create();
        }
        date.setTime(widget.getValue().getTime());
        return date;
    }
}
