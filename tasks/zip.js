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
var unzip = require('gulp-unzip');
var zip = require('gulp-zip');

var stagingPath = config.paths.staging.zip;
var version = args.release || args.preRelease ? config.version : config.snapshotVersion;
var filename = 'vaadin-components-' + version + '.zip';
var majorMinorVersion = version.replace(/(\d+\.\d+)(\.|-)(.*)/, '$1');


gulp.task('clean:zip', function() {
  fs.removeSync(stagingPath);
});

gulp.task('stage:zip', ['clean:zip', 'stage:cdn'], function() {
  return gulp.src(config.paths.staging.cdn + '/' + version + '/**/*')
    .pipe(zip(filename))
    .pipe(gulp.dest(stagingPath));
});

gulp.task('zip:upload', ['stage:zip'], function(done) {
  common.checkArguments(['zipUsername', 'zipDestination']);

  var path = args.zipDestination + majorMinorVersion + '/' + version + '/' + filename;
  gutil.log('Uploading package to ' + path);

  require('scp2').scp(stagingPath + '/' + filename, {
    host: 'vaadin.com',
    username: args.zipUsername,
    privateKey: config.paths.privateKey(),
    path: path
  }, function(err) {
    done(err);
  })
});

function ssh(command, done) {
  gutil.log('SSH: ' + command);

  require('node-ssh-exec')({
      host: 'vaadin.com',
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

gulp.task('zip-test:download', ['zip-test:clean'],  function() {
  return download('https://vaadin.com/download/components/' + majorMinorVersion +'/' + version + '/' + filename)
    .pipe(gulp.dest(stagingPath + '/test'));
});

gulp.task('zip-test:unzip', ['zip-test:download'], function() {
  return gulp.src(stagingPath + '/test/' + filename)
    .pipe(unzip())
    .pipe(gulp.dest(stagingPath + '/test'));
});

gulp.task('zip-test:install-wct', ['zip-test:download'], function() {
  return bower({
    cwd: stagingPath + '/test',
    directory: '.',
    cmd: 'install'
  }, [['web-component-tester#2.2.6']]);
});

config.components.forEach(function (n) {
  gulp.task('zip-test:stage:' + n, ['zip-test:download'], function() {
    return gulp.src('vaadin-components/' + n + '/test/**/*')
      .pipe(replace(/(src|href)=("|')(.*?)\.\.\/\.\.\/\.\.\/\.\.\/(bower_components|node_modules)\//mg, '$1=$2../../$3'))
      .pipe(replace(/(src|href)=("|')(.*?)\.\.\/\.\.\/\.\.\/(bower_components|node_modules)\//mg, '$1=$2../../$3'))
      .pipe(replace(/(src|href)=("|')(.*?)\.\.\/(vaadin-)/mg, '$1=$2../../' + n + '/$3$4'))
      .pipe(gulp.dest(stagingPath + '/test/test/' + n + '/'));
  });
});

gulp.task('zip-test:stage', _.map(
  _.reject(config.components, function(n) {
    return n === 'vaadin-button';
  }), function (n) {
    return 'zip-test:stage:'+n;
  }));

gulp.task('verify:zip', ['zip-test:unzip', 'zip-test:install-wct', 'zip-test:stage'], function(done) {
  if(args.autoRevert) {
    common.checkArguments(['zipUsername', 'zipDestination']);
  }

  common.testSauce(
    ['target/zip/test/test/**/index.html'],
    ['Windows 7/internet explorer@11'],
    'vaadin-components / vaadin.com / ' + version,
    function(err) {
      common.autoRevert(err, function() {
        var path = args.zipDestination + majorMinorVersion + '/' + version;

        gutil.log('Deleting package ' + path);

        // remove the version from VERSIONS
        ssh('grep -v "components/' + majorMinorVersion + '/' + version + '" ' +
          args.zipDestination + 'VERSIONS > temp && mv temp ' + args.zipDestination + 'VERSIONS', function(error) {
          if(error) done(error);

          // remove the package
          ssh('rm -rf ' + path, done);
        });
        }, done);
      });
});


