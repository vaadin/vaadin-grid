package com.vaadin.prototype.wc.server.ui;

import com.vaadin.prototype.wc.gwt.client.ui.CoreDropdownOverlayState;
import com.vaadin.ui.AbstractComponent;

@SuppressWarnings("serial")
public class CoreDropdownOverlayComponent extends CoreOverlayComponent {
    protected String[] events() {
      return concat(super.events(), new String[]{});
    }

    protected String[] attributes() {
      return concat(super.attributes(),new String[]{"relatedTarget","halign","valign","margin"});
    }

    @Override
    protected CoreDropdownOverlayState getState() {
        return (CoreDropdownOverlayState) super.getState();
    }


  /**
   * A pixel amount around the overlay that will be reserved. It's useful for
   * ensuring that, for example, a shadow displayed outside the overlay will
   * always be visible.
   *
   * @attribute margin
   * @type number
   */
    public void margin(double val) {
        getState().margin = val;
    }

  /**
   * A pixel amount around the overlay that will be reserved. It's useful for
   * ensuring that, for example, a shadow displayed outside the overlay will
   * always be visible.
   *
   * @attribute margin
   * @type number
   */
    public double margin() {
        return getState().margin;
    }

}