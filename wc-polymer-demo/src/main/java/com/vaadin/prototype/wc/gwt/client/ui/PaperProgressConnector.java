package com.vaadin.prototype.wc.gwt.client.ui;

import com.google.gwt.query.client.IsProperties;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.prototype.wc.gwt.client.components.PaperProgressWidget;
import com.vaadin.prototype.wc.server.ui.PaperProgressComponent;
import com.vaadin.shared.ui.Connect;


@SuppressWarnings("serial")
@Connect(PaperProgressComponent.class)
public class PaperProgressConnector extends CoreRangeConnector {

    public IsProperties stateProperties() {
                getState().secondaryProgress = getWidget().secondaryProgress();


      IsProperties p = super.stateProperties();
    p.set("secondaryProgress", getState().secondaryProgress);

      return p;
    }

    @Override
    public PaperProgressWidget getWidget() {
        return (PaperProgressWidget) super.getWidget();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);
            getWidget().secondaryProgress(getState().secondaryProgress);

    }

    @Override
    public PaperProgressState getState() {
        return (PaperProgressState)super.getState();
    }
}
