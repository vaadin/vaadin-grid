package com.vaadin.prototype.wc.gwt.client.components;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.prototype.wc.gwt.client.*;
import com.vaadin.prototype.wc.gwt.client.util.*;

public class CoreSplitterWidget extends BaseWidget  {

    protected String[] events() {
      return new String[]{};
    }

    public CoreSplitterWidget() {
      super(WC.create(CoreSplitter.class));
    }

    public CoreSplitterWidget(CoreSplitter element) {
      super(element);
    }

    protected CoreSplitter element() {
      return (CoreSplitter)super.getElement();
    }

    public void direction(String val) {
        element().direction(val);
    }
    public String direction() {
        return element().direction();
    }
    public void minSize(String val) {
        element().minSize(val);
    }
    public String minSize() {
        return element().minSize();
    }
    public void locked(boolean val) {
        element().locked(val);
    }
    public boolean locked() {
        return element().locked();
    }
    public void allowOverflow(boolean val) {
        element().allowOverflow(val);
    }
    public boolean allowOverflow() {
        return element().allowOverflow();
    }

}
