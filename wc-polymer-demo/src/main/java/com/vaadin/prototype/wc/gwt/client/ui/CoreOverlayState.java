package com.vaadin.prototype.wc.gwt.client.ui;

import com.vaadin.shared.AbstractFieldState;

@SuppressWarnings("serial")
public class CoreOverlayState extends AbstractFieldState {
  public boolean opened = false;
  public boolean backdrop = false;
  public boolean layered = false;
  public boolean autoCloseDisabled = false;
  public String closeAttribute = "core-overlay-toggle";
  public String closeSelector = "";
  public double margin = 0;
  public String transition = "core-transition-fade";

}
