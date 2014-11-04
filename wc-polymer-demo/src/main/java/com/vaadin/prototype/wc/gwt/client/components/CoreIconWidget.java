package com.vaadin.prototype.wc.gwt.client.components;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.prototype.wc.gwt.client.*;
import com.vaadin.prototype.wc.gwt.client.util.*;

public class CoreIconWidget extends BaseWidget  {
  
    protected String[] events() {
      return new String[]{};
    }
    
    public CoreIconWidget() {
      super(WC.create(CoreIcon.class));
    }
    
    public CoreIconWidget(CoreIcon element) {
      super(element);
    }
    
    protected CoreIcon element() {
      return (CoreIcon)super.getElement();
    }

    public void src(String val) {
        element().src(val);
    }
    public String src() {
        return element().src();
    }
    public void size(String val) {
        element().size(val);
    }
    public String size() {
        return element().size();
    }
    public void icon(String val) {
        element().icon(val);
    }
    public String icon() {
        return element().icon();
    }

}
