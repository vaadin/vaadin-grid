var _ = require('lodash');
var args = require('yargs').argv;
var bower = require('gulp-bower');
var common = require('./common');
var config = require('./config');
var download = require('gulp-download');
var fs = require('fs-extra');
var gulp = require('gulp');
var gutil = require('gulp-util');
var replace = require('gulp-replace');
var rename = require('gulp-rename');
var unzip = require('gulp-unzip');
var zip = require('gulp-zip');
var merge = require('merge-stream');

var stagingPath = config.paths.staging.zip;
var version = args.release || args.preRelease ? config.version : config.snapshotVersion;
var filename = 'vaadin-components-' + version + '.zip';
var majorMinorVersion = version.replace(/(\d+\.\d+)(\.|-)(.*)/, '$1');


gulp.task('clean:zip', function() {
  fs.removeSync(stagingPath);
});

gulp.task('stage:zip', ['clean:zip'], function() {
  var gridFiles = gulp.src(config.componentFiles, {base:"."})
    .pipe(rename(function(path) {
      path.dirname = 'vaadin-grid/' + path.dirname;
    }));
  var bowerFiles = gulp.src(config.componentBowerFiles, {base:"."})
    .pipe(rename(function(path) {
      path.dirname = path.dirname.replace(/^bower_components/,'');
    }));
  var licFiles = common.licenseFiles();
  
  return merge(gridFiles, bowerFiles, licFiles)
    .pipe(zip(filename))
    .pipe(gulp.dest(stagingPath));
});

gulp.task('zip:upload', ['stage:zip'], function(done) {
  common.checkArguments(['zipUsername', 'zipDestination']);
  var hostName = args.zipHostname || 'vaadin.com';
  var path = args.zipDestination + majorMinorVersion + '/' + version + '/' + filename;

  gutil.log('Uploading zip package (scp): ' + stagingPath + '/' + filename + ' -> ' + args.zipUsername + '@' + hostName + ':' + path);

  require('scp2').scp(stagingPath + '/' + filename, {
    host: hostName,
    username: args.zipUsername,
    privateKey: config.paths.privateKey(),
    path: path
  }, function(err) {
    done(err);
  })
});

function ssh(command, done) {
  var hostName = args.sshHostname || 'vaadin.com';
  gutil.log('SSH: ' + hostName + ' -> ' + command);

  require('node-ssh-exec')({
      host: hostName,
      username: args.zipUsername,
      privateKey: config.paths.privateKey()
    }, command,
    function (err) {
      done(err);
    });
}

gulp.task('zip:update-references', ['zip:upload'], function(done) {
  common.checkArguments(['zipUsername', 'zipDestination']);

  if(args.release) {
    ssh("sed -i '1i components/" + majorMinorVersion + '/' + version + "' " + args.zipDestination + 'VERSIONS', done);
  } else if(args.preRelease) {
    ssh("sed -i '1i components/" + majorMinorVersion + '/' + version + "' " + args.zipDestination + 'PRERELEASES', done);
  } else {
    ssh('echo components/' + majorMinorVersion + '/' + version + ' > ' + args.zipDestination + 'SNAPSHOT', done);
  }
});

gulp.task('deploy:zip', ['zip:upload', 'zip:update-references']);

gulp.task('zip-test:clean', function() {
  fs.removeSync(stagingPath + '/test');
});

