function gridLoaded() {
  return window.vaadin && vaadin.elements && vaadin.elements.grid && vaadin.elements.grid.GridElement;
}

// This is needed when vaadin-grid.min.js is loaded asynchronously like
// in SDM. We must guarantee that gwt grid is loaded and exported before
// Polymer is called.
var _Polymer = Polymer;
// Override Polymer
Polymer = function(obj) {
  // Restore Polymer
  Polymer = _Polymer;
  var _done = false;
  // Overwrite whenReady
  var _whenReady = HTMLImports.whenReady;
  HTMLImports.whenReady = function(done) {
    var id = setInterval(function() {
      if (gridLoaded()) {
        clearInterval(id);
        if (!_done) {
          // Run original Polymer
          _Polymer(obj);
          _done = true;
        }
        // Restore whenReady
        HTMLImports.whenReady = _whenReady;
        // Run original whenReady
        _whenReady(done);
      }
    }, 3);
  };
};
