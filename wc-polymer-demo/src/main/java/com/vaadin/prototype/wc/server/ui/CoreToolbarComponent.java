package com.vaadin.prototype.wc.server.ui;

import com.vaadin.prototype.wc.gwt.client.ui.CoreToolbarState;
import com.vaadin.ui.AbstractComponent;

@SuppressWarnings("serial")
public class CoreToolbarComponent extends BaseComponent {
    protected String[] events() {
      return concat(super.events(), new String[]{});
    }

    protected String[] attributes() {
      return concat(super.attributes(),new String[]{});
    }

    @Override
    protected CoreToolbarState getState() {
        return (CoreToolbarState) super.getState();
    }


}