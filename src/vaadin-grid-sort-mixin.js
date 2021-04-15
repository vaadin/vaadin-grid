/**
 * @license
 * Copyright (c) 2020 Vaadin Ltd.
 * This program is available under Apache License Version 2.0, available at https://vaadin.com/license/
 */

/**
 * @polymerMixin
 */
export const SortMixin = (superClass) =>
  class SortMixin extends superClass {
    static get properties() {
      return {
        /**
         * When `true`, all `<vaadin-grid-sorter>` are applied for sorting.
         * @attr {boolean} multi-sort
         * @type {boolean}
         */
        multiSort: {
          type: Boolean,
          value: false
        },

        /**
         * @type {!Array<!GridSorter>}
         * @protected
         */
        _sorters: {
          type: Array,
          value: function () {
            return [];
          }
        },

        /** @private */
        _previousSorters: {
          type: Array,
          value: function () {
            return [];
          }
        }
      };
    }

    /** @protected */
    ready() {
      super.ready();
      this.addEventListener('sorter-changed', this._onSorterChanged);
    }

    /** @private */
    _onSorterChanged(e) {
      const sorter = e.target;
      e.stopPropagation();
      if (e.detail.property !== '_isConnected' || !e.detail.newValue || this._sorters.indexOf(sorter) === -1) {
        this.__updateSorter(sorter);
        this.__applySorters();
      }
    }

    /** @private */
    __removeSorters(sorters) {
      if (sorters.length == 0) {
        return;
      }

      sorters.forEach((sorter) => {
        this._removeArrayItem(this._sorters, sorter);
        if (this.multiSort) {
          this.__updateSortOrders();
        }
      });
      this.__applySorters();
    }

    /** @private */
    __updateSortOrders() {
      this._sorters.forEach((sorter, index) => (sorter._order = this._sorters.length > 1 ? index : null), this);
    }

    /** @private */
    __updateSorter(sorter) {
      sorter._order = null;

      if (this.multiSort) {
        if (sorter.direction) {
          this._removeArrayItem(this._sorters, sorter);
          this._sorters.unshift(sorter);
        }
        this.__updateSortOrders();
      } else {
        if (sorter.direction) {
          this._sorters.forEach((sorter) => {
            sorter._order = null;
            sorter.direction = null;
          });
          this._sorters = [sorter];
        }
      }
    }

    /** @private */
    __applySorters() {
      if (
        this.dataProvider &&
        // No need to clear cache if sorters didn't change and grid is attached
        this.isAttached &&
        JSON.stringify(this._previousSorters) !== JSON.stringify(this._mapSorters())
      ) {
        this.clearCache();
      }

      this._a11yUpdateSorters();

      this._previousSorters = this._mapSorters();
    }

    /**
     * @return {!Array<!GridSorter>}
     * @protected
     */
    _mapSorters() {
      return this._sorters.map((sorter) => {
        return {
          path: sorter.path,
          direction: sorter.direction
        };
      });
    }

    /** @private */
    _removeArrayItem(array, item) {
      const index = array.indexOf(item);
      if (index > -1) {
        array.splice(index, 1);
      }
    }
  };
