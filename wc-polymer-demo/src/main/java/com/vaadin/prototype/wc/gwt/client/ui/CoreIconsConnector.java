package com.vaadin.prototype.wc.gwt.client.ui;

import com.google.gwt.query.client.IsProperties;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.prototype.wc.gwt.client.components.CoreIconsWidget;
import com.vaadin.prototype.wc.server.ui.CoreIconsComponent;
import com.vaadin.shared.ui.Connect;


@SuppressWarnings("serial")
@Connect(CoreIconsComponent.class)
public class CoreIconsConnector extends BaseConnector {

    public IsProperties stateProperties() {


      IsProperties p = super.stateProperties();

      return p;
    }

    @Override
    public CoreIconsWidget getWidget() {
        return (CoreIconsWidget) super.getWidget();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);

    }

    @Override
    public CoreIconsState getState() {
        return (CoreIconsState)super.getState();
    }
}
