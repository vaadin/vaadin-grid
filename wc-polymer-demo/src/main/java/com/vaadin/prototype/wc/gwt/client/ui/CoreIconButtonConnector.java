package com.vaadin.prototype.wc.gwt.client.ui;

import com.google.gwt.query.client.IsProperties;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.prototype.wc.gwt.client.components.CoreIconButtonWidget;
import com.vaadin.prototype.wc.server.ui.CoreIconButtonComponent;
import com.vaadin.shared.ui.Connect;


@SuppressWarnings("serial")
@Connect(CoreIconButtonComponent.class)
public class CoreIconButtonConnector extends BaseConnector {

    public IsProperties stateProperties() {
                getState().src = getWidget().src();
                getState().active = getWidget().active();
                getState().icon = getWidget().icon();


      IsProperties p = super.stateProperties();
    p.set("src", getState().src);
    p.set("active", getState().active);
    p.set("icon", getState().icon);

      return p;
    }

    @Override
    public CoreIconButtonWidget getWidget() {
        return (CoreIconButtonWidget) super.getWidget();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);
            getWidget().src(getState().src);
            getWidget().active(getState().active);
            getWidget().icon(getState().icon);

    }

    @Override
    public CoreIconButtonState getState() {
        return (CoreIconButtonState)super.getState();
    }
}
