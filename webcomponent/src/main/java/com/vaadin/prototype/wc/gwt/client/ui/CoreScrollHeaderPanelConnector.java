package com.vaadin.prototype.wc.gwt.client.ui;

import com.google.gwt.query.client.IsProperties;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.prototype.wc.gwt.client.components.CoreScrollHeaderPanelWidget;
import com.vaadin.prototype.wc.server.ui.CoreScrollHeaderPanelComponent;
import com.vaadin.shared.ui.Connect;


@SuppressWarnings("serial")
@Connect(CoreScrollHeaderPanelComponent.class)
public class CoreScrollHeaderPanelConnector extends BaseConnector {

    public IsProperties stateProperties() {
                getState().condenses = getWidget().condenses();
                getState().noDissolve = getWidget().noDissolve();
                getState().noReveal = getWidget().noReveal();
                getState().fixed = getWidget().fixed();
                getState().keepCondensedHeader = getWidget().keepCondensedHeader();
                getState().headerHeight = getWidget().headerHeight();
                getState().condensedHeaderHeight = getWidget().condensedHeaderHeight();

      
      IsProperties p = super.stateProperties();
    p.set("condenses", getState().condenses);
    p.set("noDissolve", getState().noDissolve);
    p.set("noReveal", getState().noReveal);
    p.set("fixed", getState().fixed);
    p.set("keepCondensedHeader", getState().keepCondensedHeader);
    p.set("headerHeight", getState().headerHeight);
    p.set("condensedHeaderHeight", getState().condensedHeaderHeight);

      return p;
    }

    @Override
    public CoreScrollHeaderPanelWidget getWidget() {
        return (CoreScrollHeaderPanelWidget) super.getWidget();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);
            getWidget().condenses(getState().condenses);
            getWidget().noDissolve(getState().noDissolve);
            getWidget().noReveal(getState().noReveal);
            getWidget().fixed(getState().fixed);
            getWidget().keepCondensedHeader(getState().keepCondensedHeader);
            getWidget().headerHeight(getState().headerHeight);
            getWidget().condensedHeaderHeight(getState().condensedHeaderHeight);

    }

    @Override
    public CoreScrollHeaderPanelState getState() {
        return (CoreScrollHeaderPanelState)super.getState();
    }
}
