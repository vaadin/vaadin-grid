package com.vaadin.prototype.wc.server.ui;

import com.vaadin.prototype.wc.gwt.client.ui.CoreDropdownState;
import com.vaadin.ui.AbstractComponent;

@SuppressWarnings("serial")
public class CoreDropdownComponent extends BaseComponent {
    protected String[] events() {
      return concat(super.events(), new String[]{});
    }

    protected String[] attributes() {
      return concat(super.attributes(),new String[]{"relatedTarget","opened","halign","valign","margin","transition"});
    }

    @Override
    protected CoreDropdownState getState() {
        return (CoreDropdownState) super.getState();
    }


  /**
   * If true, the menu is currently visible.
   *
   * @attribute opened
   * @type boolean
   */
    public void opened(boolean val) {
        getState().opened = val;
    }

  /**
   * If true, the menu is currently visible.
   *
   * @attribute opened
   * @type boolean
   */
    public boolean opened() {
        return getState().opened;
    }

  /**
   * A pixel amount around the dropdown that will be reserved. It's useful for
   * ensuring that, for example, a shadow displayed outside the dropdown will
   * always be visible.
   *
   * @attribute margin
   * @type number
   */
    public void margin(double val) {
        getState().margin = val;
    }

  /**
   * A pixel amount around the dropdown that will be reserved. It's useful for
   * ensuring that, for example, a shadow displayed outside the dropdown will
   * always be visible.
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