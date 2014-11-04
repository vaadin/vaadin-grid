package com.vaadin.prototype.wc.gwt.client.ui;

import com.google.gwt.query.client.IsProperties;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.prototype.wc.gwt.client.components.SvgSampleIconsWidget;
import com.vaadin.prototype.wc.server.ui.SvgSampleIconsComponent;
import com.vaadin.shared.ui.Connect;


@SuppressWarnings("serial")
@Connect(SvgSampleIconsComponent.class)
public class SvgSampleIconsConnector extends BaseConnector {

    public IsProperties stateProperties() {

      
      IsProperties p = super.stateProperties();

      return p;
    }

    @Override
    public SvgSampleIconsWidget getWidget() {
        return (SvgSampleIconsWidget) super.getWidget();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);

    }

    @Override
    public SvgSampleIconsState getState() {
        return (SvgSampleIconsState)super.getState();
    }
}
