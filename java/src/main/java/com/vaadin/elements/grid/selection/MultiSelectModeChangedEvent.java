package com.vaadin.elements.grid.selection;

import com.google.gwt.event.shared.GwtEvent;


public class MultiSelectModeChangedEvent extends GwtEvent<MultiSelectModeChangedHandler> {
    public static final Type<MultiSelectModeChangedHandler> eventType = new Type<MultiSelectModeChangedHandler>();

    public com.google.gwt.event.shared.GwtEvent.Type<MultiSelectModeChangedHandler> getAssociatedType() {
        return eventType;
    }

    protected void dispatch(MultiSelectModeChangedHandler handler) {
        handler.onMultiSelectModeChanged();
    }
}