package com.vaadin.prototype.wc.gwt.client.ui;

import com.google.gwt.query.client.IsProperties;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.prototype.wc.gwt.client.components.CoreTooltipWidget;
import com.vaadin.prototype.wc.server.ui.CoreTooltipComponent;
import com.vaadin.shared.ui.Connect;


@SuppressWarnings("serial")
@Connect(CoreTooltipComponent.class)
public class CoreTooltipConnector extends BaseConnector {

    public IsProperties stateProperties() {
                getState().label = getWidget().label();
                getState().show = getWidget().show();
                getState().position = getWidget().position();
                getState().noarrow = getWidget().noarrow();
                getState().tipAttribute = getWidget().tipAttribute();

      
      IsProperties p = super.stateProperties();
    p.set("label", getState().label);
    p.set("show", getState().show);
    p.set("position", getState().position);
    p.set("noarrow", getState().noarrow);
    p.set("tipAttribute", getState().tipAttribute);

      return p;
    }

    @Override
    public CoreTooltipWidget getWidget() {
        return (CoreTooltipWidget) super.getWidget();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);
            getWidget().label(getState().label);
            getWidget().show(getState().show);
            getWidget().position(getState().position);
            getWidget().noarrow(getState().noarrow);
            getWidget().tipAttribute(getState().tipAttribute);

    }

    @Override
    public CoreTooltipState getState() {
        return (CoreTooltipState)super.getState();
    }
}
