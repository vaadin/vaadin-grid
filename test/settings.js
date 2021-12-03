(function() {
  const ie = !!window.MSStream;
  const ios = !ie && /iPad|iPhone|iPod/.test(navigator.userAgent);
  // Change timeout for individual tests for the mocha test runner
  // See here: https://github.com/Polymer/tools/blob/master/packages/web-component-tester/browser.js#L1814
  window.WCT = {
    mochaOptions: {
      timeout: ios || ie ? 40000 : 30000
    }
  };
})();
