package com.vaadin.prototype.wc.gwt.client.components;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.prototype.wc.gwt.client.*;

public class CoreMetaWidget extends BaseWidget  {
  
    protected String[] events() {
      return new String[]{};
    }
    
    public CoreMetaWidget() {
      super(WC.create(CoreMeta.class));
    }
    
    public CoreMetaWidget(CoreMeta element) {
      super(element);
    }
    
    protected CoreMeta element() {
      return (CoreMeta)super.getElement();
    }

    public void type(String val) {
        element().type(val);
    }
    public String type() {
        return element().type();
    }
    public void list(JsArray val) {
        element().list(val);
    }
    public JsArray list() {
        return element().list();
    }

}
