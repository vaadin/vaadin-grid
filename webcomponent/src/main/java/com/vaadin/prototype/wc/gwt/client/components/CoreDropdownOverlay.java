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
public interface CoreDropdownOverlay extends HTMLElement, CoreOverlay {

  Class<?>[] dependencies = new Class<?>[]{CoreOverlay.class};

  /**
   * The `relatedTarget` is an element used to position the overlay. It should have
   * the same offsetParent as the target.
   *
   * @attribute relatedTarget
   * @type Node
   */
  @JsProperty CoreDropdownOverlay relatedTarget(JavaScriptObject val);
  @JsProperty JavaScriptObject relatedTarget();

  /**
   * The horizontal alignment of the overlay relative to the `relatedTarget`.
   * `left` means the left edges are aligned together and `right` means the right
   * edges are aligned together.
   *
   * @attribute halign
   * @type 'left' | 'right'
   * @default 'auto'
   */
  @JsProperty CoreDropdownOverlay halign(JavaScriptObject val);
  @JsProperty JavaScriptObject halign();

  /**
   * The vertical alignment of the overlay relative to the `relatedTarget`. `top`
   * means the top edges are aligned together and `bottom` means the bottom edges
   * are aligned together.
   *
   * @attribute valign
   * @type 'top' | 'bottom'
   * @default 'top'
   */
  @JsProperty CoreDropdownOverlay valign(JavaScriptObject val);
  @JsProperty JavaScriptObject valign();

  /**
   * A pixel amount around the overlay that will be reserved. It's useful for
   * ensuring that, for example, a shadow displayed outside the overlay will
   * always be visible.
   *
   * @attribute margin
   * @type number
   * @default 0
   */
  @JsProperty CoreDropdownOverlay margin(double val);
  @JsProperty double margin();
}
