package com.vaadin.prototype.wc.server.ui;

import com.vaadin.prototype.wc.gwt.client.ui.PaperCheckboxState;
import com.vaadin.ui.AbstractComponent;

@SuppressWarnings("serial")
public class PaperCheckboxComponent extends PaperRadioButtonComponent {
    protected String[] events() {
      return concat(super.events(), new String[]{"change"});
    }

    protected String[] attributes() {
      return concat(super.attributes(),new String[]{});
    }

    @Override
    protected PaperCheckboxState getState() {
        return (PaperCheckboxState) super.getState();
    }


}