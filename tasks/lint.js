var gulp = require('gulp');
var jscs = require('gulp-jscs');
var jshint = require('gulp-jshint');
var htmlExtract = require('gulp-html-extract');

gulp.task('lint:js', function() {
  return gulp.src([
        '*.js',
        '!*.min.js',
        'test/*.js',
        'tasks/*.js'
      ])
      .pipe(jshint())
      .pipe(jshint.reporter())
      .pipe(jshint.reporter('fail'))
      .pipe(jscs())
      .pipe(jscs.reporter())
      .pipe(jscs.reporter('fail'));
});

gulp.task('lint:html', function() {
  return gulp.src([
        '*.html',
        'demo/*.html',
        '!demo/angular2.html',
        '!demo/react.html',
        'test/*.html'
      ])
      .pipe(htmlExtract({
        sel: 'script, code-example code'
      }))
      .pipe(jshint())
      .pipe(jshint.reporter())
      .pipe(jshint.reporter('fail'))
      .pipe(jscs())
      .pipe(jscs.reporter())
      .pipe(jscs.reporter('fail'));
});
