package com.vaadin.prototype.wc.gwt.client.widgets;

import static com.google.gwt.query.client.GQuery.$;
import static com.google.gwt.query.client.GQuery.Widgets;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.js.JsExport;
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
import com.vaadin.prototype.wc.gwt.client.util.WC;

@JsExport
@JsType
public class WCDateBox extends HTMLElement.Prototype implements
        HTMLElement.LifeCycle.Created,
        HTMLElement.LifeCycle.Attached,
        HTMLElement.LifeCycle.Changed,
        Handler, ValueChangeHandler<Date> {

    public static final String TAG = "x-date-box";

    private DateBox datebox;
    private HTMLEvents changeEvent;
    private HTMLElement container;
    private HTMLElement style;
    private Panel shadowPanel;
    private boolean initialized = false;
    private String theme = "clean";

    public WCDateBox() {
        // FIXME: If there is no default constructor JsInterop does not export anything
    }

    @Override
    public void createdCallback() {
        style = WC.create("style");
        style.setAttribute("language", "text/css");

        datebox = new DateBox();
        datebox.addValueChangeHandler(this);

        changeEvent = Elements.document.createEvent("HTMLEvents");
        changeEvent.initEvent("change", false, false);

        container = WC.create("div");
        readAttributes();
    }
    
    /*
     * TODO: common stuff for exporting other widgets
     */
    private void initWidgetSystem() {
        if (!initialized) {
            initialized = true;
            Widget elementWidget = $(this).widget();
            if (elementWidget == null) {
                elementWidget = $(this).as(Widgets).panel().widget();
            }
            elementWidget.addAttachHandler(this);

            HTMLShadow shadow = this.createShadowRoot();
            shadow.appendChild(style);
            shadow.appendChild(container);

            Panel shadowPanel = $(container).as(Widgets).panel().widget();
            shadowPanel.add(datebox);
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
    }

    // TODO: Make this part of the API of a utils class.
    private double getAttrDoubleValue(String attr, double def) {
        return Double.valueOf(getAttrValue(attr, String.valueOf(def)));
    }

    // TODO: Make this part of the API of a utils class.
    private String getAttrValue(String attr, String def) {
        String val = getAttribute(attr);
        return val == null || val.isEmpty() ? def : val;
    }

    @Override
    public void onAttachOrDetach(AttachEvent event) {
        // TODO: Do something with shadowPanel, right now
        // gQuery creates a new root-panel so it does not
        // have any parent, but we should maintain the widget
        // hierarchy someway.
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
