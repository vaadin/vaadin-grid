/*
 * Copyright 2000-2014 Vaadin Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.vaadin.prototype.wc.gwt.client.ui;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.logging.Logger;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;

/**
 * Manages adding and removing of resize event listeners on elements.
 *
 * @since
 * @author Vaadin Ltd
 */
public class ElementResizeManager {

    private static final Map<Element, Collection<ElementResizeListener>> elementResizeListeners = new HashMap<Element, Collection<ElementResizeListener>>();

    private static final Map<Element, Element> resizeHelpers = new HashMap<Element, Element>();
    private static final String RESIZE_HELPER_CLASS = "v-resize-helper";

    private static final Map<Element, JavaScriptObject> deferHandles = new HashMap<Element, JavaScriptObject>();

    private static final Map<Element, Size> previousSizes = new HashMap<Element, Size>();

    private static final JavaScriptObject scheduleFrame = getScheduleFrame();
    private static final JavaScriptObject cancelFrame = getCancelFrame();

    /**
     * Adds a resize listener to an element.
     *
     * Note: if this is the first listener attached to the element, this
     * function alters the DOM. A new DIV with the class "v-resize-helper" is
     * appended to the element.
     *
     * @since
     * @param element
     *            the element whose resizing is being listened for
     * @param listener
     *            the listener which is notified when the element has resized
     */
    public static ElementResizeListener addResizeListener(Element element,
            ElementResizeListener listener) {
        if (element.getStyle().getPosition().equals(Position.STATIC)) {
            element.getStyle().setPosition(Position.RELATIVE);
        }
        Collection<ElementResizeListener> listeners = elementResizeListeners
                .get(element);
        if (listeners == null) {
            listeners = new HashSet<ElementResizeListener>();
            elementResizeListeners.put(element, listeners);
        }
        if (!hasResizeHelper(element)) {
            addResizeHelper(element);
        }
        listeners.add(listener);
        return listener;
    }

    /**
     * Removes a resize listener from an element.
     *
     * Note: if this is the last listener attached to the element, this function
     * alters the DOM. DIV with the class "v-resize-helper" is removed from the
     * element.
     *
     * @since
     * @param element
     * @param listener
     */
    public static void removeResizeListener(Element element,
            ElementResizeListener listener) {
        Collection<ElementResizeListener> listeners = elementResizeListeners
                .get(element);
        // TODO: should this throw an exception if there are no listeners for
        // the element?
        if (listeners != null) {
            listeners.remove(listener);
            if (listeners.size() == 0) {
                removeResizeHelper(element);
                elementResizeListeners.remove(element);
                previousSizes.remove(element);
                resizeHelpers.remove(element);
                deferHandles.remove(element);
            }
        }
    }

    public static boolean hasResizeListener(Element element,
            ElementResizeListener listener) {
        Collection<ElementResizeListener> listeners = elementResizeListeners
                .get(element);
        return listeners != null && listeners.contains(listener);
    }

    /**
     * Adds a resize helper DIV to the element.
     *
     * @since
     * @param element
     *            the element to which the helper DIV is appended
     */
    private static void addResizeHelper(final Element element) {
        Element helper = DOM.createDiv();

        helper.addClassName(RESIZE_HELPER_CLASS);
        Element expandDiv = DOM.createDiv();
        expandDiv.addClassName(RESIZE_HELPER_CLASS + "-expand");
        expandDiv.appendChild(DOM.createDiv());
        helper.appendChild(expandDiv);

        Element contractDiv = DOM.createDiv();
        contractDiv.addClassName(RESIZE_HELPER_CLASS + "-contract");
        helper.appendChild(contractDiv);

        resizeHelpers.put(element, helper);
        element.appendChild(helper);

        new Timer() {

            @Override
            public void run() {
                resetHelpers(element);
                if (getPreviousSize(element) == null) {
                    setPreviousSize(element, new Size(0, 0));
                }
                addScrollListener(element);
            }
        }.schedule(1);
    }

    /**
     * Attempts to remove the resize helper DIV from the element. Fails silently
     * if the element doesn't contain the a helper DIV.
     *
     * @since
     * @param element
     *            the element from which to remove the helper DIV
     */
    private static void removeResizeHelper(Element element) {
        Element helper = findResizeHelper(element);
        if (helper == null) {
            return;
        }

        removeScrollListeners(element);
        helper.removeAllChildren();
        helper.removeFromParent();
    }

