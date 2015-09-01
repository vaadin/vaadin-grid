var gemini = require('gemini');

gemini.suite('vaadin-grid', function(rootSuite) {
  rootSuite.setUrl('/visual-test/vaadin-grid/');

  rootSuite.before(function(actions, find) {
    actions.waitForJSCondition(function(window) {
      var grid = window.document.querySelector('vaadin-grid');

      if(grid._grid) {
        return grid._grid.isWorkPending() === false;
      }

      return false;

    }, 5000);
  });

  gemini.suite('overview', function(suite) {
    suite.setCaptureElements('vaadin-grid').capture('default');
  });
});