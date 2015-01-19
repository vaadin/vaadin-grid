package com.vaadin.prototype.wc.server.ui;

import com.vaadin.prototype.wc.gwt.client.ui.PaperRippleState;
import com.vaadin.ui.AbstractComponent;

@SuppressWarnings("serial")
public class PaperRippleComponent extends BaseComponent {
    protected String[] events() {
      return concat(super.events(), new String[]{});
    }

    protected String[] attributes() {
      return concat(super.attributes(),new String[]{"initialOpacity","opacityDecayVelocity"});
    }

    @Override
    protected PaperRippleState getState() {
        return (PaperRippleState) super.getState();
    }


  /**
   * The initial opacity set on the wave.
   *
   * @attribute initialOpacity
   * @type number
   */
    public void initialOpacity(double val) {
        getState().initialOpacity = val;
    }

  /**
   * The initial opacity set on the wave.
   *
   * @attribute initialOpacity
   * @type number
   */
    public double initialOpacity() {
        return getState().initialOpacity;
    }

  /**
   * How fast (opacity per second) the wave fades out.
   *
   * @attribute opacityDecayVelocity
   * @type number
   */
    public void opacityDecayVelocity(double val) {
        getState().opacityDecayVelocity = val;
    }

  /**
   * How fast (opacity per second) the wave fades out.
   *
   * @attribute opacityDecayVelocity
   * @type number
   */
    public double opacityDecayVelocity() {
        return getState().opacityDecayVelocity;
    }

}