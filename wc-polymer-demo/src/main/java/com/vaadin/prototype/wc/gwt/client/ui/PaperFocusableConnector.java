package com.vaadin.prototype.wc.gwt.client.ui;

import com.google.gwt.query.client.IsProperties;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.prototype.wc.gwt.client.components.PaperFocusableWidget;
import com.vaadin.prototype.wc.server.ui.PaperFocusableComponent;
import com.vaadin.shared.ui.Connect;


@SuppressWarnings("serial")
@Connect(PaperFocusableComponent.class)
public class PaperFocusableConnector extends BaseConnector {

    public IsProperties stateProperties() {
                getState().active = getWidget().active();
                getState().focused = getWidget().focused();
                getState().pressed = getWidget().pressed();
                getState().disabled = getWidget().disabled();
                getState().isToggle = getWidget().isToggle();


      IsProperties p = super.stateProperties();
    p.set("active", getState().active);
    p.set("focused", getState().focused);
    p.set("pressed", getState().pressed);
    p.set("disabled", getState().disabled);
    p.set("isToggle", getState().isToggle);

      return p;
    }

    @Override
    public PaperFocusableWidget getWidget() {
        return (PaperFocusableWidget) super.getWidget();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);
            getWidget().active(getState().active);
            getWidget().focused(getState().focused);
            getWidget().pressed(getState().pressed);
            getWidget().disabled(getState().disabled);
            getWidget().isToggle(getState().isToggle);

    }

    @Override
    public PaperFocusableState getState() {
        return (PaperFocusableState)super.getState();
    }
}
