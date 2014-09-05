package com.vaadin.prototype.wc.gwt.client.components;

import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.core.client.js.JsArray;
import com.google.gwt.core.client.js.JsObject;
import com.google.gwt.dom.client.BodyElement;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.HeadElement;
import com.google.gwt.user.client.EventListener;

/**
 * @group Paper Elements
 *
 * paper-focusable is a base class for paper elements that can be focused.
 *
 * @element paper-focusable
 * @status beta
 * @homepage github.io
 */
@JsType(prototype = "HTMLElement", isNative = true)
public interface PaperFocusable {

  /**
   * If true, the button is currently active either because the
   * user is holding down the button, or the button is a toggle
   * and is currently in the active state.
   *
   * @attribute active
   * @type boolean
   * @default false
   */
  @JsProperty PaperFocusable active(boolean val);
  @JsProperty boolean active();

  /**
   * If true, the element currently has focus due to keyboard
   * navigation.
   *
   * @attribute focused
   * @type boolean
   * @default false
   */
  @JsProperty PaperFocusable focused(boolean val);
  @JsProperty boolean focused();

  /**
   * If true, the user is currently holding down the button.
   *
   * @attribute pressed
   * @type boolean
   * @default false
   */
  @JsProperty PaperFocusable pressed(boolean val);
  @JsProperty boolean pressed();

  /**
   * If true, the user cannot interact with this element.
   *
   * @attribute disabled
   * @type boolean
   * @default false
   */
  @JsProperty PaperFocusable disabled(boolean val);
  @JsProperty boolean disabled();

  /**
   * If true, the button toggles the active state with each tap.
   * Otherwise, the button becomes active when the user is holding
   * it down.
   *
   * @attribute isToggle
   * @type boolean
   * @default false
   */
  @JsProperty PaperFocusable isToggle(boolean val);
  @JsProperty boolean isToggle();
}
