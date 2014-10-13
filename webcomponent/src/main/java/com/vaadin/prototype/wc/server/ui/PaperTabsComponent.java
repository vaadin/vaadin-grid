package com.vaadin.prototype.wc.server.ui;

import com.vaadin.prototype.wc.gwt.client.ui.PaperTabsState;
import com.vaadin.ui.AbstractComponent;

@SuppressWarnings("serial")
public class PaperTabsComponent extends CoreSelectorComponent {
    protected String[] events() {
      return concat(super.events(), new String[]{});
    }
  
    protected String[] attributes() {
      return concat(super.attributes(),new String[]{"noink","nobar"});
    }
  
    @Override
    protected PaperTabsState getState() {
        return (PaperTabsState) super.getState();
    }


  /**
   * If true, ink effect is disabled.
   *
   * @attribute noink
   * @type boolean
   */
    public void noink(boolean val) {
        getState().noink = val;
    }

  /**
   * If true, ink effect is disabled.
   *
   * @attribute noink
   * @type boolean
   */
    public boolean noink() {
        return getState().noink;
    }

  /**
   * If true, the bottom bar to indicate the selected tab will not be shown.
   *
   * @attribute nobar
   * @type boolean
   */
    public void nobar(boolean val) {
        getState().nobar = val;
    }

  /**
   * If true, the bottom bar to indicate the selected tab will not be shown.
   *
   * @attribute nobar
   * @type boolean
   */
    public boolean nobar() {
        return getState().nobar;
    }

}