package com.vaadin.prototype.wc.gwt.client.ui;

import com.google.gwt.query.client.IsProperties;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.prototype.wc.gwt.client.components.PaperRadioGroupWidget;
import com.vaadin.prototype.wc.server.ui.PaperRadioGroupComponent;
import com.vaadin.shared.ui.Connect;


@SuppressWarnings("serial")
@Connect(PaperRadioGroupComponent.class)
public class PaperRadioGroupConnector extends CoreSelectorConnector {

    public IsProperties stateProperties() {

      
      IsProperties p = super.stateProperties();

      return p;
    }

    @Override
    public PaperRadioGroupWidget getWidget() {
        return (PaperRadioGroupWidget) super.getWidget();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);

    }

    @Override
    public PaperRadioGroupState getState() {
        return (PaperRadioGroupState)super.getState();
    }
}
