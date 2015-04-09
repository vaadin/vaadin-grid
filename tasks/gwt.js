var args = require('yargs').argv;
var cmd = require('child_process');
var fs = require('fs-extra');
var gulp = require('gulp');
var gutil = require('gulp-util');
var rename = require('gulp-rename');
var replace = require('gulp-replace');

var pwd = process.cwd();
var gwtproject = 'vaadin-components-gwt';
var version = '0.2.0';
var major = version.replace(/(\d+\.\d+\.).*$/, '$1');
var patch = ~~((new Date().getTime() / 1000 - 1420070400) / 60)
var tag =  args.version || major + patch; // will rename the tag variable in a separate patch

var component = 'vaadin-grid';
var moduleName = 'VaadinGrid';
var webDir = 'vaadin-components-gwt/src/main/webapp/';
var webComponentDir = webDir + component + '/';
var componentDir = 'vaadin-components/' + component;

function system(command, cb) {
  cmd.exec(command, function(err, stdout, stderr) {
    if (err) {
      gutil.log(stderr);
      throw err;
    }
    if (cb) {
      cb(err);
    }
  });
};

gulp.task('gwt:compile', function(done) {
  gutil.log('Updating Maven dependencies ...');
  system('mvn compile -q -am -pl ' + gwtproject, function() {
    gutil.log('Compiling GWT components ...');
    var command = 'mvn package -q -am -pl ' + gwtproject + (args.gwtPretty ? ' -Ppretty' : '');
    system(command, function() {
      gutil.log('GWT components compilation succeeded.')

      done();
    });
  });
});

gulp.task('gwt:copy', ['gwt:copy-files', 'gwt:copy-imports', 'gwt:copy-deferred']);

gulp.task('gwt:clean-maven', function(done) {
  system('mvn clean', done);
});

gulp.task('clean:gwt', ['gwt:clean-maven'], function() {
  fs.removeSync(componentDir);
  fs.mkdirsSync(componentDir);
});

gulp.task('gwt:copy-files', ['gwt:compile', 'clean:gwt'], function() {
  return gulp.src(webComponentDir + '**/*')
          .pipe(replace(new RegExp('^.*script.*' + moduleName + '.*$','mg'), ''))
          .pipe(gulp.dest(componentDir));
});

gulp.task('gwt:copy-imports', ['gwt:compile', 'clean:gwt'], function() {
  var warDir = 'vaadin-components-gwt/target/vaadin-components-gwt-' + version + '/';
  var modulePath = warDir + moduleName + '/';

  return gulp.src(modulePath + moduleName +  '-import.html')
    .pipe(rename(function (path) {
      path.basename = "vaadin-grid-import";
    }))
    .pipe(gulp.dest(componentDir));
});

gulp.task('gwt:copy-deferred', ['gwt:compile', 'clean:gwt'], function() {
  return gulp.src(componentDir + 'deferred')
          .pipe(gulp.dest(componentDir));
});

