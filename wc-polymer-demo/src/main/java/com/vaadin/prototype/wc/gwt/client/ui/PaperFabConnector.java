package com.vaadin.prototype.wc.gwt.client.ui;

import com.google.gwt.query.client.IsProperties;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.prototype.wc.gwt.client.components.PaperFabWidget;
import com.vaadin.prototype.wc.server.ui.PaperFabComponent;
import com.vaadin.shared.ui.Connect;


@SuppressWarnings("serial")
@Connect(PaperFabComponent.class)
public class PaperFabConnector extends PaperIconButtonConnector {

    public IsProperties stateProperties() {
                getState().raisedButton = getWidget().raisedButton();

      
      IsProperties p = super.stateProperties();
    p.set("raisedButton", getState().raisedButton);

      return p;
    }

    @Override
    public PaperFabWidget getWidget() {
        return (PaperFabWidget) super.getWidget();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);
            getWidget().raisedButton(getState().raisedButton);

    }

    @Override
    public PaperFabState getState() {
        return (PaperFabState)super.getState();
    }
}
