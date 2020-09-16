import { html } from '@polymer/polymer/lib/utils/html-tag.js';
import { PolymerElement } from '@polymer/polymer/polymer-element.js';
export let GridDemo = GridDemo || {};
GridDemo._treeData = [{"uuid":"e784","name":"Automobile","hasChildren":true},{"uuid":"e785","name":"Gasoline","parentUuid":"e784","hasChildren":false},{"uuid":"e786","name":"Maintenance","parentUuid":"e784","hasChildren":false},{"uuid":"e787","name":"Registration fees","parentUuid":"e784","hasChildren":false},{"uuid":"e788","name":"Auto loan payment","parentUuid":"e784","hasChildren":false},{"uuid":"e789","name":"Bank Charges","hasChildren":true},{"uuid":"e78a","name":"Check orders","parentUuid":"e789","hasChildren":false},{"uuid":"e78b","name":"Service fees","parentUuid":"e789","hasChildren":false},{"uuid":"e78c","name":"Insufficient funds fee","parentUuid":"e789","hasChildren":false},{"uuid":"e78d","name":"Minimum balance fee","parentUuid":"e789","hasChildren":false},{"uuid":"e78e","name":"ATM fees","parentUuid":"e789","hasChildren":false},{"uuid":"e78f","name":"Charity","hasChildren":false},{"uuid":"e790","name":"Childcare","hasChildren":true},{"uuid":"e791","name":"Babysitting","parentUuid":"e790","hasChildren":false},{"uuid":"e792","name":"Child support","parentUuid":"e790","hasChildren":false},{"uuid":"e793","name":"Clothing","hasChildren":false},{"uuid":"e794","name":"Credit Card Fees","hasChildren":true},{"uuid":"e795","name":"Annual fee","parentUuid":"e794","hasChildren":false},{"uuid":"e796","name":"Finance charge","parentUuid":"e794","hasChildren":false},{"uuid":"e797","name":"Over the limit fee","parentUuid":"e794","hasChildren":false},{"uuid":"e798","name":"Minimum usage fee","parentUuid":"e794","hasChildren":false},{"uuid":"e799","name":"Cash advance fee","parentUuid":"e794","hasChildren":false},{"uuid":"e79a","name":"Late fee","parentUuid":"e794","hasChildren":false},{"uuid":"e79b","name":"Rewards programs","parentUuid":"e794","hasChildren":false},{"uuid":"e79c","name":"Monthly payment","parentUuid":"e794","hasChildren":false},{"uuid":"e79d","name":"Education","hasChildren":true},{"uuid":"e79e","name":"Tuition","parentUuid":"e79d","hasChildren":false},{"uuid":"e79f","name":"Books","parentUuid":"e79d","hasChildren":false},{"uuid":"e7a0","name":"School supplies","parentUuid":"e79d","hasChildren":false},{"uuid":"e7a1","name":"Field trips","parentUuid":"e79d","hasChildren":false},{"uuid":"e7a2","name":"Misc. fees","parentUuid":"e79d","hasChildren":false},{"uuid":"e7a3","name":"Student loan payment","parentUuid":"e79d","hasChildren":false},{"uuid":"e7a4","name":"Events","hasChildren":true},{"uuid":"e7a5","name":"Wedding","parentUuid":"e7a4","hasChildren":false},{"uuid":"e7a6","name":"Moving","parentUuid":"e7a4","hasChildren":false},{"uuid":"e7a7","name":"Food","hasChildren":true},{"uuid":"e7a8","name":"Groceries","parentUuid":"e7a7","hasChildren":false},{"uuid":"e7a9","name":"Dining out","parentUuid":"e7a7","hasChildren":false},{"uuid":"e7aa","name":"Vending machines","parentUuid":"e7a7","hasChildren":false},{"uuid":"e7ab","name":"Coffee house","parentUuid":"e7a7","hasChildren":false},{"uuid":"e7ac","name":"Gifts","hasChildren":true},{"uuid":"e7ad","name":"Birthday","parentUuid":"e7ac","hasChildren":false},{"uuid":"e7ae","name":"Wedding / Wedding shower","parentUuid":"e7ac","hasChildren":false},{"uuid":"e7af","name":"Baby shower","parentUuid":"e7ac","hasChildren":false},{"uuid":"e7b0","name":"Holiday","parentUuid":"e7ac","hasChildren":false},{"uuid":"e7b1","name":"Anniversary","parentUuid":"e7ac","hasChildren":false},{"uuid":"e7b2","name":"Just because","parentUuid":"e7ac","hasChildren":false},{"uuid":"e7b3","name":"Healthcare","hasChildren":true},{"uuid":"e7b4","name":"Dental","parentUuid":"e7b3","hasChildren":false},{"uuid":"e7b5","name":"Vision","parentUuid":"e7b3","hasChildren":false},{"uuid":"e7b6","name":"Physician","parentUuid":"e7b3","hasChildren":false},{"uuid":"e7b7","name":"Hospital","parentUuid":"e7b3","hasChildren":false},{"uuid":"e7b8","name":"Prescriptions","parentUuid":"e7b3","hasChildren":false},{"uuid":"e7b9","name":"Over the counter medication","parentUuid":"e7b3","hasChildren":false},{"uuid":"e7ba","name":"Vitamins","parentUuid":"e7b3","hasChildren":false},{"uuid":"e7bb","name":"Household","hasChildren":true},{"uuid":"e7bc","name":"Rent / Mortgage payment","parentUuid":"e7bb","hasChildren":false},{"uuid":"e7bd","name":"Home owners association dues","parentUuid":"e7bb","hasChildren":false},{"uuid":"e7be","name":"Furniture","parentUuid":"e7bb","hasChildren":false},{"uuid":"e7bf","name":"Supplies","parentUuid":"e7bb","hasChildren":false},{"uuid":"e7c0","name":"Decorating","parentUuid":"e7bb","hasChildren":false},{"uuid":"e7c1","name":"Tools","parentUuid":"e7bb","hasChildren":false},{"uuid":"e7c2","name":"Home maintenance and repair","parentUuid":"e7bb","hasChildren":false},{"uuid":"e7c3","name":"Home improvement","parentUuid":"e7bb","hasChildren":false},{"uuid":"e7c4","name":"Insurance","hasChildren":true},{"uuid":"e7c5","name":"Automobile","parentUuid":"e7c4","hasChildren":false},{"uuid":"e7c6","name":"Health","parentUuid":"e7c4","hasChildren":false},{"uuid":"e7c7","name":"Life","parentUuid":"e7c4","hasChildren":false},{"uuid":"e7c8","name":"Disability","parentUuid":"e7c4","hasChildren":false},{"uuid":"e7c9","name":"Long term care","parentUuid":"e7c4","hasChildren":false},{"uuid":"e7ca","name":"Roadside assistance","parentUuid":"e7c4","hasChildren":false},{"uuid":"e7cb","name":"Job expenses","hasChildren":true},{"uuid":"e7cc","name":"Reimbursed","parentUuid":"e7cb","hasChildren":false},{"uuid":"e7cd","name":"Clothing","parentUuid":"e7cb","hasChildren":false},{"uuid":"e7ce","name":"Professional dues","parentUuid":"e7cb","hasChildren":false},{"uuid":"e7cf","name":"Leisure","hasChildren":true},{"uuid":"e7d0","name":"Books","parentUuid":"e7cf","hasChildren":false},{"uuid":"e7d1","name":"Magazines","parentUuid":"e7cf","hasChildren":false},{"uuid":"e7d2","name":"Movie theater","parentUuid":"e7cf","hasChildren":false},{"uuid":"e7d3","name":"Video rental / Pay per view","parentUuid":"e7cf","hasChildren":false},{"uuid":"e7d4","name":"Sporting events","parentUuid":"e7cf","hasChildren":false},{"uuid":"e7d5","name":"Sporting goods","parentUuid":"e7cf","hasChildren":false},{"uuid":"e7d6","name":"Hobbies","hasChildren":true},{"uuid":"e7d7","name":"Cultural events","parentUuid":"e7d6","hasChildren":false},{"uuid":"e7d9","name":"Video games","parentUuid":"e7d6","hasChildren":false},{"uuid":"e7da","name":"Toys","parentUuid":"e7d6","hasChildren":false},{"uuid":"e7db","name":"Tourist attractions","parentUuid":"e7d6","hasChildren":false},{"uuid":"e7dc","name":"Loans","hasChildren":true},{"uuid":"e7dd","name":"Loan Payment","parentUuid":"e7dc","hasChildren":false},{"uuid":"e7de","name":"Finance charge / Interest","parentUuid":"e7dc","hasChildren":false},{"uuid":"e7df","name":"Late fee","parentUuid":"e7dc","hasChildren":false},{"uuid":"e7e0","name":"Pet Care","hasChildren":true},{"uuid":"e7e1","name":"Food","parentUuid":"e7e0","hasChildren":false},{"uuid":"e7e2","name":"Supplies","parentUuid":"e7e0","hasChildren":false},{"uuid":"e7e3","name":"Veterinarian","parentUuid":"e7e0","hasChildren":false},{"uuid":"e7e4","name":"Savings","hasChildren":true},{"uuid":"e7e5","name":"Retirement","parentUuid":"e7e4","hasChildren":false},{"uuid":"e7e6","name":"Investments","parentUuid":"e7e4","hasChildren":false},{"uuid":"e7e7","name":"Emergency fund","parentUuid":"e7e4","hasChildren":false},{"uuid":"e7e8","name":"Reserve funds","parentUuid":"e7e4","hasChildren":false},{"uuid":"e7e9","name":"Taxes","hasChildren":true},{"uuid":"e7ea","name":"Federal","parentUuid":"e7e9","hasChildren":false},{"uuid":"e7eb","name":"State","parentUuid":"e7e9","hasChildren":false},{"uuid":"e7ec","name":"Local","parentUuid":"e7e9","hasChildren":false},{"uuid":"e7ed","name":"Utilities","hasChildren":true},{"uuid":"e7ee","name":"Water","parentUuid":"e7ed","hasChildren":false},{"uuid":"e7ef","name":"Sewer","parentUuid":"e7ed","hasChildren":false},{"uuid":"e7f0","name":"Electricity","parentUuid":"e7ed","hasChildren":false},{"uuid":"e7f1","name":"Gas","parentUuid":"e7ed","hasChildren":false},{"uuid":"e7f2","name":"Television","parentUuid":"e7ed","hasChildren":false},{"uuid":"e7f3","name":"Telephone","parentUuid":"e7ed","hasChildren":false},{"uuid":"e7f4","name":"Internet service","parentUuid":"e7ed","hasChildren":false},{"uuid":"e7f5","name":"Garbage and recycling","parentUuid":"e7ed","hasChildren":false},{"uuid":"e7f6","name":"Vacation","hasChildren":true},{"uuid":"e7f7","name":"Day trips","parentUuid":"e7f6","hasChildren":false},{"uuid":"e7f8","name":"Transportation","parentUuid":"e7f6","hasChildren":false},{"uuid":"e7f9","name":"Lodging","parentUuid":"e7f6","hasChildren":false},{"uuid":"e7fa","name":"Entertainment","parentUuid":"e7f6","hasChildren":false}];

