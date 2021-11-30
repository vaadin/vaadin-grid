const isPolymer2 = document.querySelector('script[src*="wct-browser-legacy"]') === null;

window.VaadinGridSuitsBatch1 = [
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
window.VaadinGridSuitsBatch2 = [
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
window.VaadinGridSuitsBatch3 = [
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
window.VaadinGridSuitsBatch4 = [
  'row-height.html',
  'scroll-to-index.html',
  'scrolling-mode.html',
  'selecting.html',
  'sorting.html',
  'style-scope.html',
  'templates.html',
  'renderers.html'
];

if (isPolymer2) {
  window.VaadinGridSuitsBatch1.push('app-localize-behavior.html');
  window.VaadinGridSuitsBatch2.push('app-localize-behavior.html');
  window.VaadinGridSuitsBatch3.push('app-localize-behavior.html');
  window.VaadinGridSuitsBatch4.push('app-localize-behavior.html');
}
