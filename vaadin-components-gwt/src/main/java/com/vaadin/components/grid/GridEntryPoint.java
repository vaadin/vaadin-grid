package com.vaadin.components.grid;

import static com.google.gwt.query.client.GQuery.$;
import static com.google.gwt.query.client.GQuery.document;

import com.google.gwt.core.client.EntryPoint;

/**
 * Exports vaadin grid.
 */
public class GridEntryPoint implements EntryPoint {

    public void onModuleLoad() {
        $(document).trigger("v-grid-loaded");
    }
}
