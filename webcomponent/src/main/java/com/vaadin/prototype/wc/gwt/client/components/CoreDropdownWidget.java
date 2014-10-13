package com.vaadin.prototype.wc.gwt.client.components;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.prototype.wc.gwt.client.*;

public class CoreDropdownWidget extends BaseWidget  {
  
    protected String[] events() {
      return new String[]{};
    }
    
    public CoreDropdownWidget() {
      super(WC.create(CoreDropdown.class));
    }
    
    public CoreDropdownWidget(CoreDropdown element) {
      super(element);
    }
    
    protected CoreDropdown element() {
      return (CoreDropdown)super.getElement();
    }

    public void relatedTarget(JavaScriptObject val) {
        element().relatedTarget(val);
    }
    public JavaScriptObject relatedTarget() {
        return element().relatedTarget();
    }
    public void opened(boolean val) {
        element().opened(val);
    }
    public boolean opened() {
        return element().opened();
    }
    public void halign(JavaScriptObject val) {
        element().halign(val);
    }
    public JavaScriptObject halign() {
        return element().halign();
    }
    public void valign(JavaScriptObject val) {
        element().valign(val);
    }
    public JavaScriptObject valign() {
        return element().valign();
    }
    public void margin(double val) {
        element().margin(val);
    }
    public double margin() {
        return element().margin();
    }
    public void transition(String val) {
        element().transition(val);
    }
    public String transition() {
        return element().transition();
    }

}
