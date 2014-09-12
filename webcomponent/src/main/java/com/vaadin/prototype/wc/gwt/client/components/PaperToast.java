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
public interface PaperToast extends HTMLElement {

  Class<?>[] dependencies = new Class<?>[]{CoreOverlay.class, CoreTransitionCss.class, CoreMediaQuery.class};

  /**
   * The text shows in a toast.
   *
   * @attribute text
   * @type string
   * @default ''
   */
  @JsProperty PaperToast text(String val);
  @JsProperty String text();

  /**
   * The duration in milliseconds to show the toast.
   *
   * @attribute duration
   * @type number
   * @default 3000
   */
  @JsProperty PaperToast duration(double val);
  @JsProperty double duration();

  /**
   * Set opened to true to show the toast and to false to hide it.
   *
   * @attribute opened
   * @type boolean
   * @default false
   */
  @JsProperty PaperToast opened(boolean val);
  @JsProperty boolean opened();

  /**
   * Min-width when the toast changes to narrow layout.  In narrow layout,
   * the toast fits at the bottom of the screen when opened.
   *
   * @attribute responsiveWidth
   * @type string
   * @default '480px'
   */
  @JsProperty PaperToast responsiveWidth(String val);
  @JsProperty String responsiveWidth();

  /**
   * If true, the toast can't be swiped.
   *
   * @attribute swipeDisabled
   * @type boolean
   * @default false
   */
  @JsProperty PaperToast swipeDisabled(boolean val);
  @JsProperty boolean swipeDisabled();

  /** 
   * Toggle the opened state of the toast.
   * @method toggle
   */
  void toggle();

  /** 
   * Show the toast for the specified duration
   * @method show
   */
  void show();

  /** 
   * Dismiss the toast and hide it.
   * @method dismiss
   */
  void dismiss();
}
