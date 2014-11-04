package com.vaadin.prototype.wc.gwt.client.components;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.prototype.wc.gwt.client.*;
import com.vaadin.prototype.wc.gwt.client.util.*;

public class PaperFabWidget extends PaperIconButtonWidget  {
  
    protected String[] events() {
      return new String[]{};
    }
    
    public PaperFabWidget() {
      super(WC.create(PaperFab.class));
    }
    
    public PaperFabWidget(PaperFab element) {
      super(element);
    }
    
    protected PaperFab element() {
      return (PaperFab)super.getElement();
    }

    public void raisedButton(boolean val) {
        element().raisedButton(val);
    }
    public boolean raisedButton() {
        return element().raisedButton();
    }

}
