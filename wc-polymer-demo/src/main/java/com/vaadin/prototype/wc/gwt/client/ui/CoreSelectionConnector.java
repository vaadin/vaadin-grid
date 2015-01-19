package com.vaadin.prototype.wc.gwt.client.ui;

import com.google.gwt.query.client.IsProperties;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.prototype.wc.gwt.client.components.CoreSelectionWidget;
import com.vaadin.prototype.wc.server.ui.CoreSelectionComponent;
import com.vaadin.shared.ui.Connect;


@SuppressWarnings("serial")
@Connect(CoreSelectionComponent.class)
public class CoreSelectionConnector extends BaseConnector {

    public IsProperties stateProperties() {
                getState().multi = getWidget().multi();


      IsProperties p = super.stateProperties();
    p.set("multi", getState().multi);

      return p;
    }

    @Override
    public CoreSelectionWidget getWidget() {
        return (CoreSelectionWidget) super.getWidget();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);
            getWidget().multi(getState().multi);

    }

    @Override
    public CoreSelectionState getState() {
        return (CoreSelectionState)super.getState();
    }
}
