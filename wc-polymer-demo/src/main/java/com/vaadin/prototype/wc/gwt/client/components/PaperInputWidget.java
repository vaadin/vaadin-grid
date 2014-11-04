package com.vaadin.prototype.wc.gwt.client.components;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.prototype.wc.gwt.client.*;
import com.vaadin.prototype.wc.gwt.client.util.*;

public class PaperInputWidget extends CoreInputWidget  {
  
    protected String[] events() {
      return new String[]{};
    }
    
    public PaperInputWidget() {
      super(WC.create(PaperInput.class));
    }
    
    public PaperInputWidget(PaperInput element) {
      super(element);
    }
    
    protected PaperInput element() {
      return (PaperInput)super.getElement();
    }

    public void label(String val) {
        element().label(val);
    }
    public String label() {
        return element().label();
    }
    public void floatingLabel(boolean val) {
        element().floatingLabel(val);
    }
    public boolean floatingLabel() {
        return element().floatingLabel();
    }
    public void maxRows(double val) {
        element().maxRows(val);
    }
    public double maxRows() {
        return element().maxRows();
    }

}
