package com.vaadin.prototype.wc.gwt.client.components;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.prototype.wc.gwt.client.*;
import com.vaadin.prototype.wc.gwt.client.util.*;

public class CoreIconsetWidget extends CoreMetaWidget  {

    protected String[] events() {
      return new String[]{};
    }

    public CoreIconsetWidget() {
      super(WC.create(CoreIconset.class));
    }

    public CoreIconsetWidget(CoreIconset element) {
      super(element);
    }

    protected CoreIconset element() {
      return (CoreIconset)super.getElement();
    }

    public void src(String val) {
        element().src(val);
    }
    public String src() {
        return element().src();
    }
    public void width(double val) {
        element().width(val);
    }
    public double width() {
        return element().width();
    }
    public void icons(String val) {
        element().icons(val);
    }
    public String icons() {
        return element().icons();
    }
    public void iconSize(double val) {
        element().iconSize(val);
    }
    public double iconSize() {
        return element().iconSize();
    }
    public void offsetX(double val) {
        element().offsetX(val);
    }
    public double offsetX() {
        return element().offsetX();
    }
    public void offsetY(double val) {
        element().offsetY(val);
    }
    public double offsetY() {
        return element().offsetY();
    }
    public JavaScriptObject getOffset(String arg0, String arg1) {
        return element().getOffset(arg0, arg1);
    }
    public void applyIcon(Element arg0, String arg1, String arg2, double arg3) {
         element().applyIcon(arg0, arg1, arg2, arg3);
    }

}
