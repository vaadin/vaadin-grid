var config = require('./config');
var common = require('./common');
var fs = require('fs-extra');
var gulp = require('gulp');
var bower = require('gulp-bower');
var git = require('gulp-git');
var json = require('gulp-json-editor');
var replace = require('gulp-replace');
var gutil = require('gulp-util');
var _ = require('lodash');
var args = require('yargs').argv;

var gitRepository = 'git@github.com:vaadin-bower/vaadin-components.git';
var checkoutPath = config.paths.staging.bower;
var components = config.components;
var version = args.release || args.preRelease ? config.version : config.snapshotVersion;
var snapshotVersion = config.snapshotVersion;

gulp.task('clean:bower', function() {
  fs.removeSync(checkoutPath);
});

gulp.task('bower:clone', ['clean:bower'], function(done) {
  var cloneArgs = '-b ' + (args.bowerCheckout ? args.bowerCheckout : 'master') + ' ' + checkoutPath;

  git.clone(gitRepository, {
    args : cloneArgs
  }, function(err) {
    if (err) throw err;
    fs.removeSync(checkoutPath + '/*');
    done();
  });
});

gulp.task('bower:stage-components', ['bower:clone'], function() {
  _.forEach(components, function(component) {
    var stagingPath = checkoutPath + '/' + component;

    fs.copySync('vaadin-components/' + component, stagingPath);

    fs.removeSync(stagingPath + '/**/demo*');
    fs.removeSync(stagingPath + '/**/sdm.js');
    fs.removeSync(stagingPath + '/**/test');
  });
});

gulp.task('bower:stage-vaadin-components.html', ['bower:clone'], function() {
  return gulp.src('vaadin-components-package/vaadin-components.html')
    .pipe(gulp.dest(checkoutPath));
});

gulp.task('bower:stage-bower.json', ['bower:clone'], function() {
  return gulp.src('vaadin-components-package/bower.json')
             .pipe(json({version: version}))
             .pipe(gulp.dest(checkoutPath));
});

gulp.task('bower:stage-bundled.md', ['bower:clone'], function() {
  return gulp.src(['vaadin-components-package/README.md', 'LICENSE.md', 'CHANGES.md'])
    .pipe(gulp.dest(checkoutPath));
});

gulp.task('bower:stage-imports', ['bower:stage-components'], function() {
  return gulp.src(checkoutPath + '/**/*.html')
             .pipe(replace(/(src|href)=("|')(.*?)(\.\.\/)+bower_components\//mg, '$1=$2$3../../'))
             .pipe(gulp.dest(checkoutPath));
});

gulp.task('stage:bower', ['bower:stage-components', 'bower:stage-imports',
  'bower:stage-bower.json', 'bower:stage-bundled.md', 'bower:stage-vaadin-components.html']);

gulp.task('bower:create-commit', ['stage:bower'], function(){
  if(args.release) {
    var message = 'Release version ' + version + '.';

    return gulp.src('./*', {cwd: checkoutPath, base: checkoutPath})
      .pipe(git.add({cwd: checkoutPath, args: '-A'}))
      .pipe(git.commit(message, {cwd: checkoutPath, args: '-q'}));
  } else {
    var message = 'Release version ' + snapshotVersion + '.';

    return gulp.src('./*', {cwd: checkoutPath, base: checkoutPath})
      .pipe(git.add({cwd: checkoutPath, args: '-A'}))
      .pipe(git.commit(message, {cwd: checkoutPath, args: '-q --amend'}));
  }
});

gulp.task('deploy:bower', ['bower:create-commit'], function() {
  if(args.release) {
    git.tag(version, 'Release version ' + version, {cwd: checkoutPath}, function (err) {
      if (err) throw err;
      return git.push('origin', version, {cwd: checkoutPath, args: '--tags'});
    });
  } else {
    return git.push('origin', 'HEAD:'+snapshotVersion, {cwd: checkoutPath, args: '--force'});
  }
});

gulp.task('bower-test:clean', function() {
  fs.removeSync(checkoutPath + '/test');
});

gulp.task('bower-test:install-vaadin-components', ['bower-test:clean'], function() {
  return bower({
    cwd: checkoutPath + '/test',
    cmd: 'install'
  }, [['vaadin-components#' + version]]);
});

gulp.task('bower-test:install-wct', ['bower-test:clean'], function() {
  return bower({
      cwd: checkoutPath + '/test',
      cmd: 'install'
    }, [['web-component-tester#2.2.6']]);
});

gulp.task('bower-test:stage-imports', ['bower-test:install-vaadin-components'], function() {
  return gulp.src(['vaadin-components/**/test/**/*'])
    .pipe(replace(/(src|href)=("|')(.*?)\.\.\/\.\.\/\.\.\/\.\.\/(bower_components|node_modules)\//mg, '$1=$2../../../$3'))
    .pipe(replace(/(src|href)=("|')(.*?)\.\.\/\.\.\/\.\.\/(bower_components|node_modules)\//mg, '$1=$2../../../$3'))
    .pipe(gulp.dest(checkoutPath + '/test/bower_components/vaadin-components/'));
});

gulp.task('bower-test:stage', ['bower-test:install-vaadin-components', 'bower-test:stage-imports', 'bower-test:install-wct']);

gulp.task('verify:bower', ['bower-test:stage'], function(done) {
  common.testSauce(
      [checkoutPath + '/test/bower_components/vaadin-components/**/test/index.html'],
    ['Windows 7/chrome@41'],
    'vaadin-components / bower / ' + version,
    function(err) {
      common.autoRevert(err, function() {
        gutil.log('Reverting branch/tag ' + version);

        // implicit requirement: repository must be cloned in checkoutPath
        git.push('origin', ':' + version, {cwd: checkoutPath}, function() {
          done(err);
        });
      }, done);
    });
});
