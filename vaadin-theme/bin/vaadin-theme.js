#!/usr/bin/env node
var args = require("yargs").argv;

require("../gulpfile").compile(args._[0], args._[1], args);
