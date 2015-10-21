package com.vaadin.elements.grid.data;

/**
 * Simple wrapper object for native data.
 */
public class DataItemContainer {

    private final Object dataItem;

    public DataItemContainer(Object dataItem) {
        this.dataItem = dataItem;
    }

    public Object getDataItem() {
        return dataItem;
    }

}
