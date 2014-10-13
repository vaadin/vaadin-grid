package com.vaadin.prototype.wc.server.ui;

import com.vaadin.prototype.wc.gwt.client.ui.SamplerScaffoldState;
import com.vaadin.ui.AbstractComponent;

@SuppressWarnings("serial")
public class SamplerScaffoldComponent extends BaseComponent {
    protected String[] events() {
      return concat(super.events(), new String[]{});
    }
  
    protected String[] attributes() {
      return concat(super.attributes(),new String[]{});
    }
  
    @Override
    protected SamplerScaffoldState getState() {
        return (SamplerScaffoldState) super.getState();
    }


}