
// SuperDevMode loads .nocache.js asynchronously, so vaadin-grid.html
// is executed before .nocache.js is processed. Here we wait until
// window.vaadin.GridComponent exists, which implies that the nocache
// file was loaded.
(function() {
  if (!window.vaadin || !window.vaadin.GridComponent) {
    _whenReady = HTMLImports.whenReady;
    _imported = false;
    var gridHref = "../vaadin-grid.html";
    HTMLImports.whenReady = function(done) {
      var id = setInterval(function() {
        if (window.vaadin && window.vaadin.GridComponent) {
          clearInterval(id);
          if (!_imported) {
            _imported = true;
            var link = document.createElement("link");
            link.setAttribute("rel", "import");
            link.setAttribute("href", gridHref);
            document.head.appendChild(link);
          }
          (HTMLImports.whenReady = _whenReady)(done);
        }
      }, 1);
    }
  }
})();
