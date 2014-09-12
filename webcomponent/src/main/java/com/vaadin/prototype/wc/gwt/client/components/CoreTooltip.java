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
public interface CoreTooltip extends HTMLElement {

  Class<?>[] dependencies = new Class<?>[]{};

  /**
   * A simple string label for the tooltip to display. To display a rich
   * HTML tooltip instead, omit `label` and include the `tip` attribute
   * on a child node of `core-tooltip`.
   *
   * @attribute label
   * @type string
   * @default null
   */
  @JsProperty CoreTooltip label(String val);
  @JsProperty String label();

  /**
   * If true, the tooltip displays by default.
   *
   * @attribute show
   * @type boolean
   * @default false
   */
  @JsProperty CoreTooltip show(boolean val);
  @JsProperty boolean show();

  /**
   * Positions the tooltip to the top, right, bottom, left of its content.
   *
   * @attribute position
   * @type string
   * @default 'bottom'
   */
  @JsProperty CoreTooltip position(String val);
  @JsProperty String position();

  /**
   * If true, the tooltip an arrow pointing towards the content.
   *
   * @attribute noarrow
   * @type boolean
   * @default false
   */
  @JsProperty CoreTooltip noarrow(boolean val);
  @JsProperty boolean noarrow();

  /**
   * Customizes the attribute used to specify which content
   * is the rich HTML tooltip.
   *
   * @attribute tipAttribute
   * @type string
   * @default 'tip'
   */
  @JsProperty CoreTooltip tipAttribute(String val);
  @JsProperty String tipAttribute();
}
