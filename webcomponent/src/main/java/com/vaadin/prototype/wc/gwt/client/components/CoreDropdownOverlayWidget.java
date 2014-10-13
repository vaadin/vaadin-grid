package com.vaadin.prototype.wc.gwt.client.components;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.prototype.wc.gwt.client.*;

public class CoreDropdownOverlayWidget extends CoreOverlayWidget  {
  
    protected String[] events() {
      return new String[]{};
    }
    
    public CoreDropdownOverlayWidget() {
      super(WC.create(CoreDropdownOverlay.class));
    }
    
    public CoreDropdownOverlayWidget(CoreDropdownOverlay element) {
      super(element);
    }
    
    protected CoreDropdownOverlay element() {
      return (CoreDropdownOverlay)super.getElement();
    }

    public void relatedTarget(JavaScriptObject val) {
        element().relatedTarget(val);
    }
    public JavaScriptObject relatedTarget() {
        return element().relatedTarget();
    }
    public void halign(JavaScriptObject val) {
        element().halign(val);
    }
    public JavaScriptObject halign() {
        return element().halign();
    }
    public void valign(JavaScriptObject val) {
        element().valign(val);
    }
    public JavaScriptObject valign() {
        return element().valign();
    }
    public void margin(double val) {
        element().margin(val);
    }
    public double margin() {
        return element().margin();
    }

}
