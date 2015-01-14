This is the Hero demo project

It uses polymer + vaadin

- You can run the demo with any web server without compiling it.
But you need to run a web server on the webapp folder:
$ http-server src/main/webapp/
or
$ java -jar jetty-runner-8.1.12.v20130726.jar src/main/webapp/

- Java Code is here just to create the heroes mock data, you don't
need to compile it unless you need to debug internally vaadin
components, but before you should modify the vaadin-components
import:
 edit src/main/webapp/elements/hero-list/hero-list.html
 change this line
  <link rel="import" href="../../bower_components/vaadin-components/vaadin-components.html">
 by
  <link rel="import" href="../../vaadin-x.html">
 run
  $ mvn gwt:run
 and point your browser to
  http://127.0.0.1:8888/index.html
