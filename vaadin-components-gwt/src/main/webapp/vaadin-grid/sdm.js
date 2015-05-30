var webComponentHref = "vaadin-grid.html";

function handleSDMinit(done) {
  // 1. Wait for GWT module to initialize
  var id = setInterval(function() {
    if (window.vaadin && window.vaadin.GridComponent) {
      clearInterval(id);
      // 2. GWT module is ready, add the web component import
      _addWebComponentImport();
      // 3. Wait for the web component to register
      _waitForVGrid(done);
    }
  }, 200);
}

function _addWebComponentImport() {
  var fileref = document.createElement("link");
  fileref.setAttribute("rel", "import");
  fileref.setAttribute("href", webComponentHref);
  document.getElementsByTagName("head")[0].appendChild(fileref);
}

function _waitForVGrid(done) {
  var id = setInterval(function() {
    if ("VGrid" in window) {
      clearInterval(id);

      done();

      // Schedule a refresh for each Grid
      [].forEach.call(document.querySelectorAll("v-grid"), function(grid) {
        setTimeout(function() {
          grid._grid.updateSize();
        }, 1);
      });
    }
  }, 200);
}
