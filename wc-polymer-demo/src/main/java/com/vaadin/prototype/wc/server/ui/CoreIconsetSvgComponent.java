package com.vaadin.prototype.wc.server.ui;

import com.vaadin.prototype.wc.gwt.client.ui.CoreIconsetSvgState;
import com.vaadin.ui.AbstractComponent;

@SuppressWarnings("serial")
public class CoreIconsetSvgComponent extends CoreMetaComponent {
    protected String[] events() {
      return concat(super.events(), new String[]{});
    }
  
    protected String[] attributes() {
      return concat(super.attributes(),new String[]{"iconSize"});
    }
  
    @Override
    protected CoreIconsetSvgState getState() {
        return (CoreIconsetSvgState) super.getState();
    }


  /**
   * The size of an individual icon. Note that icons must be square.
   *
   * @attribute iconSize
   * @type number
   */
    public void iconSize(double val) {
        getState().iconSize = val;
    }

  /**
   * The size of an individual icon. Note that icons must be square.
   *
   * @attribute iconSize
   * @type number
   */
    public double iconSize() {
        return getState().iconSize;
    }

}