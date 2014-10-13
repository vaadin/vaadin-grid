package com.vaadin.prototype.wc.server.ui;

import com.vaadin.prototype.wc.gwt.client.ui.CoreSplitterState;
import com.vaadin.ui.AbstractComponent;

@SuppressWarnings("serial")
public class CoreSplitterComponent extends BaseComponent {
    protected String[] events() {
      return concat(super.events(), new String[]{});
    }
  
    protected String[] attributes() {
      return concat(super.attributes(),new String[]{"direction","minSize","locked","allowOverflow"});
    }
  
    @Override
    protected CoreSplitterState getState() {
        return (CoreSplitterState) super.getState();
    }


  /**
   * Possible values are `left`, `right`, `up` and `down`.
   *
   * @attribute direction
   * @type string
   */
    public void direction(String val) {
        getState().direction = val;
    }

  /**
   * Possible values are `left`, `right`, `up` and `down`.
   *
   * @attribute direction
   * @type string
   */
    public String direction() {
        return getState().direction;
    }

  /**
   * Minimum width to which the splitter target can be sized, e.g. 
   * `minSize="100px"`
   *
   * @attribute minSize
   * @type string
   */
    public void minSize(String val) {
        getState().minSize = val;
    }

  /**
   * Minimum width to which the splitter target can be sized, e.g. 
   * `minSize="100px"`
   *
   * @attribute minSize
   * @type string
   */
    public String minSize() {
        return getState().minSize;
    }

  /**
   * Locks the split bar so it can't be dragged.
   *
   * @attribute locked
   * @type boolean
   */
    public void locked(boolean val) {
        getState().locked = val;
    }

  /**
   * Locks the split bar so it can't be dragged.
   *
   * @attribute locked
   * @type boolean
   */
    public boolean locked() {
        return getState().locked;
    }

  /**
   * By default the parent and siblings of the splitter are set to overflow hidden. This helps
   * avoid elements bleeding outside the splitter regions. Set this property to true to allow
   * these elements to overflow.
   *
   * @attribute allowOverflow
   * @type boolean
   */
    public void allowOverflow(boolean val) {
        getState().allowOverflow = val;
    }

  /**
   * By default the parent and siblings of the splitter are set to overflow hidden. This helps
   * avoid elements bleeding outside the splitter regions. Set this property to true to allow
   * these elements to overflow.
   *
   * @attribute allowOverflow
   * @type boolean
   */
    public boolean allowOverflow() {
        return getState().allowOverflow;
    }

}