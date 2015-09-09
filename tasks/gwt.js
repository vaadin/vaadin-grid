var args = require('yargs').argv;
var cmd = require('child_process');
var fs = require('fs-extra');
var gulp = require('gulp');
var gutil = require('gulp-util');
var rename = require('gulp-rename');
var replace = require('gulp-replace');

var pwd = process.cwd();
var gwtproject = 'java';
var version = '0.3.0';
var major = version.replace(/(\d+\.\d+\.).*$/, '$1');

var component = 'vaadin-grid';
var moduleName = 'VaadinGrid';
var webDir = 'java/src/main/webapp/';
var webComponentDir = webDir + component + '/';
var componentDir = './';

var staticResources = ['*.html', 'dem*/**', 'tes*/**'];

function system(command, cb) {
  cmd.exec(command, function(err, stdout, stderr) {
    if (err) {
      gutil.log(stderr);
      throw err;
    }
    if (cb) {
      cb(err, stdout, stderr);
    }
  });
};

function maven(tasks, cb) {
  gutil.log(" $ mvn " + tasks);
  system('mvn -f ' + gwtproject + '/pom.xml -q ' + tasks, function() {
    gutil.log(" $ mvn " + tasks + ' [done]');
    cb();
  });
}
gulp.task('gwt:sdm', function(){
  system('mvn -f java clean gwt:run');
  gulp.watch(staticResources, ['copystatic']);
});

gulp.task('copystatic', function() {
  gutil.log('Copying static resources for sdm');
    gulp.src(staticResources)
    .pipe(gulp.dest(gwtproject + '/target/vaadin-grid-' + version));
});

gulp.task('gwt:compile', ['gwt:clean-maven'], function(done) {
  if(args.gwtSkipCompile) {
    done();
    return;
  }

  gutil.log('Updating Maven dependencies ...');
  maven('compile', function() {
    var task = 'package ' + (args.gwtPretty ? ' -Ppretty' : '');
    maven(task, done);
  });
});

gulp.task('gwt:clean-maven', function(done) {
  if(args.gwtSkipClean) {
    done();
    return;
  }

  maven('clean', done);
});

gulp.task('clean:gwt', ['gwt:clean-maven'], function() {
  if(args.gwtSkipClean) {
    return;
  }

  fs.removeSync(componentDir);
  fs.mkdirsSync(componentDir);
});

gulp.task('gwt:copy', ['gwt:compile', 'clean:gwt'], function() {
  var warDir = 'java/target/vaadin-grid-' + version + '/';
  var modulePath = warDir + moduleName + 'Import/';
  return gulp.src(modulePath + moduleName +'Import.nocache.js')
          .pipe(rename(component + '.min.js'))
          .pipe(gulp.dest(componentDir));
});
