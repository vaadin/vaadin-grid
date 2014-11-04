package com.vaadin.prototype.wc.gwt.client.ui;

import com.vaadin.shared.AbstractFieldState;

@SuppressWarnings("serial")
public class CoreRangeState extends AbstractFieldState {
  public double value = 0;
  public double min = 0;
  public double max = 100;
  public double step = 1;
  public double ratio = 0;

}
