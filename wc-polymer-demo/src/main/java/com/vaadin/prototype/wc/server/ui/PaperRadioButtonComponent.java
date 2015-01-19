package com.vaadin.prototype.wc.server.ui;

import com.vaadin.prototype.wc.gwt.client.ui.PaperRadioButtonState;
import com.vaadin.ui.AbstractComponent;

@SuppressWarnings("serial")
public class PaperRadioButtonComponent extends BaseComponent {
    protected String[] events() {
      return concat(super.events(), new String[]{"change"});
    }

    protected String[] attributes() {
      return concat(super.attributes(),new String[]{"checked","label","toggles","disabled"});
    }

    @Override
    protected PaperRadioButtonState getState() {
        return (PaperRadioButtonState) super.getState();
    }


  /**
   * Gets or sets the state, `true` is checked and `false` is unchecked.
   *
   * @attribute checked
   * @type boolean
   */
    public void checked(boolean val) {
        getState().checked = val;
    }

  /**
   * Gets or sets the state, `true` is checked and `false` is unchecked.
   *
   * @attribute checked
   * @type boolean
   */
    public boolean checked() {
        return getState().checked;
    }

  /**
   * The label for the radio button.
   *
   * @attribute label
   * @type string
   */
    public void label(String val) {
        getState().label = val;
    }

  /**
   * The label for the radio button.
   *
   * @attribute label
   * @type string
   */
    public String label() {
        return getState().label;
    }

  /**
   * Normally the user cannot uncheck the radio button by tapping once
   * checked.  Setting this property to `true` makes the radio button
   * toggleable from checked to unchecked.
   *
   * @attribute toggles
   * @type boolean
   */
    public void toggles(boolean val) {
        getState().toggles = val;
    }

  /**
   * Normally the user cannot uncheck the radio button by tapping once
   * checked.  Setting this property to `true` makes the radio button
   * toggleable from checked to unchecked.
   *
   * @attribute toggles
   * @type boolean
   */
    public boolean toggles() {
        return getState().toggles;
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

}