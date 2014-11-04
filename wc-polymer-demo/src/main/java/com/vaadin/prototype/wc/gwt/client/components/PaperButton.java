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
public interface PaperButton extends HTMLElement , PaperFocusable {
  Class<?>[] dependencies = new Class<?>[]{};

  /**
   * Fired when the clicked.
   *
   * @event click
   */
  void addEventListener(String event, EventListener listener);


  /**
   * The label of the button.
   *
   * @attribute label
   * @type string
   */
  @JsProperty PaperButton label(String val);
  @JsProperty String label();

  /**
   * If true, the button will be styled as a "raised" button.
   *
   * @attribute raisedButton
   * @type boolean
   */
  @JsProperty PaperButton raisedButton(boolean val);
  @JsProperty boolean raisedButton();

  /**
   * (optional) The URL of an image for an icon to use in the button.
   * Should not use `icon` property if you are using this property.
   *
   * @attribute iconSrc
   * @type string
   */
  @JsProperty PaperButton iconSrc(String val);
  @JsProperty String iconSrc();

  /**
   * (optional) Specifies the icon name or index in the set of icons
   * available in the icon set. If using this property, load the icon
   * set separately where the icon is used. Should not use `src`
   * if you are using this property.
   *
   * @attribute icon
   * @type string
   */
  @JsProperty PaperButton icon(String val);
  @JsProperty String icon();

}
