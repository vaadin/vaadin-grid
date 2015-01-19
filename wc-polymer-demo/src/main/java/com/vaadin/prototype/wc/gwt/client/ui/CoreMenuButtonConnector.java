package com.vaadin.prototype.wc.gwt.client.ui;

import com.google.gwt.query.client.IsProperties;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.prototype.wc.gwt.client.components.CoreMenuButtonWidget;
import com.vaadin.prototype.wc.server.ui.CoreMenuButtonComponent;
import com.vaadin.shared.ui.Connect;


@SuppressWarnings("serial")
@Connect(CoreMenuButtonComponent.class)
public class CoreMenuButtonConnector extends BaseConnector {

    public IsProperties stateProperties() {
                getState().icon = getWidget().icon();
                getState().selected = getWidget().selected();
                getState().opened = getWidget().opened();
                getState().inlineMenu = getWidget().inlineMenu();
                getState().halign = getWidget().halign();
                getState().valign = getWidget().valign();


      IsProperties p = super.stateProperties();
    p.set("icon", getState().icon);
    p.set("selected", getState().selected);
    p.set("opened", getState().opened);
    p.set("inlineMenu", getState().inlineMenu);
    p.set("halign", getState().halign);
    p.set("valign", getState().valign);

      return p;
    }

    @Override
    public CoreMenuButtonWidget getWidget() {
        return (CoreMenuButtonWidget) super.getWidget();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);
            getWidget().icon(getState().icon);
            getWidget().selected(getState().selected);
            getWidget().opened(getState().opened);
            getWidget().inlineMenu(getState().inlineMenu);
            getWidget().halign(getState().halign);
            getWidget().valign(getState().valign);

    }

    @Override
    public CoreMenuButtonState getState() {
        return (CoreMenuButtonState)super.getState();
    }
}
