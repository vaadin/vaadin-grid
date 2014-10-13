package com.vaadin.prototype.wc.gwt.client.ui;

import com.google.gwt.query.client.IsProperties;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.prototype.wc.gwt.client.components.PaperToastWidget;
import com.vaadin.prototype.wc.server.ui.PaperToastComponent;
import com.vaadin.shared.ui.Connect;


@SuppressWarnings("serial")
@Connect(PaperToastComponent.class)
public class PaperToastConnector extends BaseConnector {

    public IsProperties stateProperties() {
                getState().text = getWidget().text();
                getState().duration = getWidget().duration();
                getState().opened = getWidget().opened();
                getState().responsiveWidth = getWidget().responsiveWidth();
                getState().swipeDisabled = getWidget().swipeDisabled();

      
      IsProperties p = super.stateProperties();
    p.set("text", getState().text);
    p.set("duration", getState().duration);
    p.set("opened", getState().opened);
    p.set("responsiveWidth", getState().responsiveWidth);
    p.set("swipeDisabled", getState().swipeDisabled);

      return p;
    }

    @Override
    public PaperToastWidget getWidget() {
        return (PaperToastWidget) super.getWidget();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);
            getWidget().text(getState().text);
            getWidget().duration(getState().duration);
            getWidget().opened(getState().opened);
            getWidget().responsiveWidth(getState().responsiveWidth);
            getWidget().swipeDisabled(getState().swipeDisabled);

    }

    @Override
    public PaperToastState getState() {
        return (PaperToastState)super.getState();
    }
}
