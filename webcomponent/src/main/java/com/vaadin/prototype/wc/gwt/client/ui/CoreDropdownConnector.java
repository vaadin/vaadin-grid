package com.vaadin.prototype.wc.gwt.client.ui;

import com.google.gwt.query.client.IsProperties;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.prototype.wc.gwt.client.components.CoreDropdownWidget;
import com.vaadin.prototype.wc.server.ui.CoreDropdownComponent;
import com.vaadin.shared.ui.Connect;


@SuppressWarnings("serial")
@Connect(CoreDropdownComponent.class)
public class CoreDropdownConnector extends BaseConnector {

    public IsProperties stateProperties() {
                getState().opened = getWidget().opened();
                getState().margin = getWidget().margin();
                getState().transition = getWidget().transition();

      
      IsProperties p = super.stateProperties();
    p.set("opened", getState().opened);
    p.set("margin", getState().margin);
    p.set("transition", getState().transition);

      return p;
    }

    @Override
    public CoreDropdownWidget getWidget() {
        return (CoreDropdownWidget) super.getWidget();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);
            getWidget().opened(getState().opened);
            getWidget().margin(getState().margin);
            getWidget().transition(getState().transition);

    }

    @Override
    public CoreDropdownState getState() {
        return (CoreDropdownState)super.getState();
    }
}
