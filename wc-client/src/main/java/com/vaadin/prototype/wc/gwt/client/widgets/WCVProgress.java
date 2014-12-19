package com.vaadin.prototype.wc.gwt.client.widgets;

import static com.google.gwt.query.client.GQuery.$;
import static com.google.gwt.query.client.GQuery.Widgets;
import static com.vaadin.prototype.wc.gwt.client.widgets.WCUtils.getAttrFloatValue;

import com.google.gwt.core.client.js.JsExport;
import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.ui.VProgressBar;
import com.vaadin.prototype.wc.gwt.client.html.HTMLElement;
import com.vaadin.prototype.wc.gwt.client.html.HTMLEvents;
import com.vaadin.prototype.wc.gwt.client.html.HTMLShadow;
import com.vaadin.prototype.wc.gwt.client.util.Elements;

@JsExport
@JsType
public class WCVProgress extends HTMLElement.Prototype implements
        HTMLElement.LifeCycle.Created, HTMLElement.LifeCycle.Attached,
        HTMLElement.LifeCycle.Changed, Handler {

    public static final String TAG = "v-progress";

    private VProgressBar progress;
    private HTMLEvents changeEvent;
    private HTMLElement container;
    private HTMLElement style;
    private boolean initialized = false;

    public WCVProgress() {
        // FIXME: If there is no default constructor JsInterop does not export
        // anything
    }

    @Override
    public void createdCallback() {
        style = Elements.create("style");

        progress = new VProgressBar();

        changeEvent = Elements.document.createEvent("HTMLEvents");
        changeEvent.initEvent("change", false, false);
        changeEvent.srcElement(this);

        container = Elements.create("div");
        container.setAttribute("v-wc-container", "");
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

            if (getAttribute("shadow") != null) {
                HTMLShadow shadow = createShadowRoot();
                shadow.appendChild(style);
                shadow.appendChild(container);
            } else {
                appendChild(style);
                appendChild(container);
            }
            Panel shadowPanel = $(container).as(Widgets).panel().widget();
            shadowPanel.add(progress);
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
        progress.setState(getAttrFloatValue(this, "value", 0));
        WCUtils.loadVaadinTheme(container, this, style, null);
    }

    @Override
    public void onAttachOrDetach(AttachEvent event) {
        // TODO: Do something with shadowPanel, right now
        // gQuery creates a new root-panel so it does not
        // have any parent, but we should maintain the widget
        // hierarchy someway.
    }

    public void jsPropertyValue() {
    }

    @JsProperty
    public void setValue(double value) {
        progress.setState((float) value);
    }

    @JsProperty
    public double getValue() {
        return progress.getState();
    }
}
