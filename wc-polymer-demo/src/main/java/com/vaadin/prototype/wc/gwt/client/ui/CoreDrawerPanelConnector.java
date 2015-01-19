package com.vaadin.prototype.wc.gwt.client.ui;

import com.google.gwt.query.client.IsProperties;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.prototype.wc.gwt.client.components.CoreDrawerPanelWidget;
import com.vaadin.prototype.wc.server.ui.CoreDrawerPanelComponent;
import com.vaadin.shared.ui.Connect;


@SuppressWarnings("serial")
@Connect(CoreDrawerPanelComponent.class)
public class CoreDrawerPanelConnector extends BaseConnector {

    public IsProperties stateProperties() {
                getState().responsiveWidth = getWidget().responsiveWidth();
                getState().selected = getWidget().selected();
                getState().defaultSelected = getWidget().defaultSelected();
                getState().narrow = getWidget().narrow();


      IsProperties p = super.stateProperties();
    p.set("responsiveWidth", getState().responsiveWidth);
    p.set("selected", getState().selected);
    p.set("defaultSelected", getState().defaultSelected);
    p.set("narrow", getState().narrow);

      return p;
    }

    @Override
    public CoreDrawerPanelWidget getWidget() {
        return (CoreDrawerPanelWidget) super.getWidget();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);
            getWidget().responsiveWidth(getState().responsiveWidth);
            getWidget().selected(getState().selected);
            getWidget().defaultSelected(getState().defaultSelected);
            getWidget().narrow(getState().narrow);

    }

    @Override
    public CoreDrawerPanelState getState() {
        return (CoreDrawerPanelState)super.getState();
    }
}
