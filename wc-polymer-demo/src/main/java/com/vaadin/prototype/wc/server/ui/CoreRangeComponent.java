package com.vaadin.prototype.wc.server.ui;

import com.vaadin.prototype.wc.gwt.client.ui.CoreRangeState;
import com.vaadin.ui.AbstractComponent;

@SuppressWarnings("serial")
public class CoreRangeComponent extends BaseComponent {
    protected String[] events() {
      return concat(super.events(), new String[]{});
    }

    protected String[] attributes() {
      return concat(super.attributes(),new String[]{"value","min","max","step","ratio"});
    }

    @Override
    protected CoreRangeState getState() {
        return (CoreRangeState) super.getState();
    }


  /**
   * The number that represents the current value.
   *
   * @attribute value
   * @type number
   */
    public void value(double val) {
        getState().value = val;
    }

  /**
   * The number that represents the current value.
   *
   * @attribute value
   * @type number
   */
    public double value() {
        return getState().value;
    }

  /**
   * The number that indicates the minimum value of the range.
   *
   * @attribute min
   * @type number
   */
    public void min(double val) {
        getState().min = val;
    }

  /**
   * The number that indicates the minimum value of the range.
   *
   * @attribute min
   * @type number
   */
    public double min() {
        return getState().min;
    }

  /**
   * The number that indicates the maximum value of the range.
   *
   * @attribute max
   * @type number
   */
    public void max(double val) {
        getState().max = val;
    }

  /**
   * The number that indicates the maximum value of the range.
   *
   * @attribute max
   * @type number
   */
    public double max() {
        return getState().max;
    }

  /**
   * Specifies the value granularity of the range's value.
   *
   * @attribute step
   * @type number
   */
    public void step(double val) {
        getState().step = val;
    }

  /**
   * Specifies the value granularity of the range's value.
   *
   * @attribute step
   * @type number
   */
    public double step() {
        return getState().step;
    }

  /**
   * Returns the ratio of the value.
   *
   * @attribute ratio
   * @type number
   */
    public void ratio(double val) {
        getState().ratio = val;
    }

  /**
   * Returns the ratio of the value.
   *
   * @attribute ratio
   * @type number
   */
    public double ratio() {
        return getState().ratio;
    }

}