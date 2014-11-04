package com.vaadin.prototype.wc.gwt.client.components;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.prototype.wc.gwt.client.*;
import com.vaadin.prototype.wc.gwt.client.util.*;

public class CoreSelectorWidget extends BaseWidget  {
  
    protected String[] events() {
      return new String[]{};
    }
    
    public CoreSelectorWidget() {
      super(WC.create(CoreSelector.class));
    }
    
    public CoreSelectorWidget(CoreSelector element) {
      super(element);
    }
    
    protected CoreSelector element() {
      return (CoreSelector)super.getElement();
    }

    public void selected(JavaScriptObject val) {
        element().selected(val);
    }
    public JavaScriptObject selected() {
        return element().selected();
    }
    public void multi(boolean val) {
        element().multi(val);
    }
    public boolean multi() {
        return element().multi();
    }
    public void valueattr(String val) {
        element().valueattr(val);
    }
    public String valueattr() {
        return element().valueattr();
    }
    public void selectedClass(String val) {
        element().selectedClass(val);
    }
    public String selectedClass() {
        return element().selectedClass();
    }
    public void selectedProperty(String val) {
        element().selectedProperty(val);
    }
    public String selectedProperty() {
        return element().selectedProperty();
    }
    public void selectedAttribute(String val) {
        element().selectedAttribute(val);
    }
    public String selectedAttribute() {
        return element().selectedAttribute();
    }
    public void selectedItem(JavaScriptObject val) {
        element().selectedItem(val);
    }
    public JavaScriptObject selectedItem() {
        return element().selectedItem();
    }
    public void selectedModel(JavaScriptObject val) {
        element().selectedModel(val);
    }
    public JavaScriptObject selectedModel() {
        return element().selectedModel();
    }
    public void selectedIndex(double val) {
        element().selectedIndex(val);
    }
    public double selectedIndex() {
        return element().selectedIndex();
    }
    public void target(JavaScriptObject val) {
        element().target(val);
    }
    public JavaScriptObject target() {
        return element().target();
    }
    public void itemSelector(String val) {
        element().itemSelector(val);
    }
    public String itemSelector() {
        return element().itemSelector();
    }
    public void activateEvent(String val) {
        element().activateEvent(val);
    }
    public String activateEvent() {
        return element().activateEvent();
    }
    public void notap(boolean val) {
        element().notap(val);
    }
    public boolean notap() {
        return element().notap();
    }

}
