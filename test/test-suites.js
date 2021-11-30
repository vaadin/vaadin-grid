const isPolymer2 = document.querySelector('script[src*="wct-browser-legacy"]') === null;

window.VaadinGridSuites = [
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
  'column-resizing.html'
];

if (isPolymer2) {
  window.VaadinGridSuites.push('app-localize-behavior.html');
}
