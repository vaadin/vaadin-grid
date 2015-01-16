package com.vaadin.prototype.wc.gwt.client.components;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.BodyElement;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.HeadElement;
import com.google.gwt.user.client.EventListener;
import com.vaadin.prototype.wc.gwt.client.html.*;

@JsType(prototype = "HTMLElement", isNative = true)
public interface CoreCollapse extends HTMLElement  {
  Class<?>[] dependencies = new Class<?>[]{};

  /**
   * Fired when the target element has been resized as a result of the opened
   * state changing.
   *
   * @event core-resize
   */
  void addEventListener(String event, EventListener listener);


  /**
   * The target element.
   *
   * @attribute target
   * @type object
   */
  @JsProperty CoreCollapse target(JavaScriptObject val);
  @JsProperty JavaScriptObject target();

  /**
   * If true, the orientation is horizontal; otherwise is vertical.
   *
   * @attribute horizontal
   * @type boolean
   */
  @JsProperty CoreCollapse horizontal(boolean val);
  @JsProperty boolean horizontal();

  /**
   * Set opened to true to show the collapse element and to false to hide it.
   *
   * @attribute opened
   * @type boolean
   */
  @JsProperty CoreCollapse opened(boolean val);
  @JsProperty boolean opened();

  /**
   * Collapsing/expanding animation duration in second.
   *
   * @attribute duration
   * @type number
   */
  @JsProperty CoreCollapse duration(double val);
  @JsProperty double duration();

  /**
   * If true, the size of the target element is fixed and is set
   * on the element.  Otherwise it will try to
   * use auto to determine the natural size to use
   * for collapsing/expanding.
   *
   * @attribute fixedSize
   * @type boolean
   */
  @JsProperty CoreCollapse fixedSize(boolean val);
  @JsProperty boolean fixedSize();

  /**
   * Toggle the opened state.
   *
   * @method toggle
   */
  void toggle();

}
