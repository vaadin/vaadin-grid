package com.vaadin.prototype.wc.gwt.client.components;

import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.core.client.js.JsArray;
import com.google.gwt.core.client.js.JsObject;
import com.google.gwt.dom.client.BodyElement;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.HeadElement;
import com.google.gwt.user.client.EventListener;

@JsType(prototype = "HTMLElement", isNative = true)
public interface PaperSlider extends CoreRange {

  /**
   * Fired when the slider's value changes.
   *
   * @event change
   */
  void addEventListener(String event, EventListener listener);

  /**
   * If true, the slider thumb snaps to tick marks evenly spaced based
   * on the `step` property value.
   *
   * @attribute snaps
   * @type boolean
   * @default false
   */
  @JsProperty PaperSlider snaps(boolean val);
  @JsProperty boolean snaps();

  /**
   * If true, a pin with numeric value label is shown when the slider thumb 
   * is pressed.  Use for settings for which users need to know the exact 
   * value of the setting.
   *
   * @attribute pin
   * @type boolean
   * @default false
   */
  @JsProperty PaperSlider pin(boolean val);
  @JsProperty boolean pin();

  /**
   * If true, this slider is disabled.  A disabled slider cannot be tapped
   * or dragged to change the slider value.
   *
   * @attribute disabled
   * @type boolean
   * @default false
   */
  @JsProperty PaperSlider disabled(boolean val);
  @JsProperty boolean disabled();

  /**
   * The number that represents the current secondary progress.
   *
   * @attribute secondaryProgress
   * @type number
   * @default 0
   */
  @JsProperty PaperSlider secondaryProgress(double val);
  @JsProperty double secondaryProgress();

  /**
   * If true, an input is shown and user can use it to set the slider value.
   *
   * @attribute editable
   * @type boolean
   * @default false
   */
  @JsProperty PaperSlider editable(boolean val);
  @JsProperty boolean editable();

  /**
   * The immediate value of the slider.  This value is updated while the user
   * is dragging the slider.
   *
   * @attribute immediateValue
   * @type number
   * @default 0
   */
  @JsProperty PaperSlider immediateValue(double val);
  @JsProperty double immediateValue();
}
