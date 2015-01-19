package com.vaadin.prototype.wc.gwt.client.ui;

import com.google.gwt.query.client.IsProperties;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.prototype.wc.gwt.client.components.CoreInputWidget;
import com.vaadin.prototype.wc.server.ui.CoreInputComponent;
import com.vaadin.shared.ui.Connect;


@SuppressWarnings("serial")
@Connect(CoreInputComponent.class)
public class CoreInputConnector extends BaseConnector {

    public IsProperties stateProperties() {
                getState().placeholder = getWidget().placeholder();
                getState().disabled = getWidget().disabled();
                getState().type = getWidget().type();
                getState().multiline = getWidget().multiline();
                getState().rows = getWidget().rows();
                getState().inputValue = getWidget().inputValue();
                getState().value = getWidget().value();
                getState().validate = getWidget().validate();
                getState().invalid = getWidget().invalid();


      IsProperties p = super.stateProperties();
    p.set("placeholder", getState().placeholder);
    p.set("disabled", getState().disabled);
    p.set("type", getState().type);
    p.set("multiline", getState().multiline);
    p.set("rows", getState().rows);
    p.set("inputValue", getState().inputValue);
    p.set("value", getState().value);
    p.set("validate", getState().validate);
    p.set("invalid", getState().invalid);

      return p;
    }

    @Override
    public CoreInputWidget getWidget() {
        return (CoreInputWidget) super.getWidget();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);
            getWidget().placeholder(getState().placeholder);
            getWidget().disabled(getState().disabled);
            getWidget().type(getState().type);
            getWidget().multiline(getState().multiline);
            getWidget().rows(getState().rows);
            getWidget().inputValue(getState().inputValue);
            getWidget().value(getState().value);
            getWidget().validate(getState().validate);
            getWidget().invalid(getState().invalid);

    }

    @Override
    public CoreInputState getState() {
        return (CoreInputState)super.getState();
    }
}
