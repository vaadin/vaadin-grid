package com.vaadin.prototype.wc.gwt.client.widgets;

import static com.google.gwt.query.client.GQuery.$;
import static com.google.gwt.query.client.GQuery.Widgets;
import static com.vaadin.prototype.wc.gwt.client.widgets.WCUtils.getAttrDoubleValue;
import static com.vaadin.prototype.wc.gwt.client.widgets.WCUtils.getAttrValue;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.js.JsExport;
import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.ui.VSlider;
import com.vaadin.prototype.wc.gwt.client.html.HTMLElement;
import com.vaadin.prototype.wc.gwt.client.html.HTMLEvents;
import com.vaadin.prototype.wc.gwt.client.html.HTMLShadow;
import com.vaadin.prototype.wc.gwt.client.util.Elements;
@JsExport
@JsType
public class WCVSlider extends HTMLElement.Prototype implements
        HTMLElement.LifeCycle.Created,
        HTMLElement.LifeCycle.Attached,
        HTMLElement.LifeCycle.Changed,
        ValueChangeHandler<Double>, Handler {

    public static final String TAG = "v-slider";

    private VSlider slider;
    private HTMLEvents changeEvent;
    private HTMLElement container;
    private HTMLElement style;
    private boolean initialized = false;

    public WCVSlider() {
        // FIXME: If there is no default constructor JsInterop does not export anything
    }

    @Override
    public void createdCallback() {
        style = Elements.create("style");

        slider = new VSlider();
        slider.addValueChangeHandler(this);

        changeEvent = Elements.document.createEvent("HTMLEvents");
        changeEvent.initEvent("change", false, false);
        changeEvent.srcElement(this);

        container = Elements.create("div");
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
            shadowPanel.add(slider);
        }
    }

    @Override
    public void attachedCallback() {
        initWidgetSystem();
        slider.buildBase();
    }

    @Override
    public void onValueChange(ValueChangeEvent<Double> ev) {
        String val = ev.getValue().toString();
        if (!val.equals(getAttribute("value"))) {
            setAttribute("value", val);
            dispatchEvent(changeEvent);
            setValue(Double.valueOf(val));
        }
    }

    @Override
    public void attributeChangedCallback() {
        readAttributes();
    }

    private void readAttributes() {
        slider.setMinValue(getAttrDoubleValue(this, "min", 0));
        slider.setMaxValue(getAttrDoubleValue(this, "max", 100));
        slider.setValue(getAttrDoubleValue(this, "value", 0));
        WCUtils.loadVaadinTheme(container, this, style, "valo");
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

    @JsProperty public void setValue(double value) {
        slider.setValue(value);
    }

    @JsProperty public double getValue() {
        return slider.getValue();
    }
}
