#!/usr/bin/env node
var args = require("yargs").argv;
var gulp = require("gulp");
var gutil = require("gulp-util");
var rename = require("gulp-rename");
var path = require("path");
var vaadintheme = require("../index");

gulp.task("vaadin-theme", function () {
  // Output is by default placed in the same directory as the input,
  // with the same filename
  var outputDir = path.dirname(args.outputPath || args.inputPath);
  var outputFile = args.outputPath ? path.basename(args.outputPath, '.css') : path.basename(args.inputPath, '.scss');

  gutil.log("Compiling " + outputDir + "/" + outputFile);

  return gulp.src(args.inputPath)
    .pipe(vaadintheme())
    .pipe(rename(function (path) {
      path.basename = outputFile;
    }))
    .pipe(gulp.dest(outputDir));
});

gulp.task('watch', ['vaadin-theme'], function () {
  var glob = typeof args.watch == "string" ? args.watch : path.dirname(args.inputPath);
  gutil.log("Watching folder " + glob + " for .scss file changes...");

  return gulp.watch(glob, ['vaadin-theme']);
});

function compile(input, output, options) {
  args.inputPath = input;
  args.outputPath = output;
  if (options) {
    for (var prop in options) {
      args[prop] = options[prop];
    }
  }

  if (path.extname(args.inputPath) != ".scss") {
    gutil.log("Error: Input should be a .scss file");
    return;
  }

  if (args.watch) {
    gulp.start("watch");
  } else {
    gulp.start("vaadin-theme");
  }
};

compile(args._[0], args._[1], args);
