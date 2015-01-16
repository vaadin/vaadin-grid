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
public interface CoreOverlay extends HTMLElement  {
  Class<?>[] dependencies = new Class<?>[]{CoreTransition.class, CoreKeyHelper.class, CoreOverlayLayer.class};


  /**
   * The target element that will be shown when the overlay is
   * opened. If unspecified, the core-overlay itself is the target.
   *
   * @attribute target
   * @type Object
   */
  @JsProperty CoreOverlay target(JavaScriptObject val);
  @JsProperty JavaScriptObject target();

  /**
   * A `core-overlay`'s size is guaranteed to be
   * constrained to the window size. To achieve this, the sizingElement
   * is sized with a max-height/width. By default this element is the
   * target element, but it can be specifically set to a specific element
   * inside the target if that is more appropriate. This is useful, for
   * example, when a region inside the overlay should scroll if needed.
   *
   * @attribute sizingTarget
   * @type Object
   */
  @JsProperty CoreOverlay sizingTarget(JavaScriptObject val);
  @JsProperty JavaScriptObject sizingTarget();

  /**
   * Set opened to true to show an overlay and to false to hide it.
   * A `core-overlay` may be made initially opened by setting its
   * `opened` attribute.
   * @attribute opened
   * @type boolean
   */
  @JsProperty CoreOverlay opened(boolean val);
  @JsProperty boolean opened();

  /**
   * If true, the overlay has a backdrop darkening the rest of the screen.
   * The backdrop element is attached to the document body and may be styled
   * with the class `core-overlay-backdrop`. When opened the `core-opened`
   * class is applied.
   *
   * @attribute backdrop
   * @type boolean
   */
  @JsProperty CoreOverlay backdrop(boolean val);
  @JsProperty boolean backdrop();

  /**
   * If true, the overlay is guaranteed to display above page content.
   *
   * @attribute layered
   * @type boolean
   */
  @JsProperty CoreOverlay layered(boolean val);
  @JsProperty boolean layered();

  /**
   * By default an overlay will close automatically if the user
   * taps outside it or presses the escape key. Disable this
   * behavior by setting the `autoCloseDisabled` property to true.
   * @attribute autoCloseDisabled
   * @type boolean
   */
  @JsProperty CoreOverlay autoCloseDisabled(boolean val);
  @JsProperty boolean autoCloseDisabled();

  /**
   * This property specifies an attribute on elements that should
   * close the overlay on tap. Should not set `closeSelector` if this
   * is set.
   *
   * @attribute closeAttribute
   * @type string
   */
  @JsProperty CoreOverlay closeAttribute(String val);
  @JsProperty String closeAttribute();

  /**
   * This property specifies a selector matching elements that should
   * close the overlay on tap. Should not set `closeAttribute` if this
   * is set.
   *
   * @attribute closeSelector
   * @type string
   */
  @JsProperty CoreOverlay closeSelector(String val);
  @JsProperty String closeSelector();

  /**
   * A `core-overlay` target's size is constrained to the window size.
   * The `margin` property specifies a pixel amount around the overlay
   * that will be reserved. It's useful for ensuring that, for example,
   * a shadow displayed outside the target will always be visible.
   *
   * @attribute margin
   * @type number
   */
  @JsProperty CoreOverlay margin(double val);
  @JsProperty double margin();

  /**
   * The transition property specifies a string which identifies a
   * <a href="../core-transition/">`core-transition`</a> element that
   * will be used to help the overlay open and close. The default
   * `core-transition-fade` will cause the overlay to fade in and out.
   *
   * @attribute transition
   * @type string
   */
  @JsProperty CoreOverlay transition(String val);
  @JsProperty String transition();

  /**
   * Toggle the opened state of the overlay.
   * @method toggle
   */
  void toggle();

  /**
   * Open the overlay. This is equivalent to setting the `opened`
   * property to true.
   * @method open
   */
  void open();

  /**
   * Close the overlay. This is equivalent to setting the `opened`
   * property to false.
   * @method close
   */
  void close();

  /**
   * Extensions of core-overlay should implement the `resizeHandler`
   * method to adjust the size and position of the overlay when the
   * browser window resizes.
   * @method resizeHandler
   */
  void resizeHandler();

}
