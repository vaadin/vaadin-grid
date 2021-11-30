var envIndex = process.argv.indexOf('--env') + 1;
var env = envIndex ? process.argv[envIndex] : undefined;

module.exports = {
  testTimeout: 300 * 1000,
  verbose: true,
  plugins: {
    local: {
      browserOptions: {
        chrome: [
          'headless',
          'disable-gpu',
          'no-sandbox'
        ]
      }
    }
  },
  registerHooks: function(context) {
    const saucelabsPlatformsMobileIphone12 = [
      'iOS Simulator/iphone@12.2'
    ];
    const saucelabsPlatformsMobileIphone10 = [
      'iOS Simulator/iphone@10.3'
    ];

    const saucelabsPlatformsDesktopSafari = ['macOS 10.13/safari@latest'];
    const saucelabsPlatformsDesktopIE = ['Windows 10/internet explorer@11'];
    const saucelabsPlatformsDesktopEdge = ['Windows 10/microsoftedge@18'];

    const cronPlatforms = [
      'iOS Simulator/ipad@12.2',
      'iOS Simulator/iphone@10.3',
      'Windows 10/chrome@latest',
      'Windows 10/firefox@latest'
    ];

    if (env === 'saucelabs:mobile_iphone10') {
      context.options.plugins.sauce.browsers = saucelabsPlatformsMobileIphone10;
    } else if (env === 'saucelabs:mobile_iphone12') {
      context.options.plugins.sauce.browsers = saucelabsPlatformsMobileIphone12;
    } else if (env === 'saucelabs:desktop_safari') {
      context.options.plugins.sauce.browsers = saucelabsPlatformsDesktopSafari;
    } else if (env === 'saucelabs:desktop_edge') {
      context.options.plugins.sauce.browsers = saucelabsPlatformsDesktopEdge;
    } else if (env === 'saucelabs:desktop_ie') {
      context.options.plugins.sauce.browsers = saucelabsPlatformsDesktopIE;
    } else if (env === 'saucelabs') {
      context.options.plugins.sauce.browsers = [
        ...saucelabsPlatformsMobileIphone10,
        ...saucelabsPlatformsMobileIphone12,
        ...saucelabsPlatformsDesktopSafari,
        ...saucelabsPlatformsDesktopEdge,
        ...saucelabsPlatformsDesktopIE
      ];

    } else if (env === 'saucelabs-cron') {
      context.options.plugins.sauce.browsers = cronPlatforms;

    // Add coverage for local tests only
    } else {
      /* context.options.plugins.istanbul = {
        'dir': './coverage',
        'reporters': ['text-summary', 'lcov'],
        'include': [
          '/vaadin-grid-active-item-mixin.html',
          '/vaadin-grid-array-data-provider-mixin.html',
          '/vaadin-grid-cell-click-mixin.html',
          '/vaadin-grid-column-group.html',
          '/vaadin-grid-column-reordering-mixin.html',
          '/vaadin-grid-column-resizing-mixin.html',
          '/vaadin-grid-column.html',
          '/vaadin-grid-data-provider-mixin.html',
          '/vaadin-grid-dynamic-columns-mixin.html',
          '/vaadin-grid-filter-mixin.html',
          '/vaadin-grid-filter.html',
          '/vaadin-grid-keyboard-navigation-mixin.html',
          '/vaadin-grid-row-details-mixin.html',
          '/vaadin-grid-scroll-mixin.html',
          '/vaadin-grid-scroller.html',
          '/vaadin-grid-selection-column.html',
          '/vaadin-grid-selection-mixin.html',
          '/vaadin-grid-sort-mixin.html',
          '/vaadin-grid-sorter.html',
          '/vaadin-grid-styles.html',
          '/vaadin-grid-templatizer.html',
          '/vaadin-grid.html'
        ],
        'exclude': []
      };*/
    }
  }
};
