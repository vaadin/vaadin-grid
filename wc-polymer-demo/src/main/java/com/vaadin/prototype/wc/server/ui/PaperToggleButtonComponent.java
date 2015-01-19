package com.vaadin.prototype.wc.server.ui;

import com.vaadin.prototype.wc.gwt.client.ui.PaperToggleButtonState;
import com.vaadin.ui.AbstractComponent;

@SuppressWarnings("serial")
public class PaperToggleButtonComponent extends BaseComponent {
    protected String[] events() {
      return concat(super.events(), new String[]{"change"});
    }

    protected String[] attributes() {
      return concat(super.attributes(),new String[]{"checked"});
    }

    @Override
    protected PaperToggleButtonState getState() {
        return (PaperToggleButtonState) super.getState();
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

}