'use strict';

const gulp = require('gulp');
const eslint = require('gulp-eslint');
const htmlExtract = require('gulp-html-extract');
const stylelint = require('gulp-stylelint');
const vulcanize = require('gulp-vulcanize');
const crisper = require('gulp-crisper');
const closure = require('google-closure-compiler-js').gulp();
const fs = require('fs');
const filter = require('gulp-filter');

gulp.task('lint', ['lint:js', 'lint:html', 'lint:css']);

gulp.task('lint:js', function() {
  return gulp.src([
    '*.js',
    'test/**/*.js'
  ])
  .pipe(eslint())
  .pipe(eslint.format())
  .pipe(eslint.failAfterError('fail'));
});

gulp.task('lint:html', function() {
  return gulp.src([
    '*.html',
    'demo/**/*.html',
    'test/**/*.html',
    '!demo/x-data-provider.html'
  ])
  .pipe(htmlExtract({
    sel: 'script, code-example code'
  }))
  .pipe(eslint())
  .pipe(eslint.format())
  .pipe(eslint.failAfterError('fail'));
});

gulp.task('lint:css', function() {
  return gulp.src([
    '*.html',
    'demo/**/*.html',
    'test/**/*.html'
  ])
  .pipe(htmlExtract({
    sel: 'style'
  }))
  .pipe(stylelint({
    reporters: [
      {formatter: 'string', console: true}
    ]
  }));
});

gulp.task('compile', function() {
  const excludes = fs.readdirSync('bower_components').map((dependency) => {
    return '../' + dependency + '/';
  });
  const externsPath =
      'node_modules/google-closure-compiler-js/contrib/externs/polymer-1.0.js';
  const externsSrc = fs.readFileSync(externsPath, 'utf-8');
  return gulp.src('*.html')
    .pipe(vulcanize({
      excludes: excludes
    }))
    .pipe(crisper({onlySplit: true}))
    .pipe(filter('*.js'))
    .pipe(closure({
      polymerPass: true,
      externs: [
        {'src': externsSrc, 'path': 'polymer-1.0.js'}
      ],
      compilationLevel: 'SIMPLE',
      warningLevel: 'VERBOSE',
      jsOutputFile: 'elements.min.js',
      createSourceMap: true,
    }));
});
