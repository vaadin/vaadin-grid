var args = require('yargs').argv;
var fs = require('fs');

var userhome = process.env.HOME || process.env.HOMEPATH || process.env.USERPROFILE;

module.exports = {
  components: ['vaadin-grid'],
  snapshotVersion: '0.3.0-snapshot',
  version: args.version || '0.3.0-snapshot',
  paths: {
    staging: {
      bower: 'target/bower',
      cdn: 'target/cdn',
      zip: 'target/zip'
    },
    userhome: userhome,
    privateKey: function() {
      try {
        return fs.readFileSync(userhome + '/.ssh/id_rsa');
      } catch(error) {
        return fs.readFileSync(userhome + '/.ssh/id_dsa');
      }
    }
  },
  componentFiles: [
    'vaadin-grid.html',
    'vaadin-grid.min.js',
    'vaadin-grid-doc.html',
    'gulp.js',
    'demo/*',
    'test/*',
    'img/*',
  ],
  componentBowerFiles: [
     'bower_components/webcomponentsjs/**/*',
     'bower_components/polymer/**/*',
     'bower_components/code-example/**/*',
     'bower_components/zeroclipboard/**/*'
  ],
  componentHtmlFiles: [
     'vaadin-grid.html',
     'demo/*.html',
     'test/*.html'
  ],
  componentLicenseFiles: [
     'README.md', 'LICENSE.md', 'CHANGES.md'
  ]
};
