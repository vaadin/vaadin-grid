package com.vaadin.prototype.wc.gwt.client.ui;

import com.google.gwt.query.client.IsProperties;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.prototype.wc.gwt.client.components.CoreOverlayWidget;
import com.vaadin.prototype.wc.server.ui.CoreOverlayComponent;
import com.vaadin.shared.ui.Connect;


@SuppressWarnings("serial")
@Connect(CoreOverlayComponent.class)
public class CoreOverlayConnector extends BaseConnector {

    public IsProperties stateProperties() {
                getState().opened = getWidget().opened();
                getState().backdrop = getWidget().backdrop();
                getState().layered = getWidget().layered();
                getState().autoCloseDisabled = getWidget().autoCloseDisabled();
                getState().closeAttribute = getWidget().closeAttribute();
                getState().closeSelector = getWidget().closeSelector();
                getState().margin = getWidget().margin();
                getState().transition = getWidget().transition();

      
      IsProperties p = super.stateProperties();
    p.set("opened", getState().opened);
    p.set("backdrop", getState().backdrop);
    p.set("layered", getState().layered);
    p.set("autoCloseDisabled", getState().autoCloseDisabled);
    p.set("closeAttribute", getState().closeAttribute);
    p.set("closeSelector", getState().closeSelector);
    p.set("margin", getState().margin);
    p.set("transition", getState().transition);

      return p;
    }

    @Override
    public CoreOverlayWidget getWidget() {
        return (CoreOverlayWidget) super.getWidget();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);
            getWidget().opened(getState().opened);
            getWidget().backdrop(getState().backdrop);
            getWidget().layered(getState().layered);
            getWidget().autoCloseDisabled(getState().autoCloseDisabled);
            getWidget().closeAttribute(getState().closeAttribute);
            getWidget().closeSelector(getState().closeSelector);
            getWidget().margin(getState().margin);
            getWidget().transition(getState().transition);

    }

    @Override
    public CoreOverlayState getState() {
        return (CoreOverlayState)super.getState();
    }
}
