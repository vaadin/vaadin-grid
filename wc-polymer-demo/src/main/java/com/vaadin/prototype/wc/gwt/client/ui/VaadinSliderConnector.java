package com.vaadin.prototype.wc.gwt.client.ui;

import com.google.gwt.query.client.IsProperties;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.prototype.wc.gwt.client.components.VaadinSliderWidget;
import com.vaadin.prototype.wc.server.ui.VaadinSliderComponent;
import com.vaadin.shared.ui.Connect;


@SuppressWarnings("serial")
@Connect(VaadinSliderComponent.class)
public class VaadinSliderConnector extends BaseConnector {

    public IsProperties stateProperties() {
                getState().value = getWidget().value();
                getState().min = getWidget().min();
                getState().max = getWidget().max();
                getState().theme = getWidget().theme();


      IsProperties p = super.stateProperties();
    p.set("value", getState().value);
    p.set("min", getState().min);
    p.set("max", getState().max);
    p.set("theme", getState().theme);

      return p;
    }

    @Override
    public VaadinSliderWidget getWidget() {
        return (VaadinSliderWidget) super.getWidget();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);
            getWidget().value(getState().value);
            getWidget().min(getState().min);
            getWidget().max(getState().max);
            getWidget().theme(getState().theme);

    }

    @Override
    public VaadinSliderState getState() {
        return (VaadinSliderState)super.getState();
    }
}
