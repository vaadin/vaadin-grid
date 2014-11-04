package com.vaadin.prototype.wc.server.ui;

import com.vaadin.prototype.wc.gwt.client.ui.CoreIconButtonState;
import com.vaadin.ui.AbstractComponent;

@SuppressWarnings("serial")
public class CoreIconButtonComponent extends BaseComponent {
    protected String[] events() {
      return concat(super.events(), new String[]{});
    }
  
    protected String[] attributes() {
      return concat(super.attributes(),new String[]{"src","active","icon"});
    }
  
    @Override
    protected CoreIconButtonState getState() {
        return (CoreIconButtonState) super.getState();
    }


  /**
   * The URL of an image for the icon.  Should not use `icon` property
   * if you are using this property.
   *
   * @attribute src
   * @type string
   */
    public void src(String val) {
        getState().src = val;
    }

  /**
   * The URL of an image for the icon.  Should not use `icon` property
   * if you are using this property.
   *
   * @attribute src
   * @type string
   */
    public String src() {
        return getState().src;
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
   * Specifies the icon name or index in the set of icons available in
   * the icon set.  Should not use `src` property if you are using this
   * property.
   *
   * @attribute icon
   * @type string
   */
    public void icon(String val) {
        getState().icon = val;
    }

  /**
   * Specifies the icon name or index in the set of icons available in
   * the icon set.  Should not use `src` property if you are using this
   * property.
   *
   * @attribute icon
   * @type string
   */
    public String icon() {
        return getState().icon;
    }

}