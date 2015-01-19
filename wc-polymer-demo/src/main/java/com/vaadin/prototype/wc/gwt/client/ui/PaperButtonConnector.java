package com.vaadin.prototype.wc.gwt.client.ui;

import com.google.gwt.query.client.IsProperties;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.prototype.wc.gwt.client.components.PaperButtonWidget;
import com.vaadin.prototype.wc.server.ui.PaperButtonComponent;
import com.vaadin.shared.ui.Connect;


@SuppressWarnings("serial")
@Connect(PaperButtonComponent.class)
public class PaperButtonConnector extends PaperFocusableConnector {

    public IsProperties stateProperties() {
                getState().label = getWidget().label();
                getState().raisedButton = getWidget().raisedButton();
                getState().iconSrc = getWidget().iconSrc();
                getState().icon = getWidget().icon();


      IsProperties p = super.stateProperties();
    p.set("label", getState().label);
    p.set("raisedButton", getState().raisedButton);
    p.set("iconSrc", getState().iconSrc);
    p.set("icon", getState().icon);

      return p;
    }

    @Override
    public PaperButtonWidget getWidget() {
        return (PaperButtonWidget) super.getWidget();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);
            getWidget().label(getState().label);
            getWidget().raisedButton(getState().raisedButton);
            getWidget().iconSrc(getState().iconSrc);
            getWidget().icon(getState().icon);

    }

    @Override
    public PaperButtonState getState() {
        return (PaperButtonState)super.getState();
    }
}
