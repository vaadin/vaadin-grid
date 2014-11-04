package com.vaadin.prototype.webcomponentwrapper.element;

public class TextNode extends NodeImpl {

    private String text;

    public TextNode(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String asHtml() {
        // TODO html encode?
        return text;
    }

}
