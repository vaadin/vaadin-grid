package com.vaadin.prototype.wc.server.ui;

import com.vaadin.prototype.wc.gwt.client.ui.PaperFocusableState;
import com.vaadin.ui.AbstractComponent;

@SuppressWarnings("serial")
public class PaperFocusableComponent extends BaseComponent {
    protected String[] events() {
      return concat(super.events(), new String[]{});
    }

    protected String[] attributes() {
      return concat(super.attributes(),new String[]{"active","focused","pressed","disabled","isToggle"});
    }

    @Override
    protected PaperFocusableState getState() {
        return (PaperFocusableState) super.getState();
    }


  /**
   * If true, the button is currently active either because the
   * user is holding down the button, or the button is a toggle
   * and is currently in the active state.
   *
   * @attribute active
   * @type boolean
   */
    public void active(boolean val) {
        getState().active = val;
    }

  /**
   * If true, the button is currently active either because the
   * user is holding down the button, or the button is a toggle
   * and is currently in the active state.
   *
   * @attribute active
   * @type boolean
   */
    public boolean active() {
        return getState().active;
    }

  /**
   * If true, the element currently has focus due to keyboard
   * navigation.
   *
   * @attribute focused
   * @type boolean
   */
    public void focused(boolean val) {
        getState().focused = val;
    }

  /**
   * If true, the element currently has focus due to keyboard
   * navigation.
   *
   * @attribute focused
   * @type boolean
   */
    public boolean focused() {
        return getState().focused;
    }

  /**
   * If true, the user is currently holding down the button.
   *
   * @attribute pressed
   * @type boolean
   */
    public void pressed(boolean val) {
        getState().pressed = val;
    }

  /**
   * If true, the user is currently holding down the button.
   *
   * @attribute pressed
   * @type boolean
   */
    public boolean pressed() {
        return getState().pressed;
    }

  /**
   * If true, the user cannot interact with this element.
   *
   * @attribute disabled
   * @type boolean
   */
    public void disabled(boolean val) {
        getState().disabled = val;
    }

  /**
   * If true, the user cannot interact with this element.
   *
   * @attribute disabled
   * @type boolean
   */
    public boolean disabled() {
        return getState().disabled;
    }

  /**
   * If true, the button toggles the active state with each tap.
   * Otherwise, the button becomes active when the user is holding
   * it down.
   *
   * @attribute isToggle
   * @type boolean
   */
    public void isToggle(boolean val) {
        getState().isToggle = val;
    }

  /**
   * If true, the button toggles the active state with each tap.
   * Otherwise, the button becomes active when the user is holding
   * it down.
   *
   * @attribute isToggle
   * @type boolean
   */
    public boolean isToggle() {
        return getState().isToggle;
    }

}