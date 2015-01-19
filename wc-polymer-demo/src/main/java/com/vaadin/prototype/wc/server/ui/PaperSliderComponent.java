package com.vaadin.prototype.wc.server.ui;

import com.vaadin.prototype.wc.gwt.client.ui.PaperSliderState;
import com.vaadin.ui.AbstractComponent;

@SuppressWarnings("serial")
public class PaperSliderComponent extends CoreRangeComponent {
    protected String[] events() {
      return concat(super.events(), new String[]{"change"});
    }

    protected String[] attributes() {
      return concat(super.attributes(),new String[]{"snaps","pin","disabled","secondaryProgress","editable","immediateValue"});
    }

    @Override
    protected PaperSliderState getState() {
        return (PaperSliderState) super.getState();
    }


  /**
   * If true, the slider thumb snaps to tick marks evenly spaced based
   * on the `step` property value.
   *
   * @attribute snaps
   * @type boolean
   */
    public void snaps(boolean val) {
        getState().snaps = val;
    }

  /**
   * If true, the slider thumb snaps to tick marks evenly spaced based
   * on the `step` property value.
   *
   * @attribute snaps
   * @type boolean
   */
    public boolean snaps() {
        return getState().snaps;
    }

  /**
   * If true, a pin with numeric value label is shown when the slider thumb 
   * is pressed.  Use for settings for which users need to know the exact 
   * value of the setting.
   *
   * @attribute pin
   * @type boolean
   */
    public void pin(boolean val) {
        getState().pin = val;
    }

  /**
   * If true, a pin with numeric value label is shown when the slider thumb 
   * is pressed.  Use for settings for which users need to know the exact 
   * value of the setting.
   *
   * @attribute pin
   * @type boolean
   */
    public boolean pin() {
        return getState().pin;
    }

  /**
   * If true, this slider is disabled.  A disabled slider cannot be tapped
   * or dragged to change the slider value.
   *
   * @attribute disabled
   * @type boolean
   */
    public void disabled(boolean val) {
        getState().disabled = val;
    }

  /**
   * If true, this slider is disabled.  A disabled slider cannot be tapped
   * or dragged to change the slider value.
   *
   * @attribute disabled
   * @type boolean
   */
    public boolean disabled() {
        return getState().disabled;
    }

  /**
   * The number that represents the current secondary progress.
   *
   * @attribute secondaryProgress
   * @type number
   */
    public void secondaryProgress(double val) {
        getState().secondaryProgress = val;
    }

  /**
   * The number that represents the current secondary progress.
   *
   * @attribute secondaryProgress
   * @type number
   */
    public double secondaryProgress() {
        return getState().secondaryProgress;
    }

  /**
   * If true, an input is shown and user can use it to set the slider value.
   *
   * @attribute editable
   * @type boolean
   */
    public void editable(boolean val) {
        getState().editable = val;
    }

  /**
   * If true, an input is shown and user can use it to set the slider value.
   *
   * @attribute editable
   * @type boolean
   */
    public boolean editable() {
        return getState().editable;
    }

  /**
   * The immediate value of the slider.  This value is updated while the user
   * is dragging the slider.
   *
   * @attribute immediateValue
   * @type number
   */
    public void immediateValue(double val) {
        getState().immediateValue = val;
    }

  /**
   * The immediate value of the slider.  This value is updated while the user
   * is dragging the slider.
   *
   * @attribute immediateValue
   * @type number
   */
    public double immediateValue() {
        return getState().immediateValue;
    }

}