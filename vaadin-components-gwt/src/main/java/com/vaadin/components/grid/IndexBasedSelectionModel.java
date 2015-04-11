package com.vaadin.components.grid;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.vaadin.client.data.DataSource.RowHandle;
import com.vaadin.client.widget.grid.selection.SelectionEvent;
import com.vaadin.client.widget.grid.selection.SelectionModel;
import com.vaadin.client.widget.grid.selection.SelectionModelMulti;
import com.vaadin.client.widget.grid.selection.SelectionModelNone;
import com.vaadin.client.widget.grid.selection.SelectionModelSingle;
import com.vaadin.client.widgets.Grid;
import com.vaadin.components.grid.data.GridDataSource;

/**
 * Selection model based on row indexes.
 */
public interface IndexBasedSelectionModel extends SelectionModel<Object> {

    /**
     * Sets the selected indexes.
     */
    void setSelectedIndexes(Set<Integer> selectedIndexes);

    /**
     * Returns selected indexes.
     */
    Set<Integer> getSelectedIndexes();

    /**
     * Returns an index for a specific row. Row availability is assumed.
     */
    static int getRowIndex(Grid<Object> grid, Object row) {
        return ((GridDataSource) grid.getDataSource()).indexOf(row);
    }

    /**
     * An {@link IndexBasedSelectionModel} for single selection.
     */
    public class IndexBasedSelectionModelSingle extends SelectionModelSingle<Object>
            implements IndexBasedSelectionModel {

        private Grid<Object> grid;
        private int selectedRow = -1;

        @Override
        public void setSelectedIndexes(Set<Integer> selectedIndexes) {
            if (selectedIndexes == null || selectedIndexes.isEmpty()) {
                selectedRow = -1;
            } else {
                selectedRow = selectedIndexes.iterator().next();
            }
            grid.fireEvent(new SelectionEvent<Object>(grid, null, null, false));
        }

        @Override
        protected boolean selectByHandle(RowHandle<Object> handle) {
            super.selectByHandle(handle);
            int previous = selectedRow;
            selectedRow = getRowIndex(grid, handle.getRow());
            return selectedRow != previous;
        }

        @Override
        protected boolean deselectByHandle(RowHandle<Object> handle) {
            super.deselectByHandle(handle);
            selectedRow = -1;
            return true;
        }

        @Override
        public Set<Integer> getSelectedIndexes() {
            Set<Integer> result = new HashSet<Integer>();
            if (selectedRow > -1) {
                result.add(selectedRow);
            }
            return result;
        }

        @Override
        public boolean isSelected(Object row) {
            return selectedRow == getRowIndex(grid, row);
        }

        @Override
        public void setGrid(Grid<Object> grid) {
            this.grid = grid;
            super.setGrid(grid);
        }

        @Override
        public void reset() {
            setSelectedIndexes(new HashSet<Integer>());
        }

    }

    /**
     * An {@link IndexBasedSelectionModel} for multiple selection.
     */
    public class IndexBasedSelectionModelMulti extends SelectionModelMulti<Object>
            implements IndexBasedSelectionModel {

        private Grid<Object> grid;
        private final Set<Integer> selectedIndexes = new HashSet<Integer>();

        @Override
        public void setSelectedIndexes(Set<Integer> selectedIndexes) {
            this.selectedIndexes.clear();
            if (selectedIndexes != null) {
                this.selectedIndexes.addAll(selectedIndexes);
            }
            grid.fireEvent(new SelectionEvent<Object>(grid, null, null, true));
        }

        @Override
        protected boolean selectByHandle(RowHandle<Object> handle) {
            super.selectByHandle(handle);
            int rowIndex = getRowIndex(grid, handle.getRow());
            return selectedIndexes.add(rowIndex);
        }

        @Override
        protected boolean deselectByHandle(RowHandle<Object> handle) {
            super.deselectByHandle(handle);
            int rowIndex = getRowIndex(grid, handle.getRow());
            return selectedIndexes.remove(rowIndex);
        }

        @Override
        public Set<Integer> getSelectedIndexes() {
            return Collections.unmodifiableSet(selectedIndexes);
        }

        @Override
        public boolean isSelected(Object row) {
            return selectedIndexes.contains(getRowIndex(grid, row));
        }

        @Override
        public void setGrid(Grid<Object> grid) {
            this.grid = grid;
            super.setGrid(grid);
        }

        @Override
        public void reset() {
            setSelectedIndexes(new HashSet<Integer>());
        }

    }

    /**
     * An {@link IndexBasedSelectionModel} for none selection.
     */
    public class IndexBasedSelectionModelNone extends SelectionModelNone<Object>
            implements IndexBasedSelectionModel {

        @Override
        public void setSelectedIndexes(Set<Integer> selectedIndexes) {
        }

        @Override
        public Set<Integer> getSelectedIndexes() {
            return new HashSet<Integer>();
        }

    }
}