class GridTreeDemos extends DemoReadyEventEmitter(GridDemo(PolymerElement)) {
  static get template() {
    return html`
    <style include="vaadin-component-demo-shared-styles">
      :host {
        display: block;
      }
    </style>

    <h3>Tree Column Helper and Hierarchical Data</h3>
    <p>
      Use <code>&lt;vaadin-grid-tree-column&gt;</code> to create a column with tree toggle UI controls in the body cells.
      The property <code>itemHasChildrenPath</code> is used for determining whether an individual item has child items
      or if it's a leaf node instead.
    </p>
    <p>
      <strong>NOTE: Hierarchical data only works with a <code>dataProvider</code>.
        Using <code>items</code> will not work properly with a tree grid.</strong>
    </p>
    <p>
      <strong>NOTE: You must explicitly import <code>vaadin-grid-tree-column</code> in order to use it.</strong>
    </p>
    <vaadin-demo-snippet id="grid-tree-demos-renderer-data-provider" when-defined="vaadin-grid">
      <template preserve-content="">
        <vaadin-grid aria-label="Tree Data Grid Example" data-provider="[[_dataProvider]]">
          <vaadin-grid-tree-column path="name" header="Expense category" item-has-children-path="hasChildren"></vaadin-grid-tree-column>
          <vaadin-grid-column path="uuid" header="Code" width="8em" flex-grow="0"></vaadin-grid-column>
        </vaadin-grid>

        <script>
          window.addDemoReadyListener('#grid-tree-demos-renderer-data-provider', function(document) {
            const grid = document.querySelector('vaadin-grid');
            grid.dataProvider = function(params, callback) {
              // If the data request concerns a tree sub-level, \`params\` has an additional
              // \`parentItem\` property that refers to the sub-level's parent item
              const parentUuid = params.parentItem ? params.parentItem.uuid : null;

              // Demo helper API that fetches expenses categories
              Vaadin.GridDemo._getExpensesCategories(parentUuid, function(categories) {
                // Here \`categories\` is an array consisting of items with the following structure:
                // {
                //   hasChildren: false
                //   name: "Service fees",
                //   uuid: "e78b"
                //   parentUuid: "e789"
                // }
                // This demo uses the \`hasChildren\` flag as an indicator of the item having child items.
                // The property is used internally by the \`<vaadin-grid-tree-column>\`.

                // Slice out only the requested items
                const startIndex = params.page * params.pageSize;
                const pageItems = categories.slice(startIndex, startIndex + params.pageSize);
                // Inform grid of the requested tree level's full size
                const treeLevelSize = categories.length;
                callback(pageItems, treeLevelSize);
              });
            };
          });
        &lt;/script>
      </template>
    </vaadin-demo-snippet>

    <h3>Tree Toggle Helper</h3>
    <p>
      Use <code>&lt;vaadin-grid-tree-toggle&gt;</code> in the cell content
      to make a tree UI inside a grid. You need to update the <code>expanded</code>,
      <code>level</code>, and <code>leaf</code> properties in your renderer.
    </p>
    <p>
      <strong>NOTE: You must explicitly import <code>vaadin-grid-tree-toggle</code> in order to use it.</strong>
    </p>
    <vaadin-demo-snippet id="grid-tree-demos-tree-toggle" when-defined="vaadin-grid">
      <template preserve-content="">
        <vaadin-grid aria-label="Tree Data Grid Example" data-provider="[[_dataProvider]]">
          <vaadin-grid-column header="Expense category"></vaadin-grid-column>
          <vaadin-grid-column path="uuid" header="Code" width="8em" flex-grow="0"></vaadin-grid-column>
        </vaadin-grid>

        <script>
          window.addDemoReadyListener('#grid-tree-demos-tree-toggle', function(document) {
            const grid = document.querySelector('vaadin-grid');
            const column = grid.querySelector('vaadin-grid-column');

            column.renderer = function(root, col, model) {
              let toggler = root.firstElementChild;
              if (!toggler) {
                toggler = window.document.createElement('vaadin-grid-tree-toggle');
                toggler.addEventListener('expanded-changed', function(e) {
                  grid[toggler.expanded ? 'expandItem' : 'collapseItem'](toggler.item);
                });
                root.appendChild(toggler);
              }

              toggler.item = model.item;
              toggler.leaf = !model.item.hasChildren;
              toggler.expanded = model.expanded;
              toggler.level = model.level;
              toggler.textContent = model.item.name;
            };

            grid.dataProvider = function(params, callback) {
              // If the data request concerns a tree sub-level, \`params\` has an additional
              // \`parentItem\` property that refers to the sub-level's parent item
              const parentUuid = params.parentItem ? params.parentItem.uuid : null;

              // Demo helper API that fetches expenses categories
              Vaadin.GridDemo._getExpensesCategories(parentUuid, function(categories) {
                // Here \`categories\` is an array consisting of items with the following structure:
                // {
                //   hasChildren: false
                //   name: "Service fees",
                //   uuid: "e78b"
                //   parentUuid: "e789"
                // }
                // This demo uses the \`hasChildren\` flag as an indicator of the item having child items.
                // The property is bound to \`<vaadin-grid-tree-toggle>\`'s \`leaf\` property in
                // the renderer above.

                // Slice out only the requested items
                const startIndex = params.page * params.pageSize;
                const pageItems = categories.slice(startIndex, startIndex + params.pageSize);
                // Inform grid of the requested tree level's full size
                const treeLevelSize = categories.length;
                callback(pageItems, treeLevelSize);
              });
            };
          });
        &lt;/script>
      </template>
    </vaadin-demo-snippet>

    <h3>Tree Using Templates and Polymer Binding</h3>
    <p>
      Use <code>&lt;vaadin-grid-tree-toggle&gt;</code> in the column body
      template to make a tree UI inside a grid. Make sure you have bindings
      for the <code>expanded</code>, <code>level</code>, and <code>leaf</code>
      properties.
    </p>
    <vaadin-demo-snippet id="grid-tree-demos-polymer-template" when-defined="vaadin-grid">
      <template preserve-content="">
        <vaadin-grid aria-label="Tree Data Grid Example" data-provider="[[_dataProvider]]">
          <vaadin-grid-column>
            <template class="header">Expense category</template>
            <template>
              <vaadin-grid-tree-toggle leaf="[[!item.hasChildren]]" expanded="{{expanded}}" level="[[level]]">
                [[item.name]]
              </vaadin-grid-tree-toggle>
            </template>
          </vaadin-grid-column>
          <vaadin-grid-column width="8em" flex-grow="0">
            <template class="header">Code</template>
            <template>[[item.uuid]]</template>
          </vaadin-grid-column>
        </vaadin-grid>

        <script>
          window.addDemoReadyListener('#grid-tree-demos-polymer-template', function(document) {
            const grid = document.querySelector('vaadin-grid');
            grid.dataProvider = function(params, callback) {
              // If the data request concerns a tree sub-level, \`params\` has an additional
              // \`parentItem\` property that refers to the sub-level's parent item
              const parentUuid = params.parentItem ? params.parentItem.uuid : null;

              // Demo helper API that fetches expenses categories
              Vaadin.GridDemo._getExpensesCategories(parentUuid, function(categories) {
                // Here \`categories\` is an array consisting of items with the following structure:
                // {
                //   hasChildren: false
                //   name: "Service fees",
                //   uuid: "e78b"
                //   parentUuid: "e789"
                // }
                // This demo uses the \`hasChildren\` flag as an indicator of the item having child items.
                // The property is bound to \`<vaadin-grid-tree-toggle>\`'s \`leaf\` property in
                // the template above.

                // Slice out only the requested items
                const startIndex = params.page * params.pageSize;
                const pageItems = categories.slice(startIndex, startIndex + params.pageSize);
                // Inform grid of the requested tree level's full size
                const treeLevelSize = categories.length;
                callback(pageItems, treeLevelSize);
              });
            };
          });
        &lt;/script>
      </template>
    </vaadin-demo-snippet>
`;
  }

  static get is() {
    return 'grid-tree-demos';
  }

  ready() {
    super.ready();

    GridDemo = GridDemo || {};
    GridDemo._getExpensesCategories = function(parentUuid, callback) {
      callback(GridDemo._treeData.filter(function(expense) {
        if (parentUuid) {
          return expense.parentUuid === parentUuid;
        } else {
          return !expense.parentUuid;
        }
      }));
    };
  }
}
customElements.define(GridTreeDemos.is, GridTreeDemos);
