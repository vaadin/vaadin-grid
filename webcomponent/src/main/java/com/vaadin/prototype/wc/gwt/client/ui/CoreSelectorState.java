package com.vaadin.prototype.wc.gwt.client.ui;

import com.vaadin.shared.AbstractFieldState;

@SuppressWarnings("serial")
public class CoreSelectorState extends AbstractFieldState {
  public boolean multi = false;
  public String valueattr = "name";
  public String selectedClass = "core-selected";
  public String selectedProperty = "";
  public String selectedAttribute = "active";
  public double selectedIndex = -1;
  public String itemSelector = "";
  public String activateEvent = "tap";
  public boolean notap = false;

}
