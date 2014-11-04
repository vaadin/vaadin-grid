package com.vaadin.prototype.wc.gwt.client.ui;

import com.google.gwt.query.client.IsProperties;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.prototype.wc.gwt.client.components.PaperCheckboxWidget;
import com.vaadin.prototype.wc.server.ui.PaperCheckboxComponent;
import com.vaadin.shared.ui.Connect;


@SuppressWarnings("serial")
@Connect(PaperCheckboxComponent.class)
public class PaperCheckboxConnector extends PaperRadioButtonConnector {

    public IsProperties stateProperties() {

      
      IsProperties p = super.stateProperties();

      return p;
    }

    @Override
    public PaperCheckboxWidget getWidget() {
        return (PaperCheckboxWidget) super.getWidget();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);

    }

    @Override
    public PaperCheckboxState getState() {
        return (PaperCheckboxState)super.getState();
    }
}
