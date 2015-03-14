"use strict";
var _ = require('lodash');
var gulp = require('gulp');
require('require-dir')('./tasks');

var gutil = require('gulp-util');
var chalk = require('chalk');
var cmd = require('child_process');
var git = require('gulp-git');
var fs = require('fs-extra');
var rename = require('gulp-rename');
var json = require('gulp-json-editor');
var replace = require('gulp-replace');
require('web-component-tester').gulp.init(gulp);
var test = require('./node_modules/web-component-tester/runner/test.js');
var rsync = require('gulp-rsync');


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
var tag =  args.version || major + patch; // will rename the tag variable in a separate patch
var target_cdn = target + '/cdn/' + tag;
var target_bower = target + '/bower';
var target_zip = target + '/zip';

var _components = ['vaadin-button', 'vaadin-grid'];

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

  fs.removeSync(target_bower);

  var cloneArgs = (args.checkout ? '-b ' + args.checkout + ' ' : '') + target_bower;

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
    fs.copySync('vaadin-components/' + component, target_bower + '/' + component);
  });
});

gulp.task('stage:bower.json', ['stage:clone'], function() {
  return gulp.src(pwd + '/vaadin-components-package/bower.json')
             .pipe(json({version: tag}))
             .pipe(gulp.dest(target_bower));
});

gulp.task('stage:replace', ['stage:components'], function() {
  return gulp.src(target_bower + '/**/*.html')
             .pipe(replace(/(src|href)=("|')(.*?)\.\.\/\.\.\/bower_components\//mg, '$1=$2$3../../'))
             .pipe(gulp.dest(target_bower));
});

gulp.task('stage', ['stage:components', 'stage:replace', 'stage:bower.json'], function() {

});

gulp.task('deploy', ['deploy:bower', 'deploy:cdn']);

gulp.task('verify', ['verify:bower', 'verify:cdn']);

// zipping doesn't really depend on stage:clone because we're not going to push,
// but it makes sense to reuse the existing stage tasks for copying the needed files.
gulp.task('stage:zip', ['stage'], function() {
    fs.removeSync(target_zip);

    return gulp.src(target_bower + '/**/*')
      .pipe(zip('vaadin-components-' + tag + '.zip'))
      .pipe(gulp.dest(target_zip));
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
        'teamcity-reporter': args.teamcity
      },
      root: '.',
      webserver: {
        port: 2000,
        hostname: localAddress()
      }
    }, cleanDone(done));
});

gulp.task('test:sauce', function(done) {
  test(
    {
      browserOptions: {
        name: localAddress() + ' / ' + new Date(),
        build: 'vaadin-components'
      },

      plugins: {
        local: false,
        sauce: {
          username: args.sauceUsername,
          accessKey: args.sauceAccessKey,
          browsers: ['Windows 7/chrome@41',
                     'Windows 7/firefox@36',
                     'Windows 7/internet explorer@11',
                     'OS X 10.10/safari@8.0', //slow startup
                     'OS X 10.10/iphone@8.1', //slow as hell startup
                     'Linux/android@5.0']
        },
        'teamcity-reporter': args.teamcity
      },
      webserver: {
        //Sauce OSX doesn't work with 'localhost', need real IP
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
