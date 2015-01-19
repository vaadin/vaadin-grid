package com.vaadin.prototype.wc.gwt.client.ui;

import com.google.gwt.query.client.IsProperties;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.prototype.wc.gwt.client.components.PaperIconButtonWidget;
import com.vaadin.prototype.wc.server.ui.PaperIconButtonComponent;
import com.vaadin.shared.ui.Connect;


@SuppressWarnings("serial")
@Connect(PaperIconButtonComponent.class)
public class PaperIconButtonConnector extends PaperButtonConnector {

    public IsProperties stateProperties() {
                getState().fill = getWidget().fill();


      IsProperties p = super.stateProperties();
    p.set("fill", getState().fill);

      return p;
    }

    @Override
    public PaperIconButtonWidget getWidget() {
        return (PaperIconButtonWidget) super.getWidget();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);
            getWidget().fill(getState().fill);

    }

    @Override
    public PaperIconButtonState getState() {
        return (PaperIconButtonState)super.getState();
    }
}
