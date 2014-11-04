package com.vaadin.prototype.wc.gwt.client.components;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.prototype.wc.gwt.client.*;
import com.vaadin.prototype.wc.gwt.client.util.*;

public class CoreComponentPageWidget extends CoreMetaWidget  {
  
    protected String[] events() {
      return new String[]{"core-response","core-error","core-complete"};
    }
    
    public CoreComponentPageWidget() {
      super(WC.create(CoreComponentPage.class));
    }
    
    public CoreComponentPageWidget(CoreComponentPage element) {
      super(element);
    }
    
    protected CoreComponentPage element() {
      return (CoreComponentPage)super.getElement();
    }

    public void vertical(boolean val) {
        element().vertical(val);
    }
    public boolean vertical() {
        return element().vertical();
    }
    public void justify(String val) {
        element().justify(val);
    }
    public String justify() {
        return element().justify();
    }
    public void align(String val) {
        element().align(val);
    }
    public String align() {
        return element().align();
    }
    public void reverse(boolean val) {
        element().reverse(val);
    }
    public boolean reverse() {
        return element().reverse();
    }
    public void type(String val) {
        element().type(val);
    }
    public String type() {
        return element().type();
    }
    public void list(JsArray val) {
        element().list(val);
    }
    public JsArray list() {
        return element().list();
    }
    public void src(String val) {
        element().src(val);
    }
    public String src() {
        return element().src();
    }
    public void width(String val) {
        element().width(val);
    }
    public String width() {
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
    public void size(String val) {
        element().size(val);
    }
    public String size() {
        return element().size();
    }
    public void icon(String val) {
        element().icon(val);
    }
    public String icon() {
        return element().icon();
    }
    public void active(boolean val) {
        element().active(val);
    }
    public boolean active() {
        return element().active();
    }
    public void mode(String val) {
        element().mode(val);
    }
    public String mode() {
        return element().mode();
    }
    public void tallClass(String val) {
        element().tallClass(val);
    }
    public String tallClass() {
        return element().tallClass();
    }
    public void shadow(boolean val) {
        element().shadow(val);
    }
    public boolean shadow() {
        return element().shadow();
    }
    public void url(String val) {
        element().url(val);
    }
    public String url() {
        return element().url();
    }
    public void handleAs(String val) {
        element().handleAs(val);
    }
    public String handleAs() {
        return element().handleAs();
    }
    public void auto(boolean val) {
        element().auto(val);
    }
    public boolean auto() {
        return element().auto();
    }
    public void params(String val) {
        element().params(val);
    }
    public String params() {
        return element().params();
    }
    public void response(JavaScriptObject val) {
        element().response(val);
    }
    public JavaScriptObject response() {
        return element().response();
    }
    public void method(String val) {
        element().method(val);
    }
    public String method() {
        return element().method();
    }
    public void headers(JavaScriptObject val) {
        element().headers(val);
    }
    public JavaScriptObject headers() {
        return element().headers();
    }
    public void body(JavaScriptObject val) {
        element().body(val);
    }
    public JavaScriptObject body() {
        return element().body();
    }
    public void contentType(String val) {
        element().contentType(val);
    }
    public String contentType() {
        return element().contentType();
    }
    public void withCredentials(boolean val) {
        element().withCredentials(val);
    }
    public boolean withCredentials() {
        return element().withCredentials();
    }
    public void multi(boolean val) {
        element().multi(val);
    }
    public boolean multi() {
        return element().multi();
    }
    public void selected(JavaScriptObject val) {
        element().selected(val);
    }
    public JavaScriptObject selected() {
        return element().selected();
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
    public void label(String val) {
        element().label(val);
    }
    public String label() {
        return element().label();
    }
    public void sources(JsArray val) {
        element().sources(val);
    }
    public JsArray sources() {
        return element().sources();
    }

}
