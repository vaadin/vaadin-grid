package com.vaadin.prototype.wc.gwt.client.components;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.prototype.wc.gwt.client.*;

public class CoreScrollHeaderPanelWidget extends BaseWidget  {
  
    protected String[] events() {
      return new String[]{"scroll","core-header-transform"};
    }
    
    public CoreScrollHeaderPanelWidget() {
      super(WC.create(CoreScrollHeaderPanel.class));
    }
    
    public CoreScrollHeaderPanelWidget(CoreScrollHeaderPanel element) {
      super(element);
    }
    
    protected CoreScrollHeaderPanel element() {
      return (CoreScrollHeaderPanel)super.getElement();
    }

    public void condenses(boolean val) {
        element().condenses(val);
    }
    public boolean condenses() {
        return element().condenses();
    }
    public void noDissolve(boolean val) {
        element().noDissolve(val);
    }
    public boolean noDissolve() {
        return element().noDissolve();
    }
    public void noReveal(boolean val) {
        element().noReveal(val);
    }
    public boolean noReveal() {
        return element().noReveal();
    }
    public void fixed(boolean val) {
        element().fixed(val);
    }
    public boolean fixed() {
        return element().fixed();
    }
    public void keepCondensedHeader(boolean val) {
        element().keepCondensedHeader(val);
    }
    public boolean keepCondensedHeader() {
        return element().keepCondensedHeader();
    }
    public void headerHeight(double val) {
        element().headerHeight(val);
    }
    public double headerHeight() {
        return element().headerHeight();
    }
    public void condensedHeaderHeight(double val) {
        element().condensedHeaderHeight(val);
    }
    public double condensedHeaderHeight() {
        return element().condensedHeaderHeight();
    }

}
