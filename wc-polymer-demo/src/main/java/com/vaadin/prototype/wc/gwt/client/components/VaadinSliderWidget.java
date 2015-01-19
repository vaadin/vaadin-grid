package com.vaadin.prototype.wc.gwt.client.components;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.prototype.wc.gwt.client.*;
import com.vaadin.prototype.wc.gwt.client.util.*;

public class VaadinSliderWidget extends BaseWidget  {

    protected String[] events() {
      return new String[]{"change"};
    }

    public VaadinSliderWidget() {
      super(WC.create(VaadinSlider.class));
    }

    public VaadinSliderWidget(VaadinSlider element) {
      super(element);
    }

    protected VaadinSlider element() {
      return (VaadinSlider)super.getElement();
    }

    public void value(double val) {
        element().value(val);
    }
    public double value() {
        return element().value();
    }
    public void min(double val) {
        element().min(val);
    }
    public double min() {
        return element().min();
    }
    public void max(double val) {
        element().max(val);
    }
    public double max() {
        return element().max();
    }
    public void theme(String val) {
        element().theme(val);
    }
    public String theme() {
        return element().theme();
    }

}
