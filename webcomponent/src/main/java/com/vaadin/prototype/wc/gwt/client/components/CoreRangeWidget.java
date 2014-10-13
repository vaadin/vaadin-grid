package com.vaadin.prototype.wc.gwt.client.components;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.prototype.wc.gwt.client.*;

public class CoreRangeWidget extends BaseWidget  {
  
    protected String[] events() {
      return new String[]{};
    }
    
    public CoreRangeWidget() {
      super(WC.create(CoreRange.class));
    }
    
    public CoreRangeWidget(CoreRange element) {
      super(element);
    }
    
    protected CoreRange element() {
      return (CoreRange)super.getElement();
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
    public void step(double val) {
        element().step(val);
    }
    public double step() {
        return element().step();
    }
    public void ratio(double val) {
        element().ratio(val);
    }
    public double ratio() {
        return element().ratio();
    }

}
