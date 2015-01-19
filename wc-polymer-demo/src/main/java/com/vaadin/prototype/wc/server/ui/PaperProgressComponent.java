package com.vaadin.prototype.wc.server.ui;

import com.vaadin.prototype.wc.gwt.client.ui.PaperProgressState;
import com.vaadin.ui.AbstractComponent;

@SuppressWarnings("serial")
public class PaperProgressComponent extends CoreRangeComponent {
    protected String[] events() {
      return concat(super.events(), new String[]{});
    }

    protected String[] attributes() {
      return concat(super.attributes(),new String[]{"secondaryProgress"});
    }

    @Override
    protected PaperProgressState getState() {
        return (PaperProgressState) super.getState();
    }


  /**
   * The number that represents the current secondary progress.
   *
   * @attribute secondaryProgress
   * @type number
   */
    public void secondaryProgress(double val) {
        getState().secondaryProgress = val;
    }

  /**
   * The number that represents the current secondary progress.
   *
   * @attribute secondaryProgress
   * @type number
   */
    public double secondaryProgress() {
        return getState().secondaryProgress;
    }

}