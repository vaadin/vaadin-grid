# Vaadin Components

A set of high-quality Web Components with a customizable Sass based theme.

The contained modules are:

- **vaadin-components**:
  Polymer-based Web Components.

- **vaadin-theme**:
  Sass-based customizable theme, called [Valo](https://vaadin.com/valo),
  for all Vaadin Components.

- **vaadin-components-gwt**:
  The internal GWT implementations for some of the components,
  exported to JavaScript which is used by the Polymer-based implementations.

- **vaadin-components-export**:
  A set of utilities and scripts to create vaadin-components packages and
  deploy them to production.

The main Git repository is located at https://dev.vaadin.com:29418/components,
and is replicated in GitHub as a read-only repository.


## Building

Install [Node.js](http://nodejs.org), and optionally
[JDK8](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
and [Maven](http://maven.apache.org/download.cgi), and be sure that they are
available in your PATH. JDK8 and Maven are only necessary if you wish to test
or develop the GWT-based components, such as vaadin-grid.

1. Clone the project:

        $ git clone https://github.com/vaadin/components.git
        $ cd components

2. Install the dependencies. Bower and Gulp need to be installed
globally, other dependencies can be local:

        $ npm install -g bower gulp
        $ npm install

3. *(Optional)* Compile the GWT modules to JS. Only necessary if you wish
to test or develop those components:

        $ gulp gwt

  3.1. If you want to compile GWT using "pretty" output (easier to debug):

        $ gulp clean gwt-pretty

4. Compile the theme for all components:

        $ gulp css

  4.1. To compile the theme for just one component:

        $ gulp css --component=button

  4.2. Additionally you can produce a debug version of the styles (with
  source maps), and start a watch task to monitor the source files for
  any changes:

        $ gulp css --component=button --debug --watch


## Demos / examples

Start a server in either the root folder or the vaadin-components folder,
and access one of the demo.html files inside the component folders, e.g:

    $ cd vaadin-components
    $ serve

    Open http://localhost:3000/vaadin-grid/demo.html


## Tests

To run all tests:

    $ gulp test

This will compile the GWT modules, so you need to have that set up.


## License

Vaadin Components is licensed under the Apache License 2.0.
