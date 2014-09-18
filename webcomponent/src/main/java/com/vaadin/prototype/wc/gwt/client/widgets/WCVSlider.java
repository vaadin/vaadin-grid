package com.vaadin.prototype.wc.gwt.client.widgets;

import static com.google.gwt.query.client.GQuery.$;
import static com.google.gwt.query.client.GQuery.Widgets;

import com.google.gwt.core.client.js.JsExport;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.dom.client.StyleInjector;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Panel;
import com.vaadin.client.ui.VSlider;
import com.vaadin.prototype.wc.gwt.client.WC;
import com.vaadin.prototype.wc.gwt.client.html.HTMLElement;
import com.vaadin.prototype.wc.gwt.client.html.HTMLEvents;

@JsExport
@JsType
public class WCVSlider extends HTMLElement.Prototype implements
        HTMLElement.LifeCycle.Created,
        HTMLElement.LifeCycle.Attached,
        HTMLElement.LifeCycle.Changed,
        ValueChangeHandler<Double> {

    public static final String TAG = "v-slider";

    private VSlider slider;
    private HTMLEvents changeEvent;

    public WCVSlider() {
        // FIXME: If there is no default constructor JsInterop does not export anything
    }

    @Override
    public void createdCallback() {
        StyleInjector.injectStylesheetAtEnd("@import url('/VAADIN/themes/valo/styles.css')");
        setAttribute("class", "valo");
        slider = new VSlider();
        slider.addValueChangeHandler(this);
        changeEvent = WC.document.createEvent("HTMLEvents");
        changeEvent.initEvent("change", false, false);

        readAttributes();
    }

    @Override
    public void attachedCallback() {
      // Use gQuery to promote element to widget
      Panel p = $(this).widget();
      // Don't promote to widget twice
      if (p == null) {
          p = $(this).as(Widgets).panel().widget();
          p.add(slider);
      }
      slider.buildBase();
    }

    @Override
    public void onValueChange(ValueChangeEvent<Double> ev) {
        String val = ev.getValue().toString();
        if (!val.equals(getAttribute("value"))) {
            setAttribute("value", val);
            dispatchEvent(changeEvent);
        }
    }

    @Override
    public void attributeChangedCallback() {
        readAttributes();
    }

    private void readAttributes() {
        slider.setMinValue(getAttrValue("min", 0));
        slider.setMaxValue(getAttrValue("max", 100));
        slider.setValue(getAttrValue("value", 0));
    }

    // TODO: Make this part of the API of a utils class.
    private double getAttrValue(String attr, double def) {
        String val = getAttribute(attr);
        return val == null || val.isEmpty() ? def : Double.valueOf(val);
    }
}
