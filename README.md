Here you have a set of modules used for Vaadin Components

- vaadin-components-gwt
   Exported vaadin widgets from html and js

- vaadin-components
   Polymer ready vaadin webcomponents

- vaadin-components-export
   A set of utilities and scripts to create vaadin-components
   packages and deploy them to central repos.


How to build

- Install node, gulp, bower, git, java and maven in your system, and
  be sure that all of them are available in your PATH.

- Clone the project:
  $ ssh://<your_vaadin_user_name>@dev.vaadin.com:29418/components
  $ cd components

- Install the rest of needed dependencies:
  $ node install.

- To compile gwt modules to JS, just run:
  $ gulp clean gwt

- To deploy all webcomponents to bower repos:
  $ gulp deploy

- Alrernativally you could run everything using maven:
  $ mvn clean package
