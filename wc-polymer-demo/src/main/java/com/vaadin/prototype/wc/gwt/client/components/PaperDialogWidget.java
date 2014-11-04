package com.vaadin.prototype.wc.gwt.client.components;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.prototype.wc.gwt.client.*;
import com.vaadin.prototype.wc.gwt.client.util.*;

public class PaperDialogWidget extends BaseWidget  {
  
    protected String[] events() {
      return new String[]{};
    }
    
    public PaperDialogWidget() {
      super(WC.create(PaperDialog.class));
    }
    
    public PaperDialogWidget(PaperDialog element) {
      super(element);
    }
    
    protected PaperDialog element() {
      return (PaperDialog)super.getElement();
    }

    public void opened(boolean val) {
        element().opened(val);
    }
    public boolean opened() {
        return element().opened();
    }
    public void heading(String val) {
        element().heading(val);
    }
    public String heading() {
        return element().heading();
    }
    public void transition(String val) {
        element().transition(val);
    }
    public String transition() {
        return element().transition();
    }

}
