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
public interface CoreMenuButton extends HTMLElement {

  Class<?>[] dependencies = new Class<?>[]{};

  /**
   * The icon to display.
   * @attribute icon
   * @type string
   */
  @JsProperty CoreMenuButton icon(String val);
  @JsProperty String icon();

  /**
   * The index of the selected menu item.
   * @attribute selected
   * @type number
   */
  @JsProperty CoreMenuButton selected(double val);
  @JsProperty double selected();

  /**
   * Set to true to open the menu.
   * @attribute opened
   * @type boolean
   */
  @JsProperty CoreMenuButton opened(boolean val);
  @JsProperty boolean opened();

  /**
   * Set to true to cause the menu popup to be displayed inline rather 
   * than in its own layer.
   * @attribute inlineMenu
   * @type boolean
   */
  @JsProperty CoreMenuButton inlineMenu(boolean val);
  @JsProperty boolean inlineMenu();

  /**
   * Horizontally align the overlay with the button.
   * @attribute halign
   * @type string
   */
  @JsProperty CoreMenuButton halign(String val);
  @JsProperty String halign();

  /**
   * Display the overlay on top or below the button.
   * @attribute valign
   * @type string
   */
  @JsProperty CoreMenuButton valign(String val);
  @JsProperty String valign();

  /**
   * Toggle the opened state of the dropdown.
   * @method toggle
   */
  void toggle();
}
