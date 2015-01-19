package com.vaadin.prototype.wc.server.ui;

import com.vaadin.prototype.wc.gwt.client.ui.CoreTransitionState;
import com.vaadin.ui.AbstractComponent;

@SuppressWarnings("serial")
public class CoreTransitionComponent extends BaseComponent {
    protected String[] events() {
      return concat(super.events(), new String[]{});
    }

    protected String[] attributes() {
      return concat(super.attributes(),new String[]{});
    }

    @Override
    protected CoreTransitionState getState() {
        return (CoreTransitionState) super.getState();
    }


}