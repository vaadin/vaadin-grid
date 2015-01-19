package com.vaadin.prototype.wc.server.ui;

import com.vaadin.prototype.wc.gwt.client.ui.PaperTabState;
import com.vaadin.ui.AbstractComponent;

@SuppressWarnings("serial")
public class PaperTabComponent extends BaseComponent {
    protected String[] events() {
      return concat(super.events(), new String[]{});
    }

    protected String[] attributes() {
      return concat(super.attributes(),new String[]{"noink"});
    }

    @Override
    protected PaperTabState getState() {
        return (PaperTabState) super.getState();
    }


  /**
   * If true, ink ripple effect is disabled.
   *
   * @attribute noink
   * @type boolean
   */
    public void noink(boolean val) {
        getState().noink = val;
    }

  /**
   * If true, ink ripple effect is disabled.
   *
   * @attribute noink
   * @type boolean
   */
    public boolean noink() {
        return getState().noink;
    }

}