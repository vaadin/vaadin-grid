package com.vaadin.prototype.wc.gwt.client.ui;

import com.google.gwt.query.client.IsProperties;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.prototype.wc.gwt.client.components.PaperInputWidget;
import com.vaadin.prototype.wc.server.ui.PaperInputComponent;
import com.vaadin.shared.ui.Connect;


@SuppressWarnings("serial")
@Connect(PaperInputComponent.class)
public class PaperInputConnector extends CoreInputConnector {

    public IsProperties stateProperties() {
                getState().label = getWidget().label();
                getState().floatingLabel = getWidget().floatingLabel();
                getState().maxRows = getWidget().maxRows();

      
      IsProperties p = super.stateProperties();
    p.set("label", getState().label);
    p.set("floatingLabel", getState().floatingLabel);
    p.set("maxRows", getState().maxRows);

      return p;
    }

    @Override
    public PaperInputWidget getWidget() {
        return (PaperInputWidget) super.getWidget();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);
            getWidget().label(getState().label);
            getWidget().floatingLabel(getState().floatingLabel);
            getWidget().maxRows(getState().maxRows);

    }

    @Override
    public PaperInputState getState() {
        return (PaperInputState)super.getState();
    }
}
