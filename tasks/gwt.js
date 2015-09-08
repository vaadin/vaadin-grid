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
var patch = ~~((new Date().getTime() / 1000 - 1420070400) / 60)
var tag =  args.version || major + patch; // will rename the tag variable in a separate patch

var component = 'vaadin-grid';
var moduleName = 'VaadinGrid';
var webDir = 'java/src/main/webapp/';
var webComponentDir = webDir + component + '/';
var componentDir = './';

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

gulp.task('gwt:compile', ['gwt:clean-maven'], function(done) {
  if(args.gwtSkipCompile) {
    done();
    return;
  }

  gutil.log('Updating Maven dependencies ...');
  system('mvn -f ' + gwtproject + '/pom.xml compile -q ', function() {
    gutil.log('Compiling GWT components ...');
    var command = 'mvn -f ' + gwtproject + '/pom.xml package -q ' + (args.gwtPretty ? ' -Ppretty' : '') + " -P compile";
    system(command, function() {
      gutil.log('GWT components compilation succeeded.')
      done();
    });
  });
});

gulp.task('gwt:clean-maven', function(done) {
  if(args.gwtSkipClean) {
    done();
    return;
  }

  system('mvn -f ' + gwtproject + '/pom.xml clean', done);
});

gulp.task('test:gwt', function(done) {
  cmd.exec('mvn test', function(err, stdout) {
    gutil.log(stdout);
    done();
  });
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
