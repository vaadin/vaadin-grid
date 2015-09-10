var args = require('yargs').argv;
var chalk = require('chalk');
var wct = require('web-component-tester').test;
var _ = require('lodash');
var gutil = require('gulp-util');

function cleanDone(done) {
  return function(error) {
    if (error) {
      // Pretty error for gulp.
      error = new Error(chalk.red(error.message || error));
      error.showStack = false;
    }
    done(error);
  };
}

function checkArguments(arguments) {
  _.forEach(arguments, function(a) {
    if(!args.hasOwnProperty(a)) {
      throw Error('Required argument \'--'+ a +'\' is missing.');
    }
  });
}

function localAddress() {
  var ip, tun, ifaces = require('os').networkInterfaces();
  Object.keys(ifaces).forEach(function (ifname) {
    ifaces[ifname].forEach(function (iface) {
      if ('IPv4' == iface.family && !iface.internal) {
        if (!ip) ip = iface.address;
        if (/tun/.test(ifname)) tun = iface.address;
      }
    });
  });
  return tun || ip;
}

function test(options, done) {
  wct(options, cleanDone(done));
}

module.exports = {
  localAddress: localAddress,
  test: test,
  checkArguments: checkArguments,

  testSauce: function(suites, browsers, build, done) {
    test(
      {
        suites: suites,
        browserOptions: {
          name: localAddress() + ' / ' + new Date(),
          build: build
        },

        plugins: {
          //local: {
          //  browsers: ['chrome']
          //},
          sauce: {
            username: args.sauceUsername,
            accessKey: args.sauceAccessKey,
            browsers: browsers
          },
          'teamcity-reporter': args.teamcity
        },
        webserver: {
          hostname: localAddress()
        }
    }, done);
  },

  autoRevert: function(err, handler, done) {
    if(err) {
      gutil.log(err.toString());
      if(args.autoRevert) {
        handler();
      } else {
        gutil.log('No action. Use --auto-revert to revert changes.')
        done(err);
      }
    } else {
      done();
    }
  }
};
