var replace = require("gulp-replace");
var insert = require('gulp-insert');
var path = require("path");
var sass = require("gulp-sass");
var sourcemaps = require("gulp-sourcemaps");
var autoprefixer = require("gulp-autoprefixer");
var stripComments = require("gulp-strip-css-comments");
var args = require("yargs").argv;
var gulpif = require('gulp-if');
var lazypipe = require('lazypipe');


module.exports = function () {
  var pipes = lazypipe()
    .pipe(replace, /@import\s+(["'])vaadin-theme(\.scss|)\1;/i, "\n@import '" + __dirname + "/vaadin-theme.scss';")
    .pipe(function () {
      return gulpif(args.debug, sourcemaps.init());
    })
    .pipe(sass, {
      outputStyle: (args.pretty || args.debug) ? "nested" : "compressed"
    })
    .pipe(autoprefixer, {
      browsers: ['last 2 versions'],
      cascade: false
    })
    .pipe(function () {
      return gulpif(args.debug, sourcemaps.write(), stripComments());
    });

  return pipes();
};
