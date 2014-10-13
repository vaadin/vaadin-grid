package com.vaadin.prototype.wc.gwt.client.components;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.prototype.wc.gwt.client.*;

public class PaperItemWidget extends BaseWidget  {
  
    protected String[] events() {
      return new String[]{};
    }
    
    public PaperItemWidget() {
      super(WC.create(PaperItem.class));
    }
    
    public PaperItemWidget(PaperItem element) {
      super(element);
    }
    
    protected PaperItem element() {
      return (PaperItem)super.getElement();
    }

    public void label(String val) {
        element().label(val);
    }
    public String label() {
        return element().label();
    }
    public void iconSrc(String val) {
        element().iconSrc(val);
    }
    public String iconSrc() {
        return element().iconSrc();
    }
    public void icon(String val) {
        element().icon(val);
    }
    public String icon() {
        return element().icon();
    }

}
