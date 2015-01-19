package com.vaadin.prototype.wc.gwt.client.ui;

import com.google.gwt.query.client.IsProperties;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.prototype.wc.gwt.client.components.CoreSelectorWidget;
import com.vaadin.prototype.wc.server.ui.CoreSelectorComponent;
import com.vaadin.shared.ui.Connect;


@SuppressWarnings("serial")
@Connect(CoreSelectorComponent.class)
public class CoreSelectorConnector extends BaseConnector {

    public IsProperties stateProperties() {
                getState().multi = getWidget().multi();
                getState().valueattr = getWidget().valueattr();
                getState().selectedClass = getWidget().selectedClass();
                getState().selectedProperty = getWidget().selectedProperty();
                getState().selectedAttribute = getWidget().selectedAttribute();
                getState().selectedIndex = getWidget().selectedIndex();
                getState().itemSelector = getWidget().itemSelector();
                getState().activateEvent = getWidget().activateEvent();
                getState().notap = getWidget().notap();


      IsProperties p = super.stateProperties();
    p.set("multi", getState().multi);
    p.set("valueattr", getState().valueattr);
    p.set("selectedClass", getState().selectedClass);
    p.set("selectedProperty", getState().selectedProperty);
    p.set("selectedAttribute", getState().selectedAttribute);
    p.set("selectedIndex", getState().selectedIndex);
    p.set("itemSelector", getState().itemSelector);
    p.set("activateEvent", getState().activateEvent);
    p.set("notap", getState().notap);

      return p;
    }

    @Override
    public CoreSelectorWidget getWidget() {
        return (CoreSelectorWidget) super.getWidget();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);
            getWidget().multi(getState().multi);
            getWidget().valueattr(getState().valueattr);
            getWidget().selectedClass(getState().selectedClass);
            getWidget().selectedProperty(getState().selectedProperty);
            getWidget().selectedAttribute(getState().selectedAttribute);
            getWidget().selectedIndex(getState().selectedIndex);
            getWidget().itemSelector(getState().itemSelector);
            getWidget().activateEvent(getState().activateEvent);
            getWidget().notap(getState().notap);

    }

    @Override
    public CoreSelectorState getState() {
        return (CoreSelectorState)super.getState();
    }
}
