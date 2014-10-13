package com.vaadin.prototype.wc.gwt.client.components;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.prototype.wc.gwt.client.*;

public class CoreCollapseWidget extends BaseWidget  {
  
    protected String[] events() {
      return new String[]{"core-resize"};
    }
    
    public CoreCollapseWidget() {
      super(WC.create(CoreCollapse.class));
    }
    
    public CoreCollapseWidget(CoreCollapse element) {
      super(element);
    }
    
    protected CoreCollapse element() {
      return (CoreCollapse)super.getElement();
    }

    public void target(JavaScriptObject val) {
        element().target(val);
    }
    public JavaScriptObject target() {
        return element().target();
    }
    public void horizontal(boolean val) {
        element().horizontal(val);
    }
    public boolean horizontal() {
        return element().horizontal();
    }
    public void opened(boolean val) {
        element().opened(val);
    }
    public boolean opened() {
        return element().opened();
    }
    public void duration(double val) {
        element().duration(val);
    }
    public double duration() {
        return element().duration();
    }
    public void fixedSize(boolean val) {
        element().fixedSize(val);
    }
    public boolean fixedSize() {
        return element().fixedSize();
    }

}
