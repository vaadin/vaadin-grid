package com.vaadin.prototype.wc.gwt.client.ui;

import com.google.gwt.query.client.IsProperties;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.prototype.wc.gwt.client.components.PaperItemWidget;
import com.vaadin.prototype.wc.server.ui.PaperItemComponent;
import com.vaadin.shared.ui.Connect;


@SuppressWarnings("serial")
@Connect(PaperItemComponent.class)
public class PaperItemConnector extends BaseConnector {

    public IsProperties stateProperties() {
                getState().label = getWidget().label();
                getState().iconSrc = getWidget().iconSrc();
                getState().icon = getWidget().icon();

      
      IsProperties p = super.stateProperties();
    p.set("label", getState().label);
    p.set("iconSrc", getState().iconSrc);
    p.set("icon", getState().icon);

      return p;
    }

    @Override
    public PaperItemWidget getWidget() {
        return (PaperItemWidget) super.getWidget();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);
            getWidget().label(getState().label);
            getWidget().iconSrc(getState().iconSrc);
            getWidget().icon(getState().icon);

    }

    @Override
    public PaperItemState getState() {
        return (PaperItemState)super.getState();
    }
}
