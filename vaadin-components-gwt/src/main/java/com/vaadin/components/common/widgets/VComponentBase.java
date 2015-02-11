package com.vaadin.components.common.widgets;

import static com.google.gwt.query.client.GQuery.$;
import static com.google.gwt.query.client.GQuery.Widgets;

import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.components.common.html.HTMLElement;
import com.vaadin.components.common.html.HTMLEvents;
import com.vaadin.components.common.html.HTMLShadow;
import com.vaadin.components.common.util.DOMUtils;
import com.vaadin.components.common.util.Elements;


// TODO: this should be common to all Vaadin component classes, but it seems that
// super.whatever does not work in jsinterop
public class VComponentBase extends HTMLElement.Prototype implements
        HTMLElement.LifeCycle.Created, HTMLElement.LifeCycle.Attached,
        HTMLElement.LifeCycle.Changed, Handler {

    protected Widget widget;
    private HTMLEvents changeEvent;
    private HTMLElement container;
    private HTMLElement style;
    protected boolean initialized = false;

    public VComponentBase() {
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
        DOMUtils.loadVaadinTheme(container, this, style, null);
    }

    @Override
    public void onAttachOrDetach(AttachEvent event) {
    }
}
