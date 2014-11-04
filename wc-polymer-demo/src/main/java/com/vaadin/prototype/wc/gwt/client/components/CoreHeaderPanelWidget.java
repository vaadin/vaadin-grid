package com.vaadin.prototype.wc.gwt.client.components;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.prototype.wc.gwt.client.*;
import com.vaadin.prototype.wc.gwt.client.util.*;

public class CoreHeaderPanelWidget extends BaseWidget  {
  
    protected String[] events() {
      return new String[]{};
    }
    
    public CoreHeaderPanelWidget() {
      super(WC.create(CoreHeaderPanel.class));
    }
    
    public CoreHeaderPanelWidget(CoreHeaderPanel element) {
      super(element);
    }
    
    protected CoreHeaderPanel element() {
      return (CoreHeaderPanel)super.getElement();
    }

    public void mode(String val) {
        element().mode(val);
    }
    public String mode() {
        return element().mode();
    }
    public void tallClass(String val) {
        element().tallClass(val);
    }
    public String tallClass() {
        return element().tallClass();
    }
    public void shadow(boolean val) {
        element().shadow(val);
    }
    public boolean shadow() {
        return element().shadow();
    }

}
