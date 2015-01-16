package com.vaadin.prototype.wc.gwt.client.components;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.prototype.wc.gwt.client.*;
import com.vaadin.prototype.wc.gwt.client.util.*;

public class PaperIconButtonWidget extends PaperButtonWidget  {

    protected String[] events() {
      return new String[]{};
    }

    public PaperIconButtonWidget() {
      super(WC.create(PaperIconButton.class));
    }

    public PaperIconButtonWidget(PaperIconButton element) {
      super(element);
    }

    protected PaperIconButton element() {
      return (PaperIconButton)super.getElement();
    }

    public void fill(boolean val) {
        element().fill(val);
    }
    public boolean fill() {
        return element().fill();
    }

}
