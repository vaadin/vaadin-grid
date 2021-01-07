import { ElementMixin } from '@vaadin/vaadin-element-mixin/vaadin-element-mixin.js';

import { ThemableMixin } from '@vaadin/vaadin-themable-mixin/vaadin-themable-mixin.js';

import {
  GridCellClassNameGenerator,
  GridDataProvider,
  GridDragAndDropFilter,
  GridDropLocation,
  GridEventContext,
  GridItemModel,
  GridRowDetailsRenderer
} from './interfaces';

import { ScrollerElement } from './vaadin-grid-scroller.js';

import { A11yMixin } from './vaadin-grid-a11y-mixin.js';

import { ActiveItemMixin } from './vaadin-grid-active-item-mixin.js';

import { ArrayDataProviderMixin } from './vaadin-grid-array-data-provider-mixin.js';

import { ColumnResizingMixin } from './vaadin-grid-column-resizing-mixin.js';

import { DataProviderMixin } from './vaadin-grid-data-provider-mixin.js';

import { DynamicColumnsMixin } from './vaadin-grid-dynamic-columns-mixin.js';

import { EventContextMixin } from './vaadin-grid-event-context-mixin.js';

import { FilterMixin } from './vaadin-grid-filter-mixin.js';

import { RowDetailsMixin } from './vaadin-grid-row-details-mixin.js';

import { ScrollMixin } from './vaadin-grid-scroll-mixin.js';

import { SelectionMixin } from './vaadin-grid-selection-mixin.js';

import { SortMixin } from './vaadin-grid-sort-mixin.js';

import { StylingMixin } from './vaadin-grid-styling-mixin.js';

import { DragAndDropMixin } from './vaadin-grid-drag-and-drop-mixin.js';

import { KeyboardNavigationMixin } from './vaadin-grid-keyboard-navigation-mixin.js';

import { ColumnReorderingMixin } from './vaadin-grid-column-reordering-mixin.js';

import { GridColumnElement } from './vaadin-grid-column.js';

/**
 * Fired when the `activeItem` property changes.
 */
export type GridActiveItemChanged<T> = CustomEvent<{ value: T }>;

/**
 * Fired when the cell is activated with click or keyboard.
 */
export type GridCellActivate<T> = CustomEvent<{ model: GridItemModel<T> }>;

/**
 * Fired when the columns in the grid are reordered.
 */
export type GridColumnReorder<T> = CustomEvent<{ columns: GridColumnElement<T>[] }>;

/**
 * Fired when the grid column resize is finished.
 */
export type GridColumnResize<T> = CustomEvent<{ resizedColumn: GridColumnElement<T> }>;

/**
 * Fired when the `expandedItems` property changes.
 */
export type GridExpandedItemsChanged<T> = CustomEvent<{ value: Array<T> }>;

/**
 * Fired when starting to drag grid rows.
 */
export type GridDragStart<T> = CustomEvent<{
  draggedItems: Array<T>;
  setDraggedItemsCount: (count: number) => void;
  setDragData: (type: string, data: string) => void;
}>;

/**
 * Fired when a drop occurs on top of the grid.
 */
export type GridDrop<T> = CustomEvent<{
  dropTargetItem: T;
  dropLocation: GridDropLocation;
  dragData: Array<{ type: string; data: string }>;
}>;

/**
 * Fired when the `loading` property changes.
 */
export type GridLoadingChanged = CustomEvent<{ value: boolean }>;

/**
 * Fired when the `selectedItems` property changes.
 */
export type GridSelectedItemsChanged<T> = CustomEvent<{ value: Array<T> }>;

export interface GridElementEventMap<T> {
  'active-item-changed': GridActiveItemChanged<T>;

  'cell-activate': GridCellActivate<T>;

  'column-reorder': GridColumnReorder<T>;

  'column-resize': GridColumnResize<T>;

  'expanded-items-changed': GridExpandedItemsChanged<T>;

  'grid-dragstart': GridDragStart<T>;

  'grid-dragend': Event;

  'grid-drop': GridDrop<T>;

  'loading-changed': GridLoadingChanged;

  'selected-items-changed': GridSelectedItemsChanged<T>;
}

export type GridEventMap<T> = HTMLElementEventMap & GridElementEventMap<T>;

