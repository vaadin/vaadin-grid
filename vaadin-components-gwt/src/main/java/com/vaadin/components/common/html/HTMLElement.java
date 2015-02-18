package com.vaadin.components.common.html;

import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.user.client.EventListener;

@JsType(prototype = "HTMLElement")
public interface HTMLElement {

    // FIXME: Creating prototypes by hand because @JsExport causes:
    // Uncaught ReferenceError: $clinit_HTMLElement$Prototype is not defined
    // @PrototypeOfJsType
    public static class Prototype implements HTMLElement {
        private HTMLElement htmlElement() {
            return (HTMLElement) this;
        }
        @Override public void addEventListener(String event, EventListener listener) {
            htmlElement().addEventListener(event, listener);
        }
        @Override public void appendChild(HTMLElement element) {
            htmlElement().appendChild(element);
        }
        @Override public void setAttribute(String name, String value) {
            htmlElement().setAttribute(name, value);
        }
        @Override public String getAttribute(String name) {
            return htmlElement().getAttribute(name);
        }
        @Override public HTMLShadow createShadowRoot() {
            return htmlElement().createShadowRoot();
        }
        @Override public Node shadowRoot() {
            return htmlElement().shadowRoot();
        }
        @Override public String className() {
            return htmlElement().className();
        }
        @Override public HTMLElement className(String string) {
            return htmlElement().className(string);
        }
        @Override public HTMLStyle style() {
            return htmlElement().style();
        }
        @Override public void innerHTML(String string) {
            htmlElement().innerHTML(string);
        }
        @Override public void innerText(String string) {
            htmlElement().innerText(string);
        }
        @Override
        public void dispatchEvent(HTMLEvents ev) {
            htmlElement().dispatchEvent(ev);
        }
        @Override
        public NodeList<?> children() {
            return htmlElement().children();
        }
        @Override
        public String innerText() {
            return htmlElement().innerText();
        }
    }

    public interface LifeCycle {
        /**
         * fires when an instance of the element is created.
         */
        public interface Created {
            void createdCallback();
        }
        /**
         * fires when injected into the document.
         */
        public interface Attached {
            void attachedCallback();
        }
        /**
         * fires when removed from the document.
         */
        public interface Dettached {
            void detachedCallback();
        }
        /**
         * fires when an attribute is added, removed, or changed.
         */
        public interface Changed {
            void attributeChangedCallback();
        }
    }

    void addEventListener(String event, EventListener listener);
    void dispatchEvent(HTMLEvents ev);
    void appendChild(HTMLElement element);
    void setAttribute(String name, String value);
    String getAttribute(String name);
    HTMLShadow createShadowRoot();
    @JsProperty Node shadowRoot();
    @JsProperty String className();
    @JsProperty HTMLElement className(String string);
    @JsProperty HTMLStyle style();
    @JsProperty void innerHTML(String string);
    @JsProperty void innerText(String string);
    @JsProperty String innerText();
    @JsProperty NodeList<?> children();
}
