package com.vaadin.prototype.wc.gwt.client.components;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.prototype.wc.gwt.client.*;
import com.vaadin.prototype.wc.gwt.client.util.*;

public class PaperFocusableWidget extends BaseWidget  {
  
    protected String[] events() {
      return new String[]{};
    }
    
    public PaperFocusableWidget() {
      super(WC.create(PaperFocusable.class));
    }
    
    public PaperFocusableWidget(PaperFocusable element) {
      super(element);
    }
    
    protected PaperFocusable element() {
      return (PaperFocusable)super.getElement();
    }

    public void active(boolean val) {
        element().active(val);
    }
    public boolean active() {
        return element().active();
    }
    public void focused(boolean val) {
        element().focused(val);
    }
    public boolean focused() {
        return element().focused();
    }
    public void pressed(boolean val) {
        element().pressed(val);
    }
    public boolean pressed() {
        return element().pressed();
    }
    public void disabled(boolean val) {
        element().disabled(val);
    }
    public boolean disabled() {
        return element().disabled();
    }
    public void isToggle(boolean val) {
        element().isToggle(val);
    }
    public boolean isToggle() {
        return element().isToggle();
    }

}
