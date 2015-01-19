package com.vaadin.prototype.wc.server.ui;

import com.vaadin.prototype.wc.gwt.client.ui.SvgSampleIconsState;
import com.vaadin.ui.AbstractComponent;

@SuppressWarnings("serial")
public class SvgSampleIconsComponent extends BaseComponent {
    protected String[] events() {
      return concat(super.events(), new String[]{});
    }

    protected String[] attributes() {
      return concat(super.attributes(),new String[]{});
    }

    @Override
    protected SvgSampleIconsState getState() {
        return (SvgSampleIconsState) super.getState();
    }


}