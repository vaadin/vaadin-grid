package com.vaadin.prototype.wc.gwt.client.ui;

import com.google.gwt.query.client.IsProperties;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.prototype.wc.gwt.client.components.CoreMediaQueryWidget;
import com.vaadin.prototype.wc.server.ui.CoreMediaQueryComponent;
import com.vaadin.shared.ui.Connect;


@SuppressWarnings("serial")
@Connect(CoreMediaQueryComponent.class)
public class CoreMediaQueryConnector extends BaseConnector {

    public IsProperties stateProperties() {
                getState().queryMatches = getWidget().queryMatches();
                getState().query = getWidget().query();

      
      IsProperties p = super.stateProperties();
    p.set("queryMatches", getState().queryMatches);
    p.set("query", getState().query);

      return p;
    }

    @Override
    public CoreMediaQueryWidget getWidget() {
        return (CoreMediaQueryWidget) super.getWidget();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);
            getWidget().queryMatches(getState().queryMatches);
            getWidget().query(getState().query);

    }

    @Override
    public CoreMediaQueryState getState() {
        return (CoreMediaQueryState)super.getState();
    }
}
