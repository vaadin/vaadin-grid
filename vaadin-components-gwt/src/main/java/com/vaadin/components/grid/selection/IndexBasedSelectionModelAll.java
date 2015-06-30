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
 * An {@link IndexBasedSelectionModel} for inverted multiselection.
 */
public class IndexBasedSelectionModelAll extends
        IndexBasedSelectionModelMultiAbstract {

    private final JSArray<Double> deselectedIndexes = JS.createArray();
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
        return deselectedIndexes.indexOf((double) SelectionUtil
                .getRowIndexByRow(grid, row)) == -1;
    }

    @Override
    public boolean deselectAll() {
        grid.fireEvent(new SelectAllEvent<Object>(this));
        return true;
    }

    @Override
    public void reset() {
        deselectedIndexes.setLength(0);
        grid.fireEvent(new SelectionEvent<Object>(grid, null, null, true));
    }

    @Override
    public JSArray selected(JavaScriptObject mapper, Integer from, Integer to) {
        JSArray result = JS.createArray();
        mapper = SelectionUtil.verifyMapper(mapper);

        int size = size();

        int fromIndex = JSValidate.Integer.val(from, 0, 0);
        fromIndex = Math.min(Math.max(fromIndex, 0), size - 1);

        int defaultTo = size() - 1;
        int toIndex = JSValidate.Integer.val(to, defaultTo, defaultTo);
        toIndex = Math.min(Math.max(toIndex, 0), size - 1);

        int count = toIndex - fromIndex + 1;

        int index = 0;
        int selectedIndexCount = 0;
        int addedSelectedIndexCount = 0;
        while (addedSelectedIndexCount < count) {
            if (deselectedIndexes.indexOf((double) index) == -1) {
                if (selectedIndexCount++ >= fromIndex) {
                    addedSelectedIndexCount++;
                    Object mappedValue = JsUtils.jsni(mapper, "call", mapper,
                            index);
                    if (mappedValue != null) {
                        result.add(mappedValue);
                    }
                }
            }
            index++;
        }

        return result;
    }

    @Override
    public JSArray deselected(JavaScriptObject mapper, Integer from, Integer to) {
        JSArray result = JS.createArray();
        mapper = SelectionUtil.verifyMapper(mapper);

        int fromIndex = JSValidate.Integer.val(from, 0, 0);
        fromIndex = Math.min(fromIndex, deselectedIndexes.length() - 1);
        int defaultTo = deselectedIndexes.length() - 1;
        int toIndex = JSValidate.Integer.val(to, defaultTo, defaultTo);
        toIndex = Math.min(toIndex, deselectedIndexes.length() - 1);

        for (int i = fromIndex; i <= toIndex; i++) {
            Object mappedValue = JsUtils.jsni(mapper, "call", mapper,
                    deselectedIndexes.get(i));
            if (mappedValue != null) {
                result.add(mappedValue);
            }
        }
        return result;
    }

    @Override
    public int size() {
        return grid.getDataSource().size() - deselectedIndexes.length();
    }

    @Override
    public boolean select(int index, boolean skipOwnEvents) {
        if (deselectedIndexes.indexOf((double) index) != -1) {
            deselectedIndexes.remove((double) index);
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
    public boolean deselect(int index, boolean skipOwnEvents) {
        if (index >= 0
                && (!dataSizeUpdated || index < grid.getDataSource().size())
                && deselectedIndexes.indexOf((double) index) == -1) {
            deselectedIndexes.add((double) index);

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
        grid.fireEvent(new SelectAllEvent<Object>(
                new IndexBasedSelectionModelMulti()));
    }

    @Override
    public void selectAll() {
        reset();
    }

    @Override
    public IndexBasedSelectionMode getMode() {
        return IndexBasedSelectionMode.ALL;
    }

    @Override
    public void dataSizeUpdated(int newSize) {
        dataSizeUpdated = true;
        // If selected rows contains values that are out of bounds, remove
        // them.
        boolean changed = false;
        for (int i = 0; i < deselectedIndexes.length(); i++) {
            if (deselectedIndexes.get(i) >= newSize) {
                deselectedIndexes.remove(deselectedIndexes.get(i--));
                changed = true;
            }
        }
        if (changed) {
            grid.fireEvent(new SelectionEvent<Object>(grid, null, null, true));
        }
    }
}
