package com.vaadin.prototype.wc.gwt.client.components;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.prototype.wc.gwt.client.*;

public class CoreTransitionWidget extends BaseWidget  {
  
    protected String[] events() {
      return new String[]{};
    }
    
    public CoreTransitionWidget() {
      super(WC.create(CoreTransition.class));
    }
    
    public CoreTransitionWidget(CoreTransition element) {
      super(element);
    }
    
    protected CoreTransition element() {
      return (CoreTransition)super.getElement();
    }


}
