package com.vaadin.prototype.wc.server.ui;

import com.vaadin.prototype.wc.gwt.client.ui.PaperShadowState;
import com.vaadin.ui.AbstractComponent;

@SuppressWarnings("serial")
public class PaperShadowComponent extends BaseComponent {
    protected String[] events() {
      return concat(super.events(), new String[]{});
    }
  
    protected String[] attributes() {
      return concat(super.attributes(),new String[]{"target","z","animated","hasPosition"});
    }
  
    @Override
    protected PaperShadowState getState() {
        return (PaperShadowState) super.getState();
    }


  /**
   * The z-depth of this shadow, from 0-5.
   *
   * @attribute z
   * @type number
   */
    public void z(double val) {
        getState().z = val;
    }

  /**
   * The z-depth of this shadow, from 0-5.
   *
   * @attribute z
   * @type number
   */
    public double z() {
        return getState().z;
    }

  /**
   * If true, the shadow animates between z-depth changes.
   *
   * @attribute animated
   * @type boolean
   */
    public void animated(boolean val) {
        getState().animated = val;
    }

  /**
   * If true, the shadow animates between z-depth changes.
   *
   * @attribute animated
   * @type boolean
   */
    public boolean animated() {
        return getState().animated;
    }

  /**
   * Workaround: getComputedStyle is wrong sometimes so `paper-shadow`
   * may overwrite the `position` CSS property. Set this property to
   * true to prevent this.
   *
   * @attribute hasPosition
   * @type boolean
   */
    public void hasPosition(boolean val) {
        getState().hasPosition = val;
    }

  /**
   * Workaround: getComputedStyle is wrong sometimes so `paper-shadow`
   * may overwrite the `position` CSS property. Set this property to
   * true to prevent this.
   *
   * @attribute hasPosition
   * @type boolean
   */
    public boolean hasPosition() {
        return getState().hasPosition;
    }

}