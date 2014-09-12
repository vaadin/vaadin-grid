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
public interface CoreSplitter extends HTMLElement {

  Class<?>[] dependencies = new Class<?>[]{};

  /**
   * Possible values are `left`, `right`, `up` and `down`.
   *
   * @attribute direction
   * @type string
   * @default 'left'
   */
  @JsProperty CoreSplitter direction(String val);
  @JsProperty String direction();

  /**
   * Minimum width to which the splitter target can be sized, e.g. 
   * `minSize="100px"`
   *
   * @attribute minSize
   * @type string
   * @default ''
   */
  @JsProperty CoreSplitter minSize(String val);
  @JsProperty String minSize();

  /**
   * Locks the split bar so it can't be dragged.
   *
   * @attribute locked
   * @type boolean
   * @default false
   */
  @JsProperty CoreSplitter locked(boolean val);
  @JsProperty boolean locked();

  /**
   * By default the parent and siblings of the splitter are set to overflow hidden. This helps
   * avoid elements bleeding outside the splitter regions. Set this property to true to allow
   * these elements to overflow.
   *
   * @attribute allowOverflow
   * @type boolean
   * @default false
   */
  @JsProperty CoreSplitter allowOverflow(boolean val);
  @JsProperty boolean allowOverflow();
}
