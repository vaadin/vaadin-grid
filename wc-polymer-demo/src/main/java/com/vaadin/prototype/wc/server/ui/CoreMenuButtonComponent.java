package com.vaadin.prototype.wc.server.ui;

import com.vaadin.prototype.wc.gwt.client.ui.CoreMenuButtonState;
import com.vaadin.ui.AbstractComponent;

@SuppressWarnings("serial")
public class CoreMenuButtonComponent extends BaseComponent {
    protected String[] events() {
      return concat(super.events(), new String[]{});
    }

    protected String[] attributes() {
      return concat(super.attributes(),new String[]{"icon","selected","opened","inlineMenu","halign","valign"});
    }

    @Override
    protected CoreMenuButtonState getState() {
        return (CoreMenuButtonState) super.getState();
    }


  /**
   * The icon to display.
   * @attribute icon
   * @type string
   */
    public void icon(String val) {
        getState().icon = val;
    }

  /**
   * The icon to display.
   * @attribute icon
   * @type string
   */
    public String icon() {
        return getState().icon;
    }

  /**
   * The index of the selected menu item.
   * @attribute selected
   * @type number
   */
    public void selected(double val) {
        getState().selected = val;
    }

  /**
   * The index of the selected menu item.
   * @attribute selected
   * @type number
   */
    public double selected() {
        return getState().selected;
    }

  /**
   * Set to true to open the menu.
   * @attribute opened
   * @type boolean
   */
    public void opened(boolean val) {
        getState().opened = val;
    }

  /**
   * Set to true to open the menu.
   * @attribute opened
   * @type boolean
   */
    public boolean opened() {
        return getState().opened;
    }

  /**
   * Set to true to cause the menu popup to be displayed inline rather 
   * than in its own layer.
   * @attribute inlineMenu
   * @type boolean
   */
    public void inlineMenu(boolean val) {
        getState().inlineMenu = val;
    }

  /**
   * Set to true to cause the menu popup to be displayed inline rather 
   * than in its own layer.
   * @attribute inlineMenu
   * @type boolean
   */
    public boolean inlineMenu() {
        return getState().inlineMenu;
    }

  /**
   * Horizontally align the overlay with the button.
   * @attribute halign
   * @type string
   */
    public void halign(String val) {
        getState().halign = val;
    }

  /**
   * Horizontally align the overlay with the button.
   * @attribute halign
   * @type string
   */
    public String halign() {
        return getState().halign;
    }

  /**
   * Display the overlay on top or below the button.
   * @attribute valign
   * @type string
   */
    public void valign(String val) {
        getState().valign = val;
    }

  /**
   * Display the overlay on top or below the button.
   * @attribute valign
   * @type string
   */
    public String valign() {
        return getState().valign;
    }

}