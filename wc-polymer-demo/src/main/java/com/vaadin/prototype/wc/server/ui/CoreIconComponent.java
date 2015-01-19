package com.vaadin.prototype.wc.server.ui;

import com.vaadin.prototype.wc.gwt.client.ui.CoreIconState;
import com.vaadin.ui.AbstractComponent;

@SuppressWarnings("serial")
public class CoreIconComponent extends BaseComponent {
    protected String[] events() {
      return concat(super.events(), new String[]{});
    }

    protected String[] attributes() {
      return concat(super.attributes(),new String[]{"src","size","icon"});
    }

    @Override
    protected CoreIconState getState() {
        return (CoreIconState) super.getState();
    }


  /**
   * The URL of an image for the icon. If the src property is specified,
   * the icon property should not be.
   *
   * @attribute src
   * @type string
   */
    public void src(String val) {
        getState().src = val;
    }

  /**
   * The URL of an image for the icon. If the src property is specified,
   * the icon property should not be.
   *
   * @attribute src
   * @type string
   */
    public String src() {
        return getState().src;
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

}