var chalk = require('chalk');
var test = require('../node_modules/web-component-tester/runner/test.js');

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

module.exports = {
  localAddress : function () {
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
  },

  test: function(options, done) {
    test(options, cleanDone(done));
  }
};
