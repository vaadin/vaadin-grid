package com.vaadin.prototype.wc.gwt.client.ui;

import com.google.gwt.query.client.IsProperties;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.prototype.wc.gwt.client.components.CoreSplitterWidget;
import com.vaadin.prototype.wc.server.ui.CoreSplitterComponent;
import com.vaadin.shared.ui.Connect;


@SuppressWarnings("serial")
@Connect(CoreSplitterComponent.class)
public class CoreSplitterConnector extends BaseConnector {

    public IsProperties stateProperties() {
                getState().direction = getWidget().direction();
                getState().minSize = getWidget().minSize();
                getState().locked = getWidget().locked();
                getState().allowOverflow = getWidget().allowOverflow();

      
      IsProperties p = super.stateProperties();
    p.set("direction", getState().direction);
    p.set("minSize", getState().minSize);
    p.set("locked", getState().locked);
    p.set("allowOverflow", getState().allowOverflow);

      return p;
    }

    @Override
    public CoreSplitterWidget getWidget() {
        return (CoreSplitterWidget) super.getWidget();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);
            getWidget().direction(getState().direction);
            getWidget().minSize(getState().minSize);
            getWidget().locked(getState().locked);
            getWidget().allowOverflow(getState().allowOverflow);

    }

    @Override
    public CoreSplitterState getState() {
        return (CoreSplitterState)super.getState();
    }
}
