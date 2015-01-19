package com.vaadin.prototype.wc.gwt.client.components;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.prototype.wc.gwt.client.*;
import com.vaadin.prototype.wc.gwt.client.util.*;

public class PaperToastWidget extends BaseWidget  {

    protected String[] events() {
      return new String[]{};
    }

    public PaperToastWidget() {
      super(WC.create(PaperToast.class));
    }

    public PaperToastWidget(PaperToast element) {
      super(element);
    }

    protected PaperToast element() {
      return (PaperToast)super.getElement();
    }

    public void text(String val) {
        element().text(val);
    }
    public String text() {
        return element().text();
    }
    public void duration(double val) {
        element().duration(val);
    }
    public double duration() {
        return element().duration();
    }
    public void opened(boolean val) {
        element().opened(val);
    }
    public boolean opened() {
        return element().opened();
    }
    public void responsiveWidth(String val) {
        element().responsiveWidth(val);
    }
    public String responsiveWidth() {
        return element().responsiveWidth();
    }
    public void swipeDisabled(boolean val) {
        element().swipeDisabled(val);
    }
    public boolean swipeDisabled() {
        return element().swipeDisabled();
    }
    public void toggle() {
         element().toggle();
    }
    public void show() {
         element().show();
    }
    public void dismiss() {
         element().dismiss();
    }

}
