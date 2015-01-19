package com.vaadin.prototype.wc.gwt.client.ui;

import com.google.gwt.query.client.IsProperties;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.prototype.wc.gwt.client.components.CoreCollapseWidget;
import com.vaadin.prototype.wc.server.ui.CoreCollapseComponent;
import com.vaadin.shared.ui.Connect;


@SuppressWarnings("serial")
@Connect(CoreCollapseComponent.class)
public class CoreCollapseConnector extends BaseConnector {

    public IsProperties stateProperties() {
                getState().horizontal = getWidget().horizontal();
                getState().opened = getWidget().opened();
                getState().duration = getWidget().duration();
                getState().fixedSize = getWidget().fixedSize();


      IsProperties p = super.stateProperties();
    p.set("horizontal", getState().horizontal);
    p.set("opened", getState().opened);
    p.set("duration", getState().duration);
    p.set("fixedSize", getState().fixedSize);

      return p;
    }

    @Override
    public CoreCollapseWidget getWidget() {
        return (CoreCollapseWidget) super.getWidget();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);
            getWidget().horizontal(getState().horizontal);
            getWidget().opened(getState().opened);
            getWidget().duration(getState().duration);
            getWidget().fixedSize(getState().fixedSize);

    }

    @Override
    public CoreCollapseState getState() {
        return (CoreCollapseState)super.getState();
    }
}
