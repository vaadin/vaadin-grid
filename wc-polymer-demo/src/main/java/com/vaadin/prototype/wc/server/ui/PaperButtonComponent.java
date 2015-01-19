package com.vaadin.prototype.wc.server.ui;

import com.vaadin.prototype.wc.gwt.client.ui.PaperButtonState;
import com.vaadin.ui.AbstractComponent;

@SuppressWarnings("serial")
public class PaperButtonComponent extends PaperFocusableComponent {
    protected String[] events() {
      return concat(super.events(), new String[]{"click"});
    }

    protected String[] attributes() {
      return concat(super.attributes(),new String[]{"label","raisedButton","iconSrc","icon"});
    }

    @Override
    protected PaperButtonState getState() {
        return (PaperButtonState) super.getState();
    }


  /**
   * The label of the button.
   *
   * @attribute label
   * @type string
   */
    public void label(String val) {
        getState().label = val;
    }

  /**
   * The label of the button.
   *
   * @attribute label
   * @type string
   */
    public String label() {
        return getState().label;
    }

  /**
   * If true, the button will be styled as a "raised" button.
   *
   * @attribute raisedButton
   * @type boolean
   */
    public void raisedButton(boolean val) {
        getState().raisedButton = val;
    }

  /**
   * If true, the button will be styled as a "raised" button.
   *
   * @attribute raisedButton
   * @type boolean
   */
    public boolean raisedButton() {
        return getState().raisedButton;
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