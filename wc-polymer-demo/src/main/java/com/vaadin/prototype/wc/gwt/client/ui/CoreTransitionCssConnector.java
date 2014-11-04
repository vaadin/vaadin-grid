package com.vaadin.prototype.wc.gwt.client.ui;

import com.google.gwt.query.client.IsProperties;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.prototype.wc.gwt.client.components.CoreTransitionCssWidget;
import com.vaadin.prototype.wc.server.ui.CoreTransitionCssComponent;
import com.vaadin.shared.ui.Connect;


@SuppressWarnings("serial")
@Connect(CoreTransitionCssComponent.class)
public class CoreTransitionCssConnector extends CoreTransitionConnector {

    public IsProperties stateProperties() {

      
      IsProperties p = super.stateProperties();

      return p;
    }

    @Override
    public CoreTransitionCssWidget getWidget() {
        return (CoreTransitionCssWidget) super.getWidget();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);

    }

    @Override
    public CoreTransitionCssState getState() {
        return (CoreTransitionCssState)super.getState();
    }
}
