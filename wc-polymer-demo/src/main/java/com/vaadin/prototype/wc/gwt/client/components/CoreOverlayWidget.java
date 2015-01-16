package com.vaadin.prototype.wc.gwt.client.components;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.prototype.wc.gwt.client.*;
import com.vaadin.prototype.wc.gwt.client.util.*;

public class CoreOverlayWidget extends BaseWidget  {

    protected String[] events() {
      return new String[]{};
    }

    public CoreOverlayWidget() {
      super(WC.create(CoreOverlay.class));
    }

    public CoreOverlayWidget(CoreOverlay element) {
      super(element);
    }

    protected CoreOverlay element() {
      return (CoreOverlay)super.getElement();
    }

    public void target(JavaScriptObject val) {
        element().target(val);
    }
    public JavaScriptObject target() {
        return element().target();
    }
    public void sizingTarget(JavaScriptObject val) {
        element().sizingTarget(val);
    }
    public JavaScriptObject sizingTarget() {
        return element().sizingTarget();
    }
    public void opened(boolean val) {
        element().opened(val);
    }
    public boolean opened() {
        return element().opened();
    }
    public void backdrop(boolean val) {
        element().backdrop(val);
    }
    public boolean backdrop() {
        return element().backdrop();
    }
    public void layered(boolean val) {
        element().layered(val);
    }
    public boolean layered() {
        return element().layered();
    }
    public void autoCloseDisabled(boolean val) {
        element().autoCloseDisabled(val);
    }
    public boolean autoCloseDisabled() {
        return element().autoCloseDisabled();
    }
    public void closeAttribute(String val) {
        element().closeAttribute(val);
    }
    public String closeAttribute() {
        return element().closeAttribute();
    }
    public void closeSelector(String val) {
        element().closeSelector(val);
    }
    public String closeSelector() {
        return element().closeSelector();
    }
    public void margin(double val) {
        element().margin(val);
    }
    public double margin() {
        return element().margin();
    }
    public void transition(String val) {
        element().transition(val);
    }
    public String transition() {
        return element().transition();
    }

}
