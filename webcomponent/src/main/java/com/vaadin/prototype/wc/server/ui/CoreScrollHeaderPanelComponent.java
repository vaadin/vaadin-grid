package com.vaadin.prototype.wc.server.ui;

import com.vaadin.prototype.wc.gwt.client.ui.CoreScrollHeaderPanelState;
import com.vaadin.ui.AbstractComponent;

@SuppressWarnings("serial")
public class CoreScrollHeaderPanelComponent extends BaseComponent {
    protected String[] events() {
      return concat(super.events(), new String[]{"scroll","core-header-transform"});
    }
  
    protected String[] attributes() {
      return concat(super.attributes(),new String[]{"condenses","noDissolve","noReveal","fixed","keepCondensedHeader","headerHeight","condensedHeaderHeight"});
    }
  
    @Override
    protected CoreScrollHeaderPanelState getState() {
        return (CoreScrollHeaderPanelState) super.getState();
    }


  /**
   * If true, the header's height will condense to `condensedHeaderHeight`
   * as the user scrolls down from the top of the content area.
   *
   * @attribute condenses
   * @type boolean
   */
    public void condenses(boolean val) {
        getState().condenses = val;
    }

  /**
   * If true, the header's height will condense to `condensedHeaderHeight`
   * as the user scrolls down from the top of the content area.
   *
   * @attribute condenses
   * @type boolean
   */
    public boolean condenses() {
        return getState().condenses;
    }

  /**
   * If true, no cross-fade transition from one background to another.
   *
   * @attribute noDissolve
   * @type boolean
   */
    public void noDissolve(boolean val) {
        getState().noDissolve = val;
    }

  /**
   * If true, no cross-fade transition from one background to another.
   *
   * @attribute noDissolve
   * @type boolean
   */
    public boolean noDissolve() {
        return getState().noDissolve;
    }

  /**
   * If true, the header doesn't slide back in when scrolling back up.
   *
   * @attribute noReveal
   * @type boolean
   */
    public void noReveal(boolean val) {
        getState().noReveal = val;
    }

  /**
   * If true, the header doesn't slide back in when scrolling back up.
   *
   * @attribute noReveal
   * @type boolean
   */
    public boolean noReveal() {
        return getState().noReveal;
    }

  /**
   * If true, the header is fixed to the top and never moves away.
   *
   * @attribute fixed
   * @type boolean
   */
    public void fixed(boolean val) {
        getState().fixed = val;
    }

  /**
   * If true, the header is fixed to the top and never moves away.
   *
   * @attribute fixed
   * @type boolean
   */
    public boolean fixed() {
        return getState().fixed;
    }

  /**
   * If true, the condensed header is always shown and not moves away.
   *
   * @attribute keepCondensedHeader
   * @type boolean
   */
    public void keepCondensedHeader(boolean val) {
        getState().keepCondensedHeader = val;
    }

  /**
   * If true, the condensed header is always shown and not moves away.
   *
   * @attribute keepCondensedHeader
   * @type boolean
   */
    public boolean keepCondensedHeader() {
        return getState().keepCondensedHeader;
    }

  /**
   * The height of the header when it is at its full size.
   *
   * By default, the height will be measused when it is ready.  If the height
   * changes later user needs to either set this value to reflect the new
   * height or invoke `measureHeaderHeight()`.
   *
   * @attribute headerHeight
   * @type number
   */
    public void headerHeight(double val) {
        getState().headerHeight = val;
    }

  /**
   * The height of the header when it is at its full size.
   *
   * By default, the height will be measused when it is ready.  If the height
   * changes later user needs to either set this value to reflect the new
   * height or invoke `measureHeaderHeight()`.
   *
   * @attribute headerHeight
   * @type number
   */
    public double headerHeight() {
        return getState().headerHeight;
    }

  /**
   * The height of the header when it is condensed.
   *
   * By default, this will be 1/3 of `headerHeight`.
   *
   * @attribute condensedHeaderHeight
   * @type number
   */
    public void condensedHeaderHeight(double val) {
        getState().condensedHeaderHeight = val;
    }

  /**
   * The height of the header when it is condensed.
   *
   * By default, this will be 1/3 of `headerHeight`.
   *
   * @attribute condensedHeaderHeight
   * @type number
   */
    public double condensedHeaderHeight() {
        return getState().condensedHeaderHeight;
    }

}