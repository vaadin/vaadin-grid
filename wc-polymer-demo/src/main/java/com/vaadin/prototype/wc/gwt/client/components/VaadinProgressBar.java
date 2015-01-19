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
public interface VaadinProgressBar extends HTMLElement  {
  Class<?>[] dependencies = new Class<?>[]{};
/**
 * @event change
 */
  void addEventListener(String event, EventListener listener);

/**
 * @attribute value
 * @type number
 */
  @JsProperty VaadinProgressBar value(double val);
  @JsProperty double value();
/**
 * @attribute theme
 * @type string
 */
  @JsProperty VaadinProgressBar theme(String val);
  @JsProperty String theme();

}
