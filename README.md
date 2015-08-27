[![Build Status](https://travis-ci.org/vaadin/components.svg?branch=master)](https://travis-ci.org/vaadin/components)
[![Gitter](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/vaadin/components?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge)

# Vaadin Components

##### *Notice*: This is the development repository for Vaadin Components. If you just want to use the components, see the [Getting Started instructions](http://vaadin.github.io/components-examples/).

Vaadin Components is an evolving set of custom HTML elements, built using [Polymer](https://www.polymer-project.org), for building mobile and desktop web applications in modern browsers. [Learn more &raquo;](https://vaadin.com/components/)

## Using the components

##### - [Getting Started instructions](http://vaadin.github.io/components-examples/)
##### - [Discussion](https://vaadin.com/forum/#!/category/9848927/)

## Overview of this repository

- **vaadin-components**:
  Polymer-based Web Components.

- **vaadin-components-gwt**:
  The internal GWT implementations for some of the components,
  exported to JavaScript which is used by the Polymer-based implementations.

- **tasks**:
  Gulp tasks for building and packaging.

- **visual-test**:
  Visual regression testing related files, like test scripts and reference screenshots.

## Developing

### Setting up the project for the first time

First, make sure you've installed all the necessary tooling:
- [Node.js](http://nodejs.org).
- [JDK8](http://www.oracle.com/technetwork/java/javase/downloads/index.html).
- [Maven](http://maven.apache.org/download.cgi).

> If you encounter permission issues when running `npm` see [thread in StackOverFlow.com](http://stackoverflow.com/questions/16151018/npm-throws-error-without-sudo)

Then do the following:

1. Install [bower](https://www.npmjs.com/package/bower), [gulp](https://www.npmjs.com/package/gulp), [web-component-tester](https://www.npmjs.com/package/web-component-tester) and [serve](https://www.npmjs.com/package/serve) globally:
 ```shell
 $ npm install -g bower gulp web-component-tester serve
 ```

2. Clone the project:
 ```shell
 $ git clone https://github.com/vaadin/components.git
 $ cd components
 ```

3. Install the project dependencies:
 ```shell
 $ npm install
 ```

### Building the project

- Compile the GWT modules to JS.
```shell
$ gulp gwt
```

### Serving the components

- Spin up a web server:
```shell
$ serve
```
- Access the components through `http://localhost:3000/vaadin-components/`
> If you wish to import components into your application using the development URL, remember to manually install [Polymer](https://github.com/Polymer/polymer) as a dependency.
```shell
$ cd your-application
$ bower install polymer --save
```

### Running automated tests

- Run all tests locally:
```shell
$ gulp test
```
- Run a single browser locally:
```shell
$ wct --local=chrome/firefox/safari
```
- Run and debug tests manually:
```shell
$ serve
```
  - Open http://localhost:3000/vaadin-components/vaadin-grid/test/
- Gather references for visual tests:
```shell
$ gemini gather visual-test --screenshots-dir=visual-test
```
- Run visual tests:
```shell
$ gemini test visual-test --screenshots-dir=visual-test
```
> You need to install `gemini`globally using `npm install -g gemini`. We use [SauceLabs](http://www.saucelabs.com) for visual testing, so you also need to have `SAUCE_USERNAME` and `SAUCE_ACCESS_KEY` set as environmental parameters.

### Development Protips

- Compiling GWT using "pretty" output:
```shell
$ gulp gwt --gwt-pretty
```
- Adding file watcher for GWT compilation:
```shell
$ gulp watch:gwt
```
- Running and debugging in GWT SuperDevMode:
```shell
$ mvn -f vaadin-components-gwt/pom.xml gwt:run
```
- Update your dependencies once in a while:
```shell
$ npm install
```

## Packaging

- Creating a zip package under `target/zip/`:
```shell
$ gulp stage:zip
```
or
```shell
$ gulp stage:zip --version=0.3.0-custom --release
```
- Copying bower.json and components under `target/bower/`:
```shell
$ gulp stage:bower
```
or
```shell
$ gulp stage:bower --version=0.3.0-custom --release
```

## Demos / examples

Start a server in the root folder,
and access one of the demo.html files inside the component folders, e.g:
```shell
$ serve
```
- Open http://localhost:3000/vaadin-components/vaadin-grid/demo.html


## License

Vaadin Components is licensed under the Apache License 2.0.
