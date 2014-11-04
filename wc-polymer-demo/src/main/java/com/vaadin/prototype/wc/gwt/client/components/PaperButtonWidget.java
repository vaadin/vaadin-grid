package com.vaadin.prototype.wc.gwt.client.components;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.prototype.wc.gwt.client.*;
import com.vaadin.prototype.wc.gwt.client.util.*;

public class PaperButtonWidget extends PaperFocusableWidget  {
  
    protected String[] events() {
      return new String[]{"click"};
    }
    
    public PaperButtonWidget() {
      super(WC.create(PaperButton.class));
    }
    
    public PaperButtonWidget(PaperButton element) {
      super(element);
    }
    
    protected PaperButton element() {
      return (PaperButton)super.getElement();
    }

    public void label(String val) {
        element().label(val);
    }
    public String label() {
        return element().label();
    }
    public void raisedButton(boolean val) {
        element().raisedButton(val);
    }
    public boolean raisedButton() {
        return element().raisedButton();
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
