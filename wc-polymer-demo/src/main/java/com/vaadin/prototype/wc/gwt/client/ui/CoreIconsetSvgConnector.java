package com.vaadin.prototype.wc.gwt.client.ui;

import com.google.gwt.query.client.IsProperties;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.prototype.wc.gwt.client.components.CoreIconsetSvgWidget;
import com.vaadin.prototype.wc.server.ui.CoreIconsetSvgComponent;
import com.vaadin.shared.ui.Connect;


@SuppressWarnings("serial")
@Connect(CoreIconsetSvgComponent.class)
public class CoreIconsetSvgConnector extends CoreMetaConnector {

    public IsProperties stateProperties() {
                getState().iconSize = getWidget().iconSize();


      IsProperties p = super.stateProperties();
    p.set("iconSize", getState().iconSize);

      return p;
    }

    @Override
    public CoreIconsetSvgWidget getWidget() {
        return (CoreIconsetSvgWidget) super.getWidget();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);
            getWidget().iconSize(getState().iconSize);

    }

    @Override
    public CoreIconsetSvgState getState() {
        return (CoreIconsetSvgState)super.getState();
    }
}
