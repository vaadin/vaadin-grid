package com.vaadin.prototype.wc.server.ui;

import com.vaadin.prototype.wc.gwt.client.ui.CoreComponentPageState;
import com.vaadin.ui.AbstractComponent;

@SuppressWarnings("serial")
public class CoreComponentPageComponent extends CoreMetaComponent {
    protected String[] events() {
      return concat(super.events(), new String[]{"core-response","core-error","core-complete"});
    }

    protected String[] attributes() {
      return concat(super.attributes(),new String[]{"vertical","justify","align","reverse","type","list","src","width","icons","iconSize","offsetX","offsetY","size","icon","active","mode","tallClass","shadow","url","handleAs","auto","params","response","method","headers","body","contentType","withCredentials","multi","selected","valueattr","selectedClass","selectedProperty","selectedItem","selectedModel","selectedIndex","target","itemSelector","activateEvent","notap","label","sources"});
    }

    @Override
    protected CoreComponentPageState getState() {
        return (CoreComponentPageState) super.getState();
    }


  /**
   * Controls if the element lays out vertically or not.
   *
   * @attribute vertical
   * @type boolean
   */
    public void vertical(boolean val) {
        getState().vertical = val;
    }

  /**
   * Controls if the element lays out vertically or not.
   *
   * @attribute vertical
   * @type boolean
   */
    public boolean vertical() {
        return getState().vertical;
    }

  /**
   * Controls how the items are aligned in the main-axis direction. For 
   * example for a horizontal layout, this controls how each item is aligned
   * horizontally.
   *
   * @attribute justify
   * @type string start|center|end|between
   */
    public void justify(String val) {
        getState().justify = val;
    }

  /**
   * Controls how the items are aligned in the main-axis direction. For 
   * example for a horizontal layout, this controls how each item is aligned
   * horizontally.
   *
   * @attribute justify
   * @type string start|center|end|between
   */
    public String justify() {
        return getState().justify;
    }

  /**
   * Controls how the items are aligned in cross-axis direction. For 
   * example for a horizontal layout, this controls how each item is aligned
   * vertically.
   *
   * @attribute align
   * @type string start|center|end
   */
    public void align(String val) {
        getState().align = val;
    }

  /**
   * Controls how the items are aligned in cross-axis direction. For 
   * example for a horizontal layout, this controls how each item is aligned
   * vertically.
   *
   * @attribute align
   * @type string start|center|end
   */
    public String align() {
        return getState().align;
    }

  /**
   * Controls whether or not the items layout in reverse order.
   *
   * @attribute reverse
   * @type boolean
   */
    public void reverse(boolean val) {
        getState().reverse = val;
    }

  /**
   * Controls whether or not the items layout in reverse order.
   *
   * @attribute reverse
   * @type boolean
   */
    public boolean reverse() {
        return getState().reverse;
    }

  /**
   * The type of meta-data.  All meta-data with the same type with be
   * stored together.
   * 
   * @attribute type
   * @type string
   */
    public void type(String val) {
        getState().type = val;
    }

  /**
   * The type of meta-data.  All meta-data with the same type with be
   * stored together.
   * 
   * @attribute type
   * @type string
   */
    public String type() {
        return getState().type;
    }

  /**
   * The URL of the iconset image.
   *
   * @attribute src
   * @type string
   */
    public void src(String val) {
        getState().src = val;
    }

  /**
   * The URL of the iconset image.
   *
   * @attribute src
   * @type string
   */
    public String src() {
        return getState().src;
    }

  /**
   * The width of the iconset image. This must only be specified if the
   * icons are arranged into separate rows inside the image.
   *
   * @attribute width
   * @type string
   */
    public void width(String val) {
        getState().width = val;
    }

  /**
   * The width of the iconset image. This must only be specified if the
   * icons are arranged into separate rows inside the image.
   *
   * @attribute width
   * @type string
   */
    public String width() {
        return getState().width;
    }

  /**
   * A space separated list of names corresponding to icons in the iconset
   * image file. This list must be ordered the same as the icon images
   * in the image file.
   *
   * @attribute icons
   * @type string
   */
    public void icons(String val) {
        getState().icons = val;
    }

  /**
   * A space separated list of names corresponding to icons in the iconset
   * image file. This list must be ordered the same as the icon images
   * in the image file.
   *
   * @attribute icons
   * @type string
   */
    public String icons() {
        return getState().icons;
    }

  /**
   * The size of an individual icon. Note that icons must be square.
   *
   * @attribute iconSize
   * @type number
   */
    public void iconSize(double val) {
        getState().iconSize = val;
    }

