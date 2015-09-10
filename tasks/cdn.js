var bower =  require('gulp-bower');
var config = require('./config');
var common = require('./common')
var gulp = require('gulp');
var fs = require('fs-extra');
var markdown = require('gulp-markdown');
var replace = require('gulp-replace');
var rsync = require('gulp-rsync');
var gutil = require('gulp-util');
var _ = require('lodash');
var args = require('yargs').argv;
var rename = require('gulp-rename');
var merge = require('merge-stream');

var stagingBasePath = config.paths.staging.cdn;
var version = args.release || args.preRelease || args.autoRevert ? config.version : config.snapshotVersion;
var stagingPath = stagingBasePath + '/' + version;

gulp.task('clean:cdn', function() {
  fs.removeSync(stagingBasePath);
});

gulp.task('stage:cdn', ['clean:cdn'], function() {
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
    .pipe(gulp.dest(stagingPath));
});

gulp.task('deploy:cdn', ['stage:cdn'], function() {
  common.checkArguments(['cdnUsername', 'cdnDestination']);
  var hostName = args.cdnHostname || 'cdn.vaadin.com';

  gutil.log('Uploading to cdn (rsync): ' + stagingPath + ' -> '+ args.cdnUsername + '@' + hostName + ':' + args.cdnDestination + version);

  return gulp.src(stagingPath)
    .pipe(rsync({
      username: args.cdnUsername,
      hostname: hostName,
      root: stagingPath,
      emptyDirectories: false,
      recursive: true,
      clean: true,
      silent: true,
      destination: args.cdnDestination + version
    }));
});
