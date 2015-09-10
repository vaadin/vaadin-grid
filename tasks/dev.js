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

gulp.task('dev', function() {
  return gulp.src(config.componentHtmlFiles, {base:"."})
    .pipe(replace(/(src|href)=("|')\.\.\/([\.\/]*)?(polymer|webcomponentsjs|\w+-[\w-]+)\//mg, '$1=$2$3bower_components/$4/'))
    .pipe(gulp.dest('./'));
});

gulp.task('prod', function() {
  return gulp.src(config.componentHtmlFiles, {base:"."})
    .pipe(replace(/(src|href)=("|')([\.\/]*)bower_components\//mg, '$1=$2$3../'))
    .pipe(gulp.dest('./'));
});
