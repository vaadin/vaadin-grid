package com.vaadin.prototype.wc.gwt.client.ui;

import com.vaadin.shared.communication.ServerRpc;

public interface EventServerRpc extends ServerRpc {
    void stateChanged(String eventName, String json);
}
