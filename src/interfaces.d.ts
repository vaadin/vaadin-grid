import { GridColumnElement } from './vaadin-grid-column.js';
import { GridElement } from './vaadin-grid.js';

export type GridBodyRenderer<T> = (
  root: HTMLElement,
  column?: GridColumnElement<T>,
  model?: GridItemModel<T>
) => void;

export type GridCellClassNameGenerator<T> = (
  column: GridColumnElement<T>,
  model: GridItemModel<T>
) => string;

export type GridColumnTextAlign = 'start' | 'center' | 'end' | null;

export type GridDataProviderCallback<T> = (
  items: Array<T>,
  size?: number
) => void;

export type GridDataProviderParams<T> = {
  page: number;
  pageSize: number;
  filters: Array<GridFilter>;
  sortOrders: Array<GridSorter>;
  parentItem?: T;
};

export type GridDataProvider<T> = (
  params: GridDataProviderParams<T>,
  callback: GridDataProviderCallback<T>
) => void;

export type GridDragAndDropFilter<T> = (model: GridItemModel<T>) => boolean;

export type GridDropLocation = 'above' | 'on-top' | 'below' | 'empty';

export type GridDropMode = 'between' | 'on-top' | 'on-top-or-between' | 'on-grid';

export interface GridFilter {
  path: string;
  value: string;
}

export interface GridEventContext<T> {
  section: 'body' | 'header' | 'footer' | 'details';
  item?: T;
  column?: GridColumnElement<T>;
  index?: number;
  selected?: boolean;
  detailsOpened?: boolean;
  expanded?: boolean;
  level?: number;
}

export type GridHeaderFooterRenderer<T> = (
  root: HTMLElement,
  column?: GridColumnElement<T>
) => void;

export interface GridItemModel<T> {
  index: number;
  item: T;
  selected?: boolean;
  expanded?: boolean;
  level?: number;
  detailsOpened?: boolean;
}

export type GridRowDetailsRenderer<T> = (
  root: HTMLElement,
  grid?: GridElement<T>,
  model?: GridItemModel<T>
) => void;

export type GridSorterDirection = 'asc' | 'desc' | null;

export interface GridSorter {
  path: string;
  direction: GridSorterDirection;
}

export { GridColumnElement };
