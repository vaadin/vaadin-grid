package com.vaadin.prototype.wc.server.ui;

import com.vaadin.prototype.wc.gwt.client.ui.CoreSubmenuState;
import com.vaadin.ui.AbstractComponent;

@SuppressWarnings("serial")
public class CoreSubmenuComponent extends CoreItemComponent {
    protected String[] events() {
      return concat(super.events(), new String[]{});
    }
  
    protected String[] attributes() {
      return concat(super.attributes(),new String[]{});
    }
  
    @Override
    protected CoreSubmenuState getState() {
        return (CoreSubmenuState) super.getState();
    }


}