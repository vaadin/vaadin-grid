package com.vaadin.prototype.wc.gwt.client.components;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.prototype.wc.gwt.client.*;
import com.vaadin.prototype.wc.gwt.client.util.*;

public class VaadinGridWidget extends BaseWidget  {

    protected String[] events() {
      return new String[]{"select"};
    }

    public VaadinGridWidget() {
      super(WC.create(VaadinGrid.class));
    }

    public VaadinGridWidget(VaadinGrid element) {
      super(element);
    }

    protected VaadinGrid element() {
      return (VaadinGrid)super.getElement();
    }

    public void refresh() {
         element().refresh();
    }
    public void redraw() {
         element().redraw();
    }
    public void shadow(boolean val) {
        element().shadow(val);
    }
    public boolean shadow() {
        return element().shadow();
    }
    public void selectedRow(double val) {
        element().selectedRow(val);
    }
    public double selectedRow() {
        return element().selectedRow();
    }
    public void selectedRows(JavaScriptObject val) {
        element().selectedRows(val);
    }
    public JavaScriptObject selectedRows() {
        return element().selectedRows();
    }
    public void rowCount(double val) {
        element().rowCount(val);
    }
    public double rowCount() {
        return element().rowCount();
    }
    public void type(String val) {
        element().type(val);
    }
    public String type() {
        return element().type();
    }
    public void url(String val) {
        element().url(val);
    }
    public String url() {
        return element().url();
    }
    public void dataSource(JavaScriptObject val) {
        element().dataSource(val);
    }
    public JavaScriptObject dataSource() {
        return element().dataSource();
    }
    public void theme(String val) {
        element().theme(val);
    }
    public String theme() {
        return element().theme();
    }

}
