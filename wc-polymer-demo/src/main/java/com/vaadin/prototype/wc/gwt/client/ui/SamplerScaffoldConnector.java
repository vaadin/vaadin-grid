package com.vaadin.prototype.wc.gwt.client.ui;

import com.google.gwt.query.client.IsProperties;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.prototype.wc.gwt.client.components.SamplerScaffoldWidget;
import com.vaadin.prototype.wc.server.ui.SamplerScaffoldComponent;
import com.vaadin.shared.ui.Connect;


@SuppressWarnings("serial")
@Connect(SamplerScaffoldComponent.class)
public class SamplerScaffoldConnector extends BaseConnector {

    public IsProperties stateProperties() {

      
      IsProperties p = super.stateProperties();

      return p;
    }

    @Override
    public SamplerScaffoldWidget getWidget() {
        return (SamplerScaffoldWidget) super.getWidget();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);

    }

    @Override
    public SamplerScaffoldState getState() {
        return (SamplerScaffoldState)super.getState();
    }
}
