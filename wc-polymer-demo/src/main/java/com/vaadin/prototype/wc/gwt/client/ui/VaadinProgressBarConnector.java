package com.vaadin.prototype.wc.gwt.client.ui;

import com.google.gwt.query.client.IsProperties;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.prototype.wc.gwt.client.components.VaadinProgressBarWidget;
import com.vaadin.prototype.wc.server.ui.VaadinProgressBarComponent;
import com.vaadin.shared.ui.Connect;


@SuppressWarnings("serial")
@Connect(VaadinProgressBarComponent.class)
public class VaadinProgressBarConnector extends BaseConnector {

    public IsProperties stateProperties() {
                getState().value = getWidget().value();
                getState().theme = getWidget().theme();


      IsProperties p = super.stateProperties();
    p.set("value", getState().value);
    p.set("theme", getState().theme);

      return p;
    }

    @Override
    public VaadinProgressBarWidget getWidget() {
        return (VaadinProgressBarWidget) super.getWidget();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);
            getWidget().value(getState().value);
            getWidget().theme(getState().theme);

    }

    @Override
    public VaadinProgressBarState getState() {
        return (VaadinProgressBarState)super.getState();
    }
}
