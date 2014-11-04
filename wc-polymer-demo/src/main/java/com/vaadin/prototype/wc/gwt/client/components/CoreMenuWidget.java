package com.vaadin.prototype.wc.gwt.client.components;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.prototype.wc.gwt.client.*;
import com.vaadin.prototype.wc.gwt.client.util.*;

public class CoreMenuWidget extends CoreSelectorWidget  {
  
    protected String[] events() {
      return new String[]{};
    }
    
    public CoreMenuWidget() {
      super(WC.create(CoreMenu.class));
    }
    
    public CoreMenuWidget(CoreMenu element) {
      super(element);
    }
    
    protected CoreMenu element() {
      return (CoreMenu)super.getElement();
    }


}
