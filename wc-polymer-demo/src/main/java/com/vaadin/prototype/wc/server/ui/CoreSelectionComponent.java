package com.vaadin.prototype.wc.server.ui;

import com.vaadin.prototype.wc.gwt.client.ui.CoreSelectionState;
import com.vaadin.ui.AbstractComponent;

@SuppressWarnings("serial")
public class CoreSelectionComponent extends BaseComponent {
    protected String[] events() {
      return concat(super.events(), new String[]{});
    }

    protected String[] attributes() {
      return concat(super.attributes(),new String[]{"multi"});
    }

    @Override
    protected CoreSelectionState getState() {
        return (CoreSelectionState) super.getState();
    }


  /**
   * If true, multiple selections are allowed.
   *
   * @attribute multi
   * @type boolean
   */
    public void multi(boolean val) {
        getState().multi = val;
    }

  /**
   * If true, multiple selections are allowed.
   *
   * @attribute multi
   * @type boolean
   */
    public boolean multi() {
        return getState().multi;
    }

}