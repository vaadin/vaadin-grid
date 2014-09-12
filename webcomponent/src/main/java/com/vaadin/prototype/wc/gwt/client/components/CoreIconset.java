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
 * The `core-iconset` element allows users to define their own icon sets. 
 * The `src` property specifies the url of the icon image. Multiple icons may
 * be included in this image and they may be organized into rows.
 * The `icons` property is a space separated list of names corresponding to the
 * icons. The names must be ordered as the icons are ordered in the icon image.
 * Icons are expected to be square and are the size specified by the `iconSize`
 * property. The `width` property corresponds to the width of the icon image 
 * and must be specified if icons are arranged into multiple rows in the image.
 *
 * All `core-iconset` elements are available for use by other `core-iconset`
 * elements via a database keyed by id. Typically, an element author that wants
 * to support a set of custom icons uses a `core-iconset` to retrieve 
 * and use another, user-defined iconset.
 *
 * Example:
 *
 *     <core-iconset id="my-icons" src="my-icons.png" width="96" iconSize="24"
 *         icons="location place starta stopb bus car train walk">
 *     </core-iconset>
 *
 * This will automatically register the icon set "my-icons" to the iconset
 * database.  To use these icons from within another element, make a 
 * `core-iconset` element and call the `byId` method to retrieve a
 * given iconset. To apply a particular icon to an element, use the 
 * `applyIcon` method. For example:
 *
 *     iconset.applyIcon(iconNode, 'car');
 *
 * Themed icon sets are also supported. The `core-iconset` can contain child
 * `property` elements that specify a theme with an offsetX and offsetY of the
 * theme within the icon resource. For example.
 *
 *     <core-iconset id="my-icons" src="my-icons.png" width="96" iconSize="24"
 *         icons="location place starta stopb bus car train walk">
 *       <property theme="special" offsetX="256" offsetY="24"></property>
 *     </core-iconset>
 *
 * Then a themed icon can be applied like this:
 *
 *     iconset.applyIcon(iconNode, 'car', 'special');
 *
 * @element core-iconset
 * @extends core-meta
 * @homepage github.io
 */
@JsType(prototype = "HTMLElement", isNative = true)
public interface CoreIconset extends HTMLElement, CoreMeta {

  Class<?>[] dependencies = new Class<?>[]{CoreMeta.class};

  /**
   * The URL of the iconset image.
   *
   * @attribute src
   * @type string
   * @default ''
   */
  @JsProperty CoreIconset src(String val);
  @JsProperty String src();

  /**
   * The width of the iconset image. This must only be specified if the
   * icons are arranged into separate rows inside the image.
   *
   * @attribute width
   * @type number
   * @default 0
   */
  @JsProperty CoreIconset width(double val);
  @JsProperty double width();

  /**
   * A space separated list of names corresponding to icons in the iconset
   * image file. This list must be ordered the same as the icon images
   * in the image file.
   *
   * @attribute icons
   * @type string
   * @default ''
   */
  @JsProperty CoreIconset icons(String val);
  @JsProperty String icons();

  /**
   * The size of an individual icon. Note that icons must be square.
   *
   * @attribute iconSize
   * @type number
   * @default 24
   */
  @JsProperty CoreIconset iconSize(double val);
  @JsProperty double iconSize();

  /**
   * The horizontal offset of the icon images in the inconset src image.
   * This is typically used if the image resource contains additional images
   * beside those intended for the iconset.
   *
   * @attribute offsetX
   * @type number
   * @default 0
   */
  @JsProperty CoreIconset offsetX(double val);
  @JsProperty double offsetX();

  /**
   * The vertical offset of the icon images in the inconset src image.
   * This is typically used if the image resource contains additional images
   * beside those intended for the iconset.
   *
   * @attribute offsetY
   * @type number
   * @default 0
   */
  @JsProperty CoreIconset offsetY(double val);
  @JsProperty double offsetY();

  /**
   * Returns an object containing `offsetX` and `offsetY` properties which
   * specify the pixel locaion in the iconset's src file for the given
   * `icon` and `theme`. It's uncommon to call this method. It is useful,
   * for example, to manually position a css backgroundImage to the proper
   * offset. It's more common to use the `applyIcon` method.
   *
   * @method getOffset
   * @param {String|Number} icon The name of the icon or the index of the
   * icon within in the icon image.
   * @param {String} theme The name of the theme.
   * @returns {Object} An object specifying the offset of the given icon 
   * within the icon resource file; `offsetX` is the horizontal offset and
   * `offsetY` is the vertical offset. Both values are in pixel units.
   */
  JavaScriptObject getOffset(String arg0, String arg1);

  /**
   * Applies an icon to the given element as a css background image. This
   * method does not size the element, and it's often necessary to set 
   * the element's height and width so that the background image is visible.
   *
   * @method applyIcon
   * @param {Element} element The element to which the background is
   * applied.
   * @param {String|Number} icon The name or index of the icon to apply.
   * @param {String} theme (optional) The name of the theme for the icon.
   * @param {Number} scale (optional, defaults to 1) A scaling factor 
   * with which the icon can be magnified.
   */
  void applyIcon(Element arg0, String arg1, String arg2, double arg3);
}
