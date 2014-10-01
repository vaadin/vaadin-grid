package com.vaadin.prototype.wc.server.ui;

import com.vaadin.prototype.wc.gwt.client.ui.PaperToggleButtonState;
import com.vaadin.ui.AbstractComponent;

@SuppressWarnings("serial")
public class PaperToggleButtonComponent extends AbstractComponent {
    public PaperToggleButtonComponent() {
        getState().checked = false;
    }
    @Override
    protected PaperToggleButtonState getState() {
        return (PaperToggleButtonState) super.getState();
    }

    public void setChecked(boolean b) {
        getState().checked = b;
        System.out.println("ADFAsdfsaf");
    }
}
