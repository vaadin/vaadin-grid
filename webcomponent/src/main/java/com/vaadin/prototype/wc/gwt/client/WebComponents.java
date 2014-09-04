package com.vaadin.prototype.wc.gwt.client;
import static com.google.gwt.query.client.GQuery.$;
import static com.google.gwt.query.client.GQuery.document;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.js.Js;
import com.vaadin.prototype.wc.gwt.client.JsInterfaces.Window;

/**
 * Example code for a GwtQuery application
 */
public class WebComponents implements EntryPoint {

  public void onModuleLoad() {
      $("<div id='hi'>Hello</div>").appendTo(document);
      
      Window wnd = Js.js("$wnd");
      wnd.alert("Hello JSInterop");
  }

}
