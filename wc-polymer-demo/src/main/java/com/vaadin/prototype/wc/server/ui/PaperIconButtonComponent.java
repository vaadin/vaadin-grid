package com.vaadin.prototype.wc.server.ui;

import com.vaadin.prototype.wc.gwt.client.ui.PaperIconButtonState;
import com.vaadin.ui.AbstractComponent;

@SuppressWarnings("serial")
public class PaperIconButtonComponent extends PaperButtonComponent {
    protected String[] events() {
      return concat(super.events(), new String[]{});
    }

    protected String[] attributes() {
      return concat(super.attributes(),new String[]{"fill"});
    }

    @Override
    protected PaperIconButtonState getState() {
        return (PaperIconButtonState) super.getState();
    }


  /**
   * If true, the ripple expands to a square to fill the containing box.
   *
   * @attribute fill
   * @type boolean
   */
    public void fill(boolean val) {
        getState().fill = val;
    }

  /**
   * If true, the ripple expands to a square to fill the containing box.
   *
   * @attribute fill
   * @type boolean
   */
    public boolean fill() {
        return getState().fill;
    }

}