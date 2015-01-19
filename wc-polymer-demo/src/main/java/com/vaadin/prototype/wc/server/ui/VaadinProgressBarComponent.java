package com.vaadin.prototype.wc.server.ui;

import com.vaadin.prototype.wc.gwt.client.ui.VaadinProgressBarState;
import com.vaadin.ui.AbstractComponent;

@SuppressWarnings("serial")
public class VaadinProgressBarComponent extends BaseComponent {
    protected String[] events() {
      return concat(super.events(), new String[]{"change"});
    }

    protected String[] attributes() {
      return concat(super.attributes(),new String[]{"value","theme"});
    }

    @Override
    protected VaadinProgressBarState getState() {
        return (VaadinProgressBarState) super.getState();
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