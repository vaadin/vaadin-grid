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
public interface PaperProgress extends HTMLElement, CoreRange {

  Class<?>[] dependencies = new Class<?>[]{CoreRange.class};

  /**
   * The number that represents the current secondary progress.
   *
   * @attribute secondaryProgress
   * @type number
   * @default 0
   */
  @JsProperty PaperProgress secondaryProgress(double val);
  @JsProperty double secondaryProgress();
}
