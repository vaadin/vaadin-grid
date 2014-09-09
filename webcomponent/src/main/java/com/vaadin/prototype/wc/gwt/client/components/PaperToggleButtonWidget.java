package com.vaadin.prototype.wc.gwt.client.components;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.prototype.wc.gwt.client.WC;

public class PaperToggleButtonWidget extends Widget  {
    private PaperToggleButton element;
    
    public PaperToggleButtonWidget() {
        element = WC.create(PaperToggleButton.class);
        setElement((Element) element);
    }

    public void setChecked(boolean val) {
        element.checked(val);
    }

    public boolean getChecked() {
        return element.checked();
    }
}
