declare function SelectionMixin<T extends new (...args: any[]) => {}>(base: T): T & SelectionMixinConstructor;

interface SelectionMixinConstructor {
  new (...args: any[]): SelectionMixin;
}

interface SelectionMixin {
  /**
   * An array that contains the selected items.
   */
  selectedItems: Array<unknown> | null;

  _isSelected(item: unknown): boolean;

  /**
   * Selects the given item.
   *
   * @param item The item object
   */
  selectItem(item: unknown): void;

  /**
   * Deselects the given item if it is already selected.
   *
   * @param item The item object
   */
  deselectItem(item: unknown): void;

  /**
   * Toggles the selected state of the given item.
   *
   * @param item The item object
   */
  _toggleItem(item: unknown): void;
}

export { SelectionMixin, SelectionMixinConstructor };
