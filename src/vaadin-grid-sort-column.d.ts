import {GridSorterDirection} from '../@types/interfaces';

import {GridColumnElement} from './vaadin-grid-column.js';

/**
 * `<vaadin-grid-sort-column>` is a helper element for the `<vaadin-grid>`
 * that provides default header template and functionality for sorting.
 *
 * #### Example:
 * ```html
 * <vaadin-grid items="[[items]]">
 *  <vaadin-grid-sort-column path="name.first" direction="asc"></vaadin-grid-sort-column>
 *
 *  <vaadin-grid-column>
 *    ...
 * ```
 */
declare class GridSortColumnElement extends GridColumnElement {

  /**
   * JS Path of the property in the item used for sorting the data.
   */
  path: string|null|undefined;

  /**
   * How to sort the data.
   * Possible values are `asc` to use an ascending algorithm, `desc` to sort the data in
   * descending direction, or `null` for not sorting the data.
   */
  direction: GridSorterDirection|null|undefined;
}

declare global {
  interface HTMLElementTagNameMap {
    "vaadin-grid-sort-column": GridSortColumnElement;
  }
}

export {GridSortColumnElement};
