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
public interface PaperShadow {

  /**
   * If set, the shadow is applied to this node.
   *
   * @attribute target
   * @type Element
   * @default null
   */
  @JsProperty PaperShadow target(Element val);
  @JsProperty Element target();

  /**
   * The z-depth of this shadow, from 0-5.
   *
   * @attribute z
   * @type number
   * @default 1
   */
  @JsProperty PaperShadow z(double val);
  @JsProperty double z();

  /**
   * If true, the shadow animates between z-depth changes.
   *
   * @attribute animated
   * @type boolean
   * @default false
   */
  @JsProperty PaperShadow animated(boolean val);
  @JsProperty boolean animated();

  /**
   * Workaround: getComputedStyle is wrong sometimes so `paper-shadow`
   * may overwrite the `position` CSS property. Set this property to
   * true to prevent this.
   *
   * @attribute hasPosition
   * @type boolean
   * @default false
   */
  @JsProperty PaperShadow hasPosition(boolean val);
  @JsProperty boolean hasPosition();
}
