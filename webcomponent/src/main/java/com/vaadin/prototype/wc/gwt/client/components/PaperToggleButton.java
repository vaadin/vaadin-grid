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
public interface PaperToggleButton {

  /**
   * Fired when the checked state changes.
   *
   * @event change
   */
  void addEventListener(String event, EventListener listener);

  /**
   * Gets or sets the state, `true` is checked and `false` is unchecked.
   *
   * @attribute checked
   * @type boolean
   * @default false
   */
  @JsProperty PaperToggleButton checked(boolean val);
  @JsProperty boolean checked();
}
