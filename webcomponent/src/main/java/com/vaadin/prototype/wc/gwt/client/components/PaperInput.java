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
public interface PaperInput extends HTMLElement , CoreInput {
  Class<?>[] dependencies = new Class<?>[]{};


  /**
   * The label for this input. It normally appears as grey text inside
   * the text input and disappears once the user enters text.
   *
   * @attribute label
   * @type string
   */
  @JsProperty PaperInput label(String val);
  @JsProperty String label();

  /**
   * If true, the label will "float" above the text input once the
   * user enters text instead of disappearing.
   *
   * @attribute floatingLabel
   * @type boolean
   */
  @JsProperty PaperInput floatingLabel(boolean val);
  @JsProperty boolean floatingLabel();

  /**
   * (multiline only) If set to a non-zero value, the height of this
   * text input will grow with the value changes until it is maxRows
   * rows tall. If the maximum size does not fit the value, the text
   * input will scroll internally.
   *
   * @attribute maxRows
   * @type number
   */
  @JsProperty PaperInput maxRows(double val);
  @JsProperty double maxRows();

}
