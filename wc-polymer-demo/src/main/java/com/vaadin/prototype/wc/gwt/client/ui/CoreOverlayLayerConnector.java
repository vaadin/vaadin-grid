package com.vaadin.prototype.wc.gwt.client.ui;

import com.google.gwt.query.client.IsProperties;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.prototype.wc.gwt.client.components.CoreOverlayLayerWidget;
import com.vaadin.prototype.wc.server.ui.CoreOverlayLayerComponent;
import com.vaadin.shared.ui.Connect;


@SuppressWarnings("serial")
@Connect(CoreOverlayLayerComponent.class)
public class CoreOverlayLayerConnector extends BaseConnector {

    public IsProperties stateProperties() {


      IsProperties p = super.stateProperties();

      return p;
    }

    @Override
    public CoreOverlayLayerWidget getWidget() {
        return (CoreOverlayLayerWidget) super.getWidget();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);

    }

    @Override
    public CoreOverlayLayerState getState() {
        return (CoreOverlayLayerState)super.getState();
    }
}
