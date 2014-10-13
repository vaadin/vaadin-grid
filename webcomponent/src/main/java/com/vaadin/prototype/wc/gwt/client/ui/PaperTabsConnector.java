package com.vaadin.prototype.wc.gwt.client.ui;

import com.google.gwt.query.client.IsProperties;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.prototype.wc.gwt.client.components.PaperTabsWidget;
import com.vaadin.prototype.wc.server.ui.PaperTabsComponent;
import com.vaadin.shared.ui.Connect;


@SuppressWarnings("serial")
@Connect(PaperTabsComponent.class)
public class PaperTabsConnector extends CoreSelectorConnector {

    public IsProperties stateProperties() {
                getState().noink = getWidget().noink();
                getState().nobar = getWidget().nobar();

      
      IsProperties p = super.stateProperties();
    p.set("noink", getState().noink);
    p.set("nobar", getState().nobar);

      return p;
    }

    @Override
    public PaperTabsWidget getWidget() {
        return (PaperTabsWidget) super.getWidget();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);
            getWidget().noink(getState().noink);
            getWidget().nobar(getState().nobar);

    }

    @Override
    public PaperTabsState getState() {
        return (PaperTabsState)super.getState();
    }
}
