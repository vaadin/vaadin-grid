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
public interface PaperIconButton extends HTMLElement , PaperButton {
  Class<?>[] dependencies = new Class<?>[]{};


  /**
   * If true, the ripple expands to a square to fill the containing box.
   *
   * @attribute fill
   * @type boolean
   */
  @JsProperty PaperIconButton fill(boolean val);
  @JsProperty boolean fill();

}
