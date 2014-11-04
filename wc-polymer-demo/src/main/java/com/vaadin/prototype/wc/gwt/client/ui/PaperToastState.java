package com.vaadin.prototype.wc.gwt.client.ui;

import com.vaadin.shared.AbstractFieldState;

@SuppressWarnings("serial")
public class PaperToastState extends AbstractFieldState {
  public String text = "";
  public double duration = 3000;
  public boolean opened = false;
  public String responsiveWidth = "480px";
  public boolean swipeDisabled = false;

}
