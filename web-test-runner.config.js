/* eslint-env node */
const { createSauceLabsLauncher } = require('@web/test-runner-saucelabs');

const config = {
  nodeResolve: true,
  browserStartTimeout: 60000, // default 30000
  testsStartTimeout: 60000, // default 10000
  testsFinishTimeout: 60000, // default 20000
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
    browserVersion: '13.1'
  }
};

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

  config.concurrency = 2;
  config.browsers = [
    sauceLabsLauncher({
      ...sharedCapabilities,
      ...sauce[env]
    })
  ];
}

module.exports = config;
