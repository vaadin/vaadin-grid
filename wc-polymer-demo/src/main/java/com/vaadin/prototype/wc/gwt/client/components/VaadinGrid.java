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
public interface VaadinGrid extends HTMLElement  {
  Class<?>[] dependencies = new Class<?>[]{};
/**
 * @event select
 */
  void addEventListener(String event, EventListener listener);

/**
 * @method refresh
 */
  void refresh();
/**
 * @method redraw
 */
  void redraw();
/**
 * @attribute shadow
 * @type boolean
 */
  @JsProperty VaadinGrid shadow(boolean val);
  @JsProperty boolean shadow();
/**
 * @attribute selectedRow
 * @type number
 */ 
  @JsProperty VaadinGrid selectedRow(double val);
  @JsProperty double selectedRow();
/**
 * @attribute selectedRows
 * @type object
 */ 
  @JsProperty VaadinGrid selectedRows(JavaScriptObject val);
  @JsProperty JavaScriptObject selectedRows();
/**
 * @attribute rowCount
 * @type number
 */ 
  @JsProperty VaadinGrid rowCount(double val);
  @JsProperty double rowCount();
/**
 * @attribute type
 * @type string
 */ 
  @JsProperty VaadinGrid type(String val);
  @JsProperty String type();
/**
 * @attribute url
 * @type string
 */ 
  @JsProperty VaadinGrid url(String val);
  @JsProperty String url();
/**
 * @attribute dataSource
 * @type object
 */ 
  @JsProperty VaadinGrid dataSource(JavaScriptObject val);
  @JsProperty JavaScriptObject dataSource();
/**
 * @attribute theme
 * @type string
 */ 
  @JsProperty VaadinGrid theme(String val);
  @JsProperty String theme();

}
