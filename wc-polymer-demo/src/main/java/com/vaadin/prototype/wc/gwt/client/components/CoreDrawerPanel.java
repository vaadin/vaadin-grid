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
public interface CoreDrawerPanel extends HTMLElement  {
  Class<?>[] dependencies = new Class<?>[]{CoreMediaQuery.class, CoreSelector.class};

  /**
   * Fired when the narrow layout changes.
   * 
   * @event core-responsive-change
   * @param {Object} detail
   * @param {boolean} detail.narrow true if the panel is in narrow layout.
   */
  void addEventListener(String event, EventListener listener);


  /**
   * Max-width when the panel changes to narrow layout.
   *
   * @attribute responsiveWidth
   * @type string
   */
  @JsProperty CoreDrawerPanel responsiveWidth(String val);
  @JsProperty String responsiveWidth();

  /**
   * The panel that is being selected. `drawer` for the drawer panel and
   * `main` for the main panel.
   *
   * @attribute selected
   * @type string
   */
  @JsProperty CoreDrawerPanel selected(String val);
  @JsProperty String selected();

  /**
   * The panel to be selected when `core-drawer-panel` changes to narrow 
   * layout.
   *
   * @attribute defaultSelected
   * @type string
   */
  @JsProperty CoreDrawerPanel defaultSelected(String val);
  @JsProperty String defaultSelected();

  /**
   * Returns true if the panel is in narrow layout.  This is useful if you
   * need to show/hide elements based on the layout.
   *
   * @attribute narrow
   * @type boolean
   */
  @JsProperty CoreDrawerPanel narrow(boolean val);
  @JsProperty boolean narrow();

  /**
   * Toggle the panel.
   * @method togglePanel
   */
  void togglePanel();

  /**
   * Open Drawer.
   * @method openDrawer
   */
  void openDrawer();

  /**
   * Close Drawer.
   * @method closeDrawer
   */
  void closeDrawer();

}
