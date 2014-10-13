package com.vaadin.prototype.wc.gwt.client.ui;

import com.vaadin.shared.AbstractFieldState;

@SuppressWarnings("serial")
public class PaperFocusableState extends AbstractFieldState {
  public boolean active = false;
  public boolean focused = false;
  public boolean pressed = false;
  public boolean disabled = false;
  public boolean isToggle = false;

}
