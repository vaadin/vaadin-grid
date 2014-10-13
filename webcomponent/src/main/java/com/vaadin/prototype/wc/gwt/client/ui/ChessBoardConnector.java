package com.vaadin.prototype.wc.gwt.client.ui;

import com.google.gwt.query.client.IsProperties;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.prototype.wc.gwt.client.components.ChessBoardWidget;
import com.vaadin.prototype.wc.server.ui.ChessBoardComponent;
import com.vaadin.shared.ui.Connect;


@SuppressWarnings("serial")
@Connect(ChessBoardComponent.class)
public class ChessBoardConnector extends BaseConnector {

    public IsProperties stateProperties() {
                getState().fen = getWidget().fen();
                getState().frame = getWidget().frame();

      
      IsProperties p = super.stateProperties();
    p.set("fen", getState().fen);
    p.set("frame", getState().frame);

      return p;
    }

    @Override
    public ChessBoardWidget getWidget() {
        return (ChessBoardWidget) super.getWidget();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);
            getWidget().fen(getState().fen);
            getWidget().frame(getState().frame);

    }

    @Override
    public ChessBoardState getState() {
        return (ChessBoardState)super.getState();
    }
}
