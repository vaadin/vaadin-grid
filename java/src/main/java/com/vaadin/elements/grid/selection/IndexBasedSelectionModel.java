package com.vaadin.elements.grid.selection;

import jsinterop.annotations.JsType;

import com.vaadin.client.widget.grid.selection.SelectionModel;
import com.vaadin.elements.common.js.JS;
import com.vaadin.elements.common.js.JSArray;
import com.vaadin.elements.common.js.JSFunction;

/**
 * Selection model based on row indexes.
 */
@JsType(namespace = JS.NAMESPACE_API)
public interface IndexBasedSelectionModel extends SelectionModel<Object> {

    /**
     * Deselects an index.
     */
    boolean deselect(int index, boolean skipOwnEvents);

    /**
     * Selects an index.
     */
    boolean select(int index, boolean skipOwnEvents);

    /**
     * Returns an array mapped from the selected indexes.
     */
    JSArray<Object> selected(JSFunction<Object, Integer> mapper, Integer from, Integer to);

    /**
     * Returns an array mapped from the deselected indexes.
     */
    JSArray<Object> deselected(JSFunction<Object, Integer> mapper, Integer from, Integer to);

    /**
     * Returns the count of selected items.
     */
    int size();

    /**
     * Selects all selections.
     */
    void selectAll();

    /**
     * Clears all selections.
     */
    void clear();

    /**
     * Returns the selection mode.
     */
    IndexBasedSelectionMode getMode();

    /**
     * Notified the selection model of updated data size.
     */
    void dataSizeUpdated(int newSize);

    /**
     * Updates the selection mode if the model supports it.
     */
    void setMode(IndexBasedSelectionMode mode);

    /**
     * Returns true if model supports the selection mode.
     */
    boolean supportsMode(IndexBasedSelectionMode mode);

}
