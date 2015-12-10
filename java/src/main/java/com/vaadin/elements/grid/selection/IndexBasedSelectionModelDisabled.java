package com.vaadin.elements.grid.selection;

import com.vaadin.client.widget.grid.selection.SelectionModelNone;
import com.vaadin.elements.common.js.JS;
import com.vaadin.elements.common.js.JSArray;
import com.vaadin.elements.common.js.JSFunction;

/**
 * An {@link IndexBasedSelectionModel} for selection disabled.
 */
public class IndexBasedSelectionModelDisabled extends
        SelectionModelNone<Object> implements IndexBasedSelectionModel {

    @Override
    public int size() {
        return 0;
    }

    @Override
    public JSArray<Object> selected(JSFunction<Object, Integer> mapper,
            Integer from, Integer to) {
        return JS.createArray();
    }

    @Override
    public JSArray<Object> deselected(JSFunction<Object, Integer> mapper,
            Integer from, Integer to) {
        return JS.createArray();
    }

    @Override
    public boolean select(int index, boolean skipOwnEvents) {
        return false;
    }

    @Override
    public boolean deselect(int index, boolean skipOwnEvents) {
        return false;
    }

    @Override
    public void clear() {
    }

    @Override
    public void selectAll() {
    }

    @Override
    public IndexBasedSelectionMode getMode() {
        return IndexBasedSelectionMode.DISABLED;
    }

    @Override
    public void dataSizeUpdated(int newSize) {
    }

    @Override
    public void setMode(IndexBasedSelectionMode mode) {
    }

    @Override
    public boolean supportsMode(IndexBasedSelectionMode mode) {
        return mode == IndexBasedSelectionMode.DISABLED;
    }

}
