package com.vaadin.prototype.wc.gwt.client.ui;

import com.google.gwt.query.client.IsProperties;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.prototype.wc.gwt.client.components.CoreKeyHelperWidget;
import com.vaadin.prototype.wc.server.ui.CoreKeyHelperComponent;
import com.vaadin.shared.ui.Connect;


@SuppressWarnings("serial")
@Connect(CoreKeyHelperComponent.class)
public class CoreKeyHelperConnector extends BaseConnector {

    public IsProperties stateProperties() {


      IsProperties p = super.stateProperties();

      return p;
    }

    @Override
    public CoreKeyHelperWidget getWidget() {
        return (CoreKeyHelperWidget) super.getWidget();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);

    }

    @Override
    public CoreKeyHelperState getState() {
        return (CoreKeyHelperState)super.getState();
    }
}
