package com.vaadin.prototype.wc.gwt.client.ui;

import com.google.gwt.query.client.IsProperties;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.prototype.wc.gwt.client.components.PaperDialogWidget;
import com.vaadin.prototype.wc.server.ui.PaperDialogComponent;
import com.vaadin.shared.ui.Connect;


@SuppressWarnings("serial")
@Connect(PaperDialogComponent.class)
public class PaperDialogConnector extends BaseConnector {

    public IsProperties stateProperties() {
                getState().opened = getWidget().opened();
                getState().heading = getWidget().heading();
                getState().transition = getWidget().transition();

      
      IsProperties p = super.stateProperties();
    p.set("opened", getState().opened);
    p.set("heading", getState().heading);
    p.set("transition", getState().transition);

      return p;
    }

    @Override
    public PaperDialogWidget getWidget() {
        return (PaperDialogWidget) super.getWidget();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);
            getWidget().opened(getState().opened);
            getWidget().heading(getState().heading);
            getWidget().transition(getState().transition);

    }

    @Override
    public PaperDialogState getState() {
        return (PaperDialogState)super.getState();
    }
}