/**
 * `<vaadin-grid>` is a free, high quality data grid / data table Web Component. The content of the
 * the grid can be populated in two ways: imperatively by using renderer callback function and
 * declaratively by using Polymer's Templates.
 *
 * ### Quick Start
 *
 * Start with an assigning an array to the [`items`](#/elements/vaadin-grid#property-items) property to visualize your data.
 *
 * Use the [`<vaadin-grid-column>`](#/elements/vaadin-grid-column) element to configure the grid columns. Set `path` and `header`
 * shorthand properties for the columns to define what gets rendered in the cells of the column.
 *
 * #### Example:
 * ```html
 * <vaadin-grid>
 *   <vaadin-grid-column path="name.first" header="First name"></vaadin-grid-column>
 *   <vaadin-grid-column path="name.last" header="Last name"></vaadin-grid-column>
 *   <vaadin-grid-column path="email"></vaadin-grid-column>
 * </vaadin-grid>
 * ```
 *
 * For custom content `vaadin-grid-column` element provides you with three types of `renderer` callback functions: `headerRenderer`,
 * `renderer` and `footerRenderer`.
 *
 * Each of those renderer functions provides `root`, `column`, `model` arguments when applicable.
 * Generate DOM content, append it to the `root` element and control the state
 * of the host element by accessing `column`. Before generating new content,
 * users are able to check if there is already content in `root` for reusing it.
 *
 * Renderers are called on initialization of new column cells and each time the
 * related row model is updated. DOM generated during the renderer call can be reused
 * in the next renderer call and will be provided with the `root` argument.
 * On first call it will be empty.
 *
 * #### Example:
 * ```html
 * <vaadin-grid>
 *   <vaadin-grid-column></vaadin-grid-column>
 *   <vaadin-grid-column></vaadin-grid-column>
 *   <vaadin-grid-column></vaadin-grid-column>
 * </vaadin-grid>
 * ```
 * ```js
 * const grid = document.querySelector('vaadin-grid');
 * grid.items = [{'name': 'John', 'surname': 'Lennon', 'role': 'singer'},
 *               {'name': 'Ringo', 'surname': 'Starr', 'role': 'drums'}];
 *
 * const columns = grid.querySelectorAll('vaadin-grid-column');
 *
 * columns[0].headerRenderer = function(root) {
 *   root.textContent = 'Name';
 * };
 * columns[0].renderer = function(root, column, model) {
 *   root.textContent = model.item.name;
 * };
 *
 * columns[1].headerRenderer = function(root) {
 *   root.textContent = 'Surname';
 * };
 * columns[1].renderer = function(root, column, model) {
 *   root.textContent = model.item.surname;
 * };
 *
 * columns[2].headerRenderer = function(root) {
 *   root.textContent = 'Role';
 * };
 * columns[2].renderer = function(root, column, model) {
 *   root.textContent = model.item.role;
 * };
 * ```
 *
 * Alternatively, the content can be provided with Polymer's Templates:
 *
 * #### Example:
 * ```html
 * <vaadin-grid items='[{"name": "John", "surname": "Lennon", "role": "singer"},
 * {"name": "Ringo", "surname": "Starr", "role": "drums"}]'>
 *   <vaadin-grid-column>
 *     <template class="header">Name</template>
 *     <template>[[item.name]]</template>
 *   </vaadin-grid-column>
 *   <vaadin-grid-column>
 *     <template class="header">Surname</template>
 *     <template>[[item.surname]]</template>
 *   </vaadin-grid-column>
 *   <vaadin-grid-column>
 *     <template class="header">Role</template>
 *     <template>[[item.role]]</template>
 *   </vaadin-grid-column>
 * </vaadin-grid>
 * ```
 *
 * The following helper elements can be used for further customization:
 * - [`<vaadin-grid-column-group>`](#/elements/vaadin-grid-column-group)
 * - [`<vaadin-grid-filter>`](#/elements/vaadin-grid-filter)
 * - [`<vaadin-grid-sorter>`](#/elements/vaadin-grid-sorter)
 * - [`<vaadin-grid-selection-column>`](#/elements/vaadin-grid-selection-column)
 * - [`<vaadin-grid-tree-toggle>`](#/elements/vaadin-grid-tree-toggle)
 *
 * __Note that the helper elements must be explicitly imported.__
 * If you want to import everything at once you can use the `all-imports.html` bundle.
 *
 * A column template can be decorated with one the following class names to specify its purpose
 * - `header`: Marks a header template
 * - `footer`: Marks a footer template
 * - `row-details`: Marks a row details template
 *
 * The following built-in template variables can be bound to inside the column templates:
 * - `[[index]]`: Number representing the row index
 * - `[[item]]` and it's sub-properties: Data object (provided by a data provider / items array)
 * - `{{selected}}`: True if the item is selected (can be two-way bound)
 * - `{{detailsOpened}}`: True if the item has row details opened (can be two-way bound)
 * - `{{expanded}}`: True if the item has tree sublevel expanded (can be two-way bound)
 * - `[[level]]`: Number of the tree sublevel of the item, first level-items have 0
 *
 * ### Lazy Loading with Function Data Provider
 *
 * In addition to assigning an array to the items property, you can alternatively
 * provide the `<vaadin-grid>` data through the
 * [`dataProvider`](#/elements/vaadin-grid#property-dataProvider) function property.
 * The `<vaadin-grid>` calls this function lazily, only when it needs more data
 * to be displayed.
 *
 * See the [`dataProvider`](#/elements/vaadin-grid#property-dataProvider) in
 * the API reference below for the detailed data provider arguments description,
 * and the “Assigning Data” page in the demos.
 *
 * __Note that expanding the tree grid's item will trigger a call to the `dataProvider`.__
 *
 * __Also, note that when using function data providers, the total number of items
 * needs to be set manually. The total number of items can be returned
 * in the second argument of the data provider callback:__
 *
 * ```javascript
 * grid.dataProvider = function(params, callback) {
 *   const url = 'https://api.example/data' +
 *       '?page=' + params.page +        // the requested page index
 *       '&per_page=' + params.pageSize; // number of items on the page
 *   const xhr = new XMLHttpRequest();
 *   xhr.onload = function() {
 *     const response = JSON.parse(xhr.responseText);
 *     callback(
 *       response.employees, // requested page of items
 *       response.totalSize  // total number of items
 *     );
 *   };
 *   xhr.open('GET', url, true);
 *   xhr.send();
 * };
 * ```
 *
 * __Alternatively, you can use the `size` property to set the total number of items:__
 *
 * ```javascript
 * grid.size = 200; // The total number of items
 * grid.dataProvider = function(params, callback) {
 *   const url = 'https://api.example/data' +
 *       '?page=' + params.page +        // the requested page index
 *       '&per_page=' + params.pageSize; // number of items on the page
 *   const xhr = new XMLHttpRequest();
 *   xhr.onload = function() {
 *     const response = JSON.parse(xhr.responseText);
 *     callback(response.employees);
 *   };
 *   xhr.open('GET', url, true);
 *   xhr.send();
 * };
 * ```
 *
 * ### Styling
 *
 * The following shadow DOM parts are available for styling:
 *
 * Part name | Description
 * ----------------|----------------
 * `row` | Row in the internal table
 * `cell` | Cell in the internal table
 * `header-cell` | Header cell in the internal table
 * `body-cell` | Body cell in the internal table
 * `footer-cell` | Footer cell in the internal table
 * `details-cell` | Row details cell in the internal table
 * `resize-handle` | Handle for resizing the columns
 * `reorder-ghost` | Ghost element of the header cell being dragged
 *
 * The following state attributes are available for styling:
 *
 * Attribute    | Description | Part name
 * -------------|-------------|------------
 * `loading` | Set when the grid is loading data from data provider | :host
 * `interacting` | Keyboard navigation in interaction mode | :host
 * `navigating` | Keyboard navigation in navigation mode | :host
 * `overflow` | Set when rows are overflowing the grid viewport. Possible values: `top`, `bottom`, `left`, `right` | :host
 * `reordering` | Set when the grid's columns are being reordered | :host
 * `dragover` | Set when the grid (not a specific row) is dragged over | :host
 * `dragging-rows` : Set when grid rows are dragged  | :host
 * `reorder-status` | Reflects the status of a cell while columns are being reordered | cell
 * `frozen` | Frozen cell | cell
 * `last-frozen` | Last frozen cell | cell
 * * `first-column` | First visible cell on a row | cell
 * `last-column` | Last visible cell on a row | cell
 * `selected` | Selected row | row
 * `expanded` | Expanded row | row
 * `details-opened` | Row with details open | row
 * `loading` | Row that is waiting for data from data provider | row
 * `odd` | Odd row | row
 * `first` | The first body row | row
 * `dragstart` | Set for one frame when drag of a row is starting. The value is a number when multiple rows are dragged | row
 * `dragover` | Set when the row is dragged over | row
 * `drag-disabled` | Set to a row that isn't available for dragging | row
 * `drop-disabled` | Set to a row that can't be dropped on top of | row
 *
 * See [ThemableMixin – how to apply styles for shadow parts](https://github.com/vaadin/vaadin-themable-mixin/wiki)
 *
 * @fires {CustomEvent} active-item-changed - Fired when the `activeItem` property changes.
 * @fires {CustomEvent} cell-activate - Fired when the cell is activated with click or keyboard.
 * @fires {CustomEvent} column-reorder - Fired when the columns in the grid are reordered.
 * @fires {CustomEvent} column-resize - Fired when the grid column resize is finished.
 * @fires {CustomEvent} expanded-items-changed - Fired when the `expandedItems` property changes.
 * @fires {CustomEvent} grid-dragstart - Fired when starting to drag grid rows.
 * @fires {CustomEvent} grid-dragend - Fired when the dragging of the rows ends.
 * @fires {CustomEvent} grid-drop - Fired when a drop occurs on top of the grid.
 * @fires {CustomEvent} loading-changed - Fired when the `loading` property changes.
 * @fires {CustomEvent} selected-items-changed - Fired when the `selectedItems` property changes.
 */
