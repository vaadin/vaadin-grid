package com.vaadin.elements.grid.selection;

/**
 * Enumeration for easy setting of selection mode.
 */
public enum IndexBasedSelectionMode {

    SINGLE {

        @Override
        public IndexBasedSelectionModel createModel() {
            return new IndexBasedSelectionModelSingle();
        }
    },

    MULTI {

        @Override
        public IndexBasedSelectionModel createModel() {
            return new IndexBasedSelectionModelMulti(false);
        }
    },

    ALL {

        @Override
        public IndexBasedSelectionModel createModel() {
            return new IndexBasedSelectionModelMulti(true);
        }
    },

    DISABLED {

        @Override
        public IndexBasedSelectionModel createModel() {
            return new IndexBasedSelectionModelDisabled();
        }
    };

    public abstract IndexBasedSelectionModel createModel();
}