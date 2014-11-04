package com.vaadin.prototype.wc.gwt.client.components;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.prototype.wc.gwt.client.*;
import com.vaadin.prototype.wc.gwt.client.util.*;

public class CoreDrawerPanelWidget extends BaseWidget  {
  
    protected String[] events() {
      return new String[]{"core-responsive-change"};
    }
    
    public CoreDrawerPanelWidget() {
      super(WC.create(CoreDrawerPanel.class));
    }
    
    public CoreDrawerPanelWidget(CoreDrawerPanel element) {
      super(element);
    }
    
    protected CoreDrawerPanel element() {
      return (CoreDrawerPanel)super.getElement();
    }

    public void responsiveWidth(String val) {
        element().responsiveWidth(val);
    }
    public String responsiveWidth() {
        return element().responsiveWidth();
    }
    public void selected(String val) {
        element().selected(val);
    }
    public String selected() {
        return element().selected();
    }
    public void defaultSelected(String val) {
        element().defaultSelected(val);
    }
    public String defaultSelected() {
        return element().defaultSelected();
    }
    public void narrow(boolean val) {
        element().narrow(val);
    }
    public boolean narrow() {
        return element().narrow();
    }

}
