package com.vaadin.prototype.wc.gwt.client.components;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.prototype.wc.gwt.client.*;
import com.vaadin.prototype.wc.gwt.client.util.*;

public class CoreSelectionWidget extends BaseWidget  {
  
    protected String[] events() {
      return new String[]{};
    }
    
    public CoreSelectionWidget() {
      super(WC.create(CoreSelection.class));
    }
    
    public CoreSelectionWidget(CoreSelection element) {
      super(element);
    }
    
    protected CoreSelection element() {
      return (CoreSelection)super.getElement();
    }

    public void multi(boolean val) {
        element().multi(val);
    }
    public boolean multi() {
        return element().multi();
    }

}
