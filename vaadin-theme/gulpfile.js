var gulp = require("gulp");
var gutil = require("gulp-util");
var rename = require("gulp-rename");
var replace = require("gulp-replace");
var insert = require('gulp-insert');
var path = require("path");
var fs = require("fs");
var sass = require("gulp-sass");
var sourcemaps = require("gulp-sourcemaps");
var autoprefixer = require("gulp-autoprefixer");
var stripComments = require("gulp-strip-css-comments");
var args = require("yargs").argv;

gulp.task("vaadin-theme", function() {
  var inputPath = args.inputPath || args._[0];
  var outputPath = args.outputPath || args._[1];

  if (!inputPath) {
    gutil.log("You need to provide an input file, e.g. 'vaadin-theme my-theme.scss'");
    return;
  }

  // Resolve input path
  var inputPath = path.resolve(process.cwd(), inputPath);

  if (!fs.existsSync(inputPath) || !fs.statSync(inputPath).isFile()) {
    gutil.log("Error: Input does not exist or is not a file");
    return;
  }

  if (path.extname(inputPath) != ".scss") {
    gutil.log("Error: Input should be a .scss file");
    return;
  }

  gutil.log("Compiling " + inputPath);

  // Output is by default placed in the same directory as the input,
  // with the same filename
  var outputFilename = path.basename(inputPath, ".scss");
  var outputDir = path.dirname(inputPath);

  // Resolve output path, if specified
  if (outputPath) {
    if (path.extname(outputPath) == "") {
      // No extension, targeting a directory
      outputDir = path.resolve(process.cwd(), outputPath);
    } else {
      outputFilename = path.basename(outputPath);
      outputDir = path.dirname(path.resolve(process.cwd(), outputPath));
    }
  }

  // Compile the custom theme
  var stream = gulp.src(inputPath)
    .pipe(rename(outputFilename))

  // Rewrite vaadin-theme import statement, so that it works from anywhere
  // in the file system
  .pipe(replace(/@import\s+(["'])vaadin-theme(\.scss|)\1;/i, "\n@import '"
        + __dirname + "/vaadin-theme.scss';"))

  // Insert component list for the build
  .pipe(insert.prepend(args.component ? "$v-included-components: "
        + args.component + ";" : ""))

  .pipe(sourcemaps.init())
    .pipe(sass({
      outputStyle: (args.pretty || args.debug)? "nested" : "compressed"
    }))
    .pipe(autoprefixer({
      browsers: ['last 2 versions'],
      cascade: false
    }));

  if (args.debug) {
    stream = stream.pipe(sourcemaps.write());
  } else {
    stream = stream.pipe(stripComments());
  }
  stream = stream.pipe(gulp.dest(outputDir));

  var absoluteOutputPath = path.join(outputDir,
    outputFilename.replace(path.extname(outputFilename), "") + ".css"
  );
  
  gutil.log("Compiled " + absoluteOutputPath);

  if (args.watch) {
    var watchedFolder = path.resolve(process.cwd(),
      path.dirname(inputPath), typeof args.watch == "string" ? args.watch : "");

    gulp.watch(watchedFolder + "/**/*.scss", ["vaadin-theme"]);
    gutil.log("Watching folder " + watchedFolder + " for .scss file changes...");

    // Only add the watch once
    args.watch = false;
  }
});


module.exports.compile = function(input, output, options) {
  args.inputPath = input;
  args.outputPath = output;
  if (options) {
    for (var prop in options) {
      args[prop] = options[prop];
    }
  }
  gulp.start("vaadin-theme");
};
