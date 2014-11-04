package com.vaadin.prototype.wc.gwt.client.components;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.prototype.wc.gwt.client.*;
import com.vaadin.prototype.wc.gwt.client.util.*;

public class PaperElementsWidget extends BaseWidget  {
  
    protected String[] events() {
      return new String[]{};
    }
    
    public PaperElementsWidget() {
      super(WC.create(PaperElements.class));
    }
    
    public PaperElementsWidget(PaperElements element) {
      super(element);
    }
    
    protected PaperElements element() {
      return (PaperElements)super.getElement();
    }


}
