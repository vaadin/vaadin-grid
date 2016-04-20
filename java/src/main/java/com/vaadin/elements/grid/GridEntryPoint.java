package com.vaadin.elements.grid;


import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.user.client.ui.RootPanel;

import com.vaadin.client.BrowserInfo;


/**
 * Exports vaadin grid.
 */
public class GridEntryPoint implements EntryPoint {

    @Override
    public void onModuleLoad() {
    	checkPermutation(System.getProperty("user.agent"));

        Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
            @Override
            public void execute() {
                RootPanel.get().removeStyleName(BrowserInfo.get().getCSSClass());
            }
        });
        removePrivateApi();
    }
    
    // Show an error in console if the DOMImpl selected for this ua
    // is not the correct one. This happens when using the SSO linker
    // with multiple properties like `user.agent` and `modernie`, and then
    // collapsing all in a single permutation.
    // TODO(manolo): This is probably a bug in the linker (report to GWT)
    private native static Object checkPermutation(String ua) /*-{
       var i = @com.google.gwt.dom.client.DOMImpl::impl;
       i = i.@com.google.gwt.dom.client.DOMImpl::getClass()();

       var c = ua == 'safari' ? @com.google.gwt.dom.client.DOMImplWebkit::class :
         ua == 'gecko1_8' ? @com.google.gwt.dom.client.DOMImplWebkit::class : 'unknown';

       if (i != c) {
         $wnd.console.error("VaadinGrid: wrong permutation selected for UA=" + ua);
       }
    }-*/;
    
    // We don't expose constructors exported under the _api namespace.
    private static native void removePrivateApi() /*-{
        delete $wnd.vaadin.elements._api;
    }-*/;
}
