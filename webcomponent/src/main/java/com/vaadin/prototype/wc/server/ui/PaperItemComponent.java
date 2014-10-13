package com.vaadin.prototype.wc.server.ui;

import com.vaadin.prototype.wc.gwt.client.ui.PaperItemState;
import com.vaadin.ui.AbstractComponent;

@SuppressWarnings("serial")
public class PaperItemComponent extends BaseComponent {
    protected String[] events() {
      return concat(super.events(), new String[]{});
    }
  
    protected String[] attributes() {
      return concat(super.attributes(),new String[]{"label","iconSrc","icon"});
    }
  
    @Override
    protected PaperItemState getState() {
        return (PaperItemState) super.getState();
    }


  /**
   * The label for the item.
   *
   * @attribute label
   * @type string
   */
    public void label(String val) {
        getState().label = val;
    }

  /**
   * The label for the item.
   *
   * @attribute label
   * @type string
   */
    public String label() {
        return getState().label;
    }

  /**
   * (optional) The URL of an image for an icon to use in the button.
   * Should not use `icon` property if you are using this property.
   *
   * @attribute iconSrc
   * @type string
   */
    public void iconSrc(String val) {
        getState().iconSrc = val;
    }

  /**
   * (optional) The URL of an image for an icon to use in the button.
   * Should not use `icon` property if you are using this property.
   *
   * @attribute iconSrc
   * @type string
   */
    public String iconSrc() {
        return getState().iconSrc;
    }

  /**
   * (optional) Specifies the icon name or index in the set of icons
   * available in the icon set. If using this property, load the icon
   * set separately where the icon is used. Should not use `src`
   * if you are using this property.
   *
   * @attribute icon
   * @type string
   */
    public void icon(String val) {
        getState().icon = val;
    }

  /**
   * (optional) Specifies the icon name or index in the set of icons
   * available in the icon set. If using this property, load the icon
   * set separately where the icon is used. Should not use `src`
   * if you are using this property.
   *
   * @attribute icon
   * @type string
   */
    public String icon() {
        return getState().icon;
    }

}