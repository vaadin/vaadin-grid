package com.vaadin.prototype.wc.gwt.client.ui;

import com.vaadin.shared.AbstractFieldState;

@SuppressWarnings("serial")
public class CoreInputState extends AbstractFieldState {
  public String placeholder = "";
  public boolean disabled = false;
  public String type = "text";
  public boolean multiline = false;
  public double rows = 0;
  public String inputValue = "";
  public String value = "";
  public String validate = "null";
  public boolean invalid = false;

}
