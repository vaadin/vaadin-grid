const suites = require('./test/test-suites');

var envIndex = process.argv.indexOf('--env') + 1;
var env = envIndex ? process.argv[envIndex] : undefined;
var suitesIndex = process.argv.indexOf('--suites') + 1;
var suitesParam = suitesIndex ? process.argv[suitesIndex] : undefined;

// workaround for Android 7+ blocking all HTTP traffic
// see https://wiki.saucelabs.com/display/DOCS/Known+Issues
// add it to your own local `/etc/hosts` to run SauceLabs tests locally
var tunneledLocalhost = 'localhost-for-saucelabs';

module.exports = {
  testTimeout: 360 * 1000,
  verbose: false,
  plugins: {
    local: {
      browserOptions: {
        chrome: [
          'headless',
          'disable-gpu',
          'no-sandbox'
        ]
      }
    },
  },

  registerHooks: function(context) {
    if (suitesParam) {
      context.options.suites = suites[suitesParam] || [];
    } else {
      context.options.suites = suites.all;
    }

    const testBrowsers = [
      {
        deviceName: 'Android GoogleAPI Emulator',
        platformName: 'Android',
        platformVersion: '11.0',
        browserName: 'Chrome',
      },
      'iOS Simulator/iphone@12.2', // should be 9.x, but SauceLabs does not provide that
      'macOS 11/safari@latest',
      'Windows 10/microsoftedge@latest',
      'Windows 10/internet explorer@11',
      'Windows 10/chrome@latest',
      'Windows 10/firefox@latest',
    ];

    if (env === 'saucelabs') {
      context.options.webserver = context.options.webserver || {};
      context.options.webserver.hostname = tunneledLocalhost;
      context.options.plugins.sauce.tunnelOptions = {
        tunnelDomains: tunneledLocalhost
      };

      context.options.plugins.sauce.browsers = testBrowsers;
    }

    // Map legacy tunnel-identifier option to new tunnel-name option
    context.hookLate('prepare', (done) => {
      context.options.activeBrowsers.forEach((browser) => {
        browser['tunnel-name'] = browser['tunnel-identifier'];
        delete browser['tunnel-identifier'];
      });
      done();
    });
  }
};
