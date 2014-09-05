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
public interface PaperTabs extends CoreSelector {

  /**
   * If true, ink effect is disabled.
   *
   * @attribute noink
   * @type boolean
   * @default false
   */
  @JsProperty PaperTabs noink(boolean val);
  @JsProperty boolean noink();

  /**
   * If true, the bottom bar to indicate the selected tab will not be shown.
   *
   * @attribute nobar
   * @type boolean
   * @default false
   */
  @JsProperty PaperTabs nobar(boolean val);
  @JsProperty boolean nobar();
}
