package com.vaadin.prototype.wc.gwt.client.ui;

import com.google.gwt.query.client.IsProperties;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.prototype.wc.gwt.client.components.VaadinGridWidget;
import com.vaadin.prototype.wc.server.ui.VaadinGridComponent;
import com.vaadin.shared.ui.Connect;


@SuppressWarnings("serial")
@Connect(VaadinGridComponent.class)
public class VaadinGridConnector extends BaseConnector {

    public IsProperties stateProperties() {
                getState().shadow = getWidget().shadow();
                getState().selectedRow = getWidget().selectedRow();
                getState().rowCount = getWidget().rowCount();
                getState().type = getWidget().type();
                getState().url = getWidget().url();
                getState().theme = getWidget().theme();


      IsProperties p = super.stateProperties();
    p.set("shadow", getState().shadow);
    p.set("selectedRow", getState().selectedRow);
    p.set("rowCount", getState().rowCount);
    p.set("type", getState().type);
    p.set("url", getState().url);
    p.set("theme", getState().theme);

      return p;
    }

    @Override
    public VaadinGridWidget getWidget() {
        return (VaadinGridWidget) super.getWidget();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);
            getWidget().shadow(getState().shadow);
            getWidget().selectedRow(getState().selectedRow);
            getWidget().rowCount(getState().rowCount);
            getWidget().type(getState().type);
            getWidget().url(getState().url);
            getWidget().theme(getState().theme);

    }

    @Override
    public VaadinGridState getState() {
        return (VaadinGridState)super.getState();
    }
}
