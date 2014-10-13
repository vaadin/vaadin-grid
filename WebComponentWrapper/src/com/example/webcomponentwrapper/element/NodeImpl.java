package com.example.webcomponentwrapper.element;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class NodeImpl implements Node {

    private List<Node> children = new ArrayList<>();
    private Node parent;

    @Override
    public void appendChild(Node child) {
        children.add(child);

        Document document = getDocument();
        if (document != null) {
            document.setChildAdded(this, child);
        }
    }

    @Override
    public Node getParent() {
        return parent;
    }

    @Override
    public void setParent(Node parent) {
        this.parent = parent;
    }

    @Override
    public List<Node> getChildren() {
        return Collections.unmodifiableList(children);
    }

    @Override
    public void removeAllChildren() {
        // TODO send all as one job to the client instead of individually
        children.forEach(this::removeChild);
    }

    @Override
    public void removeChild(Node child) {
        assert child.getParent() == this;

        Document document = getDocument();
        if (document != null) {
            document.remove(child);
        }
        children.remove(child);
    }

    @Override
    public Document getDocument() {
        if (parent != null) {
            return parent.getDocument();
        } else {
            return null;
        }
    }

}