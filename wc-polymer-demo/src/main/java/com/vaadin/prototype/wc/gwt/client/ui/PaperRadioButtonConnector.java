package com.vaadin.prototype.wc.gwt.client.ui;

import com.google.gwt.query.client.IsProperties;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.prototype.wc.gwt.client.components.PaperRadioButtonWidget;
import com.vaadin.prototype.wc.server.ui.PaperRadioButtonComponent;
import com.vaadin.shared.ui.Connect;


@SuppressWarnings("serial")
@Connect(PaperRadioButtonComponent.class)
public class PaperRadioButtonConnector extends BaseConnector {

    public IsProperties stateProperties() {
                getState().checked = getWidget().checked();
                getState().label = getWidget().label();
                getState().toggles = getWidget().toggles();
                getState().disabled = getWidget().disabled();


      IsProperties p = super.stateProperties();
    p.set("checked", getState().checked);
    p.set("label", getState().label);
    p.set("toggles", getState().toggles);
    p.set("disabled", getState().disabled);

      return p;
    }

    @Override
    public PaperRadioButtonWidget getWidget() {
        return (PaperRadioButtonWidget) super.getWidget();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);
            getWidget().checked(getState().checked);
            getWidget().label(getState().label);
            getWidget().toggles(getState().toggles);
            getWidget().disabled(getState().disabled);

    }

    @Override
    public PaperRadioButtonState getState() {
        return (PaperRadioButtonState)super.getState();
    }
}