    /**
     * Returns the resize helper DIV inside the parameter element, or null if no
     * helper is found.
     *
     * @since
     * @param element
     *            element wherein to search
     * @return the resize helper DIV
     */
    private static Element findResizeHelper(Element element) {
        return resizeHelpers.get(element);
    }

    /**
     * Checks if the element already has a resize helper.
     *
     * @since
     * @param element
     *            the element which might contain a resize helper
     * @return true if the helper DIV was present
     */
    private static boolean hasResizeHelper(Element element) {
        return findResizeHelper(element) != null;
    }

    /**
     * Triggers a resize event on all listeners for the given element.
     *
     * @param element
     *            the element that has resized
     * @param previousSize
     *            an array that contains the previous size of the element in
     *            [width, height] order
     * @param newSize
     *            an array that contains the current size of the element in
     *            [width, height] order
     */
    private static void triggerResize(Element element, Size previousSize,
            Size newSize) {
        Logger.getLogger(ElementResizeManager.class.getName()).info(
                "ElementResizeManager::triggerResize " + element);
        ElementResizeEvent event = new ElementResizeEvent(element,
                previousSize.width, previousSize.height, newSize.width,
                newSize.height);
        for (ElementResizeListener listener : elementResizeListeners
                .get(element)) {
            listener.onElementResize(event);
        }
    }

    /**
     * Creates a JS function that calls the method given as a parameter in the
     * next frame.
     *
     * @since
     * @return a JS function handle
     */
    private static native JavaScriptObject getScheduleFrame()
    /*-{
        var frameFunction = $wnd.requestAnimationFrame
                || $wnd.mozRequestAnimationFrame
                || $wnd.webkitRequestAnimationFrame
                || function(deferredFunction) {
                    return $wnd.setTimeout(deferredFunction, 20);
                };
        return function(deferredFunction) {
            return frameFunction(deferredFunction);
        };
    }-*/;

    /**
     * Creates a JS function that cancels a function deferred to the next frame.
     *
     * @since
     * @return a JS function handle
     */
    private static native JavaScriptObject getCancelFrame()
    /*-{
        var cancelFunction = $wnd.cancelAnimationFrame
                || $wnd.mozCancelAnimationFrame
                || $wnd.webkitCancelAnimationFrame || $wnd.clearTimeout;
        return function(id) {
            return cancelFunction(id);
        };
    }-*/;

    /**
     * Adds a scroll event listener to an element. When the listener is
     * triggered, onElementScroll function is invoked.
     *
     * @since
     * @param element
     *            the element to which attach a scroll listener
     */
    private static native void addScrollListener(Element element)
    /*-{
        element.addEventListener("scroll", @com.vaadin.prototype.wc.gwt.client.ui.ElementResizeManager::onElementScroll(Lcom/google/gwt/core/client/JavaScriptObject;), true);
    }-*/;

    /**
     * Removes the scroll listener added by addScrollListener from the element.
     *
     * @since
     * @param element
     *            the element from which to remove a scroll listener
     */
    private static native void removeScrollListeners(Element element)
    /*-{
        element.removeEventListener("scroll", @com.vaadin.prototype.wc.gwt.client.ui.ElementResizeManager::onElementScroll(Lcom/google/gwt/core/client/JavaScriptObject;));
    }-*/;

