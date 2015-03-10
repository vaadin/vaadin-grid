"use strict";
var _ = require('lodash');
var gulp = require('gulp');
var gutil = require('gulp-util');
var chalk = require('chalk');
var cmd = require('child_process');
var git = require('gulp-git');
var fs = require('fs-extra');
var rename = require('gulp-rename');
var json = require('gulp-json-editor');
var replace = require('gulp-replace');
require('web-component-tester').gulp.init(gulp);

var vaadintheme = require("./vaadin-theme/gulpfile");
var sassdoc = require('sassdoc');
var args = require('yargs').argv;
var zip = require('gulp-zip');

var pwd = process.cwd();
var gwtproject = 'vaadin-components-gwt';
var gitrepo = 'git@github.com:vaadin-bower/';
var target = pwd + '/target';
var version = '0.2.0';
var major = version.replace(/(\d+\.\d+\.).*$/, '$1');
var patch = ~~((new Date().getTime() / 1000 - 1420070400) / 60)
var tag =  major + patch;

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
  console.log('\n  Use:\n    gulp <clean|gwt|css|sassdoc|test|stage|deploy|all>\n');
});

gulp.task('clean', function(cb) {
  fs.removeSync(target);
  fs.mkdirsSync(target);
  system('mvn clean', cb);
});

gulp.task('gwt-pretty', function(cb) {
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

gulp.task('stage:clone', function(done) {
  var giturl = gitrepo + 'vaadin-components.git';

  fs.removeSync(target);

  var cloneArgs = (args.checkout ? '-b ' + args.checkout + ' ' : '') + target;

  git.clone(giturl, {
    args : cloneArgs
  }, function(err) {
    if (err) throw err;
    done();
  });
});

gulp.task('stage:components', ['stage:clone'], function() {
  var components = ['vaadin-button', 'vaadin-grid'];

  process.chdir(pwd);

  _.forEach(components, function(component) {
    fs.copySync('vaadin-components/' + component, target + '/' + component);
  });
});

gulp.task('stage:bower.json', ['stage:clone'], function() {
  return gulp.src(pwd + '/vaadin-components-package/bower.json')
             .pipe(json({version: tag}))
             .pipe(gulp.dest(target));
});

gulp.task('stage:replace', ['stage:components'], function() {
  return gulp.src(target + '/**/*.html')
             .pipe(replace(/(src|href)=("|')(.*?)\.\.\/\.\.\/bower_components\//mg, '$1=$2$3../../'))
             .pipe(gulp.dest(target));
});

gulp.task('stage', ['stage:components', 'stage:replace', 'stage:bower.json'], function() {

});

gulp.task('deploy', ['stage'], function() {
  var message = 'Release version ' + tag + '.';

  process.chdir(target);
  git.status({
    args : '--porcelain'
  }, function(err, stdout) {
    if (/\w/.test(stdout.replace('M bower.json','').replace(/\s/,''))) {
      system('git add . ; git commit -q -a -m "' + message + '"', function() {
        git.tag(tag, message, function() {
          git.push('origin', 'master', {
            args : '--tags'
          }, function() {
            gutil.log('>>>> Released a new version of components (' + tag + ').');
          })
        })
      })
    } else {
      gutil.log('>>>> No new changes to commit for components.');
    }
  });
});

gulp.task('deploy:snapshot', ['stage'], function() {
  var message = 'Release snapshot ' + tag + '.';

  process.chdir(target);
    git.status({
      args : '--porcelain'
    }, function(err, stdout) {
      system('git add . ; git commit -q -a -m "' + message + '" --amend', function() {
        git.push('origin', 'HEAD:0.2-snapshot', {
          args: '--force'
        }, function() {
          gutil.log('>>>> Released a new version of components (' + tag + ').');
        })
      })
    });
});

gulp.task('stage:zip', ['stage'], function() {
    return gulp.src(target + '/**/*')
      .pipe(zip('vaadin-components-' + tag + '.zip'))
      .pipe(gulp.dest(target));
});

gulp.task('test', ['test:local']);

function cleanDone(done) {
  return function(error) {
    if (error) {
      // Pretty error for gulp.
      error = new Error(chalk.red(error.message || error));
      error.showStack = false;
    }
    done(error);
  };
}

function localAddress() {
  var ip, tun, ifaces = require('os').networkInterfaces();
  Object.keys(ifaces).forEach(function (ifname) {
    ifaces[ifname].forEach(function (iface) {
      if ('IPv4' == iface.family && !iface.internal) {
        if (!ip) ip = iface.address;
        if (/tun/.test(ifname)) tun = iface.address;
      }
    });
  });
  return tun || ip;
}

gulp.task('test:validation', function(done) {

  //TODO: PR which enabled wct gulp script to accept options so we don't need
  //      to call test() anymore.
  // https://github.com/Polymer/web-component-tester/blob/master/runner/gulp.js
  var test = require('./node_modules/web-component-tester/runner/test.js');

  test(
    {
      browserOptions: {
        url: 'http://validation-hub.devnet.vaadin.com:4444/wd/hub',
        platform: 'VISTA'
      },
      activeBrowsers: [
        {
          browserName: "chrome",
          version: "40"
        }
        //{
        //  browserName: "firefox",
        //  version: '35'
        //},
        //{
        //  browserName: 'internet explorer',
        //  version: '11'
        //}
      ],
      plugins: {
        local: false,
        sauce: false,
        'teamcity-reporter':true
      },
      root: '.',
      webserver: {
        port: 2000,
        hostname: localAddress()
      }
    }, cleanDone(done));
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

gulp.task('css', function() {
  if (args.component) {
    // Get component list from command line arguments
    var components = args.component.split(',');
  } else {
    // Read component list from vaadin-theme.scss
    var components = [];
    var vaadinThemeStr = fs.readFileSync(pwd + '/vaadin-theme/vaadin-theme.scss').toString();

    var regexp = /@import\s+["']components\/(\w+)\//gi;
    var matches;
    while ((matches = regexp.exec(vaadinThemeStr)) !== null) {
      components.push(matches[1]);
    }
  }

  // Compile each component individually to their corresponding folder
  for (var i=0; i < components.length; i++) {
    var component = components[i].trim();
    var input = pwd + '/vaadin-theme/vaadin-theme.scss';
    var output = pwd + '/vaadin-components/vaadin-' + component + '/vaadin-' + component + ".css";
    args.component = component;

    gutil.log("Compiling CSS for " + component);
    vaadintheme.compile(input, output, args);

    // Grid theme is also compiled to the GWT package folder
    if(component == 'grid') {
      vaadintheme.compile(input, pwd + '/' + gwtproject +  '/src/main/webapp/vaadin-grid.css', args);
    }
  }
});

gulp.task('sassdoc', function () {
  var options = {
    dest: pwd + '/vaadin-theme/docs',
    verbose: true,
    display: {
      access: ['public', 'private'],
      alias: true,
      watermark: true,
    },
    basePath: 'https://github.com/vaadin/components/tree/master/vaadin-theme',
    package: pwd + '/vaadin-theme/package.json'
  };

  return gulp.src([pwd + '/vaadin-theme/*.scss', pwd + '/vaadin-theme/**/*.scss'])
    .pipe(sassdoc(options));
});
