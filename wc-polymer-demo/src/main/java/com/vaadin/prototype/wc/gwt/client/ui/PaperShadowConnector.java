package com.vaadin.prototype.wc.gwt.client.ui;

import com.google.gwt.query.client.IsProperties;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.prototype.wc.gwt.client.components.PaperShadowWidget;
import com.vaadin.prototype.wc.server.ui.PaperShadowComponent;
import com.vaadin.shared.ui.Connect;


@SuppressWarnings("serial")
@Connect(PaperShadowComponent.class)
public class PaperShadowConnector extends BaseConnector {

    public IsProperties stateProperties() {
                getState().z = getWidget().z();
                getState().animated = getWidget().animated();
                getState().hasPosition = getWidget().hasPosition();

      
      IsProperties p = super.stateProperties();
    p.set("z", getState().z);
    p.set("animated", getState().animated);
    p.set("hasPosition", getState().hasPosition);

      return p;
    }

    @Override
    public PaperShadowWidget getWidget() {
        return (PaperShadowWidget) super.getWidget();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);
            getWidget().z(getState().z);
            getWidget().animated(getState().animated);
            getWidget().hasPosition(getState().hasPosition);

    }

    @Override
    public PaperShadowState getState() {
        return (PaperShadowState)super.getState();
    }
}
