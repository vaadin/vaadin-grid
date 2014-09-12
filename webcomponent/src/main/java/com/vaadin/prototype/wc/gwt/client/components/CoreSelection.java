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
public interface CoreSelection extends HTMLElement {

  Class<?>[] dependencies = new Class<?>[]{};

  /**
   * If true, multiple selections are allowed.
   *
   * @attribute multi
   * @type boolean
   * @default false
   */
  @JsProperty CoreSelection multi(boolean val);
  @JsProperty boolean multi();

  /**
   * Retrieves the selected item(s).
   * @method getSelection
   * @returns Returns the selected item(s). If the multi property is true,
   * getSelection will return an array, otherwise it will return 
   * the selected item or undefined if there is no selection.
   */
  JavaScriptObject getSelection();

  /**
   * Indicates if a given item is selected.
   * @method isSelected
   * @param {any} item The item whose selection state should be checked.
   * @returns Returns true if `item` is selected.
   */
  JavaScriptObject isSelected(JavaScriptObject arg0);

  /**
   * Set the selection state for a given `item`. If the multi property
   * is true, then the selected state of `item` will be toggled; otherwise
   * the `item` will be selected.
   * @method select
   * @param {any} item: The item to select.
   */
  void select(JavaScriptObject arg0);

  /**
   * Toggles the selection state for `item`.
   * @method toggle
   * @param {any} item: The item to toggle.
   */
  void toggle(JavaScriptObject arg0);
}
