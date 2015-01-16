package com.vaadin.prototype.wc.gwt.client.components;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.prototype.wc.gwt.client.*;
import com.vaadin.prototype.wc.gwt.client.util.*;

public class PaperProgressWidget extends CoreRangeWidget  {

    protected String[] events() {
      return new String[]{};
    }

    public PaperProgressWidget() {
      super(WC.create(PaperProgress.class));
    }

    public PaperProgressWidget(PaperProgress element) {
      super(element);
    }

    protected PaperProgress element() {
      return (PaperProgress)super.getElement();
    }

    public void secondaryProgress(double val) {
        element().secondaryProgress(val);
    }
    public double secondaryProgress() {
        return element().secondaryProgress();
    }

}
