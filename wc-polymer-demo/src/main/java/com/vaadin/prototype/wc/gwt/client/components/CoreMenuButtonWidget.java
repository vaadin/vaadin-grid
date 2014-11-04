package com.vaadin.prototype.wc.gwt.client.components;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.prototype.wc.gwt.client.*;
import com.vaadin.prototype.wc.gwt.client.util.*;

public class CoreMenuButtonWidget extends BaseWidget  {
  
    protected String[] events() {
      return new String[]{};
    }
    
    public CoreMenuButtonWidget() {
      super(WC.create(CoreMenuButton.class));
    }
    
    public CoreMenuButtonWidget(CoreMenuButton element) {
      super(element);
    }
    
    protected CoreMenuButton element() {
      return (CoreMenuButton)super.getElement();
    }

    public void icon(String val) {
        element().icon(val);
    }
    public String icon() {
        return element().icon();
    }
    public void selected(double val) {
        element().selected(val);
    }
    public double selected() {
        return element().selected();
    }
    public void opened(boolean val) {
        element().opened(val);
    }
    public boolean opened() {
        return element().opened();
    }
    public void inlineMenu(boolean val) {
        element().inlineMenu(val);
    }
    public boolean inlineMenu() {
        return element().inlineMenu();
    }
    public void halign(String val) {
        element().halign(val);
    }
    public String halign() {
        return element().halign();
    }
    public void valign(String val) {
        element().valign(val);
    }
    public String valign() {
        return element().valign();
    }

}
