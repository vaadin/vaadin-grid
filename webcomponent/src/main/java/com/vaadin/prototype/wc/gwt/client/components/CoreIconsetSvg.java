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

/**
 * @group Polymer Core Elements
 *
 * The `core-iconset-svg` element allows users to define their own icon sets
 * that contain svg icons. The svg icon elements should be children of the
 * `core-iconset-svg` element. Multiple icons should be given distinct id's.
 *
 * Using svg elements to create icons has a few advantages over traditional
 * bitmap graphics like jpg or png. Icons that use svg are vector based so they
 * are resolution independent and should look good on any device. They are
 * stylable via css. Icons can be themed, colorized, and even animated.
 *
 * Example:
 *
 *     <core-iconset-svg id="my-svg-icons" iconSize="24">
 *       <svg>
 *         <defs>
 *           <g id="shape">
 *             <rect x="50" y="50" width="50" height="50" />
 *             <circle cx="50" cy="50" r="50" />
 *           </g>
 *         </defs>
 *       </svg>
 *     </core-iconset-svg>
 *
 * This will automatically register the icon set "my-svg-icons" to the iconset
 * database.  To use these icons from within another element, make a
 * `core-iconset` element and call the `byId` method
 * to retrieve a given iconset. To apply a particular icon inside an
 * element use the `applyIcon` method. For example:
 *
 *     iconset.applyIcon(iconNode, 'car');
 *
 * @element core-iconset-svg
 * @extends core-meta
 * @homepage github.io
 */
@JsType(prototype = "HTMLElement", isNative = true)
public interface CoreIconsetSvg extends HTMLElement, CoreMeta {

  Class<?>[] dependencies = new Class<?>[]{CoreIconset.class};

  /**
   * The size of an individual icon. Note that icons must be square.
   *
   * @attribute iconSize
   * @type number
   * @default 24
   */
  @JsProperty CoreIconsetSvg iconSize(double val);
  @JsProperty double iconSize();

  /**
   * Applies an icon to the given element. The svg icon is added to the
   * element's shadowRoot if one exists or directly to itself.
   *
   * @method applyIcon
   * @param {Element} element The element to which the icon is
   * applied.
   * @param {String|Number} icon The name the icon to apply.
   */
  void applyIcon(Element arg0, String arg1);

  /**
   * Tell users of the iconset, that the set has loaded.
   * This finds all elements matching the selector argument and calls 
   * the method argument on them.
   * @method updateIcons
   * @param selector {string} css selector to identify iconset users, 
   * defaults to '[icon]'
   * @param method {string} method to call on found elements, 
   * defaults to 'updateIcon'
   */
  void updateIcons(JavaScriptObject arg0, JavaScriptObject arg1);
}
