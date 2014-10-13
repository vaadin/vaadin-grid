package com.vaadin.prototype.wc.gwt.client.ui;

import com.vaadin.shared.AbstractFieldState;

@SuppressWarnings("serial")
public class CoreSplitterState extends AbstractFieldState {
  public String direction = "left";
  public String minSize = "";
  public boolean locked = false;
  public boolean allowOverflow = false;

}
