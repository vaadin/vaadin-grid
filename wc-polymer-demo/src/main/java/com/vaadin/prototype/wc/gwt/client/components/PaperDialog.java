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
public interface PaperDialog extends HTMLElement  {
  Class<?>[] dependencies = new Class<?>[]{};


  /**
   * Set opened to true to show the dialog and to false to hide it.
   * A dialog may be made intially opened by setting its opened attribute.
   * @attribute opened
   * @type boolean
   */
  @JsProperty PaperDialog opened(boolean val);
  @JsProperty boolean opened();

  /**
   * @attribute heading
   * @type string
   */
  @JsProperty PaperDialog heading(String val);
  @JsProperty String heading();

  /**
   * Set this property to the id of a <core-transition> element to specify
   * the transition to use when opening/closing this dialog.
   *
   * @attribute transition
   * @type string
   */
  @JsProperty PaperDialog transition(String val);
  @JsProperty String transition();

  /**
   * Toggle the dialog's opened state.
   * @method toggle
   */
  void toggle();

}
