package com.vaadin.prototype.wc.gwt.client.components;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.prototype.wc.gwt.client.*;
import com.vaadin.prototype.wc.gwt.client.util.*;

public class PaperRippleWidget extends BaseWidget  {
  
    protected String[] events() {
      return new String[]{};
    }
    
    public PaperRippleWidget() {
      super(WC.create(PaperRipple.class));
    }
    
    public PaperRippleWidget(PaperRipple element) {
      super(element);
    }
    
    protected PaperRipple element() {
      return (PaperRipple)super.getElement();
    }

    public void initialOpacity(double val) {
        element().initialOpacity(val);
    }
    public double initialOpacity() {
        return element().initialOpacity();
    }
    public void opacityDecayVelocity(double val) {
        element().opacityDecayVelocity(val);
    }
    public double opacityDecayVelocity() {
        return element().opacityDecayVelocity();
    }

}