    /**
     * Event listener function that is invoked when a resize helper DIV is
     * scrolled.
     *
     * @since
     * @param event
     *            a JS scroll event
     */
    private static native void onElementScroll(JavaScriptObject event)
    /*-{
        console.log("ElementResizemanager::onElementScroll", event);
        var target = event.currentTarget;

        // call ElementResizeManager.resetHelpers(target);
        @com.vaadin.prototype.wc.gwt.client.ui.ElementResizeManager::resetHelpers(Lcom/google/gwt/dom/client/Element;)(target);

        // set deferHandle to ElementResizeManager.getDeferHandle(target);
        var deferHandle = @com.vaadin.prototype.wc.gwt.client.ui.ElementResizeManager::getDeferHandle(Lcom/google/gwt/dom/client/Element;)(target);

        if (deferHandle) {
            // call ElementResizeManager.cancelFrame(deferHandle)
            var cancelFrameFunction = @com.vaadin.prototype.wc.gwt.client.ui.ElementResizeManager::cancelFrame;
            cancelFrameFunction(deferHandle);
        }
        // set deferHandle to ElementResizeManager.scheduleFrame(ElementResizeManager.handleDeferred,
        //      target);
        var scheduleFrameFunction = @com.vaadin.prototype.wc.gwt.client.ui.ElementResizeManager::scheduleFrame;
        deferHandle = scheduleFrameFunction(function() {
            // set previousSize to ElementResizeManager.getPreviousSize(element);
            var previousSize = @com.vaadin.prototype.wc.gwt.client.ui.ElementResizeManager::getPreviousSize(Lcom/google/gwt/dom/client/Element;)(target);
            if (target.offsetWidth != previousSize.width
                    || target.offsetHeight != previousSize.height) {
                var currentSize = @com.vaadin.prototype.wc.gwt.client.ui.ElementResizeManager.Size::new(FF)(target.offsetWidth, target.offsetHeight);
                @com.vaadin.prototype.wc.gwt.client.ui.ElementResizeManager::triggerResize(Lcom/google/gwt/dom/client/Element;Lcom/vaadin/prototype/wc/gwt/client/ui/ElementResizeManager$Size;Lcom/vaadin/prototype/wc/gwt/client/ui/ElementResizeManager$Size;)(target, previousSize, currentSize);
                @com.vaadin.prototype.wc.gwt.client.ui.ElementResizeManager::setPreviousSize(Lcom/google/gwt/dom/client/Element;Lcom/vaadin/prototype/wc/gwt/client/ui/ElementResizeManager$Size;)(target, currentSize);
            }
        });

        // call ElementResizeManager.setDeferHandle(target, deferHandle);
        @com.vaadin.prototype.wc.gwt.client.ui.ElementResizeManager::setDeferHandle(Lcom/google/gwt/dom/client/Element;Lcom/google/gwt/core/client/JavaScriptObject;)(target, deferHandle);
    }-*/;

    /**
     * Resets the helper DIVs in the parameter element.
     *
     * @since
     * @param element
     *            the element whose helpers need to be adjusted
     */
    private static native void resetHelpers(Element element)
    /*-{
        // set helpers to ElementResizeManager.findResizeHelper(element);
        var helpers = @com.vaadin.prototype.wc.gwt.client.ui.ElementResizeManager::findResizeHelper(Lcom/google/gwt/dom/client/Element;)(element);
        var expand = helpers.firstElementChild;
        var contract = helpers.lastElementChild;
        var expandChild = expand.firstElementChild;

        contract.scrollLeft = contract.scrollWidth;
        contract.scrollTop = contract.scrollHeight;

        expandChild.style.width = expand.offsetWidth + 1 + "px";
        expandChild.style.height = expand.offsetHeight + 1 + "px";
        expand.scrollLeft = expand.scrollWidth;
        expand.scrollTop = expand.scrollHeight;
    }-*/;

    /**
     * Saves the object by which a deferred function is identified to an
     * internal map. This handle can be used to cancel a deferred function call.
     *
     * @since
     * @param element
     *            the element this handle is associated with
     * @param handle
     *            the identifier for a deferred function call
     */
    private static void setDeferHandle(Element element, JavaScriptObject handle) {
        deferHandles.put(element, handle);
    }

    /**
     * Fetches the object by which a deferred function is identified. This
     * handle can be used to cancel the deferred function call.
     *
     * @since
     * @param element
     *            the element for which to fetch a deferred call handle
     * @return a JavaScript object used to identify a deferred function call
     */
    private static JavaScriptObject getDeferHandle(Element element) {
        return deferHandles.get(element);
    }

    /**
     * Removes a deferred function handle from the internal map.
     *
     * @since
     * @param element
     *            the element whose function handle to remove
     */
    private static void removeDeferHandle(Element element) {
        deferHandles.remove(element);
    }

    /**
     * Returns an array containing the previous width and size (respectively) of
     * the parameter element.
     *
     * @since
     * @param element
     *            the element whose size is wanted
     * @return an array of width and height of the element
     */
    private static Size getPreviousSize(Element element) {
        return previousSizes.get(element);
    }

    /**
     * Saves a new size for the parameter element.
     *
     * @since
     * @param element
     *            the element whose size has changed
     * @param size
     *            the new size
     */
    private static void setPreviousSize(Element element, Size size) {
        previousSizes.put(element, size);
    }

    private static class Size {

        public float width;
        public float height;

        public Size(float width, float height) {
            this.width = width;
            this.height = height;
        }
    }
}
