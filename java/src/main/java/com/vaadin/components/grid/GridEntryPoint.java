package com.vaadin.components.grid;

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
        Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
            @Override
            public void execute() {
                RootPanel.get().removeStyleName(BrowserInfo.get().getCSSClass());
            }
        });
    }
}
