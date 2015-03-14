var args = require('yargs').argv;

var pwd = process.cwd();
var gwtproject = 'vaadin-components-gwt';
var gitrepo = 'git@github.com:vaadin-bower/';
var target = pwd + '/target';
var version = '0.2.0';
var major = version.replace(/(\d+\.\d+\.).*$/, '$1');
var patch = ~~((new Date().getTime() / 1000 - 1420070400) / 60)
var tag =  args.version || major + patch; // will rename the tag variable in a separate patch
var target_cdn = target + '/cdn/' + tag;
var target_bower = target + '/bower';
var target_zip = target + '/zip';

module.exports = {
  components: ['vaadin-button', 'vaadin-grid'],
  snapshotVersion: '0.2-snapshot',
  version: args.version || '0.2-snapshot',
  paths: {
    staging: {
      bower: 'target/bower',
      cdn: 'target/cdn'
    },
    userhome: process.env.HOME || process.env.HOMEPATH || process.env.USERPROFILE
  }
};
