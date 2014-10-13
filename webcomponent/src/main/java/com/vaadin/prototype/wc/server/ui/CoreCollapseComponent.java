package com.vaadin.prototype.wc.server.ui;

import com.vaadin.prototype.wc.gwt.client.ui.CoreCollapseState;
import com.vaadin.ui.AbstractComponent;

@SuppressWarnings("serial")
public class CoreCollapseComponent extends BaseComponent {
    protected String[] events() {
      return concat(super.events(), new String[]{"core-resize"});
    }
  
    protected String[] attributes() {
      return concat(super.attributes(),new String[]{"target","horizontal","opened","duration","fixedSize"});
    }
  
    @Override
    protected CoreCollapseState getState() {
        return (CoreCollapseState) super.getState();
    }


  /**
   * If true, the orientation is horizontal; otherwise is vertical.
   *
   * @attribute horizontal
   * @type boolean
   */
    public void horizontal(boolean val) {
        getState().horizontal = val;
    }

  /**
   * If true, the orientation is horizontal; otherwise is vertical.
   *
   * @attribute horizontal
   * @type boolean
   */
    public boolean horizontal() {
        return getState().horizontal;
    }

  /**
   * Set opened to true to show the collapse element and to false to hide it.
   *
   * @attribute opened
   * @type boolean
   */
    public void opened(boolean val) {
        getState().opened = val;
    }

  /**
   * Set opened to true to show the collapse element and to false to hide it.
   *
   * @attribute opened
   * @type boolean
   */
    public boolean opened() {
        return getState().opened;
    }

  /**
   * Collapsing/expanding animation duration in second.
   *
   * @attribute duration
   * @type number
   */
    public void duration(double val) {
        getState().duration = val;
    }

  /**
   * Collapsing/expanding animation duration in second.
   *
   * @attribute duration
   * @type number
   */
    public double duration() {
        return getState().duration;
    }

  /**
   * If true, the size of the target element is fixed and is set
   * on the element.  Otherwise it will try to 
   * use auto to determine the natural size to use
   * for collapsing/expanding.
   *
   * @attribute fixedSize
   * @type boolean
   */
    public void fixedSize(boolean val) {
        getState().fixedSize = val;
    }

  /**
   * If true, the size of the target element is fixed and is set
   * on the element.  Otherwise it will try to 
   * use auto to determine the natural size to use
   * for collapsing/expanding.
   *
   * @attribute fixedSize
   * @type boolean
   */
    public boolean fixedSize() {
        return getState().fixedSize;
    }

}