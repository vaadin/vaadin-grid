package com.vaadin.prototype.wc.gwt.client.components;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.prototype.wc.gwt.client.*;
import com.vaadin.prototype.wc.gwt.client.util.*;

public class CoreMediaQueryWidget extends BaseWidget  {

    protected String[] events() {
      return new String[]{"core-media-change"};
    }

    public CoreMediaQueryWidget() {
      super(WC.create(CoreMediaQuery.class));
    }

    public CoreMediaQueryWidget(CoreMediaQuery element) {
      super(element);
    }

    protected CoreMediaQuery element() {
      return (CoreMediaQuery)super.getElement();
    }

    public void queryMatches(boolean val) {
        element().queryMatches(val);
    }
    public boolean queryMatches() {
        return element().queryMatches();
    }
    public void query(String val) {
        element().query(val);
    }
    public String query() {
        return element().query();
    }

}
