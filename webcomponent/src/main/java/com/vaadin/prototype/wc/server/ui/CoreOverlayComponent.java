package com.vaadin.prototype.wc.server.ui;

import com.vaadin.prototype.wc.gwt.client.ui.CoreOverlayState;
import com.vaadin.ui.AbstractComponent;

@SuppressWarnings("serial")
public class CoreOverlayComponent extends BaseComponent {
    protected String[] events() {
      return concat(super.events(), new String[]{});
    }
  
    protected String[] attributes() {
      return concat(super.attributes(),new String[]{"target","sizingTarget","opened","backdrop","layered","autoCloseDisabled","closeAttribute","closeSelector","margin","transition"});
    }
  
    @Override
    protected CoreOverlayState getState() {
        return (CoreOverlayState) super.getState();
    }


  /**
   * Set opened to true to show an overlay and to false to hide it.
   * A `core-overlay` may be made initially opened by setting its
   * `opened` attribute.
   * @attribute opened
   * @type boolean
   */
    public void opened(boolean val) {
        getState().opened = val;
    }

  /**
   * Set opened to true to show an overlay and to false to hide it.
   * A `core-overlay` may be made initially opened by setting its
   * `opened` attribute.
   * @attribute opened
   * @type boolean
   */
    public boolean opened() {
        return getState().opened;
    }

  /**
   * If true, the overlay has a backdrop darkening the rest of the screen.
   * The backdrop element is attached to the document body and may be styled
   * with the class `core-overlay-backdrop`. When opened the `core-opened`
   * class is applied.
   *
   * @attribute backdrop
   * @type boolean
   */    
    public void backdrop(boolean val) {
        getState().backdrop = val;
    }

  /**
   * If true, the overlay has a backdrop darkening the rest of the screen.
   * The backdrop element is attached to the document body and may be styled
   * with the class `core-overlay-backdrop`. When opened the `core-opened`
   * class is applied.
   *
   * @attribute backdrop
   * @type boolean
   */    
    public boolean backdrop() {
        return getState().backdrop;
    }

  /**
   * If true, the overlay is guaranteed to display above page content.
   *
   * @attribute layered
   * @type boolean
   */
    public void layered(boolean val) {
        getState().layered = val;
    }

  /**
   * If true, the overlay is guaranteed to display above page content.
   *
   * @attribute layered
   * @type boolean
   */
    public boolean layered() {
        return getState().layered;
    }

  /**
   * By default an overlay will close automatically if the user
   * taps outside it or presses the escape key. Disable this
   * behavior by setting the `autoCloseDisabled` property to true.
   * @attribute autoCloseDisabled
   * @type boolean
   */
    public void autoCloseDisabled(boolean val) {
        getState().autoCloseDisabled = val;
    }

  /**
   * By default an overlay will close automatically if the user
   * taps outside it or presses the escape key. Disable this
   * behavior by setting the `autoCloseDisabled` property to true.
   * @attribute autoCloseDisabled
   * @type boolean
   */
    public boolean autoCloseDisabled() {
        return getState().autoCloseDisabled;
    }

  /**
   * This property specifies an attribute on elements that should
   * close the overlay on tap. Should not set `closeSelector` if this
   * is set.
   *
   * @attribute closeAttribute
   * @type string
   */
    public void closeAttribute(String val) {
        getState().closeAttribute = val;
    }

  /**
   * This property specifies an attribute on elements that should
   * close the overlay on tap. Should not set `closeSelector` if this
   * is set.
   *
   * @attribute closeAttribute
   * @type string
   */
    public String closeAttribute() {
        return getState().closeAttribute;
    }

  /**
   * This property specifies a selector matching elements that should
   * close the overlay on tap. Should not set `closeAttribute` if this
   * is set.
   *
   * @attribute closeSelector
   * @type string
   */
    public void closeSelector(String val) {
        getState().closeSelector = val;
    }

  /**
   * This property specifies a selector matching elements that should
   * close the overlay on tap. Should not set `closeAttribute` if this
   * is set.
   *
   * @attribute closeSelector
   * @type string
   */
    public String closeSelector() {
        return getState().closeSelector;
    }

  /**
   * A `core-overlay` target's size is constrained to the window size.
   * The `margin` property specifies a pixel amount around the overlay 
   * that will be reserved. It's useful for ensuring that, for example, 
   * a shadow displayed outside the target will always be visible.
   *
   * @attribute margin
   * @type number
   */
    public void margin(double val) {
        getState().margin = val;
    }

  /**
   * A `core-overlay` target's size is constrained to the window size.
   * The `margin` property specifies a pixel amount around the overlay 
   * that will be reserved. It's useful for ensuring that, for example, 
   * a shadow displayed outside the target will always be visible.
   *
   * @attribute margin
   * @type number
   */
    public double margin() {
        return getState().margin;
    }

  /**
   * The transition property specifies a string which identifies a 
   * <a href="../core-transition/">`core-transition`</a> element that 
   * will be used to help the overlay open and close. The default
   * `core-transition-fade` will cause the overlay to fade in and out.
   *
   * @attribute transition
   * @type string
   */
    public void transition(String val) {
        getState().transition = val;
    }

  /**
   * The transition property specifies a string which identifies a 
   * <a href="../core-transition/">`core-transition`</a> element that 
   * will be used to help the overlay open and close. The default
   * `core-transition-fade` will cause the overlay to fade in and out.
   *
   * @attribute transition
   * @type string
   */
    public String transition() {
        return getState().transition;
    }

}