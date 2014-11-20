package com.vaadin.prototype.webcomponentwrapper.element;

import java.util.Collection;
import java.util.EventListener;
import java.util.List;

import com.vaadin.shared.communication.ServerRpc;
import com.vaadin.ui.JavaScriptFunction;

public interface Element extends Node {

    public String getTag();

    public void setAttribute(String name, String value);

    public void setAttribute(String name, boolean value);

    public Collection<String> getAttributeNames();

    public String getAttribute(String name);

    public boolean hasAttribute(String name);

    public void removeAttribute(String name);

    public void setInnerText(String text);

    public void setInnerHtml(String html);

    public void eval(String script, Object... arguments);

    public void addEventListener(String eventName, JavaScriptFunction listener,
            String... arguments);

    public void addEventListener(EventListener listener);

    public void addEventListener(ServerRpc rpc);
    
    public List<Node> getShadowDOMNodes();

}