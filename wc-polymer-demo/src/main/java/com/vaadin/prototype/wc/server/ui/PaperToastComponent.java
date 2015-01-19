package com.vaadin.prototype.wc.server.ui;

import com.vaadin.prototype.wc.gwt.client.ui.PaperToastState;
import com.vaadin.ui.AbstractComponent;

@SuppressWarnings("serial")
public class PaperToastComponent extends BaseComponent {
    protected String[] events() {
      return concat(super.events(), new String[]{});
    }

    protected String[] attributes() {
      return concat(super.attributes(),new String[]{"text","duration","opened","responsiveWidth","swipeDisabled"});
    }

    @Override
    protected PaperToastState getState() {
        return (PaperToastState) super.getState();
    }


  /**
   * The text shows in a toast.
   *
   * @attribute text
   * @type string
   */
    public void text(String val) {
        getState().text = val;
    }

  /**
   * The text shows in a toast.
   *
   * @attribute text
   * @type string
   */
    public String text() {
        return getState().text;
    }

  /**
   * The duration in milliseconds to show the toast.
   *
   * @attribute duration
   * @type number
   */
    public void duration(double val) {
        getState().duration = val;
    }

  /**
   * The duration in milliseconds to show the toast.
   *
   * @attribute duration
   * @type number
   */
    public double duration() {
        return getState().duration;
    }

  /**
   * Set opened to true to show the toast and to false to hide it.
   *
   * @attribute opened
   * @type boolean
   */
    public void opened(boolean val) {
        getState().opened = val;
    }

  /**
   * Set opened to true to show the toast and to false to hide it.
   *
   * @attribute opened
   * @type boolean
   */
    public boolean opened() {
        return getState().opened;
    }

  /**
   * Min-width when the toast changes to narrow layout.  In narrow layout,
   * the toast fits at the bottom of the screen when opened.
   *
   * @attribute responsiveWidth
   * @type string
   */
    public void responsiveWidth(String val) {
        getState().responsiveWidth = val;
    }

  /**
   * Min-width when the toast changes to narrow layout.  In narrow layout,
   * the toast fits at the bottom of the screen when opened.
   *
   * @attribute responsiveWidth
   * @type string
   */
    public String responsiveWidth() {
        return getState().responsiveWidth;
    }

  /**
   * If true, the toast can't be swiped.
   *
   * @attribute swipeDisabled
   * @type boolean
   */
    public void swipeDisabled(boolean val) {
        getState().swipeDisabled = val;
    }

  /**
   * If true, the toast can't be swiped.
   *
   * @attribute swipeDisabled
   * @type boolean
   */
    public boolean swipeDisabled() {
        return getState().swipeDisabled;
    }

}