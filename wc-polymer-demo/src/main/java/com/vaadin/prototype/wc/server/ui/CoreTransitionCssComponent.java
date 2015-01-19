package com.vaadin.prototype.wc.server.ui;

import com.vaadin.prototype.wc.gwt.client.ui.CoreTransitionCssState;
import com.vaadin.ui.AbstractComponent;

@SuppressWarnings("serial")
public class CoreTransitionCssComponent extends CoreTransitionComponent {
    protected String[] events() {
      return concat(super.events(), new String[]{});
    }

    protected String[] attributes() {
      return concat(super.attributes(),new String[]{});
    }

    @Override
    protected CoreTransitionCssState getState() {
        return (CoreTransitionCssState) super.getState();
    }


}