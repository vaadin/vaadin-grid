var gemini = require('gemini');

gemini.suite('vaadin-grid', function(rootSuite) {
  rootSuite.setUrl('/visual-test/vaadin-grid/');

  gemini.suite('headers', function(suite) {
    suite.setCaptureElements('th').capture('first column');
  });
});