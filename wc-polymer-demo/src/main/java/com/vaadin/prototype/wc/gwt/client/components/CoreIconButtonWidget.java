package com.vaadin.prototype.wc.gwt.client.components;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.prototype.wc.gwt.client.*;
import com.vaadin.prototype.wc.gwt.client.util.*;

public class CoreIconButtonWidget extends BaseWidget  {

    protected String[] events() {
      return new String[]{};
    }

    public CoreIconButtonWidget() {
      super(WC.create(CoreIconButton.class));
    }

    public CoreIconButtonWidget(CoreIconButton element) {
      super(element);
    }

    protected CoreIconButton element() {
      return (CoreIconButton)super.getElement();
    }

    public void src(String val) {
        element().src(val);
    }
    public String src() {
        return element().src();
    }
    public void active(boolean val) {
        element().active(val);
    }
    public boolean active() {
        return element().active();
    }
    public void icon(String val) {
        element().icon(val);
    }
    public String icon() {
        return element().icon();
    }

}
