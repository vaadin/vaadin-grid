[![Build Status](https://travis-ci.org/vaadin/components.svg?branch=master)](https://travis-ci.org/vaadin/components)
# Vaadin Components

A set of high-quality Web Components built using Polymer.

Learn more about [Vaadin Components.](https://vaadin.com/labs-components/)

## Getting Started

- For getting started, see our [README.md](vaadin-components-package/README.md) for users
- For more usage examples, see [components-examples](https://vaadin.github.io/components-examples)

## Overview of the repository

The contained modules are:

- **vaadin-components**:
  Polymer-based Web Components.

- **vaadin-components-gwt**:
  The internal GWT implementations for some of the components,
  exported to JavaScript which is used by the Polymer-based implementations.

- **vaadin-components-export**:
  A set of utilities and scripts to create vaadin-components packages and
  deploy them to production.

The main Git repository is located at https://dev.vaadin.com:29418/components,
and is replicated in GitHub as a read-only repository.

## Developing

### Setting up the project for the first time

  First, make sure you've installed all the necessary tooling:
  - [Node.js](http://nodejs.org)
  - [JDK8](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
  - [Maven](http://maven.apache.org/download.cgi)

- Install bower, gulp and web-component-tester, serve globally
```shell
$ npm install -g bower gulp web-component-tester serve
```
- Clone the project:
```shell
$ git clone https://github.com/vaadin/components.git
$ cd components
```
- Install the project dependencies
```shell
$ npm install
```
### Building the project

- Compile the GWT modules to JS.
```shell
$ gulp gwt
```
### Serving the components

- Spin up a web server
```shell
$ serve
```
- Access the components through `http://localhost:3000/vaadin-components/`
- If you wish to import components using the development URL, remember to manually install [Polymer](https://github.com/Polymer/polymer) as a dependency to your application.
```shell
$ cd your-application
$ bower install polymer#0.9.0 --save
```
### Running automated tests

 - Run all tests locally
```shell
$ gulp test
```
 - Run a single browser locally
```shell
$ wct --local=chrome/firefox/safari
```
- Run and debug tests manually
```shell
$ serve
$ open http://localhost:3000/vaadin-components/vaadin-grid/test/
```
- Gather references for visual tests
```shell
$ gemini gather visual-test --screenshots-dir=visual-test
```
- Run visual tests
```shell
$ gemini test visual-test --screenshots-dir=visual-test
```
### Development Protips

- Compiling GWT using "pretty" output
```shell
$ gulp gwt --gwt-pretty
```
- Adding file watcher for GWT compilation
```shell
$ gulp watch:gwt
```
- Running and debugging in GWT SuperDevMode
```shell
$ mvn -f vaadin-components-gwt/pom.xml gwt:run
```
- Update your dependencies once in a while
```shell
$ npm install
```
## Packaging

- Creating a zip package under `target/zip/`
```shell
$ gulp stage:zip OR
$ gulp stage:zip --version=0.3-custom --release
```
- Copying bower.json and components under `target/bower/`
```shell
$ gulp stage:bower OR
$ gulp stage:bower --version=0.3-custom --release
```
## Demos / examples

Start a server in the root folder,
and access one of the demo.html files inside the component folders, e.g:
```shell
$ serve
$ open http://localhost:3000/vaadin-components/vaadin-grid/demo.html
```
## License

Vaadin Components is licensed under the Apache License 2.0.
