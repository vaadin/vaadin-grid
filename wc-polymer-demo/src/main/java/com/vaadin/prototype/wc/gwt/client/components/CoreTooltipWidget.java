package com.vaadin.prototype.wc.gwt.client.components;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.prototype.wc.gwt.client.*;
import com.vaadin.prototype.wc.gwt.client.util.*;

public class CoreTooltipWidget extends BaseWidget  {
  
    protected String[] events() {
      return new String[]{};
    }
    
    public CoreTooltipWidget() {
      super(WC.create(CoreTooltip.class));
    }
    
    public CoreTooltipWidget(CoreTooltip element) {
      super(element);
    }
    
    protected CoreTooltip element() {
      return (CoreTooltip)super.getElement();
    }

    public void label(String val) {
        element().label(val);
    }
    public String label() {
        return element().label();
    }
    public void show(boolean val) {
        element().show(val);
    }
    public boolean show() {
        return element().show();
    }
    public void position(String val) {
        element().position(val);
    }
    public String position() {
        return element().position();
    }
    public void noarrow(boolean val) {
        element().noarrow(val);
    }
    public boolean noarrow() {
        return element().noarrow();
    }
    public void tipAttribute(String val) {
        element().tipAttribute(val);
    }
    public String tipAttribute() {
        return element().tipAttribute();
    }

}