declare class GridElement<Item> extends ElementMixin(
  ThemableMixin(
    A11yMixin(
      ActiveItemMixin(
        ArrayDataProviderMixin(
          ColumnResizingMixin(
            DataProviderMixin(
              DynamicColumnsMixin(
                FilterMixin(
                  RowDetailsMixin(
                    ScrollMixin(
                      SelectionMixin(
                        SortMixin(
                          KeyboardNavigationMixin(
                            ColumnReorderingMixin(EventContextMixin(StylingMixin(DragAndDropMixin(ScrollerElement))))
                          )
                        )
                      )
                    )
                  )
                )
              )
            )
          )
        )
      )
    )
  )
) {
  /**
   * The item user has last interacted with. Turns to `null` after user deactivates
   * the item by re-interacting with the currently active item.
   */
  activeItem: Item | null;

  /**
   * An array containing the items which will be stamped to the column template
   * instances.
   */
  items: Array<Item> | null | undefined;

  /**
   * A function that allows generating CSS class names for grid cells
   * based on their row and column. The return value should be the generated
   * class name as a string, or multiple class names separated by whitespace
   * characters.
   *
   * Receives two arguments:
   * - `column` The `<vaadin-grid-column>` element (`undefined` for details-cell).
   * - `model` The object with the properties related with
   *   the rendered item, contains:
   *   - `model.index` The index of the item.
   *   - `model.item` The item.
   *   - `model.expanded` Sublevel toggle state.
   *   - `model.level` Level of the tree represented with a horizontal offset of the toggle button.
   *   - `model.selected` Selected state.
   */
  cellClassNameGenerator: GridCellClassNameGenerator<Item> | null | undefined;

  /**
   * Function that provides items lazily. Receives arguments `params`, `callback`
   *
   * `params.page` Requested page index
   *
   * `params.pageSize` Current page size
   *
   * `params.filters` Currently applied filters
   *
   * `params.sortOrders` Currently applied sorting orders
   *
   * `params.parentItem` When tree is used, and sublevel items
   * are requested, reference to parent item of the requested sublevel.
   * Otherwise `undefined`.
   *
   * `callback(items, size)` Callback function with arguments:
   *   - `items` Current page of items
   *   - `size` Total number of items. When tree sublevel items
   *     are requested, total number of items in the requested sublevel.
   *     Optional when tree is not used, required for tree.
   */
  dataProvider: GridDataProvider<Item> | null | undefined;

  /**
   * Returns an object with context information about the event target:
   * - `item`: the data object corresponding to the targeted row (not specified when targeting header or footer)
   * - `column`: the column element corresponding to the targeted cell (not specified when targeting row details)
   * - `section`: whether the event targeted the body, header, footer or details of the grid
   *
   * These additional properties are included when `item` is specified:
   * - `index`: the index of the item
   * - `selected`: the selected state of the item
   * - `detailsOpened`: whether the row details are open for the item
   * - `expanded`: the expanded state of the tree toggle
   * - `level`: the tree hierarchy level
   *
   * The returned object is populated only when a grid cell, header, footer or row details is found in `event.composedPath()`.
   * This means mostly mouse and keyboard events. If such a grid part is not found in the path, an empty object is returned.
   * This may be the case eg. if the event is fired on the `<vaadin-grid>` element and not any deeper in the DOM, or if
   * the event targets the empty part of the grid body.
   */
  getEventContext(event: Event): GridEventContext<Item> | object | null;

  /**
   * An array that contains the expanded items.
   */
  expandedItems: Array<Item>;

  /**
   * Returns a value that identifies the item. Uses `itemIdPath` if available.
   * Can be customized by overriding.
   */
  getItemId(item: Item): Item | unknown;

  /**
   * Expands the given item tree.
   */
  expandItem(item: Item): void;

  /**
   * Collapses the given item tree.
   */
  collapseItem(item: Item): void;

  /**
   * An array containing references to items with open row details.
   */
  detailsOpenedItems: Array<Item> | null | undefined;

  /**
   * Custom function for rendering the content of the row details.
   * Receives three arguments:
   *
   * - `root` The row details content DOM element. Append your content to it.
   * - `grid` The `<vaadin-grid>` element.
   * - `model` The object with the properties related with
   *   the rendered item, contains:
   *   - `model.index` The index of the item.
   *   - `model.item` The item.
   */
  rowDetailsRenderer: GridRowDetailsRenderer<Item> | null | undefined;

  /**
   * Open the details row of a given item.
   */
  openItemDetails(item: Item): void;

  /**
   * Close the details row of a given item.
   */
  closeItemDetails(item: Item): void;

  /**
   * An array that contains the selected items.
   */
  selectedItems: Array<Item> | null;

  /**
   * Selects the given item.
   */
  selectItem(item: Item): void;

  /**
   * Deselects the given item if it is already selected.
   */
  deselectItem(item: Item): void;

  /**
   * A function that filters dragging of specific grid rows. The return value should be false
   * if dragging of the row should be disabled.
   *
   * Receives one argument:
   * - `model` The object with the properties related with
   *   the rendered item, contains:
   *   - `model.index` The index of the item.
   *   - `model.item` The item.
   *   - `model.expanded` Sublevel toggle state.
   *   - `model.level` Level of the tree represented with a horizontal offset of the toggle button.
   *   - `model.selected` Selected state.
   */
  dragFilter: GridDragAndDropFilter<Item> | null | undefined;

  /**
   * A function that filters dropping on specific grid rows. The return value should be false
   * if dropping on the row should be disabled.
   *
   * Receives one argument:
   * - `model` The object with the properties related with
   *   the rendered item, contains:
   *   - `model.index` The index of the item.
   *   - `model.item` The item.
   *   - `model.expanded` Sublevel toggle state.
   *   - `model.level` Level of the tree represented with a horizontal offset of the toggle button.
   *   - `model.selected` Selected state.
   */
  dropFilter: GridDragAndDropFilter<Item> | null | undefined;

  /**
   * If true, the grid's height is defined by its rows.
   *
   * Effectively, this disables the grid's virtual scrolling so that all the rows are rendered in the DOM at once.
   * If the grid has a large number of items, using the feature is discouraged to avoid performance issues.
   * @attr {boolean} height-by-rows
   */
  heightByRows: boolean;

  __itemsReceived(): void;

  /**
   * Updates the `width` of all columns which have `autoWidth` set to `true`.
   */
  recalculateColumnWidths(): void;

  _updateRow(
    row: HTMLTableRowElement,
    columns: Array<GridColumnElement<Item>>,
    section: string | null,
    isColumnRow: boolean,
    noNotify: boolean
  ): void;

  __updateHeaderFooterRowVisibility(row: HTMLTableRowElement | null): void;

  _renderColumnTree(columnTree: Array<GridColumnElement<Item>>): void;

  _updateItem(row: HTMLElement, item: Item | null): void;

  _toggleAttribute(name: string, bool: boolean, node: Element): void;

  __getRowModel(row: HTMLTableRowElement): GridItemModel<Item>;

  /**
   * Manually invoke existing renderers for all the columns
   * (header, footer and body cells) and opened row details.
   */
  render(): void;

  /**
   * Updates the computed metrics and positioning of internal grid parts
   * (row/details cell positioning etc). Needs to be invoked whenever the sizing of grid
   * content changes asynchronously to ensure consistent appearance (e.g. when a
   * contained image whose bounds aren't known beforehand finishes loading).
   */
  notifyResize(): void;

  __forceReflow(): void;

  addEventListener<K extends keyof GridEventMap<Item>>(
    type: K,
    listener: (this: GridElement<Item>, ev: GridEventMap<Item>[K]) => void,
    options?: boolean | AddEventListenerOptions
  ): void;

  removeEventListener<K extends keyof GridEventMap<Item>>(
    type: K,
    listener: (this: GridElement<Item>, ev: GridEventMap<Item>[K]) => void,
    options?: boolean | EventListenerOptions
  ): void;
}

declare global {
  interface HTMLElementTagNameMap {
    'vaadin-grid': GridElement<unknown>;
  }
}

export { GridElement };
