package com.vaadin.prototype.webcomponentwrapper.element;

@FunctionalInterface
public interface ElementProcessor {
    void processNode(Element element);
}
