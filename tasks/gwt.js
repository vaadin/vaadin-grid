var args = require('yargs').argv;
var cmd = require('child_process');
var fs = require('fs-extra');
var gulp = require('gulp');
var gutil = require('gulp-util');
var hashFiles = require('hash-files');
var rename = require('gulp-rename');
var replace = require('gulp-replace');
var git = require('gulp-git');
var insert = require('gulp-insert');
var rl = require('readline');
var watch = require('gulp-watch');

var gwtproject = 'java';
var version = '1.1.2';
var major = version.replace(/(\d+\.\d+\.).*$/, '$1');

var gwtNocacheJs = 'java/target/vaadin-grid-' + version + '/VaadinGridImport/VaadinGridImport.nocache.js';
var gwtMinJs = 'vaadin-grid.min.js';

var gitHash;
var jsHash;

function system(command, cb, eb) {
  cmd.exec(command, function(err, stdout, stderr) {
    if (err) {
      if (eb) {
        eb(err);
      } else {
        gutil.log(stderr);
        throw err;
      }
    } else if (cb) {
      cb(err, stdout, stderr);
    }
  });
}

function maven(tasks, done, log) {
  log = log || function(line) {
    console.log(line);
  };
  var args = tasks.split(/\s+/);
  args.unshift('-f', gwtproject, '-B');
  gutil.log(' $ mvn ' + args);
  var spawn = require('child_process').spawn;
  var mvn = spawn('mvn', args);
  mvn.stdout.on('data', function(data) {
    log(('' + data).replace(/\s+$/,''));
  });
  mvn.stderr.on('data', function(data) {
    console.log(('' + data).replace(/\s+$/,''));
  });
  mvn.on('close', done || function() {});
}

gulp.task('gwt:clean', function(done) {
  maven('clean -q', done);
});

gulp.task('gwt:hash:src', function(done) {
  hashFiles({files: ['java/src/main/java/**']}, function(err, hash) {
    if (err) {
      done(err);
    } else {
      gitHash = hash;
      done();
    }
  });
});

gulp.task('gwt:hash:js', function(done) {
  if (!fs.existsSync(gwtMinJs)) {
    jsHash = 'unknown';
    done();
    return;
  }
  var lines = rl.createInterface({
    terminal: false,
    input: require('fs').createReadStream(gwtMinJs)
  });
  lines.on('line', function(line) {
    var res = /\/\/ vaadin.elements.grid.hash = '(.*)'/.exec(line);
    if (res) {
      jsHash = res[1];
      done();
    }
  });
});

gulp.task('gwt:compile', ['gwt:clean', 'gwt:hash:src', 'gwt:hash:js'], function(done) {
  maven('compile -q', function() {
    var task = 'package -q' + (args.gwtPretty ? ' -Ppretty' : '')  + ' -P compile';
    maven(task, done);
  });
});

gulp.task('gwt:copy', ['gwt:compile'], function() {
  return gulp.src(gwtNocacheJs)
          .pipe(rename(gwtMinJs))
          .pipe(insert.append('\n// vaadin.elements.grid.hash = \'' + (gitHash ? gitHash : '-') + '\';\n'))
          .pipe(gulp.dest('./'));
});

gulp.task('gwt:validate', ['gwt:hash:src', 'gwt:hash:js'], function(done) {
  if (gitHash != jsHash) {
    throw new Error('ERROR: last java commit ' + gitHash + ' does not match js version ' + jsHash);
  }
});

gulp.task('gwt:watch', function(done) {
  gulp.watch(['demo/*', 'test/*', '*.html', 'bower_components/**/*'] , function() {
    maven('generate-sources -q');
  });
});

gulp.task('gwt:run', function(done) {
  // SDM reuses certain stuff pre-cached in user's tmp folder
  // causing errors noticeables when changing certain stuff like
  // JsInterop annotations, exported functions etc.
  require('del').sync(process.env.TMPDIR + '/gwt*', {force: true});
  maven('clean gwt:run -q', done);
});

gulp.task('gwt:sdm', ['gwt:run', 'gwt:watch']);
