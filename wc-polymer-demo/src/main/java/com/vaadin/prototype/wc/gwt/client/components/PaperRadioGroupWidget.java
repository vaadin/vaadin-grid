package com.vaadin.prototype.wc.gwt.client.components;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.prototype.wc.gwt.client.*;
import com.vaadin.prototype.wc.gwt.client.util.*;

public class PaperRadioGroupWidget extends CoreSelectorWidget  {
  
    protected String[] events() {
      return new String[]{};
    }
    
    public PaperRadioGroupWidget() {
      super(WC.create(PaperRadioGroup.class));
    }
    
    public PaperRadioGroupWidget(PaperRadioGroup element) {
      super(element);
    }
    
    protected PaperRadioGroup element() {
      return (PaperRadioGroup)super.getElement();
    }


}
