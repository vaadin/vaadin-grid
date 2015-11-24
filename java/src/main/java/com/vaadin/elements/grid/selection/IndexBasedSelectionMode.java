package com.vaadin.elements.grid.selection;

import com.vaadin.elements.grid.GridElement;

/**
 * Enumeration for easy setting of selection mode.
 */
public enum IndexBasedSelectionMode {

    SINGLE {

        @Override
        public IndexBasedSelectionModel createModel(GridElement gridElement) {
            return new IndexBasedSelectionModelSingle();
        }
    },

    MULTI {

        @Override
        public IndexBasedSelectionModel createModel(GridElement gridElement) {
            return new IndexBasedSelectionModelMulti(gridElement, false);
        }
    },

    ALL {

        @Override
        public IndexBasedSelectionModel createModel(GridElement gridElement) {
            return new IndexBasedSelectionModelMulti(gridElement, true);
        }
    },

    DISABLED {

        @Override
        public IndexBasedSelectionModel createModel(GridElement gridElement) {
            return new IndexBasedSelectionModelDisabled();
        }
    };

    public abstract IndexBasedSelectionModel createModel(GridElement gridElement);
}