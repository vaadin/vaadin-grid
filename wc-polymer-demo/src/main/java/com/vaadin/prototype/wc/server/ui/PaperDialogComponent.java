package com.vaadin.prototype.wc.server.ui;

import com.vaadin.prototype.wc.gwt.client.ui.PaperDialogState;
import com.vaadin.ui.AbstractComponent;

@SuppressWarnings("serial")
public class PaperDialogComponent extends BaseComponent {
    protected String[] events() {
      return concat(super.events(), new String[]{});
    }

    protected String[] attributes() {
      return concat(super.attributes(),new String[]{"opened","heading","transition"});
    }

    @Override
    protected PaperDialogState getState() {
        return (PaperDialogState) super.getState();
    }


  /**
   * Set opened to true to show the dialog and to false to hide it.
   * A dialog may be made intially opened by setting its opened attribute.
   * @attribute opened
   * @type boolean
   */
    public void opened(boolean val) {
        getState().opened = val;
    }

  /**
   * Set opened to true to show the dialog and to false to hide it.
   * A dialog may be made intially opened by setting its opened attribute.
   * @attribute opened
   * @type boolean
   */
    public boolean opened() {
        return getState().opened;
    }

  /**
   * @attribute heading
   * @type string
   */
    public void heading(String val) {
        getState().heading = val;
    }

  /**
   * @attribute heading
   * @type string
   */
    public String heading() {
        return getState().heading;
    }

  /**
   * Set this property to the id of a <core-transition> element to specify
   * the transition to use when opening/closing this dialog.
   *
   * @attribute transition
   * @type string
   */
    public void transition(String val) {
        getState().transition = val;
    }

  /**
   * Set this property to the id of a <core-transition> element to specify
   * the transition to use when opening/closing this dialog.
   *
   * @attribute transition
   * @type string
   */
    public String transition() {
        return getState().transition;
    }

}