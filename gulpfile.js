"use strict";
var _ = require('lodash');
var gulp = require('gulp');
require('require-dir')('./tasks');
var common = require('./tasks/common');

var gutil = require('gulp-util');
var chalk = require('chalk');
var cmd = require('child_process');
var fs = require('fs-extra');
var rename = require('gulp-rename');
var json = require('gulp-json-editor');
var replace = require('gulp-replace');
require('web-component-tester').gulp.init(gulp);

var args = require('yargs').argv;

var pwd = process.cwd();
var gwtproject = 'vaadin-components-gwt';
var target = pwd + '/target';
var version = '0.2.0';
var major = version.replace(/(\d+\.\d+\.).*$/, '$1');
var patch = ~~((new Date().getTime() / 1000 - 1420070400) / 60)
var tag =  args.version || major + patch; // will rename the tag variable in a separate patch

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
}

function compileGwt(cb, pretty) {
  gutil.log('Updating Maven dependencies ...');
  system('mvn compile -q -am -pl ' + gwtproject, function(){
    gutil.log('Compiling GWT components ...');
    var command = 'mvn package -q -am -pl ' + gwtproject + (pretty ? ' -Ppretty' : '');
    system(command, function(){
      gutil.log('GWT components compilation succeeded.')
      if (cb) cb();
    });
  });
}

function copyGwtModule(component, moduleName, version, cb) {
  var warDir = 'vaadin-components-gwt/target/vaadin-components-gwt-' + version + '/';
  var modulePath = warDir + moduleName + '/';
  var webDir = 'vaadin-components-gwt/src/main/webapp/';
  var webComponentDir = webDir + component + '/';
  var componentDir = 'vaadin-components/' + component;

  process.chdir(pwd);
  fs.mkdirsSync(componentDir);

  gulp.src(webComponentDir + '**/*')
  .pipe(replace(new RegExp('^.*script.*' + moduleName + '.*$','mg'), ''))
  .pipe(replace(/(src|href)=("|')([\.\/]*)\.\.\/bower_components\//mg, '$1=$2$3../../bower_components/'))
  .pipe(gulp.dest(componentDir));

  gulp.src(modulePath + moduleName +  '-import.html')
  .pipe(rename(function (path) {
    path.basename = component + "-import";
  }))
  .pipe(gulp.dest(componentDir));

  gulp.src(componentDir + 'deferred')
  .pipe(gulp.dest(componentDir));

  if (cb) cb();
}

gulp.task('default', function() {
  console.log('\n  Use:\n    gulp <clean|gwt[:pretty]|css|sassdoc|test[:validation:sauce]|stage|deploy[:bower:cdn:zip]|all>\n');
});

gulp.task('clean', function(cb) {
  fs.removeSync(target);
  fs.mkdirsSync(target);
  system('mvn clean', cb);
});

gulp.task('gwt:pretty', function(cb) {
  compileGwt(function() {
    copyGwtModule('vaadin-grid', 'VaadinGrid', version);
    cb();
  }, true);
});

gulp.task('gwt', function(cb) {
  compileGwt(function() {
    copyGwtModule('vaadin-grid', 'VaadinGrid', version);
    cb();
  });
});

gulp.task('deploy', ['deploy:bower', 'deploy:cdn', 'deploy:zip']);

// can't run all the verification concurrently until sauce-connect-launcher supports
// multiple tunnels
//gulp.task('verify', ['verify:bower', 'verify:cdn', 'verify:zip]);

gulp.task('test', ['test:local']);

gulp.task('test:validation', function(done) {
  common.test(
    {
      browserOptions: {
        name: common.localAddress() + ' / ' + new Date(),
        build: 'vaadin-components / validation'
      },
      plugins: {
        sauce: {
          username: args.sauceUsername,
          accessKey: args.sauceAccessKey,
          browsers: [
            'Windows 7/chrome@41',
            'Windows 7/firefox@36',
            'Windows 7/internet explorer@11'
          ]
        },
        'teamcity-reporter': args.teamcity
      },
      root: '.',
      webserver: {
        port: 2000,
        hostname: common.localAddress()
      }
    }, done);
});

gulp.task('test:vaadin', function(done) {
  common.test(
    {
      browserOptions: {
        url: 'http://validation-hub.devnet.vaadin.com:4444/wd/hub'
      },
      activeBrowsers: [
        {
          browserName: "chrome",
          version: "40",
          platform: "VISTA"
        }
      ],
      root: '.',
      webserver: {
        port: 2000,
        hostname: common.localAddress()
      }
    }, done);
});

gulp.task('test:sauce', function(done) {
  common.testSauce(
    [],
    ['Windows 7/chrome@41',
      'Windows 7/firefox@36',
      'Windows 7/internet explorer@11',
      'OS X 10.10/safari@8.0',
      'OS X 10.10/iphone@8.1',
      'Linux/android@5.0'],
    'vaadin-components / ' + version,
     done)
});

gulp.task('all', ['clean'], function() {
  // Compile themes
  gulp.start('css');

  // Compile Gwt
  compileGwt(function() {
    copyGwtModule('vaadin-grid', 'VaadinGrid', version, function() {
      // Deploy everything
      gulp.start('deploy')
    });
  });
});
