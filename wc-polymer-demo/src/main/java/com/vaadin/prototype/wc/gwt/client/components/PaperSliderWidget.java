package com.vaadin.prototype.wc.gwt.client.components;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.prototype.wc.gwt.client.*;
import com.vaadin.prototype.wc.gwt.client.util.*;

public class PaperSliderWidget extends CoreRangeWidget  {

    protected String[] events() {
      return new String[]{"change"};
    }

    public PaperSliderWidget() {
      super(WC.create(PaperSlider.class));
    }

    public PaperSliderWidget(PaperSlider element) {
      super(element);
    }

    protected PaperSlider element() {
      return (PaperSlider)super.getElement();
    }

    public void snaps(boolean val) {
        element().snaps(val);
    }
    public boolean snaps() {
        return element().snaps();
    }
    public void pin(boolean val) {
        element().pin(val);
    }
    public boolean pin() {
        return element().pin();
    }
    public void disabled(boolean val) {
        element().disabled(val);
    }
    public boolean disabled() {
        return element().disabled();
    }
    public void secondaryProgress(double val) {
        element().secondaryProgress(val);
    }
    public double secondaryProgress() {
        return element().secondaryProgress();
    }
    public void editable(boolean val) {
        element().editable(val);
    }
    public boolean editable() {
        return element().editable();
    }
    public void immediateValue(double val) {
        element().immediateValue(val);
    }
    public double immediateValue() {
        return element().immediateValue();
    }

}
