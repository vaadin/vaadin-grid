package com.vaadin.prototype.wc.gwt.client.components;

import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.core.client.js.JsArray;
import com.google.gwt.core.client.js.JsObject;
import com.google.gwt.dom.client.BodyElement;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.HeadElement;
import com.google.gwt.user.client.EventListener;

/**
 * @group Polymer Core Elements
 * @element core-media-query
 * @status beta
 * @homepage github.io
 *
 * core-media-query can be used to data bind to a CSS media query.
 * The "query" property is a bare CSS media query.
 * The "queryMatches" property will be a boolean representing if the page matches that media query.
 *
 * core-media-query uses media query listeners to dynamically update the "queryMatches" property.
 * A "core-media-change" event also fires when queryMatches changes.
 *
 * Example:
 *
 *      <core-media-query query="max-width: 640px" queryMatches="{{phoneScreen}}"></core-media-query>
 *
 */
@JsType(prototype = "HTMLElement", isNative = true)
public interface CoreMediaQuery {

  /**
   * Fired when the media query state changes
   *
   * @event core-media-change
   */
  void addEventListener(String event, EventListener listener);

  /**
   * The Boolean return value of the media query
   *
   * @attribute queryMatches
   * @type Boolean
   * @default false
   */
  @JsProperty CoreMediaQuery queryMatches(boolean val);
  @JsProperty boolean queryMatches();

  /**
   * The CSS media query to evaulate
   *
   * @attribute query
   * @type string
   * @default ''
   */
  @JsProperty CoreMediaQuery query(String val);
  @JsProperty String query();
}
