package com.vaadin.prototype.wc.gwt.client.ui;

import com.google.gwt.query.client.IsProperties;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.prototype.wc.gwt.client.components.CoreHeaderPanelWidget;
import com.vaadin.prototype.wc.server.ui.CoreHeaderPanelComponent;
import com.vaadin.shared.ui.Connect;


@SuppressWarnings("serial")
@Connect(CoreHeaderPanelComponent.class)
public class CoreHeaderPanelConnector extends BaseConnector {

    public IsProperties stateProperties() {
                getState().mode = getWidget().mode();
                getState().tallClass = getWidget().tallClass();
                getState().shadow = getWidget().shadow();

      
      IsProperties p = super.stateProperties();
    p.set("mode", getState().mode);
    p.set("tallClass", getState().tallClass);
    p.set("shadow", getState().shadow);

      return p;
    }

    @Override
    public CoreHeaderPanelWidget getWidget() {
        return (CoreHeaderPanelWidget) super.getWidget();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);
            getWidget().mode(getState().mode);
            getWidget().tallClass(getState().tallClass);
            getWidget().shadow(getState().shadow);

    }

    @Override
    public CoreHeaderPanelState getState() {
        return (CoreHeaderPanelState)super.getState();
    }
}
