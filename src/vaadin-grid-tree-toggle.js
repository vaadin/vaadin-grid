/**
@license
Copyright (c) 2017 Vaadin Ltd.
This program is available under Apache License Version 2.0, available at https://vaadin.com/license/
*/
import { PolymerElement, html } from '@polymer/polymer/polymer-element.js';
import { Debouncer } from '@polymer/polymer/lib/utils/debounce.js';
import { ThemableMixin } from '@vaadin/vaadin-themable-mixin/vaadin-themable-mixin.js';
import { DirMixin } from '@vaadin/vaadin-element-mixin/vaadin-dir-mixin.js';
import { isFocusable } from './vaadin-grid-active-item-mixin.js';
import { microTask } from '@polymer/polymer/lib/utils/async.js';
const $_documentContainer = document.createElement('template');

$_documentContainer.innerHTML = `
  <style>
    @font-face {
      font-family: "vaadin-grid-tree-icons";
      src: url(data:application/font-woff;charset=utf-8;base64,d09GRgABAAAAAAQkAA0AAAAABrwAAQAAAAAAAAAAAAAAAAAAAAAAAAAAAABGRlRNAAAECAAAABoAAAAcgHwa6EdERUYAAAPsAAAAHAAAAB4AJwAOT1MvMgAAAZQAAAA/AAAAYA8TBIJjbWFwAAAB8AAAAFUAAAFeGJvXWmdhc3AAAAPkAAAACAAAAAgAAAAQZ2x5ZgAAAlwAAABLAAAAhIrPOhFoZWFkAAABMAAAACsAAAA2DsJI02hoZWEAAAFcAAAAHQAAACQHAgPHaG10eAAAAdQAAAAZAAAAHAxVAgBsb2NhAAACSAAAABIAAAASAIAAVG1heHAAAAF8AAAAGAAAACAACgAFbmFtZQAAAqgAAAECAAACTwflzbdwb3N0AAADrAAAADYAAABZQ7Ajh3icY2BkYGAA4twv3Vfi+W2+MnCzMIDANSOmbGSa2YEZRHEwMIEoAAoiB6sAeJxjYGRgYD7w/wADAwsDCDA7MDAyoAI2AFEEAtIAAAB4nGNgZGBg4GBgZgDRDAxMDGgAAAGbABB4nGNgZp7JOIGBlYGBaSbTGQYGhn4IzfiawZiRkwEVMAqgCTA4MDA+38d84P8BBgdmIAapQZJVYGAEAGc/C54AeJxjYYAAxlAIzQTELAwMBxgZGB0ACy0BYwAAAHicY2BgYGaAYBkGRgYQiADyGMF8FgYbIM3FwMHABISMDArP9/3/+/8/WJXC8z0Q9v8nEp5gHVwMMMAIMo+RDYiZoQJMQIKJARUA7WBhGN4AACFKDtoAAAAAAAAAAAgACAAQABgAJgA0AEIAAHichYvBEYBADAKBVHBjBT4swl9KS2k05o0XHd/yW1hAfBFwCv9sIlJu3nZaNS3PXAaXXHI8Lge7DlzF7C1RgXc7xkK6+gvcD2URmQB4nK2RQWoCMRiFX3RUqtCli65yADModOMBLLgQSqHddRFnQghIAnEUvEA3vUUP0LP0Fj1G+yb8R5iEhO9/ef/7FwFwj28o9EthiVp4hBlehcfUP4Ur8o/wBAv8CU+xVFvhOR7UB7tUdUdlVRJ6HnHWTnhM/V24In8JT5j/KzzFSi2E53hUz7jCcrcIiDDwyKSW1JEct2HdIPH1DFytbUM0PofWdNk5E5oUqb/Q6HHBiVGZpfOXkyUMEj5IyBuNmYZQjBobfsuassvnkKLe1OuBBj0VQ8cRni2xjLWsHaM0jrjx3peYA0/vrdmUYqe9iy7bzrX6eNP7Jh1SijX+AaUVbB8AAHicY2BiwA84GBgYmRiYGJkZmBlZGFkZ2djScyoLMgzZS/MyDQwMwLSruZMzlHaB0q4A76kLlwAAAAEAAf//AA94nGNgZGBg4AFiMSBmYmAEQnYgZgHzGAAD6wA2eJxjYGBgZACCKxJigiD6mhFTNowGACmcA/8AAA==) format('woff');
      font-weight: normal;
      font-style: normal;
    }
  </style>
`;

