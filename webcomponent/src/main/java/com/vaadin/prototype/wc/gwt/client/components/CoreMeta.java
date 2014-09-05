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
public interface CoreMeta {

  /**
   * The type of meta-data.  All meta-data with the same type with be
   * stored together.
   * 
   * @attribute type
   * @type string
   * @default 'default'
   */
  @JsProperty CoreMeta type(String val);
  @JsProperty String type();

  /**
   * Returns a list of all meta-data elements with the same type.
   * 
   * @attribute list
   * @type array
   * @default []
   */
  @JsProperty CoreMeta list(JsArray val);
  @JsProperty JsArray list();

  /**
   * Retrieves meta-data by ID.
   *
   * @method byId
   * @param {String} id The ID of the meta-data to be returned.
   * @returns Returns meta-data.
   */
  void byId();
}
