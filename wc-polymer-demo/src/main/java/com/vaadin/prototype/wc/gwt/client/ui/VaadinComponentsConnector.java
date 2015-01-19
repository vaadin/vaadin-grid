package com.vaadin.prototype.wc.gwt.client.ui;

import com.google.gwt.query.client.IsProperties;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.prototype.wc.gwt.client.components.VaadinComponentsWidget;
import com.vaadin.prototype.wc.server.ui.VaadinComponentsComponent;
import com.vaadin.shared.ui.Connect;


@SuppressWarnings("serial")
@Connect(VaadinComponentsComponent.class)
public class VaadinComponentsConnector extends BaseConnector {

    public IsProperties stateProperties() {


      IsProperties p = super.stateProperties();

      return p;
    }

    @Override
    public VaadinComponentsWidget getWidget() {
        return (VaadinComponentsWidget) super.getWidget();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);

    }

    @Override
    public VaadinComponentsState getState() {
        return (VaadinComponentsState)super.getState();
    }
}
