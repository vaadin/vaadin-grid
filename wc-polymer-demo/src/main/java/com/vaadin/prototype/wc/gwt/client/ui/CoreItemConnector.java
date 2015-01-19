package com.vaadin.prototype.wc.gwt.client.ui;

import com.google.gwt.query.client.IsProperties;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.prototype.wc.gwt.client.components.CoreItemWidget;
import com.vaadin.prototype.wc.server.ui.CoreItemComponent;
import com.vaadin.shared.ui.Connect;


@SuppressWarnings("serial")
@Connect(CoreItemComponent.class)
public class CoreItemConnector extends BaseConnector {

    public IsProperties stateProperties() {
                getState().src = getWidget().src();
                getState().icon = getWidget().icon();
                getState().label = getWidget().label();


      IsProperties p = super.stateProperties();
    p.set("src", getState().src);
    p.set("icon", getState().icon);
    p.set("label", getState().label);

      return p;
    }

    @Override
    public CoreItemWidget getWidget() {
        return (CoreItemWidget) super.getWidget();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);
            getWidget().src(getState().src);
            getWidget().icon(getState().icon);
            getWidget().label(getState().label);

    }

    @Override
    public CoreItemState getState() {
        return (CoreItemState)super.getState();
    }
}
