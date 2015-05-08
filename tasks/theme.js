var gulp = require('gulp');
var gutil = require('gulp-util');
var insert = require('gulp-insert');
var vaadintheme = require("../vaadin-theme");
var sassdoc = require('sassdoc');
var rename = require('gulp-rename');

var pwd = process.cwd();
var gwtproject = 'vaadin-components-gwt';



/**
 * Use cases:
 *    `gulp css` - compiles sass and creates separated css files for each component in their directories
 *    `gulp css:grid` - compiles sass and creates the "vaadin-components/vaadin-grid/vaadin-grid.css"
 *    `gulp css:grid-gwt` - compiles sass and creates the "vaadin-components-gwt/src/main/webapp/vaadin-grid/vaadin-grid.css"
 */

var components = [
  {"name": "grid", "dir": "vaadin-components/", "filename": "vaadin-grid"},
  {"name": "grid", "dir": gwtproject + "/src/main/webapp/", "filename": "vaadin-grid", "taskname": "grid-gwt"}
];
components.forEach(function(component) {
  // create task to compile scss for separate component
  gulp.task('css:' + (component.taskname || component.name), function() {
    return gulp.src('vaadin-theme/vaadin-theme.scss')
      .pipe(insert.prepend("$v-included-components: " + component.name + ";" ))
      .pipe(vaadintheme())
      .pipe(rename(function (path) {
        path.basename = component.filename;
      }))
      .pipe(gulp.dest(component.dir + component.filename + '/'));
  });
});

var cssTasks = [];
components.forEach(function(component) {
  cssTasks.push('css:' + (component.taskname || component.name));
});
gulp.task('css', cssTasks);



gulp.task('sassdoc', function () {
  var options = {
    dest: pwd + '/vaadin-theme/docs',
    verbose: true,
    display: {
      access: ['public', 'private'],
      alias: true,
      watermark: true
    },
    basePath: 'https://github.com/vaadin/components/tree/master/vaadin-theme',
    package: pwd + '/vaadin-theme/package.json'
  };

  return gulp.src([pwd + '/vaadin-theme/*.scss', pwd + '/vaadin-theme/**/*.scss'])
    .pipe(sassdoc(options));
});
