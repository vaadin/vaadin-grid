/**
 * `<vaadin-grid-filter>` is a helper element for the `<vaadin-grid>` that provides out-of-the-box UI controls,
 * and handlers for filtering the grid data.
 *
 * #### Example:
 * ```html
 * <vaadin-grid-column>
 *   <template class="header">
 *     <vaadin-grid-filter path="name.first"></vaadin-grid-filter>
 *   </template>
 *   <template>[[item.name.first]]</template>
 * </vaadin-grid-column>
 * ```
 */
declare class GridFilterElement extends HTMLElement {
  /**
   * JS Path of the property in the item used for filtering the data.
   */
  path: string | null | undefined;

  /**
   * Current filter value.
   */
  value: string | null | undefined;
}

declare global {
  interface HTMLElementTagNameMap {
    'vaadin-grid-filter': GridFilterElement;
  }
}

export { GridFilterElement };
