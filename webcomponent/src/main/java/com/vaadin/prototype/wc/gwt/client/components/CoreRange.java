package com.vaadin.prototype.wc.gwt.client.components;

import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.core.client.js.JsArray;
import com.google.gwt.core.client.js.JsObject;
import com.google.gwt.dom.client.BodyElement;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.HeadElement;
import com.google.gwt.user.client.EventListener;
import com.vaadin.prototype.wc.gwt.client.html.*;

@JsType(prototype = "HTMLElement", isNative = true)
public interface CoreRange extends HTMLElement {

  Class<?>[] dependencies = new Class<?>[]{};

  /**
   * The number that represents the current value.
   *
   * @attribute value
   * @type number
   * @default 0
   */
  @JsProperty CoreRange value(double val);
  @JsProperty double value();

  /**
   * The number that indicates the minimum value of the range.
   *
   * @attribute min
   * @type number
   * @default 0
   */
  @JsProperty CoreRange min(double val);
  @JsProperty double min();

  /**
   * The number that indicates the maximum value of the range.
   *
   * @attribute max
   * @type number
   * @default 100
   */
  @JsProperty CoreRange max(double val);
  @JsProperty double max();

  /**
   * Specifies the value granularity of the range's value.
   *
   * @attribute step
   * @type number
   * @default 1
   */
  @JsProperty CoreRange step(double val);
  @JsProperty double step();

  /**
   * Returns the ratio of the value.
   *
   * @attribute ratio
   * @type number
   * @default 0
   */
  @JsProperty CoreRange ratio(double val);
  @JsProperty double ratio();
}
