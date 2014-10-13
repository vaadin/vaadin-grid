package com.vaadin.prototype.wc.gwt.client.components;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.prototype.wc.gwt.client.*;

public class CoreSubmenuWidget extends CoreItemWidget  {
  
    protected String[] events() {
      return new String[]{};
    }
    
    public CoreSubmenuWidget() {
      super(WC.create(CoreSubmenu.class));
    }
    
    public CoreSubmenuWidget(CoreSubmenu element) {
      super(element);
    }
    
    protected CoreSubmenu element() {
      return (CoreSubmenu)super.getElement();
    }


}
