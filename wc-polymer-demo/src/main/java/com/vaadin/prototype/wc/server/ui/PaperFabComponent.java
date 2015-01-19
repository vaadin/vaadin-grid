package com.vaadin.prototype.wc.server.ui;

import com.vaadin.prototype.wc.gwt.client.ui.PaperFabState;
import com.vaadin.ui.AbstractComponent;

@SuppressWarnings("serial")
public class PaperFabComponent extends PaperIconButtonComponent {
    protected String[] events() {
      return concat(super.events(), new String[]{});
    }

    protected String[] attributes() {
      return concat(super.attributes(),new String[]{"raisedButton"});
    }

    @Override
    protected PaperFabState getState() {
        return (PaperFabState) super.getState();
    }


  /**
   * See [`<paper-button>`](../paper-button).
   *
   * @attribute raisedButton
   * @type boolean
   */
    public void raisedButton(boolean val) {
        getState().raisedButton = val;
    }

  /**
   * See [`<paper-button>`](../paper-button).
   *
   * @attribute raisedButton
   * @type boolean
   */
    public boolean raisedButton() {
        return getState().raisedButton;
    }

}