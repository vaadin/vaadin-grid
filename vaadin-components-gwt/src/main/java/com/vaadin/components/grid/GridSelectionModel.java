package com.vaadin.components.grid;

import java.util.ArrayList;
import java.util.Collection;

import com.vaadin.client.data.DataSource.RowHandle;
import com.vaadin.client.widget.grid.selection.SelectionModel;
import com.vaadin.client.widget.grid.selection.SelectionModelMulti;
import com.vaadin.client.widget.grid.selection.SelectionModelNone;
import com.vaadin.client.widget.grid.selection.SelectionModelSingle;
import com.vaadin.client.widgets.Grid;

/**
 * Selection model that also accepts index arrays and converts them to row keys
 * once ready.
 */
public interface GridSelectionModel extends SelectionModel<Object> {

    /**
     * Sets the indexes to be converted to row keys in the first place.
     */
    void setSelectedIndexes(Collection<Integer> selectedIndexes);

    /**
     * Returns cached indexes that haven't yet been converted to row keys.
     */
    Collection<Integer> getSelectedIndexes();

    /**
     * A GridSelectionModel for single selection.
     */
    public class GridSelectionModelSingle extends SelectionModelSingle<Object>
            implements GridSelectionModel {

        private Grid<Object> grid;
        private Integer selectedRow;

        @Override
        public void setSelectedIndexes(Collection<Integer> selectedIndexes) {
            if (selectedIndexes != null && !selectedIndexes.isEmpty()) {
                selectedRow = selectedIndexes.iterator().next();
            }
        }

        @Override
        public Collection<Integer> getSelectedIndexes() {
            Collection<Integer> result = new ArrayList<Integer>();
            if (selectedRow != null) {
                result.add(selectedRow);
            }
            return result;
        }

        @Override
        public boolean isSelected(Object row) {
            if (selectedRow != null) {
                if (selectedRow >= grid.getDataSource().size()) {
                    selectedRow = null;
                } else {
                    Object dataItem = grid.getDataSource().getRow(selectedRow);
                    RowHandle<Object> handle = grid.getDataSource().getHandle(
                            dataItem);
                    if (handle != null) {
                        selectByHandle(handle);
                        selectedRow = null;
                    }
                }
            }
            return super.isSelected(row);
        }

        @Override
        public void setGrid(Grid<Object> grid) {
            this.grid = grid;
            super.setGrid(grid);
        }

    }

    /**
     * A GridSelectionModel for multiple selection.
     */
    public class GridSelectionModelMulti extends SelectionModelMulti<Object>
            implements GridSelectionModel {

        private Grid<Object> grid;
        private final Collection<Integer> selectedIndexes = new ArrayList<Integer>();

        @Override
        public void setSelectedIndexes(Collection<Integer> selectedIndexes) {
            this.selectedIndexes.clear();
            if (selectedIndexes != null) {
                this.selectedIndexes.addAll(selectedIndexes);
            }
        }

        @Override
        public Collection<Integer> getSelectedIndexes() {
            return selectedIndexes;
        }

        @Override
        public boolean isSelected(Object row) {
            if (selectedIndexes != null && !selectedIndexes.isEmpty()) {
                for (Integer rowIndex : new ArrayList<Integer>(selectedIndexes)) {
                    if (rowIndex >= grid.getDataSource().size()) {
                        selectedIndexes.remove(rowIndex);
                    } else {
                        Object dataItem = grid.getDataSource().getRow(rowIndex);
                        RowHandle<Object> handle = grid.getDataSource()
                                .getHandle(dataItem);
                        if (handle != null) {
                            selectByHandle(handle);
                            selectedIndexes.remove(rowIndex);
                        }
                    }
                }
            }
            return super.isSelected(row);
        }

        @Override
        public void setGrid(Grid<Object> grid) {
            this.grid = grid;
            super.setGrid(grid);
        }

    }

    /**
     * A GridSelectionModel for none selection.
     */
    public class GridSelectionModelNone extends SelectionModelNone<Object>
            implements GridSelectionModel {

        @Override
        public void setSelectedIndexes(Collection<Integer> selectedIndexes) {
        }

        @Override
        public Collection<Integer> getSelectedIndexes() {
            return new ArrayList<Integer>();
        }

    }
}
