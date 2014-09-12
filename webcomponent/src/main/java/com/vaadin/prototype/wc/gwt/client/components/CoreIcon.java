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
public interface CoreIcon extends HTMLElement {

  Class<?>[] dependencies = new Class<?>[]{CoreIconset.class};

  /**
   * The URL of an image for the icon. If the src property is specified,
   * the icon property should not be.
   *
   * @attribute src
   * @type string
   * @default ''
   */
  @JsProperty CoreIcon src(String val);
  @JsProperty String src();

  /**
   * Specifies the size of the icon in pixel units.
   *
   * @attribute size
   * @type string
   * @default 24
   */
  @JsProperty CoreIcon size(String val);
  @JsProperty String size();

  /**
   * Specifies the icon name or index in the set of icons available in
   * the icon's icon set. If the icon property is specified,
   * the src property should not be.
   *
   * @attribute icon
   * @type string
   * @default ''
   */
  @JsProperty CoreIcon icon(String val);
  @JsProperty String icon();
}
