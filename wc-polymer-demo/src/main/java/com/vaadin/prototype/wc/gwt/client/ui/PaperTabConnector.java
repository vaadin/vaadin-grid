package com.vaadin.prototype.wc.gwt.client.ui;

import com.google.gwt.query.client.IsProperties;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.prototype.wc.gwt.client.components.PaperTabWidget;
import com.vaadin.prototype.wc.server.ui.PaperTabComponent;
import com.vaadin.shared.ui.Connect;


@SuppressWarnings("serial")
@Connect(PaperTabComponent.class)
public class PaperTabConnector extends BaseConnector {

    public IsProperties stateProperties() {
                getState().noink = getWidget().noink();

      
      IsProperties p = super.stateProperties();
    p.set("noink", getState().noink);

      return p;
    }

    @Override
    public PaperTabWidget getWidget() {
        return (PaperTabWidget) super.getWidget();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);
            getWidget().noink(getState().noink);

    }

    @Override
    public PaperTabState getState() {
        return (PaperTabState)super.getState();
    }
}
