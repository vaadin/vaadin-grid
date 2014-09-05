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
public interface PaperItem {

  /**
   * The label for the item.
   *
   * @attribute label
   * @type string
   * @default ''
   */
  @JsProperty PaperItem label(String val);
  @JsProperty String label();

  /**
   * (optional) The URL of an image for an icon to use in the button.
   * Should not use `icon` property if you are using this property.
   *
   * @attribute iconSrc
   * @type string
   * @default ''
   */
  @JsProperty PaperItem iconSrc(String val);
  @JsProperty String iconSrc();

  /**
   * (optional) Specifies the icon name or index in the set of icons
   * available in the icon set. If using this property, load the icon
   * set separately where the icon is used. Should not use `src`
   * if you are using this property.
   *
   * @attribute icon
   * @type string
   * @default ''
   */
  @JsProperty PaperItem icon(String val);
  @JsProperty String icon();
}
