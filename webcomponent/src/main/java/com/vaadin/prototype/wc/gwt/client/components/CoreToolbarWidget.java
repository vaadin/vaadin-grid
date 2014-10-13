package com.vaadin.prototype.wc.gwt.client.components;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.prototype.wc.gwt.client.*;

public class CoreToolbarWidget extends BaseWidget  {
  
    protected String[] events() {
      return new String[]{};
    }
    
    public CoreToolbarWidget() {
      super(WC.create(CoreToolbar.class));
    }
    
    public CoreToolbarWidget(CoreToolbar element) {
      super(element);
    }
    
    protected CoreToolbar element() {
      return (CoreToolbar)super.getElement();
    }


}
