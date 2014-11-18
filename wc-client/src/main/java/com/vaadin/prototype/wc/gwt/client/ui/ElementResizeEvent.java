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

import com.google.gwt.dom.client.Element;

/**
 * An event that is fired after an element has resized.
 *
 * @since
 * @author Vaadin Ltd
 */
public class ElementResizeEvent {

    private Element element;

    private float previousWidth;
    private float previousHeight;

    private float currentWidth;
    private float currentHeight;

    /**
     * Returns the element that has resized.
     *
     * @return the resized element
     */
    public Element getElement() {
        return element;
    }

    /**
     * Returns the width of the element before the resize.
     *
     * @return the previous width
     */
    public float getPreviousWidth() {
        return previousWidth;
    }

    /**
     * Returns the height of the element before the resize.
     *
     * @return the previous height
     */
    public float getPreviousHeight() {
        return previousHeight;
    }

    /**
     * Returns the current width of the element.
     *
     * @return the new width
     */
    public float getCurrentWidth() {
        return currentWidth;
    }

    /**
     * Returns the current height of the element.
     *
     * @return the new height
     */
    public float getCurrentHeight() {
        return currentHeight;
    }

    public ElementResizeEvent(Element element, float previousWidth,
            float previousHeight, float currentWidth, float currentHeight) {
        this.element = element;
        this.previousWidth = previousWidth;
        this.previousHeight = previousHeight;
        this.currentWidth = currentWidth;
        this.currentHeight = currentHeight;
    }
}
