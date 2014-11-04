package com.vaadin.prototype.wc.server.ui;

import com.vaadin.prototype.wc.gwt.client.ui.PaperRadioGroupState;
import com.vaadin.ui.AbstractComponent;

@SuppressWarnings("serial")
public class PaperRadioGroupComponent extends CoreSelectorComponent {
    protected String[] events() {
      return concat(super.events(), new String[]{});
    }
  
    protected String[] attributes() {
      return concat(super.attributes(),new String[]{});
    }
  
    @Override
    protected PaperRadioGroupState getState() {
        return (PaperRadioGroupState) super.getState();
    }


}