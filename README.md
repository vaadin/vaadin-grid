Here you have a set of project used for prototyping Web Components in Vaadin.

- wc-client: 
   classes to import external web components and use them in gwt
   classes to promote wc elements to widgets
   exported vaadin widgets
- wc-server:
   classes to use wc-client classes from server side
- wc-polymer-sample:
   An example of how to use paper and polymer WC
   depends on wc-client wc-server
   wraps of all paper and polymer wc to use in gwt and sever
   scripts to generate all the classes used in the example
- wc-grid-sample
   An example to use exported vaadin widgets from html and js
   depends on wc-client
- wc-server-element
   Implementation of the Vaadin Element API

- Assuming you have installed maven, compile and package it just running:
$ mvn clean package

- To Run examples it in development mode (superdev):
$ mvn -am -pl wc-server,wc-client install
$ (cd wc-polymer-sample && mvn gwt:run)
$ (cd wc-grid-sample && mvn gwt:run)

- To Run wc-server-element
$ cd wc-server-element
$ mvn -am -pl element-api install
$ (cd demo && mvn jetty:run)