  /**
   * The size of an individual icon. Note that icons must be square.
   *
   * @attribute iconSize
   * @type number
   */
    public double iconSize() {
        return getState().iconSize;
    }

  /**
   * The horizontal offset of the icon images in the inconset src image.
   * This is typically used if the image resource contains additional images
   * beside those intended for the iconset.
   *
   * @attribute offsetX
   * @type number
   */
    public void offsetX(double val) {
        getState().offsetX = val;
    }

  /**
   * The horizontal offset of the icon images in the inconset src image.
   * This is typically used if the image resource contains additional images
   * beside those intended for the iconset.
   *
   * @attribute offsetX
   * @type number
   */
    public double offsetX() {
        return getState().offsetX;
    }

  /**
   * The vertical offset of the icon images in the inconset src image.
   * This is typically used if the image resource contains additional images
   * beside those intended for the iconset.
   *
   * @attribute offsetY
   * @type number
   */
    public void offsetY(double val) {
        getState().offsetY = val;
    }

  /**
   * The vertical offset of the icon images in the inconset src image.
   * This is typically used if the image resource contains additional images
   * beside those intended for the iconset.
   *
   * @attribute offsetY
   * @type number
   */
    public double offsetY() {
        return getState().offsetY;
    }

  /**
   * Specifies the size of the icon in pixel units.
   *
   * @attribute size
   * @type string
   */
    public void size(String val) {
        getState().size = val;
    }

  /**
   * Specifies the size of the icon in pixel units.
   *
   * @attribute size
   * @type string
   */
    public String size() {
        return getState().size;
    }

  /**
   * Specifies the icon name or index in the set of icons available in
   * the icon's icon set. If the icon property is specified,
   * the src property should not be.
   *
   * @attribute icon
   * @type string
   */
    public void icon(String val) {
        getState().icon = val;
    }

  /**
   * Specifies the icon name or index in the set of icons available in
   * the icon's icon set. If the icon property is specified,
   * the src property should not be.
   *
   * @attribute icon
   * @type string
   */
    public String icon() {
        return getState().icon;
    }

  /**
   * If true, border is placed around the button to indicate it's
   * active state.
   *
   * @attribute active
   * @type boolean
   */
    public void active(boolean val) {
        getState().active = val;
    }

  /**
   * If true, border is placed around the button to indicate it's
   * active state.
   *
   * @attribute active
   * @type boolean
   */
    public boolean active() {
        return getState().active;
    }

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
    public void mode(String val) {
        getState().mode = val;
    }

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
    public String mode() {
        return getState().mode;
    }

  /**
   * The class used in waterfall-tall mode.  Change this if the header
   * accepts a different class for toggling height, e.g. "medium-tall"
   *
   * @attribute tallClass
   * @type string
   */
    public void tallClass(String val) {
        getState().tallClass = val;
    }

  /**
   * The class used in waterfall-tall mode.  Change this if the header
   * accepts a different class for toggling height, e.g. "medium-tall"
   *
   * @attribute tallClass
   * @type string
   */
    public String tallClass() {
        return getState().tallClass;
    }

  /**
   * If true, the drop-shadow is always shown no matter what mode is set to.
   *
   * @attribute shadow
   * @type boolean
   */
    public void shadow(boolean val) {
        getState().shadow = val;
    }

  /**
   * If true, the drop-shadow is always shown no matter what mode is set to.
   *
   * @attribute shadow
   * @type boolean
   */
    public boolean shadow() {
        return getState().shadow;
    }

  /**
   * The URL target of the request.
   * 
   * @attribute url
   * @type string
   */
    public void url(String val) {
        getState().url = val;
    }

  /**
   * The URL target of the request.
   * 
   * @attribute url
   * @type string
   */
    public String url() {
        return getState().url;
    }

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
    public void handleAs(String val) {
        getState().handleAs = val;
    }

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
    public String handleAs() {
        return getState().handleAs;
    }

  /**
   * If true, automatically performs an Ajax request when either `url` or `params` changes.
   *
   * @attribute auto
   * @type boolean
   */
    public void auto(boolean val) {
        getState().auto = val;
    }

  /**
   * If true, automatically performs an Ajax request when either `url` or `params` changes.
   *
   * @attribute auto
   * @type boolean
   */
    public boolean auto() {
        return getState().auto;
    }

  /**
   * Parameters to send to the specified URL, as JSON.
   *  
   * @attribute params
   * @type string (JSON)
   */
    public void params(String val) {
        getState().params = val;
    }

