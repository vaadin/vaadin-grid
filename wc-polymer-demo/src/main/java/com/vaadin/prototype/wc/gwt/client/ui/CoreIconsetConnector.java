package com.vaadin.prototype.wc.gwt.client.ui;

import com.google.gwt.query.client.IsProperties;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.prototype.wc.gwt.client.components.CoreIconsetWidget;
import com.vaadin.prototype.wc.server.ui.CoreIconsetComponent;
import com.vaadin.shared.ui.Connect;


@SuppressWarnings("serial")
@Connect(CoreIconsetComponent.class)
public class CoreIconsetConnector extends CoreMetaConnector {

    public IsProperties stateProperties() {
                getState().src = getWidget().src();
                getState().width = getWidget().width();
                getState().icons = getWidget().icons();
                getState().iconSize = getWidget().iconSize();
                getState().offsetX = getWidget().offsetX();
                getState().offsetY = getWidget().offsetY();


      IsProperties p = super.stateProperties();
    p.set("src", getState().src);
    p.set("width", getState().width);
    p.set("icons", getState().icons);
    p.set("iconSize", getState().iconSize);
    p.set("offsetX", getState().offsetX);
    p.set("offsetY", getState().offsetY);

      return p;
    }

    @Override
    public CoreIconsetWidget getWidget() {
        return (CoreIconsetWidget) super.getWidget();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);
            getWidget().src(getState().src);
            getWidget().width(getState().width);
            getWidget().icons(getState().icons);
            getWidget().iconSize(getState().iconSize);
            getWidget().offsetX(getState().offsetX);
            getWidget().offsetY(getState().offsetY);

    }

    @Override
    public CoreIconsetState getState() {
        return (CoreIconsetState)super.getState();
    }
}
