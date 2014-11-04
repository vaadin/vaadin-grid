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
public interface PaperTab extends HTMLElement  {
  Class<?>[] dependencies = new Class<?>[]{PaperRipple.class};


  /**
   * If true, ink ripple effect is disabled.
   *
   * @attribute noink
   * @type boolean
   */
  @JsProperty PaperTab noink(boolean val);
  @JsProperty boolean noink();

}
