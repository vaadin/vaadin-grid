package com.vaadin.prototype.wc.gwt.client.ui;

import com.google.gwt.query.client.IsProperties;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.prototype.wc.gwt.client.components.PaperRippleWidget;
import com.vaadin.prototype.wc.server.ui.PaperRippleComponent;
import com.vaadin.shared.ui.Connect;


@SuppressWarnings("serial")
@Connect(PaperRippleComponent.class)
public class PaperRippleConnector extends BaseConnector {

    public IsProperties stateProperties() {
                getState().initialOpacity = getWidget().initialOpacity();
                getState().opacityDecayVelocity = getWidget().opacityDecayVelocity();

      
      IsProperties p = super.stateProperties();
    p.set("initialOpacity", getState().initialOpacity);
    p.set("opacityDecayVelocity", getState().opacityDecayVelocity);

      return p;
    }

    @Override
    public PaperRippleWidget getWidget() {
        return (PaperRippleWidget) super.getWidget();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);
            getWidget().initialOpacity(getState().initialOpacity);
            getWidget().opacityDecayVelocity(getState().opacityDecayVelocity);

    }

    @Override
    public PaperRippleState getState() {
        return (PaperRippleState)super.getState();
    }
}
