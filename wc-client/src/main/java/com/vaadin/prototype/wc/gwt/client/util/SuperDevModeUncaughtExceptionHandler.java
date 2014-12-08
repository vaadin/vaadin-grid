package com.vaadin.prototype.wc.gwt.client.util;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.UmbrellaException;

public class SuperDevModeUncaughtExceptionHandler implements GWT.UncaughtExceptionHandler {
    
    @Override
    public void onUncaughtException(final Throwable t) {
      logException(t, false);
    }
   
    private void logException(Throwable t, boolean isCause) {
      String msg = t.toString();
      if(isCause) {
        msg = "caused by: " + msg;
      }
      groupStart(msg);
      log(t);
      if(t instanceof UmbrellaException) {
        UmbrellaException umbrella = (UmbrellaException) t;
        for(Throwable cause : umbrella.getCauses()) {
          logException(cause, true);
        }
      } else if (t.getCause() != null) {
        logException(t.getCause(), true);
      }
      groupEnd();
    }
   
    private native void groupStart(String msg) /*-{
      var groupStart = console.groupCollapsed || console.group || console.error || console.log;
      groupStart.call(console, msg);
    }-*/;
   
    private native void groupEnd() /*-{
      var groupEnd = console.groupEnd || function(){};
      groupEnd.call(console);
    }-*/;
   
    private native void log(Throwable t) /*-{
      var logError = console.error || console.log;
      var backingError = t.__gwt$backingJsError;
      logError.call(console, backingError && backingError.stack);
    }-*/;
   
  }