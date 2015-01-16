package com.vaadin.prototype.wc.gwt.client.components;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.prototype.wc.gwt.client.*;
import com.vaadin.prototype.wc.gwt.client.util.*;

public class PaperRadioButtonWidget extends BaseWidget  {

    protected String[] events() {
      return new String[]{"change"};
    }

    public PaperRadioButtonWidget() {
      super(WC.create(PaperRadioButton.class));
    }

    public PaperRadioButtonWidget(PaperRadioButton element) {
      super(element);
    }

    protected PaperRadioButton element() {
      return (PaperRadioButton)super.getElement();
    }

    public void checked(boolean val) {
        element().checked(val);
    }
    public boolean checked() {
        return element().checked();
    }
    public void label(String val) {
        element().label(val);
    }
    public String label() {
        return element().label();
    }
    public void toggles(boolean val) {
        element().toggles(val);
    }
    public boolean toggles() {
        return element().toggles();
    }
    public void disabled(boolean val) {
        element().disabled(val);
    }
    public boolean disabled() {
        return element().disabled();
    }

}
