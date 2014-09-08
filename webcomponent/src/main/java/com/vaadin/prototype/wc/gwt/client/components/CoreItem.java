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
public interface CoreItem extends HTMLElement {

  Class<?>[] dependencies = new Class<?>[]{CoreIcon.class};

  /**
   * The URL of an image for the icon.
   *
   * @attribute src
   * @type string
   * @default ''
   */
  @JsProperty CoreItem src(String val);
  @JsProperty String src();

  /**
   * Specifies the icon from the Polymer icon set.
   *
   * @attribute icon
   * @type string
   * @default ''
   */
  @JsProperty CoreItem icon(String val);
  @JsProperty String icon();

  /**
   * Specifies the label for the menu item.
   *
   * @attribute label
   * @type string
   * @default ''
   */
  @JsProperty CoreItem label(String val);
  @JsProperty String label();
}
