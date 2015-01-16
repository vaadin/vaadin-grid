package com.vaadin.prototype.wc.gwt.client.components;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.prototype.wc.gwt.client.*;
import com.vaadin.prototype.wc.gwt.client.util.*;

public class PaperToggleButtonWidget extends BaseWidget  {

    protected String[] events() {
      return new String[]{"change"};
    }

    public PaperToggleButtonWidget() {
      super(WC.create(PaperToggleButton.class));
    }

    public PaperToggleButtonWidget(PaperToggleButton element) {
      super(element);
    }

    protected PaperToggleButton element() {
      return (PaperToggleButton)super.getElement();
    }

    public void checked(boolean val) {
        element().checked(val);
    }
    public boolean checked() {
        return element().checked();
    }

}
