package com.vaadin.prototype.wc.gwt.client.ui;

import com.google.gwt.query.client.IsProperties;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.prototype.wc.gwt.client.components.PaperSliderWidget;
import com.vaadin.prototype.wc.server.ui.PaperSliderComponent;
import com.vaadin.shared.ui.Connect;


@SuppressWarnings("serial")
@Connect(PaperSliderComponent.class)
public class PaperSliderConnector extends CoreRangeConnector {

    public IsProperties stateProperties() {
                getState().snaps = getWidget().snaps();
                getState().pin = getWidget().pin();
                getState().disabled = getWidget().disabled();
                getState().secondaryProgress = getWidget().secondaryProgress();
                getState().editable = getWidget().editable();
                getState().immediateValue = getWidget().immediateValue();

      
      IsProperties p = super.stateProperties();
    p.set("snaps", getState().snaps);
    p.set("pin", getState().pin);
    p.set("disabled", getState().disabled);
    p.set("secondaryProgress", getState().secondaryProgress);
    p.set("editable", getState().editable);
    p.set("immediateValue", getState().immediateValue);

      return p;
    }

    @Override
    public PaperSliderWidget getWidget() {
        return (PaperSliderWidget) super.getWidget();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);
            getWidget().snaps(getState().snaps);
            getWidget().pin(getState().pin);
            getWidget().disabled(getState().disabled);
            getWidget().secondaryProgress(getState().secondaryProgress);
            getWidget().editable(getState().editable);
            getWidget().immediateValue(getState().immediateValue);

    }

    @Override
    public PaperSliderState getState() {
        return (PaperSliderState)super.getState();
    }
}
