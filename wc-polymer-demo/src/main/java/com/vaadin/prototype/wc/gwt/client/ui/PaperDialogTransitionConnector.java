package com.vaadin.prototype.wc.gwt.client.ui;

import com.google.gwt.query.client.IsProperties;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.prototype.wc.gwt.client.components.PaperDialogTransitionWidget;
import com.vaadin.prototype.wc.server.ui.PaperDialogTransitionComponent;
import com.vaadin.shared.ui.Connect;


@SuppressWarnings("serial")
@Connect(PaperDialogTransitionComponent.class)
public class PaperDialogTransitionConnector extends BaseConnector {

    public IsProperties stateProperties() {


      IsProperties p = super.stateProperties();

      return p;
    }

    @Override
    public PaperDialogTransitionWidget getWidget() {
        return (PaperDialogTransitionWidget) super.getWidget();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);

    }

    @Override
    public PaperDialogTransitionState getState() {
        return (PaperDialogTransitionState)super.getState();
    }
}
