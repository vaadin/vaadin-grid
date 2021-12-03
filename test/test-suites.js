const batch1 = [
  'test/accessibility.html',
  'test/all-imports.html',
  // 'test/app-localize-behavior.html',
  'test/array-data-provider.html',
  'test/basic.html',
  'test/class-name-generator.html',
  'test/column-auto-width.html',
  'test/column-group.html',
  'test/column-groups.html',
  'test/column-reordering.html',
];

const batch2 = [
  'test/column-resizing.html',
  'test/column.html',
  'test/data-provider.html',
  'test/drag-and-drop.html',
  'test/dynamic-item-size.html',
  'test/event-context.html',
  'test/filtering.html',
  'test/frozen-columns.html',
  'test/hidden-grid.html',
];

const batch3 = [
  'test/tree-toggle.html',
  'test/iron-list.html',
  'test/keyboard-navigation.html',
  'test/lazy-import.html',
  'test/light-dom-observing.html',
  'test/physical-count.html',
  'test/resizing.html',
  'test/resizing-material.html',
  'test/row-details.html',
];

const batch4 = [
  'test/row-height.html',
  'test/scroll-to-index.html',
  'test/scrolling-mode.html',
  'test/selecting.html',
  'test/sorting.html',
  'test/style-scope.html',
  'test/templates.html',
  'test/renderers.html'
];

const batch5 = [
  'test/million-dollar-scrolling.html',
];

const all = [
  ...batch1,
  ...batch2,
  ...batch3,
  ...batch4,
  ...batch5,
];

module.exports = {
  batch1,
  batch2,
  batch3,
  batch4,
  batch5,
  all,
};
