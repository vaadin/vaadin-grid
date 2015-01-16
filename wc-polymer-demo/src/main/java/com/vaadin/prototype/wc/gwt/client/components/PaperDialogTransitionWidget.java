package com.vaadin.prototype.wc.gwt.client.components;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.prototype.wc.gwt.client.*;
import com.vaadin.prototype.wc.gwt.client.util.*;

public class PaperDialogTransitionWidget extends BaseWidget  {

    protected String[] events() {
      return new String[]{};
    }

    public PaperDialogTransitionWidget() {
      super(WC.create(PaperDialogTransition.class));
    }

    public PaperDialogTransitionWidget(PaperDialogTransition element) {
      super(element);
    }

    protected PaperDialogTransition element() {
      return (PaperDialogTransition)super.getElement();
    }


}
