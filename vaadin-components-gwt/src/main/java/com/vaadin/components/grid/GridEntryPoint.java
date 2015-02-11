package com.vaadin.components.grid;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.vaadin.components.common.util.DOMUtils;
import com.vaadin.components.common.util.Elements;
import com.vaadin.components.common.util.SuperDevModeUncaughtExceptionHandler;

/**
 * Exports vaadin grid.
 */
public class GridEntryPoint implements EntryPoint {

    public void onModuleLoad() {
        if (!GWT.isProdMode()) {
           GWT.setUncaughtExceptionHandler(new SuperDevModeUncaughtExceptionHandler());
        }
        // load vaadin-theme if specified in body
        DOMUtils.loadVaadinGlobalTheme();
        Elements.registerElement(GridComponent.TAG, new GridComponent());
    }
}
