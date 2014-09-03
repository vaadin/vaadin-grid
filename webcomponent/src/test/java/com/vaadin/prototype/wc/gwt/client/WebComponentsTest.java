package com.vaadin.prototype.wc.gwt.client;

import static com.google.gwt.query.client.GQuery.$;

import com.google.gwt.junit.client.GWTTestCase;
/**
 * Test class for WebComponents entry-point
 */
public class WebComponentsTest extends GWTTestCase {

  public String getModuleName() {
    return "com.vaadin.prototype.wc.gwt.WebComponents";
  }

  public void testOnModuleLoad() {
    // run onModuleLoad
    WebComponents a = new WebComponents();
    a.onModuleLoad();
    
    assertFalse($("#hi").isEmpty());
  }
}
