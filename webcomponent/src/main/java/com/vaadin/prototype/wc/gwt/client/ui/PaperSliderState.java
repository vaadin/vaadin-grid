package com.vaadin.prototype.wc.gwt.client.ui;

import com.vaadin.shared.AbstractFieldState;

@SuppressWarnings("serial")
public class PaperSliderState extends CoreRangeState {
  public boolean snaps = false;
  public boolean pin = false;
  public boolean disabled = false;
  public double secondaryProgress = 0;
  public boolean editable = false;
  public double immediateValue = 0;

}
