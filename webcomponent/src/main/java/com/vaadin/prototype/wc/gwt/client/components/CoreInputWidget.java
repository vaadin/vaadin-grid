package com.vaadin.prototype.wc.gwt.client.components;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.prototype.wc.gwt.client.*;

public class CoreInputWidget extends BaseWidget  {
  
    protected String[] events() {
      return new String[]{"input","change","input-invalid"};
    }
    
    public CoreInputWidget() {
      super(WC.create(CoreInput.class));
    }
    
    public CoreInputWidget(CoreInput element) {
      super(element);
    }
    
    protected CoreInput element() {
      return (CoreInput)super.getElement();
    }

    public void placeholder(String val) {
        element().placeholder(val);
    }
    public String placeholder() {
        return element().placeholder();
    }
    public void disabled(boolean val) {
        element().disabled(val);
    }
    public boolean disabled() {
        return element().disabled();
    }
    public void type(String val) {
        element().type(val);
    }
    public String type() {
        return element().type();
    }
    public void multiline(boolean val) {
        element().multiline(val);
    }
    public boolean multiline() {
        return element().multiline();
    }
    public void rows(double val) {
        element().rows(val);
    }
    public double rows() {
        return element().rows();
    }
    public void inputValue(String val) {
        element().inputValue(val);
    }
    public String inputValue() {
        return element().inputValue();
    }
    public void value(String val) {
        element().value(val);
    }
    public String value() {
        return element().value();
    }
    public void validate(String val) {
        element().validate(val);
    }
    public String validate() {
        return element().validate();
    }
    public void invalid(boolean val) {
        element().invalid(val);
    }
    public boolean invalid() {
        return element().invalid();
    }

}
