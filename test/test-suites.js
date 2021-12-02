const isPolymer2 = document.querySelector('script[src*="wct-browser-legacy"]') === null;

const batch1 = [
  'million-dollar-scrolling.html',
  'accessibility.html',
  'all-imports.html',
  'array-data-provider.html',
  'basic.html',
  'class-name-generator.html',
  'column-auto-width.html',
  'column-group.html',
  'column-groups.html',
  'column-reordering.html',
];
if (isPolymer2) {
  batch1.push('app-localize-behavior.html');
}

const batch2 = [
  'column-resizing.html',
  'column.html',
  'data-provider.html',
  'drag-and-drop.html',
  'dynamic-item-size.html',
  'event-context.html',
  'filtering.html',
  'frozen-columns.html',
  'hidden-grid.html',
];
const batch3 = [
  'tree-toggle.html',
  'iron-list.html',
  'keyboard-navigation.html',
  'lazy-import.html',
  'light-dom-observing.html',
  'physical-count.html',
  'resizing.html',
  'resizing-material.html',
  'row-details.html',
];
const batch4 = [
  'row-height.html',
  'scroll-to-index.html',
  'scrolling-mode.html',
  'selecting.html',
  'sorting.html',
  'style-scope.html',
  'templates.html',
  'renderers.html'
];

window.VaadinGridSuites = [
  ...batch1,
  ...batch2,
  ...batch3,
  ...batch4,
];

window.VaadinGridSuites.batch1 = batch1;
window.VaadinGridSuites.batch2 = batch2;
window.VaadinGridSuites.batch3 = batch3;
window.VaadinGridSuites.batch4 = batch4;