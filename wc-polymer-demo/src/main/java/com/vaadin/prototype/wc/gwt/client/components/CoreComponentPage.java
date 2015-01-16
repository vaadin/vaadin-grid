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
public interface CoreComponentPage extends HTMLElement /**
 * @group Polymer Core Elements
 *
 * core-xhr can be used to perform XMLHttpRequests.
 *
 *     <core-xhr id="xhr"></core-xhr>
 *     ...
 *     this.$.xhr.request({url: url, params: params, callback: callback});
 *
 * @element core-xhr
 */
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
, CoreMeta, CoreSelector {
  Class<?>[] dependencies = new Class<?>[]{};

  /**
   * Fired whenever a response or an error is received.
   *
   * @event core-complete   * Fired when an error is received.
   *
   * @event core-error   * Fired when a response is received.
   *
   * @event core-response
   */
  void addEventListener(String event, EventListener listener);


  /**
   * Controls if the element lays out vertically or not.
   *
   * @attribute vertical
   * @type boolean
   */
  @JsProperty CoreComponentPage vertical(boolean val);
  @JsProperty boolean vertical();

  /**
   * Controls how the items are aligned in the main-axis direction. For
   * example for a horizontal layout, this controls how each item is aligned
   * horizontally.
   *
   * @attribute justify
   * @type string start|center|end|between
   */
  @JsProperty CoreComponentPage justify(String val);
  @JsProperty String justify();

  /**
   * Controls how the items are aligned in cross-axis direction. For
   * example for a horizontal layout, this controls how each item is aligned
   * vertically.
   *
   * @attribute align
   * @type string start|center|end
   */
  @JsProperty CoreComponentPage align(String val);
  @JsProperty String align();

  /**
   * Controls whether or not the items layout in reverse order.
   *
   * @attribute reverse
   * @type boolean
   */
  @JsProperty CoreComponentPage reverse(boolean val);
  @JsProperty boolean reverse();

  /**
   * The type of meta-data.  All meta-data with the same type with be
   * stored together.
   *
   * @attribute type
   * @type string
   */
  @JsProperty CoreComponentPage type(String val);
  @JsProperty String type();

  /**
   * Returns a list of all meta-data elements with the same type.
   *
   * @attribute list
   * @type array
   */
  @JsProperty CoreComponentPage list(JsArray val);
  @JsProperty JsArray list();

  /**
   * Retrieves meta-data by ID.
   *
   * @method byId
   * @param {String} id The ID of the meta-data to be returned.
   * @returns Returns meta-data.
   */
  JavaScriptObject byId(String arg0);

  /**
   * The URL of the iconset image.
   *
   * @attribute src
   * @type string
   */
  @JsProperty CoreComponentPage src(String val);
  @JsProperty String src();

  /**
   * The width of the iconset image. This must only be specified if the
   * icons are arranged into separate rows inside the image.
   *
   * @attribute width
   * @type string
   */
  @JsProperty CoreComponentPage width(String val);
  @JsProperty String width();

  /**
   * A space separated list of names corresponding to icons in the iconset
   * image file. This list must be ordered the same as the icon images
   * in the image file.
   *
   * @attribute icons
   * @type string
   */
  @JsProperty CoreComponentPage icons(String val);
  @JsProperty String icons();

  /**
   * The size of an individual icon. Note that icons must be square.
   *
   * @attribute iconSize
   * @type number
   */
  @JsProperty CoreComponentPage iconSize(double val);
  @JsProperty double iconSize();

  /**
   * The horizontal offset of the icon images in the inconset src image.
   * This is typically used if the image resource contains additional images
   * beside those intended for the iconset.
   *
   * @attribute offsetX
   * @type number
   */
  @JsProperty CoreComponentPage offsetX(double val);
  @JsProperty double offsetX();

  /**
   * The vertical offset of the icon images in the inconset src image.
   * This is typically used if the image resource contains additional images
   * beside those intended for the iconset.
   *
   * @attribute offsetY
   * @type number
   */
  @JsProperty CoreComponentPage offsetY(double val);
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
   * Specifies the size of the icon in pixel units.
   *
   * @attribute size
   * @type string
   */
  @JsProperty CoreComponentPage size(String val);
  @JsProperty String size();

  /**
   * Specifies the icon name or index in the set of icons available in
   * the icon's icon set. If the icon property is specified,
   * the src property should not be.
   *
   * @attribute icon
   * @type string
   */
  @JsProperty CoreComponentPage icon(String val);
  @JsProperty String icon();

  /**
   * If true, border is placed around the button to indicate it's
   * active state.
   *
   * @attribute active
   * @type boolean
   */
  @JsProperty CoreComponentPage active(boolean val);
  @JsProperty boolean active();

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
  @JsProperty CoreComponentPage mode(String val);
  @JsProperty String mode();

  /**
   * The class used in waterfall-tall mode.  Change this if the header
   * accepts a different class for toggling height, e.g. "medium-tall"
   *
   * @attribute tallClass
   * @type string
   */
  @JsProperty CoreComponentPage tallClass(String val);
  @JsProperty String tallClass();

  /**
   * If true, the drop-shadow is always shown no matter what mode is set to.
   *
   * @attribute shadow
   * @type boolean
   */
  @JsProperty CoreComponentPage shadow(boolean val);
  @JsProperty boolean shadow();

  /**
   * Sends a HTTP request to the server and returns the XHR object.
   *
   * @method request
   * @param {Object} inOptions
   *    @param {String} inOptions.url The url to which the request is sent.
   *    @param {String} inOptions.method The HTTP method to use, default is GET.
   *    @param {boolean} inOptions.sync By default, all requests are sent asynchronously. To send synchronous requests, set to true.
   *    @param {Object} inOptions.params Data to be sent to the server.
   *    @param {Object} inOptions.body The content for the request body for POST method.
   *    @param {Object} inOptions.headers HTTP request headers.
   *    @param {String} inOptions.responseType The response type. Default is 'text'.
   *    @param {boolean} inOptions.withCredentials Whether or not to send credentials on the request. Default is false.
   *    @param {Object} inOptions.callback Called when request is completed.
   * @returns {Object} XHR object.
   */
  JavaScriptObject request(JavaScriptObject arg0, String arg1, String arg2, boolean arg3, JavaScriptObject arg4, JavaScriptObject arg5, JavaScriptObject arg6, String arg7, boolean arg8, JavaScriptObject arg9);

  /**
   * The URL target of the request.
   *
   * @attribute url
   * @type string
   */
  @JsProperty CoreComponentPage url(String val);
  @JsProperty String url();

  /**
   * Specifies what data to store in the `response` property, and
   * to deliver as `event.response` in `response` events.
   *
   * One of:
   *
   *    `text`: uses `XHR.responseText`.
   *
   *    `xml`: uses `XHR.responseXML`.
   *
   *    `json`: uses `XHR.responseText` parsed as JSON.
   *
   *    `arraybuffer`: uses `XHR.response`.
   *
   *    `blob`: uses `XHR.response`.
   *
   *    `document`: uses `XHR.response`.
   *
   * @attribute handleAs
   * @type string
   */
  @JsProperty CoreComponentPage handleAs(String val);
  @JsProperty String handleAs();

  /**
   * If true, automatically performs an Ajax request when either `url` or `params` changes.
   *
   * @attribute auto
   * @type boolean
   */
  @JsProperty CoreComponentPage auto(boolean val);
  @JsProperty boolean auto();

  /**
   * Parameters to send to the specified URL, as JSON.
   *
   * @attribute params
   * @type string (JSON)
   */
  @JsProperty CoreComponentPage params(String val);
  @JsProperty String params();

  /**
   * Returns the response object.
   *
   * @attribute response
   * @type Object
   */
  @JsProperty CoreComponentPage response(JavaScriptObject val);
  @JsProperty JavaScriptObject response();

  /**
   * The HTTP method to use such as 'GET', 'POST', 'PUT', or 'DELETE'.
   * Default is 'GET'.
   *
   * @attribute method
   * @type string
   */
  @JsProperty CoreComponentPage method(String val);
  @JsProperty String method();

  /**
   * HTTP request headers to send.
   *
   * Example:
   *
   *     <core-ajax
   *         auto
   *         url="http://somesite.com"
   *         headers='{"X-Requested-With": "XMLHttpRequest"}'
   *         handleAs="json"
   *         on-core-response="{{handleResponse}}"></core-ajax>
   *
   * @attribute headers
   * @type Object
   */
  @JsProperty CoreComponentPage headers(JavaScriptObject val);
  @JsProperty JavaScriptObject headers();

  /**
   * Optional raw body content to send when method === "POST".
   *
   * Example:
   *
   *     <core-ajax method="POST" auto url="http://somesite.com"
   *         body='{"foo":1, "bar":2}'>
   *     </core-ajax>
   *
   * @attribute body
   * @type Object
   */
  @JsProperty CoreComponentPage body(JavaScriptObject val);
  @JsProperty JavaScriptObject body();

  /**
   * Content type to use when sending data.
   *
   * @attribute contentType
   * @type string
   */
  @JsProperty CoreComponentPage contentType(String val);
  @JsProperty String contentType();

  /**
   * Set the withCredentials flag on the request.
   *
   * @attribute withCredentials
   * @type boolean
   */
  @JsProperty CoreComponentPage withCredentials(boolean val);
  @JsProperty boolean withCredentials();

  /**
   * Performs an Ajax request to the specified URL.
   *
   * @method go
   */
  void go();

  /**
   * If true, multiple selections are allowed.
   *
   * @attribute multi
   * @type boolean
   */
  @JsProperty CoreComponentPage multi(boolean val);
  @JsProperty boolean multi();

  /**
   * Retrieves the selected item(s).
   * @method getSelection
   * @returns Returns the selected item(s). If the multi property is true,
   * getSelection will return an array, otherwise it will return
   * the selected item or undefined if there is no selection.
   */
  JavaScriptObject getSelection();

  /**
   * Indicates if a given item is selected.
   * @method isSelected
   * @param {any} item The item whose selection state should be checked.
   * @returns Returns true if `item` is selected.
   */
  JavaScriptObject isSelected(JavaScriptObject arg0);

  /**
   * Set the selection state for a given `item`. If the multi property
   * is true, then the selected state of `item` will be toggled; otherwise
   * the `item` will be selected.
   * @method select
   * @param {any} item: The item to select.
   */
  void select(JavaScriptObject arg0);

  /**
   * Toggles the selection state for `item`.
   * @method toggle
   * @param {any} item: The item to toggle.
   */
  void toggle(JavaScriptObject arg0);

  /**
   * Gets or sets the selected element.  Default to use the index
   * of the item element.
   *
   * If you want a specific attribute value of the element to be
   * used instead of index, set "valueattr" to that attribute name.
   *
   * Example:
   *
   *     <core-selector valueattr="label" selected="foo">
   *       <div label="foo"></div>
   *       <div label="bar"></div>
   *       <div label="zot"></div>
   *     </core-selector>
   *
   * In multi-selection this should be an array of values.
   *
   * Example:
   *
   *     <core-selector id="selector" valueattr="label" multi>
   *       <div label="foo"></div>
   *       <div label="bar"></div>
   *       <div label="zot"></div>
   *     </core-selector>
   *
   *     this.$.selector.selected = ['foo', 'zot'];
   *
   * @attribute selected
   * @type Object
   */
  @JsProperty CoreComponentPage selected(JavaScriptObject val);
  @JsProperty JavaScriptObject selected();

  /**
   * Specifies the attribute to be used for "selected" attribute.
   *
   * @attribute valueattr
   * @type string
   */
  @JsProperty CoreComponentPage valueattr(String val);
  @JsProperty String valueattr();

  /**
   * Specifies the CSS class to be used to add to the selected element.
   *
   * @attribute selectedClass
   * @type string
   */
  @JsProperty CoreComponentPage selectedClass(String val);
  @JsProperty String selectedClass();

  /**
   * Specifies the property to be used to set on the selected element
   * to indicate its active state.
   *
   * @attribute selectedProperty
   * @type string
   */
  @JsProperty CoreComponentPage selectedProperty(String val);
  @JsProperty String selectedProperty();

  /**
   * Returns the currently selected element. In multi-selection this returns
   * an array of selected elements.
   *
   * @attribute selectedItem
   * @type Object
   */
  @JsProperty CoreComponentPage selectedItem(JavaScriptObject val);
  @JsProperty JavaScriptObject selectedItem();

  /**
   * In single selection, this returns the model associated with the
   * selected element.
   *
   * @attribute selectedModel
   * @type Object
   */
  @JsProperty CoreComponentPage selectedModel(JavaScriptObject val);
  @JsProperty JavaScriptObject selectedModel();

  /**
   * In single selection, this returns the selected index.
   *
   * @attribute selectedIndex
   * @type number
   */
  @JsProperty CoreComponentPage selectedIndex(double val);
  @JsProperty double selectedIndex();

  /**
   * The target element that contains items.  If this is not set
   * core-selector is the container.
   *
   * @attribute target
   * @type Object
   */
  @JsProperty CoreComponentPage target(JavaScriptObject val);
  @JsProperty JavaScriptObject target();

  /**
   * This can be used to query nodes from the target node to be used for
   * selection items.  Note this only works if the 'target' property is set.
   *
   * Example:
   *
   *     <core-selector target="{{$.myForm}}" itemsSelector="input[type=radio]"></core-selector>
   *     <form id="myForm">
   *       <label><input type="radio" name="color" value="red"> Red</label> <br>
   *       <label><input type="radio" name="color" value="green"> Green</label> <br>
   *       <label><input type="radio" name="color" value="blue"> Blue</label> <br>
   *       <p>color = {{color}}</p>
   *     </form>
   *
   * @attribute itemSelector
   * @type string
   */
  @JsProperty CoreComponentPage itemSelector(String val);
  @JsProperty String itemSelector();

  /**
   * The event that would be fired from the item element to indicate
   * it is being selected.
   *
   * @attribute activateEvent
   * @type string
   */
  @JsProperty CoreComponentPage activateEvent(String val);
  @JsProperty String activateEvent();

  /**
   * Set this to true to disallow changing the selection via the
   * `activateEvent`.
   *
   * @attribute notap
   * @type boolean
   */
  @JsProperty CoreComponentPage notap(boolean val);
  @JsProperty boolean notap();

  /**
   * Specifies the label for the menu item.
   *
   * @attribute label
   * @type string
   */
  @JsProperty CoreComponentPage label(String val);
  @JsProperty String label();

  /**
   * Files to parse for docs
   *
   * @attribute sources
   * @type Array
   */
  @JsProperty CoreComponentPage sources(JsArray val);
  @JsProperty JsArray sources();

}
