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
public interface CoreIconButton {

  /**
   * The URL of an image for the icon.  Should not use `icon` property
   * if you are using this property.
   *
   * @attribute src
   * @type string
   * @default ''
   */
  @JsProperty CoreIconButton src(String val);
  @JsProperty String src();

  /**
   * If true, border is placed around the button to indicate it's
   * active state.
   *
   * @attribute active
   * @type boolean
   * @default false
   */
  @JsProperty CoreIconButton active(boolean val);
  @JsProperty boolean active();

  /**
   * Specifies the icon name or index in the set of icons available in
   * the icon set.  Should not use `src` property if you are using this
   * property.
   *
   * @attribute icon
   * @type string
   * @default ''
   */
  @JsProperty CoreIconButton icon(String val);
  @JsProperty String icon();
}
