package com.vaadin.prototype.wc.server.ui;

import com.vaadin.prototype.wc.gwt.client.ui.CoreItemState;
import com.vaadin.ui.AbstractComponent;

@SuppressWarnings("serial")
public class CoreItemComponent extends BaseComponent {
    protected String[] events() {
      return concat(super.events(), new String[]{});
    }
  
    protected String[] attributes() {
      return concat(super.attributes(),new String[]{"src","icon","label"});
    }
  
    @Override
    protected CoreItemState getState() {
        return (CoreItemState) super.getState();
    }


  /**
   * The URL of an image for the icon.
   *
   * @attribute src
   * @type string
   */
    public void src(String val) {
        getState().src = val;
    }

  /**
   * The URL of an image for the icon.
   *
   * @attribute src
   * @type string
   */
    public String src() {
        return getState().src;
    }

  /**
   * Specifies the icon from the Polymer icon set.
   *
   * @attribute icon
   * @type string
   */
    public void icon(String val) {
        getState().icon = val;
    }

  /**
   * Specifies the icon from the Polymer icon set.
   *
   * @attribute icon
   * @type string
   */
    public String icon() {
        return getState().icon;
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