/**
@license
Copyright (c) 2017 Vaadin Ltd.
This program is available under Apache License Version 2.0, available at https://vaadin.com/license/
*/
import { flush } from '@polymer/polymer/lib/utils/flush.js';
import { PolymerElement } from '@polymer/polymer/polymer-element.js'
import {VirtualScroller} from 'virtual-scroller/src/VirtualScroller.js';
import {Layout1d} from 'virtual-scroller/src/layouts/Layout1d.js';
/**
 * This Element is used internally by vaadin-grid.
 *
 * @private
 */
class GridScrollerElement extends PolymerElement {

  static get is() {
    return 'vaadin-grid-scroller';
  }

  static get properties() {
    return {
      size: {
        type: Number,
        notify: true
      },
      _vidxOffset: {
        value: 0
      }
    };
  }

  static get observers() {
    return [
      '_effectiveSizeChanged(_effectiveSize)'
    ];
  }

  connectedCallback() {
    super.connectedCallback();
    this._scrollHandler();
  }

  /**
  * @protected
  */
  _updateScrollerItem(item, index) {}
  /**
  * @protected
  */
  _afterScroll() {}
  /**
  * @protected
  */
  _getRowTarget() {}
  /**
  * @protected
  */
  _createScrollerRows() {}
  /**
  * @protected
  */
  _canPopulate() {}

  ready() {
    super.ready();
    this.__elementPool = [];

    this.scrollTarget = this.$.table;
    this.scrollTarget.addEventListener('scroll', this._scrollHandler.bind(this));
    this.__scroller = new VirtualScroller({
      container: this.$.items,
      scrollTarget: this.scrollTarget
    });

    Object.assign(this.__scroller, {
      layout: new Layout1d(),
      createElement: () => this.__elementPool.pop() || this._createScrollerRows(1)[0],
      updateElement: this._updateScrollerItem.bind(this),
      recycleElement: element => this.__elementPool.push(element),
      totalItems: this._effectiveSize || 0
    });
  }

  /**
  * @private
  */
  scrollToIndex(index) {

  }

  _effectiveSizeChanged(size) {
    if (this.__scroller) {
      this.__scroller.totalItems = size;
      this.__scroller._render();
    }
  }


  /**
   * Assigns the data models to a given set of items.
   * @param {!Array<number>=} itemSet
   */
  _assignModels(itemSet) {

  }

  _scrollHandler() {
    this._scrollTop = this.$.table.scrollTop;
    this._afterScroll();
  }

  notifyResize() {

  }

  /* Warn when iron-list APIs are being accessed directly */
  _warnPrivateAPIAccess(apiName) {
    if (this._warnPrivateAPIAccessAsyncEnabled) {
      console.warn(`Accessing private API (${apiName})!`);
    }
  }

  _render() {
    this.__scroller._render();
  }

  _resizeHandler() {
    super._resizeHandler();
    flush();
  }
}

customElements.define(GridScrollerElement.is, GridScrollerElement);

export { GridScrollerElement as ScrollerElement };
