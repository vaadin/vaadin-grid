package com.vaadin.components.grid.selection;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.query.client.js.JsUtils;
import com.vaadin.client.data.DataSource.RowHandle;
import com.vaadin.client.widget.grid.events.SelectAllEvent;
import com.vaadin.client.widget.grid.selection.SelectionEvent;
import com.vaadin.components.common.js.JS;
import com.vaadin.components.common.js.JSArray;
import com.vaadin.components.common.js.JSValidate;

/**
 * An {@link IndexBasedSelectionModel} for multiple selection.
 */
public class IndexBasedSelectionModelMulti extends
        IndexBasedSelectionModelMultiAbstract {

    private final JSArray<Double> selectedIndexes = JS.createArray();
    private boolean dataSizeUpdated = false;

    @Override
    protected boolean selectByHandle(RowHandle<Object> handle) {
        return select(SelectionUtil.getRowIndex(grid, handle), true);
    }

    @Override
    protected boolean deselectByHandle(RowHandle<Object> handle) {
        return deselect(SelectionUtil.getRowIndex(grid, handle), true);
    }

    @Override
    public boolean isSelected(Object row) {
        return selectedIndexes.indexOf((double) SelectionUtil.getRowIndexByRow(
                grid, row)) != -1;
    }

    @Override
    public void reset() {
        selectedIndexes.setLength(0);
        grid.fireEvent(new SelectionEvent<Object>(grid, null, null, true));
    }

    @Override
    public JSArray<Object> selected(JavaScriptObject mapper, Integer from,
            Integer to) {
        JSArray<Object> result = JS.createArray();
        mapper = SelectionUtil.verifyMapper(mapper);

        int fromIndex = JSValidate.Integer.val(from, 0, 0);
        fromIndex = Math.min(fromIndex, selectedIndexes.length() - 1);
        int defaultTo = selectedIndexes.length() - 1;
        int toIndex = JSValidate.Integer.val(to, defaultTo, defaultTo);
        toIndex = Math.min(toIndex, selectedIndexes.length() - 1);

        for (int i = fromIndex; i <= toIndex; i++) {
            Object mappedValue = JsUtils.jsni(mapper, "call", mapper,
                    selectedIndexes.get(i));
            if (mappedValue != null) {
                result.add(mappedValue);
            }
        }
        return result;
    }

    @Override
    public JSArray<Object> deselected(JavaScriptObject mapper, Integer from,
            Integer to) {
        return JS.createArray();
    }

    @Override
    public int size() {
        return selectedIndexes.length();
    }

    @Override
    public boolean select(int index, boolean skipOwnEvents) {
        if (index >= 0
                && (!dataSizeUpdated || index < grid.getDataSource().size())
                && selectedIndexes.indexOf((double) index) == -1) {
            selectedIndexes.add((double) index);

            skipOwnEvents = JSValidate.Boolean.val(skipOwnEvents, false, false);
            if (!skipOwnEvents) {
                grid.fireEvent(new SelectionEvent<Object>(grid, null, null,
                        false));
            }

            if (isChecked()) {
                selectAll();
                return false;
            }

            return true;
        }
        return false;
    }

    @Override
    public boolean deselect(int index, boolean skipOwnEvents) {
        if (selectedIndexes.indexOf((double) index) != -1) {
            selectedIndexes.remove((double) index);

            skipOwnEvents = JSValidate.Boolean.val(skipOwnEvents, false, false);
            if (!skipOwnEvents) {
                grid.fireEvent(new SelectionEvent<Object>(grid, null, null,
                        false));
            }

            return true;
        }
        return false;
    }

    @Override
    public void clear() {
        reset();
    }

    @Override
    public void selectAll() {
        grid.fireEvent(new SelectAllEvent<Object>(
                new IndexBasedSelectionModelAll()));
    }

    @Override
    public IndexBasedSelectionMode getMode() {
        return IndexBasedSelectionMode.MULTI;
    }

    @Override
    public void dataSizeUpdated(int newSize) {
        dataSizeUpdated = true;
        // If selected rows contains values that are out of bounds, remove
        // them.
        boolean changed = false;
        for (int i = 0; i < selectedIndexes.length(); i++) {
            if (selectedIndexes.get(i) >= newSize) {
                selectedIndexes.remove(selectedIndexes.get(i--));
                changed = true;
            }
        }
        if (changed) {
            grid.fireEvent(new SelectionEvent<Object>(grid, null, null, true));
        }
    }
}
