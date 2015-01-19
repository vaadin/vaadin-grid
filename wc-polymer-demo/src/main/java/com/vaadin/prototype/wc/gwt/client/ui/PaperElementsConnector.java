package com.vaadin.prototype.wc.gwt.client.ui;

import com.google.gwt.query.client.IsProperties;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.prototype.wc.gwt.client.components.PaperElementsWidget;
import com.vaadin.prototype.wc.server.ui.PaperElementsComponent;
import com.vaadin.shared.ui.Connect;


@SuppressWarnings("serial")
@Connect(PaperElementsComponent.class)
public class PaperElementsConnector extends BaseConnector {

    public IsProperties stateProperties() {


      IsProperties p = super.stateProperties();

      return p;
    }

    @Override
    public PaperElementsWidget getWidget() {
        return (PaperElementsWidget) super.getWidget();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);

    }

    @Override
    public PaperElementsState getState() {
        return (PaperElementsState)super.getState();
    }
}
