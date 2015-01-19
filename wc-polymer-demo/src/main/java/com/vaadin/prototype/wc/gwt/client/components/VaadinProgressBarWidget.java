package com.vaadin.prototype.wc.gwt.client.components;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.prototype.wc.gwt.client.*;
import com.vaadin.prototype.wc.gwt.client.util.*;

public class VaadinProgressBarWidget extends BaseWidget  {

    protected String[] events() {
      return new String[]{"change"};
    }

    public VaadinProgressBarWidget() {
      super(WC.create(VaadinProgressBar.class));
    }

    public VaadinProgressBarWidget(VaadinProgressBar element) {
      super(element);
    }

    protected VaadinProgressBar element() {
      return (VaadinProgressBar)super.getElement();
    }

    public void value(double val) {
        element().value(val);
    }
    public double value() {
        return element().value();
    }
    public void theme(String val) {
        element().theme(val);
    }
    public String theme() {
        return element().theme();
    }

}
