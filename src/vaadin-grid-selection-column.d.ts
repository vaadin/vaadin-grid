import { GridColumnElement } from './vaadin-grid-column.js';

/**
 * `<vaadin-grid-selection-column>` is a helper element for the `<vaadin-grid>`
 * that provides default templates and functionality for item selection.
 *
 * #### Example:
 * ```html
 * <vaadin-grid items="[[items]]">
 *  <vaadin-grid-selection-column frozen auto-select></vaadin-grid-selection-column>
 *
 *  <vaadin-grid-column>
 *    ...
 * ```
 *
 * By default the selection column displays `<vaadin-checkbox>` elements in the
 * column cells. The checkboxes in the body rows toggle selection of the corresponding row items.
 *
 * When the grid data is provided as an array of [`items`](#/elements/vaadin-grid#property-items),
 * the column header gets an additional checkbox that can be used for toggling
 * selection for all the items at once.
 *
 * __The default content can also be overridden__
 */
declare class GridSelectionColumnElement extends GridColumnElement {
  /**
   * Width of the cells for this column.
   */
  width: string | null | undefined;

  /**
   * Flex grow ratio for the cell widths. When set to 0, cell width is fixed.
   * @attr {number} flex-grow
   */
  flexGrow: number;

  /**
   * When true, all the items are selected.
   * @attr {boolean} select-all
   */
  selectAll: boolean;

  /**
   * When true, the active gets automatically selected.
   * @attr {boolean} auto-select
   */
  autoSelect: boolean;
}

declare global {
  interface HTMLElementTagNameMap {
    'vaadin-grid-selection-column': GridSelectionColumnElement;
  }
}

export { GridSelectionColumnElement };
