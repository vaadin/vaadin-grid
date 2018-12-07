import '@vaadin/vaadin-material-styles/color.js';
import '@vaadin/vaadin-material-styles/font-icons.js';
import { html } from '@polymer/polymer/lib/utils/html-tag.js';

const $_documentContainer = html`<dom-module id="material-grid-sorter" theme-for="vaadin-grid-sorter">
  <template>
    <style>
      :host {
        justify-content: flex-start;
        height: 100%;
        align-items: center;
        -webkit-user-select: none;
        -moz-user-select: none;
        user-select: none;
      }

      :host([direction]) {
        color: var(--material-body-text-color);
      }

      [part="indicators"] {
        order: -1;
      }

      [part="indicators"]::before {
        display: inline-block;
        width: 24px;
        font-family: "material-icons";
        font-size: 18px;
        line-height: 18px;
        transition: .1s opacity cubic-bezier(.4, 0, .2, .1), .1s width cubic-bezier(.4, 0, .2, .1);
        opacity: .5;
      }

      :host(:not([direction])) [part="indicators"]::before {
        content: var(--material-icons-arrow-upward);
        width: 0;
        opacity: 0;
      }

      :host([direction]) [part="indicators"]::before {
        opacity: 1;
      }

      :host([direction="asc"]) [part="indicators"]::before {
        content: var(--material-icons-arrow-upward);
      }

      :host([direction="desc"]) [part="indicators"]::before {
        content: var(--material-icons-arrow-downward);
      }

      :host(:hover) [part="indicators"]::before {
        width: 24px;
        opacity: 1;
      }

      [part="order"] {
        right: 4px;
        top: -8px;
        font-size: 10px;
      }
    </style>
  </template>
</dom-module>`;

document.head.appendChild($_documentContainer.content);
