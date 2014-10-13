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
public interface PaperShadow extends HTMLElement  {
  Class<?>[] dependencies = new Class<?>[]{};


  /**
   * If set, the shadow is applied to this node.
   *
   * @attribute target
   * @type Element
   */
  @JsProperty PaperShadow target(Element val);
  @JsProperty Element target();

  /**
   * The z-depth of this shadow, from 0-5.
   *
   * @attribute z
   * @type number
   */
  @JsProperty PaperShadow z(double val);
  @JsProperty double z();

  /**
   * If true, the shadow animates between z-depth changes.
   *
   * @attribute animated
   * @type boolean
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
   */
  @JsProperty PaperShadow hasPosition(boolean val);
  @JsProperty boolean hasPosition();

}
