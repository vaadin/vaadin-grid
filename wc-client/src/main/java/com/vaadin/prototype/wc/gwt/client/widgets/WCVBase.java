package com.vaadin.prototype.wc.gwt.client.widgets;

import static com.google.gwt.query.client.GQuery.$;
import static com.google.gwt.query.client.GQuery.Widgets;

import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.prototype.wc.gwt.client.html.HTMLElement;
import com.vaadin.prototype.wc.gwt.client.html.HTMLEvents;
import com.vaadin.prototype.wc.gwt.client.html.HTMLShadow;
import com.vaadin.prototype.wc.gwt.client.util.Elements;


// TODO: this should be common to all WCV classes, but it seems that
// super.whatever does not work in jsinterop
public class WCVBase extends HTMLElement.Prototype implements
        HTMLElement.LifeCycle.Created, HTMLElement.LifeCycle.Attached,
        HTMLElement.LifeCycle.Changed, Handler {

    protected Widget widget;
    private HTMLEvents changeEvent;
    private HTMLElement container;
    private HTMLElement style;
    protected boolean initialized = false;

    public WCVBase() {
    }

    @Override
    public void createdCallback() {
        style = Elements.create("style");
        changeEvent = Elements.document.createEvent("HTMLEvents");
        changeEvent.initEvent("change", false, false);
        changeEvent.srcElement(this);
        container = Elements.create("div");
        readAttributes();
    }

    protected void initWidgetSystem() {
        if (!initialized) {
            initialized = true;
            Widget elementWidget = $(this).widget();
            if (elementWidget == null) {
                elementWidget = $(this).as(Widgets).panel().widget();
            }
            elementWidget.addAttachHandler(this);

            if (getAttribute("useshadow") != null) {
                HTMLShadow shadow = createShadowRoot();
                shadow.appendChild(style);
                shadow.appendChild(container);
            } else {
                appendChild(style);
                appendChild(container);
            }
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

    protected void readAttributes() {
        WCUtils.loadVaadinTheme(container, this, style, null);
    }

    @Override
    public void onAttachOrDetach(AttachEvent event) {
    }
}
