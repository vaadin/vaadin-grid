package com.vaadin.prototype.wc.gwt.client.components;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.prototype.wc.gwt.client.*;
import com.vaadin.prototype.wc.gwt.client.util.*;

public class PaperShadowWidget extends BaseWidget  {

    protected String[] events() {
      return new String[]{};
    }

    public PaperShadowWidget() {
      super(WC.create(PaperShadow.class));
    }

    public PaperShadowWidget(PaperShadow element) {
      super(element);
    }

    protected PaperShadow element() {
      return (PaperShadow)super.getElement();
    }

    public void target(Element val) {
        element().target(val);
    }
    public Element target() {
        return element().target();
    }
    public void z(double val) {
        element().z(val);
    }
    public double z() {
        return element().z();
    }
    public void animated(boolean val) {
        element().animated(val);
    }
    public boolean animated() {
        return element().animated();
    }
    public void hasPosition(boolean val) {
        element().hasPosition(val);
    }
    public boolean hasPosition() {
        return element().hasPosition();
    }

}
