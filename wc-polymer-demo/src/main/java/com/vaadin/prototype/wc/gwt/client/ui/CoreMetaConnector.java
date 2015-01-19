package com.vaadin.prototype.wc.gwt.client.ui;

import com.google.gwt.query.client.IsProperties;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.prototype.wc.gwt.client.components.CoreMetaWidget;
import com.vaadin.prototype.wc.server.ui.CoreMetaComponent;
import com.vaadin.shared.ui.Connect;


@SuppressWarnings("serial")
@Connect(CoreMetaComponent.class)
public class CoreMetaConnector extends BaseConnector {

    public IsProperties stateProperties() {
                getState().type = getWidget().type();


      IsProperties p = super.stateProperties();
    p.set("type", getState().type);

      return p;
    }

    @Override
    public CoreMetaWidget getWidget() {
        return (CoreMetaWidget) super.getWidget();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);
            getWidget().type(getState().type);

    }

    @Override
    public CoreMetaState getState() {
        return (CoreMetaState)super.getState();
    }
}
