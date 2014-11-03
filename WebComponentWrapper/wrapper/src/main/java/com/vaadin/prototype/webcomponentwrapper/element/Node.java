package com.vaadin.prototype.webcomponentwrapper.element;

import java.util.List;

public interface Node {

    public void appendChild(Node child);

    public Node getParent();

    public void setParent(Node parent);

    public List<Node> getChildren();

    public void removeAllChildren();

    public void removeChild(Node child);

    public Document getDocument();

    public String asHtml();
}