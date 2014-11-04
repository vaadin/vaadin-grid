package com.vaadin.prototype.wc.gwt.client.components;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.prototype.wc.gwt.client.*;
import com.vaadin.prototype.wc.gwt.client.util.*;

public class CoreFieldWidget extends BaseWidget  {
  
    protected String[] events() {
      return new String[]{};
    }
    
    public CoreFieldWidget() {
      super(WC.create(CoreField.class));
    }
    
    public CoreFieldWidget(CoreField element) {
      super(element);
    }
    
    protected CoreField element() {
      return (CoreField)super.getElement();
    }


}
