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
public interface PaperRadioButton extends HTMLElement {

  Class<?>[] dependencies = new Class<?>[]{PaperRipple.class};

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
  @JsProperty PaperRadioButton checked(boolean val);
  @JsProperty boolean checked();

  /**
   * The label for the radio button.
   *
   * @attribute label
   * @type string
   * @default ''
   */
  @JsProperty PaperRadioButton label(String val);
  @JsProperty String label();

  /**
   * Normally the user cannot uncheck the radio button by tapping once
   * checked.  Setting this property to `true` makes the radio button
   * toggleable from checked to unchecked.
   *
   * @attribute toggles
   * @type boolean
   * @default false
   */
  @JsProperty PaperRadioButton toggles(boolean val);
  @JsProperty boolean toggles();

  /**
   * If true, the user cannot interact with this element.
   *
   * @attribute disabled
   * @type boolean
   * @default false
   */
  @JsProperty PaperRadioButton disabled(boolean val);
  @JsProperty boolean disabled();
}