document.head.appendChild($_documentContainer.content);
/**
 * `<vaadin-grid-tree-toggle>` is a helper element for the `<vaadin-grid>`
 * that provides toggle and level display functionality for the item tree.
 *
 * #### Example:
 * ```html
 * <vaadin-grid-column>
 *   <template class="header">Package name</template>
 *   <template>
 *     <vaadin-grid-tree-toggle
 *         leaf="[[!item.hasChildren]]"
 *         expanded="{{expanded}}"
 *         level="[[level]]">
 *       [[item.name]]
 *     </vaadin-grid-tree-toggle>
 *   </template>
 * </vaadin-grid-column>
 * ```
 *
 * ### Styling
 *
 * The following shadow DOM parts are available for styling:
 *
 * Part name | Description
 * ---|---
 * `toggle` | The tree toggle icon
 *
 * The following state attributes are available for styling:
 *
 * Attribute    | Description | Part name
 * ---|---|---
 * `expanded` | When present, the toggle is expanded | :host
 * `leaf` | When present, the toggle is not expandable, i. e., the current item is a leaf | :host
 *
 * The following custom CSS properties are available on
 * the `<vaadin-grid-tree-toggle>` element:
 *
 * Custom CSS property | Description | Default
 * ---|---|---
 * `--vaadin-grid-tree-toggle-level-offset` | Visual offset step for each tree sublevel | `1em`
 *
 * @extends PolymerElement
 * @mixes ThemableMixin
 */
class GridTreeToggleElement extends ThemableMixin(DirMixin(PolymerElement)) {
  static get template() {
    return html`
    <style>
      :host {
        display: inline-flex;
        align-items: baseline;

        /* CSS API for :host */
        --vaadin-grid-tree-toggle-level-offset: 1em;

        /*
          ShadyCSS seems to polyfill :dir(rtl) only for :host, thus using
          a host custom CSS property for ltr/rtl toggle icon choice.
         */
        ---collapsed-icon: "\\e7be\\00a0";
      }

      :host(:dir(rtl)) {
        ---collapsed-icon: "\\e7bd\\00a0";
      }

      :host([hidden]) {
        display: none !important;
      }

      :host(:not([leaf])) {
        cursor: pointer;
      }

      #level-spacer,
      [part="toggle"] {
        flex: none;
      }

      #level-spacer {
        display: inline-block;
        width: calc(var(---level, '0') * var(--vaadin-grid-tree-toggle-level-offset));
      }

      [part="toggle"]::before {
        font-family: "vaadin-grid-tree-icons";
        line-height: 1em; /* make icon font metrics not affect baseline */
      }

      :host(:not([expanded])) [part="toggle"]::before {
        content: var(---collapsed-icon);
      }

      :host([expanded]) [part="toggle"]::before {
        content: "\\e7bc\\00a0"; /* icon glyph + single non-breaking space */
      }

      :host([leaf]) [part="toggle"] {
        visibility: hidden;
      }
    </style>

    <span id="level-spacer"></span>
    <span part="toggle"></span>
    <slot></slot>
`;
  }

  static get is() {
    return 'vaadin-grid-tree-toggle';
  }

  static get properties() {
    return {
      /**
       * Current level of the tree represented with a horizontal offset
       * of the toggle button.
       * @type {number}
       */
      level: {
        type: Number,
        value: 0,
        observer: '_levelChanged'
      },

      /**
       * Hides the toggle icon and disables toggling a tree sublevel.
       * @type {boolean}
       */
      leaf: {
        type: Boolean,
        value: false,
        reflectToAttribute: true
      },

      /**
       * Sublevel toggle state.
       * @type {boolean}
       */
      expanded: {
        type: Boolean,
        value: false,
        reflectToAttribute: true,
        notify: true
      }
    };
  }

  /** @protected */
  ready() {
    super.ready();

    this.addEventListener('click', e => this._onClick(e));
  }

  /** @private */
  _onClick(e) {
    if (this.leaf) {
      return;
    }
    if (isFocusable(e.target)) {
      return;
    }

    e.preventDefault();
    this.expanded = !this.expanded;
  }

  /** @private */
  _levelChanged(level) {
    const value = Number(level).toString();
    this.style['---level'] = value;
    // Async is to make DOM updates applied before evaluating the style
    // update, required for polyfilled RTL support in MSIE and Edge.
    this._debouncerUpdateLevel = Debouncer.debounce(
      this._debouncerUpdateLevel,
      microTask,
      () => this.updateStyles({'---level': value})
    );
  }
}

customElements.define(GridTreeToggleElement.is, GridTreeToggleElement);

export { GridTreeToggleElement };
