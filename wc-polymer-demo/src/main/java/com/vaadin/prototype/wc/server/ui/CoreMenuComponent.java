package com.vaadin.prototype.wc.server.ui;

import com.vaadin.prototype.wc.gwt.client.ui.CoreMenuState;
import com.vaadin.ui.AbstractComponent;

@SuppressWarnings("serial")
public class CoreMenuComponent extends CoreSelectorComponent {
    protected String[] events() {
      return concat(super.events(), new String[]{});
    }

    protected String[] attributes() {
      return concat(super.attributes(),new String[]{});
    }

    @Override
    protected CoreMenuState getState() {
        return (CoreMenuState) super.getState();
    }


}