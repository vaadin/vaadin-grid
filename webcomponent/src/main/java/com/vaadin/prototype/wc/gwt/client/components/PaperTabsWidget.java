package com.vaadin.prototype.wc.gwt.client.components;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.prototype.wc.gwt.client.*;

public class PaperTabsWidget extends CoreSelectorWidget  {
  
    protected String[] events() {
      return new String[]{};
    }
    
    public PaperTabsWidget() {
      super(WC.create(PaperTabs.class));
    }
    
    public PaperTabsWidget(PaperTabs element) {
      super(element);
    }
    
    protected PaperTabs element() {
      return (PaperTabs)super.getElement();
    }

    public void noink(boolean val) {
        element().noink(val);
    }
    public boolean noink() {
        return element().noink();
    }
    public void nobar(boolean val) {
        element().nobar(val);
    }
    public boolean nobar() {
        return element().nobar();
    }

}
