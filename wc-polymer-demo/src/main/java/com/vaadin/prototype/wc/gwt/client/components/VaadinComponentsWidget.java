package com.vaadin.prototype.wc.gwt.client.components;

import com.vaadin.prototype.wc.gwt.client.util.WC;

public class VaadinComponentsWidget extends BaseWidget  {

    protected String[] events() {
      return new String[]{};
    }

    public VaadinComponentsWidget() {
      super(WC.create(VaadinComponents.class));
    }

    public VaadinComponentsWidget(VaadinComponents element) {
      super(element);
    }

    protected VaadinComponents element() {
      return (VaadinComponents)super.getElement();
    }


}
