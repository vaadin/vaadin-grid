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
public interface CoreScrollHeaderPanel {

  /**
   * Fired when the header is transformed.
   *
   * @event core-header-transform   * Fired when the content has been scrolled.
   *
   * @event scroll
   */
  void addEventListener(String event, EventListener listener);

  /**
   * If true, the header's height will condense to `condensedHeaderHeight`
   * as the user scrolls down from the top of the content area.
   *
   * @attribute condenses
   * @type boolean
   * @default false
   */
  @JsProperty CoreScrollHeaderPanel condenses(boolean val);
  @JsProperty boolean condenses();

  /**
   * If true, no cross-fade transition from one background to another.
   *
   * @attribute noDissolve
   * @type boolean
   * @default false
   */
  @JsProperty CoreScrollHeaderPanel noDissolve(boolean val);
  @JsProperty boolean noDissolve();

  /**
   * If true, the header doesn't slide back in when scrolling back up.
   *
   * @attribute noReveal
   * @type boolean
   * @default false
   */
  @JsProperty CoreScrollHeaderPanel noReveal(boolean val);
  @JsProperty boolean noReveal();

  /**
   * If true, the header is fixed to the top and never moves away.
   *
   * @attribute fixed
   * @type boolean
   * @default false
   */
  @JsProperty CoreScrollHeaderPanel fixed(boolean val);
  @JsProperty boolean fixed();

  /**
   * If true, the condensed header is always shown and not moves away.
   *
   * @attribute keepCondensedHeader
   * @type boolean
   * @default false
   */
  @JsProperty CoreScrollHeaderPanel keepCondensedHeader(boolean val);
  @JsProperty boolean keepCondensedHeader();

  /**
   * The height of the header when it is at its full size.
   *
   * By default, the height will be measused when it is ready.  If the height
   * changes later user needs to either set this value to reflect the new
   * height or invoke `measureHeaderHeight()`.
   *
   * @attribute headerHeight
   * @type number
   */
  @JsProperty CoreScrollHeaderPanel headerHeight(double val);
  @JsProperty double headerHeight();

  /**
   * The height of the header when it is condensed.
   *
   * By default, this will be 1/3 of `headerHeight`.
   *
   * @attribute condensedHeaderHeight
   * @type number
   */
  @JsProperty CoreScrollHeaderPanel condensedHeaderHeight(double val);
  @JsProperty double condensedHeaderHeight();
}
