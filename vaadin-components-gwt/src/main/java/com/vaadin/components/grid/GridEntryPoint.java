package com.vaadin.components.grid;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.shared.GWT;

/**
 * Exports vaadin grid.
 */
public class GridEntryPoint implements EntryPoint {

    public void onModuleLoad() {
    	GWT.create(GridComponent.class);
    }
    
}
