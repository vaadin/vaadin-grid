import { GridColumnElement } from './vaadin-grid-column.js';

declare function ColumnReorderingMixin<T extends new (...args: any[]) => {}>(
  base: T
): T & ColumnReorderingMixinConstructor;

interface ColumnReorderingMixinConstructor {
  new (...args: any[]): ColumnReorderingMixin;
}

export { ColumnReorderingMixinConstructor };

interface ColumnReorderingMixin {
  /**
   * Set to true to allow column reordering.
   * @attr {boolean} column-reordering-allowed
   */
  columnReorderingAllowed: boolean;

  _getColumnsInOrder(): Array<GridColumnElement<unknown>>;

  _cellFromPoint(x: number, y: number): HTMLElement | null | undefined;

  _updateGhostPosition(eventClientX: number, eventClientY: number): void;

  _updateGhost(cell: HTMLElement): HTMLElement;

  _setSiblingsReorderStatus(column: GridColumnElement<unknown>, status: string): void;

  _autoScroller(): void;

  _isSwapAllowed(
    column1: GridColumnElement<unknown> | null | undefined,
    column2: GridColumnElement<unknown> | null | undefined
  ): boolean | undefined;

  _isSwappableByPosition(targetColumn: GridColumnElement<unknown>, clientX: number): boolean;

  _swapColumnOrders(column1: GridColumnElement<unknown>, column2: GridColumnElement<unknown>): void;

  _getTargetColumn(
    targetCell: HTMLElement | null | undefined,
    draggedColumn: GridColumnElement<unknown> | null
  ): GridColumnElement<unknown> | null | undefined;
}

export { ColumnReorderingMixin };
