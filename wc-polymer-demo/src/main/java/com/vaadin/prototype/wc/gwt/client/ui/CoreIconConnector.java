package com.vaadin.prototype.wc.gwt.client.ui;

import com.google.gwt.query.client.IsProperties;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.prototype.wc.gwt.client.components.CoreIconWidget;
import com.vaadin.prototype.wc.server.ui.CoreIconComponent;
import com.vaadin.shared.ui.Connect;


@SuppressWarnings("serial")
@Connect(CoreIconComponent.class)
public class CoreIconConnector extends BaseConnector {

    public IsProperties stateProperties() {
                getState().src = getWidget().src();
                getState().size = getWidget().size();
                getState().icon = getWidget().icon();


      IsProperties p = super.stateProperties();
    p.set("src", getState().src);
    p.set("size", getState().size);
    p.set("icon", getState().icon);

      return p;
    }

    @Override
    public CoreIconWidget getWidget() {
        return (CoreIconWidget) super.getWidget();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);
            getWidget().src(getState().src);
            getWidget().size(getState().size);
            getWidget().icon(getState().icon);

    }

    @Override
    public CoreIconState getState() {
        return (CoreIconState)super.getState();
    }
}
