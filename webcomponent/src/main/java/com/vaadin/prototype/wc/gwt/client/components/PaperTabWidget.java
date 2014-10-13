package com.vaadin.prototype.wc.gwt.client.components;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.prototype.wc.gwt.client.*;

public class PaperTabWidget extends BaseWidget  {
  
    protected String[] events() {
      return new String[]{};
    }
    
    public PaperTabWidget() {
      super(WC.create(PaperTab.class));
    }
    
    public PaperTabWidget(PaperTab element) {
      super(element);
    }
    
    protected PaperTab element() {
      return (PaperTab)super.getElement();
    }

    public void noink(boolean val) {
        element().noink(val);
    }
    public boolean noink() {
        return element().noink();
    }

}
