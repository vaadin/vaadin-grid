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
public interface CoreHeaderPanel extends HTMLElement  {
  Class<?>[] dependencies = new Class<?>[]{};


  /**
   * Controls header and scrolling behavior. Options are
   * `standard`, `seamed`, `waterfall`, `waterfall-tall`,
   * `waterfall-medium-tall`, `scroll` and `cover`.
   * Default is `standard`.
   *
   * `standard`: The header is a step above the panel. The header will consume the
   * panel at the point of entry, preventing it from passing through to the
   * opposite side.
   *
   * `seamed`: The header is presented as seamed with the panel.
   *
   * `waterfall`: Similar to standard mode, but header is initially presented as
   * seamed with panel, but then separates to form the step.
   *
   * `waterfall-tall`: The header is initially taller (`tall` class is added to
   * the header).  As the user scrolls, the header separates (forming an edge)
   * while condensing (`tall` class is removed from the header).
   *
   * `scroll`: The header keeps its seam with the panel, and is pushed off screen.
   *
   * `cover`: The panel covers the whole `core-header-panel` including the
   * header. This allows user to style the panel in such a way that the panel is
   * partially covering the header.
   *
   *     <style>
   *       core-header-panel[mode=cover]::shadow #mainContainer {
   *         left: 80px;
   *       }
   *       .content {
   *         margin: 60px 60px 60px 0;
   *       }
   *     </style>
   *
   *     <core-header-panel mode="cover">
   *       <core-appbar class="tall">
   *         <core-icon-button icon="menu"></core-icon-button>
   *       </core-appbar>
   *       <div class="content"></div>
   *     </core-header-panel>
   *
   * @attribute mode
   * @type string
   */
  @JsProperty CoreHeaderPanel mode(String val);
  @JsProperty String mode();

  /**
   * The class used in waterfall-tall mode.  Change this if the header
   * accepts a different class for toggling height, e.g. "medium-tall"
   *
   * @attribute tallClass
   * @type string
   */
  @JsProperty CoreHeaderPanel tallClass(String val);
  @JsProperty String tallClass();

  /**
   * If true, the drop-shadow is always shown no matter what mode is set to.
   *
   * @attribute shadow
   * @type boolean
   */
  @JsProperty CoreHeaderPanel shadow(boolean val);
  @JsProperty boolean shadow();

}
