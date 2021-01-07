import { GridDataProviderCallback, GridDataProviderParams, GridFilter, GridSorter } from './interfaces';

declare function ArrayDataProviderMixin<T extends new (...args: any[]) => {}>(
  base: T
): T & ArrayDataProviderMixinConstructor;

interface ArrayDataProviderMixinConstructor {
  new (...args: any[]): ArrayDataProviderMixin;
}

interface ArrayDataProviderMixin {
  /**
   * An array containing the items which will be stamped to the column template
   * instances.
   */
  items: Array<unknown> | null | undefined;

  _arrayDataProvider(opts: GridDataProviderParams<unknown> | null, cb: GridDataProviderCallback<unknown> | null): void;

  /**
   * Check array of filters/sorters for paths validity, console.warn invalid items
   *
   * @param arrayToCheck The array of filters/sorters to check
   * @param action The name of action to include in warning (filtering, sorting)
   */
  _checkPaths(arrayToCheck: Array<GridFilter | GridSorter>, action: string, items: Array<unknown>): any;

  _multiSort(a: unknown | null, b: unknown | null): number;

  _normalizeEmptyValue(value: unknown | null): string;

  _compare(a: unknown | null, b: unknown | null): number;

  _filter(items: Array<unknown>): Array<unknown>;
}

export { ArrayDataProviderMixin, ArrayDataProviderMixinConstructor };
