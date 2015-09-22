var args = require('yargs').argv;
var cmd = require('child_process');
var fs = require('fs-extra');
var gulp = require('gulp');
var gutil = require('gulp-util');
var rename = require('gulp-rename');
var replace = require('gulp-replace');
var git = require('gulp-git');
var insert = require('gulp-insert');
var rl = require('readline');
var watch = require('gulp-watch');

var gwtproject = 'java';
var version = '0.3.0';
var major = version.replace(/(\d+\.\d+\.).*$/, '$1');

var gwtNocacheJs = 'java/target/vaadin-grid-' + version + '/VaadinGridImport/VaadinGridImport.nocache.js';
var gwtMinJs = 'vaadin-grid.min.js';

var gitHash;
var jsHash;

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

function maven(tasks, done, log) {
  log = log || function(line) {
    console.log(line);
  }
  var args = tasks.split(/\s+/);
  args.unshift('-f', gwtproject, '-B');
  gutil.log(" $ mvn " + args);
  var spawn = require('child_process').spawn;
  var mvn = spawn('mvn', args);
  mvn.stdout.on('data', function (data) {
    log(('' + data).replace(/\s+$/,''));
  });
  mvn.stderr.on('data', function (data) {
    console.log(('' + data).replace(/\s+$/,''));
  });
  mvn.on('close', done || function(){});
}

gulp.task('gwt:clean', function(done) {
  maven('clean -q', done);
});

gulp.task('gwt:hash:src', function(done) {
  system('git log -1 --format=%H java/src/main/java', function(err, stdout) {
    gitHash = stdout.replace(/\s/g, '');
    done();
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
  lines.on('line', function (line) {
    var res = /vaadin.GridCommit = '(.*)'/.exec(line) 
    if (res) {
      jsHash = res[1];
      done();
    }
  });
});

gulp.task('gwt:compile', ['gwt:clean', 'gwt:hash:src', 'gwt:hash:js'], function(done) {
  if(gitHash == jsHash) {
    done();
  } else {
    maven('compile -q', function() {
      var task = 'package -q' + (args.gwtPretty ? ' -Ppretty' : '')  + " -P compile";
      maven(task, done);
    });
  }
});

gulp.task('gwt:copy', ['gwt:compile'], function() {
  return gulp.src(gwtNocacheJs)
          .pipe(rename(gwtMinJs))
          .pipe(insert.append("\nvaadin.GridCommit = '" + gitHash + "';\n"))
          .pipe(gulp.dest('./'));
});

gulp.task('gwt:validate', ['gwt:hash:src', 'gwt:hash:js'], function(done) {
  if (gitHash != jsHash) {
    throw new Error('ERROR: last java commit ' + gitHash + ' does not match js version ' + jsHash);
  }
});

gulp.task('gwt:watch', function(done) {
  gulp.watch(['demo/*', 'test/*', '*.html'] , function(){
    maven('generate-sources -q');
  });
})

gulp.task('gwt:run', function(done) {
  maven('clean gwt:run -q', done);
});

gulp.task('gwt:sdm', ['gwt:run', 'gwt:watch']);
