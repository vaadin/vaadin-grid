package com.vaadin.prototype.wc.gwt.client.ui;

import com.google.gwt.query.client.IsProperties;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.prototype.wc.gwt.client.components.CoreRangeWidget;
import com.vaadin.prototype.wc.server.ui.CoreRangeComponent;
import com.vaadin.shared.ui.Connect;


@SuppressWarnings("serial")
@Connect(CoreRangeComponent.class)
public class CoreRangeConnector extends BaseConnector {

    public IsProperties stateProperties() {
                getState().value = getWidget().value();
                getState().min = getWidget().min();
                getState().max = getWidget().max();
                getState().step = getWidget().step();
                getState().ratio = getWidget().ratio();

      
      IsProperties p = super.stateProperties();
    p.set("value", getState().value);
    p.set("min", getState().min);
    p.set("max", getState().max);
    p.set("step", getState().step);
    p.set("ratio", getState().ratio);

      return p;
    }

    @Override
    public CoreRangeWidget getWidget() {
        return (CoreRangeWidget) super.getWidget();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);
            getWidget().value(getState().value);
            getWidget().min(getState().min);
            getWidget().max(getState().max);
            getWidget().step(getState().step);
            getWidget().ratio(getState().ratio);

    }

    @Override
    public CoreRangeState getState() {
        return (CoreRangeState)super.getState();
    }
}
