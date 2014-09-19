package com.vaadin.prototype.wc.gwt.client.components;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.prototype.wc.gwt.client.WC;

public class PaperToggleButtonWidget extends Widget  {
    private PaperToggleButton element;
    // TODO: a list with
    private EventListener listener;
    
    public PaperToggleButtonWidget() {
        element = WC.create(PaperToggleButton.class);
        setElement((Element) element);
        element.addEventListener("change", this);
    }

    public void setChecked(boolean val) {
        element.checked(val);
    }

    public boolean isChecked() {
        return element.checked();
    }
    
    @Override
    public void onBrowserEvent(Event event) {
        if ("change".equals(event.getType())) {
            if (listener != null) {
                listener.onBrowserEvent(event);
            }
        } else {
            super.onBrowserEvent(event);
        }
    }
    
    public void addChangeHandler(EventListener arg) {
        listener = arg;
    }
}
