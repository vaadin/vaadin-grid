package com.vaadin.prototype.wc.gwt.client.ui;

import com.google.gwt.query.client.IsProperties;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.prototype.wc.gwt.client.components.CoreDropdownOverlayWidget;
import com.vaadin.prototype.wc.server.ui.CoreDropdownOverlayComponent;
import com.vaadin.shared.ui.Connect;


@SuppressWarnings("serial")
@Connect(CoreDropdownOverlayComponent.class)
public class CoreDropdownOverlayConnector extends CoreOverlayConnector {

    public IsProperties stateProperties() {
                getState().margin = getWidget().margin();


      IsProperties p = super.stateProperties();
    p.set("margin", getState().margin);

      return p;
    }

    @Override
    public CoreDropdownOverlayWidget getWidget() {
        return (CoreDropdownOverlayWidget) super.getWidget();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);
            getWidget().margin(getState().margin);

    }

    @Override
    public CoreDropdownOverlayState getState() {
        return (CoreDropdownOverlayState)super.getState();
    }
}
