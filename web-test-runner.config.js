/* eslint-env node */
const { createSauceLabsLauncher } = require('@web/test-runner-saucelabs');
const { webdriverLauncher } = require('@web/test-runner-webdriver');
const fs = require('fs');

const config = {
  nodeResolve: true,
  browserStartTimeout: 1000 * 60 * 3, // default 30000
  testsStartTimeout: 1000 * 60 * 3, // default 10000
  testsFinishTimeout: 1000 * 60 * 3, // default 20000
  testFramework: {
    config: {
      timeout: '10000' // default 2000
    }
  },
  coverageConfig: {
    include: ['**/src/*'],
    threshold: {
      statements: 97,
      branches: 58,
      functions: 96,
      lines: 97
    }
  }
};

const env = process.env.TEST_ENV;

const sauce = {
  firefox: {
    browserName: 'firefox',
    platform: 'Windows 10',
    browserVersion: 'latest'
  },
  safari: {
    browserName: 'safari',
    platform: 'macOS 10.15',
    browserVersion: 'latest'
  }
};

let tests;

if (env === 'firefox' || env === 'safari' || env === 'ios') {
  // Exclude some suites to speed up tests.
  const exclude = [
    'all-imports.test.js',
    'extension.test.js',
    'hidden-grid.test.js',
    'iron-list.test.js',
    'missing-imports.test.js',
    'resizing-material.test.js'
  ];

  tests = fs
    .readdirSync('./test/')
    .filter((file) => file.includes('test.js') && !exclude.includes(file))
    .map((file) => `test/${file}`);
}

if (env === 'firefox' || env === 'safari') {
  const sauceLabsLauncher = createSauceLabsLauncher({
    user: process.env.SAUCE_USERNAME,
    key: process.env.SAUCE_ACCESS_KEY
  });

  const sharedCapabilities = {
    'sauce:options': {
      name: 'vaadin-grid unit tests',
      build: `${process.env.GITHUB_REF || 'local'} build ${process.env.GITHUB_RUN_NUMBER || ''}`
    }
  };

  config.files = tests;
  config.concurrency = env === 'firefox' ? 2 : 1;
  config.browsers = [
    sauceLabsLauncher({
      ...sharedCapabilities,
      ...sauce[env]
    })
  ];
}

if (env === 'ios') {
  // Exclude keyboard navigation tests on iOS
  config.files = tests.filter((test) => test.indexOf('keyboard-navigation') === -1);
  config.concurrency = 1;
  config.browsers = [
    webdriverLauncher({
      port: 4723,
      path: '/wd/hub/',
      capabilities: {
        // The defaults you need to have in your config
        browserName: 'safari',
        platformName: 'iOS',
        maxInstances: 1,
        // For W3C the appium capabilities need to have an extension prefix
        // This is `appium:` for all Appium Capabilities which can be found here
        // http://appium.io/docs/en/writing-running-appium/caps/
        'appium:deviceName': 'iPhone 11',
        // See https://github.com/actions/virtual-environments/blob/main/images/macos/macos-10.15-Readme.md#installed-simulators
        'appium:platformVersion': '14.2',
        'appium:orientation': 'PORTRAIT',
        'appium:automationName': 'XCUITest',
        'appium:newCommandTimeout': 240
      }
    })
  ];
}

module.exports = config;
