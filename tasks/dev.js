var common = require('./common');
var config = require('./config');
var fs = require('fs-extra');
var gulp = require('gulp');
var gutil = require('gulp-util');
var replace = require('gulp-replace');

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
