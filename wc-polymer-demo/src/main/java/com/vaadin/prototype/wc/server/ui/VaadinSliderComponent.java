package com.vaadin.prototype.wc.server.ui;

import com.vaadin.prototype.wc.gwt.client.ui.VaadinSliderState;
import com.vaadin.ui.AbstractComponent;

@SuppressWarnings("serial")
public class VaadinSliderComponent extends BaseComponent {
    protected String[] events() {
      return concat(super.events(), new String[]{"change"});
    }

    protected String[] attributes() {
      return concat(super.attributes(),new String[]{"value","min","max","theme"});
    }

    @Override
    protected VaadinSliderState getState() {
        return (VaadinSliderState) super.getState();
    }

/**
 * @attribute value
 * @type number
 */
    public void value(double val) {
        getState().value = val;
    }
/**
 * @attribute value
 * @type number
 */
    public double value() {
        return getState().value;
    }
/**
 * @attribute min
 * @type number
 */
    public void min(double val) {
        getState().min = val;
    }
/**
 * @attribute min
 * @type number
 */
    public double min() {
        return getState().min;
    }
/**
 * @attribute max
 * @type number
 */
    public void max(double val) {
        getState().max = val;
    }
/**
 * @attribute max
 * @type number
 */
    public double max() {
        return getState().max;
    }
/**
 * @attribute theme
 * @type string
 */
    public void theme(String val) {
        getState().theme = val;
    }
/**
 * @attribute theme
 * @type string
 */
    public String theme() {
        return getState().theme;
    }

}