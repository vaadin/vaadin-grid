import { html } from '@polymer/polymer/lib/utils/html-tag.js';
import { PolymerElement } from '@polymer/polymer/polymer-element.js';
class GridEventContextDemos extends DemoReadyEventEmitter(GridDemo(PolymerElement)) {
  static get template() {
    return html`
    <style include="vaadin-component-demo-shared-styles">
      :host {
        display: block;
      }
    </style>

    <h3>Event Context</h3>
    <p>
      Use <code>getEventContext</code> function in your event handler to get information about the targeted cell,
      such as the corresponding item and column.
    </p>
    <p>
      This feature can be useful to execute item-specific actions, like selection or opening row details
      on any mouse or key event.
    </p>
    <p>
      <b>Hint:</b> Try double-clicking a row or using enter to select a row with the keyboard in this demo.
    </p>
    <vaadin-demo-snippet id="grid-event-context-demos-event-context" when-defined="vaadin-grid">
      <template preserve-content="">
        <vaadin-grid aria-label="Event Context Example">
          <vaadin-grid-column path="name.first" header="First name"></vaadin-grid-column>
          <vaadin-grid-column path="name.last" header="Last name"></vaadin-grid-column>
          <vaadin-grid-column path="email"></vaadin-grid-column>
        </vaadin-grid>
        <script>
          window.addDemoReadyListener('#grid-event-context-demos-event-context', function(document) {
            const grid = document.querySelector('vaadin-grid');
            grid.items = Vaadin.GridDemo.users;

            const toggleSelection = function(item) {
              grid.selectedItems = grid.selectedItems[0] === item ? [] : [item];
            };

            grid.addEventListener('dblclick', function(e) {
              const item = grid.getEventContext(e).item;
              toggleSelection(item);
            });

            grid.addEventListener('keydown', function(e) {
              const item = grid.getEventContext(e).item;
              if (e.key === 'Enter') {
                toggleSelection(item);
              }
            });
          });
        &lt;/script>
      </template>
    </vaadin-demo-snippet>
`;
  }

  static get is() {
    return 'grid-event-context-demos';
  }
}
customElements.define(GridEventContextDemos.is, GridEventContextDemos);
