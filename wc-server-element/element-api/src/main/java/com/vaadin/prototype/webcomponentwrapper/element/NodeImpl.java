package com.vaadin.prototype.webcomponentwrapper.element;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class NodeImpl implements Node {

    private List<Node> children = new ArrayList<>();
    private Node parent;
    private Document document;

    @Override
    public void appendChild(Node child) {
        children.add(child);
        child.setParent(this);

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
            return document;
        }
    }

    //dirty hack to make shadow DOM nodes use the same document
    void setDocument(Document document) {
        if (parent == null) {
            this.document = document;
        }
    }

}