  /**
   * Parameters to send to the specified URL, as JSON.
   *  
   * @attribute params
   * @type string (JSON)
   */
    public String params() {
        return getState().params;
    }

  /**
   * The HTTP method to use such as 'GET', 'POST', 'PUT', or 'DELETE'.
   * Default is 'GET'.
   *
   * @attribute method
   * @type string
   */
    public void method(String val) {
        getState().method = val;
    }

  /**
   * The HTTP method to use such as 'GET', 'POST', 'PUT', or 'DELETE'.
   * Default is 'GET'.
   *
   * @attribute method
   * @type string
   */
    public String method() {
        return getState().method;
    }

  /**
   * Content type to use when sending data.
   *
   * @attribute contentType
   * @type string
   */
    public void contentType(String val) {
        getState().contentType = val;
    }

  /**
   * Content type to use when sending data.
   *
   * @attribute contentType
   * @type string
   */
    public String contentType() {
        return getState().contentType;
    }

  /**
   * Set the withCredentials flag on the request.
   * 
   * @attribute withCredentials
   * @type boolean
   */
    public void withCredentials(boolean val) {
        getState().withCredentials = val;
    }

  /**
   * Set the withCredentials flag on the request.
   * 
   * @attribute withCredentials
   * @type boolean
   */
    public boolean withCredentials() {
        return getState().withCredentials;
    }

  /**
   * If true, multiple selections are allowed.
   *
   * @attribute multi
   * @type boolean
   */
    public void multi(boolean val) {
        getState().multi = val;
    }

  /**
   * If true, multiple selections are allowed.
   *
   * @attribute multi
   * @type boolean
   */
    public boolean multi() {
        return getState().multi;
    }

  /**
   * Specifies the attribute to be used for "selected" attribute.
   *
   * @attribute valueattr
   * @type string
   */
    public void valueattr(String val) {
        getState().valueattr = val;
    }

  /**
   * Specifies the attribute to be used for "selected" attribute.
   *
   * @attribute valueattr
   * @type string
   */
    public String valueattr() {
        return getState().valueattr;
    }

  /**
   * Specifies the CSS class to be used to add to the selected element.
   * 
   * @attribute selectedClass
   * @type string
   */
    public void selectedClass(String val) {
        getState().selectedClass = val;
    }

  /**
   * Specifies the CSS class to be used to add to the selected element.
   * 
   * @attribute selectedClass
   * @type string
   */
    public String selectedClass() {
        return getState().selectedClass;
    }

  /**
   * Specifies the property to be used to set on the selected element
   * to indicate its active state.
   *
   * @attribute selectedProperty
   * @type string
   */
    public void selectedProperty(String val) {
        getState().selectedProperty = val;
    }

  /**
   * Specifies the property to be used to set on the selected element
   * to indicate its active state.
   *
   * @attribute selectedProperty
   * @type string
   */
    public String selectedProperty() {
        return getState().selectedProperty;
    }

  /**
   * In single selection, this returns the selected index.
   *
   * @attribute selectedIndex
   * @type number
   */
    public void selectedIndex(double val) {
        getState().selectedIndex = val;
    }

  /**
   * In single selection, this returns the selected index.
   *
   * @attribute selectedIndex
   * @type number
   */
    public double selectedIndex() {
        return getState().selectedIndex;
    }

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
    public void itemSelector(String val) {
        getState().itemSelector = val;
    }

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
    public String itemSelector() {
        return getState().itemSelector;
    }

  /**
   * The event that would be fired from the item element to indicate
   * it is being selected.
   *
   * @attribute activateEvent
   * @type string
   */
    public void activateEvent(String val) {
        getState().activateEvent = val;
    }

  /**
   * The event that would be fired from the item element to indicate
   * it is being selected.
   *
   * @attribute activateEvent
   * @type string
   */
    public String activateEvent() {
        return getState().activateEvent;
    }

  /**
   * Set this to true to disallow changing the selection via the
   * `activateEvent`.
   *
   * @attribute notap
   * @type boolean
   */
    public void notap(boolean val) {
        getState().notap = val;
    }

  /**
   * Set this to true to disallow changing the selection via the
   * `activateEvent`.
   *
   * @attribute notap
   * @type boolean
   */
    public boolean notap() {
        return getState().notap;
    }

  /**
   * Specifies the label for the menu item.
   *
   * @attribute label
   * @type string
   */
    public void label(String val) {
        getState().label = val;
    }

  /**
   * Specifies the label for the menu item.
   *
   * @attribute label
   * @type string
   */
    public String label() {
        return getState().label;
    }

}