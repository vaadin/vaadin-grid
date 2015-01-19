package com.vaadin.prototype.wc.gwt.client.ui;

import com.google.gwt.query.client.IsProperties;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.prototype.wc.gwt.client.components.CoreComponentPageWidget;
import com.vaadin.prototype.wc.server.ui.CoreComponentPageComponent;
import com.vaadin.shared.ui.Connect;


@SuppressWarnings("serial")
@Connect(CoreComponentPageComponent.class)
public class CoreComponentPageConnector extends CoreMetaConnector {

    public IsProperties stateProperties() {
                getState().vertical = getWidget().vertical();
                getState().justify = getWidget().justify();
                getState().align = getWidget().align();
                getState().reverse = getWidget().reverse();
                getState().type = getWidget().type();
                getState().src = getWidget().src();
                getState().width = getWidget().width();
                getState().icons = getWidget().icons();
                getState().iconSize = getWidget().iconSize();
                getState().offsetX = getWidget().offsetX();
                getState().offsetY = getWidget().offsetY();
                getState().size = getWidget().size();
                getState().icon = getWidget().icon();
                getState().active = getWidget().active();
                getState().mode = getWidget().mode();
                getState().tallClass = getWidget().tallClass();
                getState().shadow = getWidget().shadow();
                getState().url = getWidget().url();
                getState().handleAs = getWidget().handleAs();
                getState().auto = getWidget().auto();
                getState().params = getWidget().params();
                getState().method = getWidget().method();
                getState().contentType = getWidget().contentType();
                getState().withCredentials = getWidget().withCredentials();
                getState().multi = getWidget().multi();
                getState().valueattr = getWidget().valueattr();
                getState().selectedClass = getWidget().selectedClass();
                getState().selectedProperty = getWidget().selectedProperty();
                getState().selectedIndex = getWidget().selectedIndex();
                getState().itemSelector = getWidget().itemSelector();
                getState().activateEvent = getWidget().activateEvent();
                getState().notap = getWidget().notap();
                getState().label = getWidget().label();


      IsProperties p = super.stateProperties();
    p.set("vertical", getState().vertical);
    p.set("justify", getState().justify);
    p.set("align", getState().align);
    p.set("reverse", getState().reverse);
    p.set("type", getState().type);
    p.set("src", getState().src);
    p.set("width", getState().width);
    p.set("icons", getState().icons);
    p.set("iconSize", getState().iconSize);
    p.set("offsetX", getState().offsetX);
    p.set("offsetY", getState().offsetY);
    p.set("size", getState().size);
    p.set("icon", getState().icon);
    p.set("active", getState().active);
    p.set("mode", getState().mode);
    p.set("tallClass", getState().tallClass);
    p.set("shadow", getState().shadow);
    p.set("url", getState().url);
    p.set("handleAs", getState().handleAs);
    p.set("auto", getState().auto);
    p.set("params", getState().params);
    p.set("method", getState().method);
    p.set("contentType", getState().contentType);
    p.set("withCredentials", getState().withCredentials);
    p.set("multi", getState().multi);
    p.set("valueattr", getState().valueattr);
    p.set("selectedClass", getState().selectedClass);
    p.set("selectedProperty", getState().selectedProperty);
    p.set("selectedIndex", getState().selectedIndex);
    p.set("itemSelector", getState().itemSelector);
    p.set("activateEvent", getState().activateEvent);
    p.set("notap", getState().notap);
    p.set("label", getState().label);

      return p;
    }

    @Override
    public CoreComponentPageWidget getWidget() {
        return (CoreComponentPageWidget) super.getWidget();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);
            getWidget().vertical(getState().vertical);
            getWidget().justify(getState().justify);
            getWidget().align(getState().align);
            getWidget().reverse(getState().reverse);
            getWidget().type(getState().type);
            getWidget().src(getState().src);
            getWidget().width(getState().width);
            getWidget().icons(getState().icons);
            getWidget().iconSize(getState().iconSize);
            getWidget().offsetX(getState().offsetX);
            getWidget().offsetY(getState().offsetY);
            getWidget().size(getState().size);
            getWidget().icon(getState().icon);
            getWidget().active(getState().active);
            getWidget().mode(getState().mode);
            getWidget().tallClass(getState().tallClass);
            getWidget().shadow(getState().shadow);
            getWidget().url(getState().url);
            getWidget().handleAs(getState().handleAs);
            getWidget().auto(getState().auto);
            getWidget().params(getState().params);
            getWidget().method(getState().method);
            getWidget().contentType(getState().contentType);
            getWidget().withCredentials(getState().withCredentials);
            getWidget().multi(getState().multi);
            getWidget().valueattr(getState().valueattr);
            getWidget().selectedClass(getState().selectedClass);
            getWidget().selectedProperty(getState().selectedProperty);
            getWidget().selectedIndex(getState().selectedIndex);
            getWidget().itemSelector(getState().itemSelector);
            getWidget().activateEvent(getState().activateEvent);
            getWidget().notap(getState().notap);
            getWidget().label(getState().label);

    }

    @Override
    public CoreComponentPageState getState() {
        return (CoreComponentPageState)super.getState();
    }
}
