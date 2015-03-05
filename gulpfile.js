"use strict";
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

var sass = require('gulp-sass');
var sassdoc = require('sassdoc');
var sourcemaps = require('gulp-sourcemaps');
var autoprefixer = require('gulp-autoprefixer');
var minify = require('gulp-minify-css');
var args = require('yargs').argv;

var pwd = process.cwd();
var gwtproject = 'vaadin-components-gwt';
var gitrepo = 'git@vaadin-components.intra.itmill.com:/opt/git/';
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

function updatePolymerComponent(component, tag, tmpdir ) {
  var message = 'Releasing_version_' + tag;
  var gitcwd = tmpdir + '/' + component;
  var giturl = gitrepo + '/' + component + '.git';
  var componentDir = 'vaadin-components/' + component;
  fs.removeSync(gitcwd);
  git.clone(giturl, {
    args : gitcwd
  }, function(err) {
    if (err) throw err;

    process.chdir(pwd);
    fs.copySync(componentDir, gitcwd);

    gulp.src(gitcwd + '/bower.json')
    .pipe(json({version: tag, name: component}))
    .pipe(gulp.dest(gitcwd));

    gulp.src(gitcwd + '/**/*.html')
    .pipe(replace(/(src|href)=("|')(.*?)\.\.\/\.\.\/bower_components\//mg, '$1=$2$3../'))
    .pipe(gulp.dest(gitcwd));

    process.chdir(gitcwd);
    git.status({
      args : '--porcelain'
    }, function(err, stdout) {
      if (/\w/.test(stdout.replace('M bower.json','').replace(/\s/,''))) {
        // FIXME: This is executed in parallel, I think git.commit
        // implementation is buggy, so using a hack with system.
        // gulp.src('*')
        // .pipe(git.commit(message))
        // .pipe(git.tag('v' + tag, message));
        system('git add . ; git commit -q -a -m ' + message, function() {
          git.tag(tag, message, function() {
            git.push('origin', 'master', {
              args : '--tags'
            }, function() {
              gutil.log('>>>> Released a new version of ' + component + ' (' + tag + ')');
            })
          })
        })
      } else {
        gutil.log('>>>> No new changes to commit for component ' + component);
      }
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
  console.log('\n  Use:\n    gulp <clean|gwt|css|sassdoc|deploy|all>\n');
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

gulp.task('deploy', function() {
  updatePolymerComponent('vaadin-button', tag, target);
  updatePolymerComponent('vaadin-grid', tag, target);
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

gulp.task('css', function () {
  // TODO this list should be autogenerated somehow
  var components = ["button", "grid"];

  if(args.component) {
    var components = args.component.split(',');
  }

  for (var i in components) {
    var component = components[i].trim();
    gutil.log('Compiling theme for component: ' + component);
    var input = pwd + '/vaadin-theme/components/' + component + '/' + component + '.scss';
    var output = pwd + '/vaadin-components/vaadin-' + component;

    var stream = gulp.src(input)
      .pipe(rename({prefix: 'vaadin-'}))
      .pipe(sourcemaps.init())
      .pipe(sass())
      .pipe(autoprefixer({
        browsers: ['last 2 versions'],
        cascade: false
      }));

    if(!args.pretty) {
      stream = stream.pipe(minify({ keepBreaks: args.debug }))
    }
    if(args.debug) {
      stream.pipe(sourcemaps.write());
    }
    if (component == 'grid') {
      stream.pipe(gulp.dest(pwd + '/' + gwtproject +  '/src/main/webapp/vaadin-' + component));
    }

    stream.pipe(gulp.dest(output));
  }

  if(args.watch) {
    gulp.watch(pwd + '/vaadin-theme/**/*.scss', ['css']);

    // Only add the watch once
    args.watch = false;
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
