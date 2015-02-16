Here you have a set of modules used for Vaadin Components

- vaadin-components-gwt
   Exported vaadin widgets from html and js

- vaadin-components
   Polymer ready vaadin webcomponents

- vaadin-components-export
   A set of utilities and scripts to create vaadin-components
   packages and deploy them to central repos.


How to build

- Install node, java and maven in your system, and
  be sure that all of them are available in your PATH.

- Clone the project:
  $ ssh://<your_vaadin_user_name>@dev.vaadin.com:29418/components
  $ cd components

- Install the rest of needed dependencies:
  $ npm install

- Add the folder node_modules/.bin in your PATH so as you can run
  bower, gulp, etc. from command line
  $ export PATH=$PATH:$PWD/node_modules/.bin/

- To compile gwt modules to JS, just run:
  $ gulp gwt

  If you wanted to produce GWT pretty code run:
  $ gulp clean gwt-pretty

- To compile each component theme, run:
  $ gulp css --component=button,grid

  Additionaly you can produce a debug version of the styles (with source maps),
  and start a watch task to monitor the source files for any changes
  $ gulp css --component=button --debug --watch

- To deploy all webcomponents to bower repos:
  $ gulp deploy

- Alrernativally you could run everything using maven:
  $ mvn clean package
