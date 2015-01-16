package com.vaadin.prototype.wc.gwt.client.components;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.prototype.wc.gwt.client.*;
import com.vaadin.prototype.wc.gwt.client.util.*;

public class SamplerScaffoldWidget extends BaseWidget  {

    protected String[] events() {
      return new String[]{};
    }

    public SamplerScaffoldWidget() {
      super(WC.create(SamplerScaffold.class));
    }

    public SamplerScaffoldWidget(SamplerScaffold element) {
      super(element);
    }

    protected SamplerScaffold element() {
      return (SamplerScaffold)super.getElement();
    }


}
