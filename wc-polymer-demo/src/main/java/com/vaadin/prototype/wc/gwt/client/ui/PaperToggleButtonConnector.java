package com.vaadin.prototype.wc.gwt.client.ui;

import com.google.gwt.query.client.IsProperties;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.prototype.wc.gwt.client.components.PaperToggleButtonWidget;
import com.vaadin.prototype.wc.server.ui.PaperToggleButtonComponent;
import com.vaadin.shared.ui.Connect;


@SuppressWarnings("serial")
@Connect(PaperToggleButtonComponent.class)
public class PaperToggleButtonConnector extends BaseConnector {

    public IsProperties stateProperties() {
                getState().checked = getWidget().checked();


      IsProperties p = super.stateProperties();
    p.set("checked", getState().checked);

      return p;
    }

    @Override
    public PaperToggleButtonWidget getWidget() {
        return (PaperToggleButtonWidget) super.getWidget();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);
            getWidget().checked(getState().checked);

    }

    @Override
    public PaperToggleButtonState getState() {
        return (PaperToggleButtonState)super.getState();
    }
}
