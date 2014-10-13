package com.vaadin.prototype.wc.gwt.client.components;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.prototype.wc.gwt.client.*;

public class CoreItemWidget extends BaseWidget  {
  
    protected String[] events() {
      return new String[]{};
    }
    
    public CoreItemWidget() {
      super(WC.create(CoreItem.class));
    }
    
    public CoreItemWidget(CoreItem element) {
      super(element);
    }
    
    protected CoreItem element() {
      return (CoreItem)super.getElement();
    }

    public void src(String val) {
        element().src(val);
    }
    public String src() {
        return element().src();
    }
    public void icon(String val) {
        element().icon(val);
    }
    public String icon() {
        return element().icon();
    }
    public void label(String val) {
        element().label(val);
    }
    public String label() {
        return element().label();
    }

}
