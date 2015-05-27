# Vaadin Components

Vaadin Components is an evolving set of custom HTML elements, built using [Polymer](https://www.polymer-project.org), for building mobile and desktop web applications in modern browsers.

For contributions and issues, see the project&lsquo;s [Github repository](https://github.com/vaadin/components).

### Examples &amp; API Docs

View live examples and source code side-by-side for individual custom elements.

- [&lt;**v-grid**&gt;](http://vaadin.github.io/components-examples/v-grid/) &ndash; Data grid for showing large amounts of tabular data ([API](http://vaadin.github.io/components-apidoc/#v-grid))


### Quickstart

 Get a quick test-drive of the custom elements by forking one of the following JSFiddles:

- &lt;**v-grid**&gt;
 - [Data generated on-the-fly](http://jsfiddle.net/jounik/tvk1235r/)
 - [JSON data from a URL](http://jsfiddle.net/jounik/tLour4gv/)


### Installation

We offer three ways to use Vaadin Components in your project: Bower, CDN and ZIP archive. The only difference between the options is the URL you use to import the necessary files into your HTML page.

#### 1. Create a new folder for your project

 ```shell
 $ mkdir my-project
 $ cd my-project
 ```

#### 2. Install Vaadin Components

- ##### Bower

 We recommend using [Bower](http://bower.io) for managing your front-end dependencies. Follow the [Bower installation instructions](http://bower.io/#install-bower), the run the following command inside your project folder:

 ```shell
 $ bower install --save vaadin-components#0.3.0-beta1
 ```

 This will download Vaadin Components and its dependencies to the `bower_components` folder inside your project&lsquo;s folder.

- ##### CDN

 You can use Vaadin Components from CDN (see example below). This is especially convenient for services like JSFiddle, Codepen.io, etc.


- ##### Download ZIP

 1. Download the latest ZIP archive from [vaadin.com/download](https://vaadin.com/download#components)
 2. Extract the archive under your project folder, for example `deps`

#### 3. Create a HTML file
 
 Create a new HTML file inside your project folder and copy the following code into it (choose one of the options how to import Vaadin Components in the `<head>` section):
 
 > **Note on serving the files during development**, when using Bower or the ZIP archive:
 
 > Due to browser security restrictions, serving HTML imports from a `file:///` URL does not work. You need a web server to view pages where you use custom elements. One simple option is to use the [`serve`](https://www.npmjs.com/package/serve) NPM package.
 
  ```html
<!doctype html>
<html>
  <head>
    <!-- Import Web Component polyfills and the components that you want -->
    
    <!-- CDN -->
    <script src="https://cdn.vaadin.com/vaadin-components/0.3.0-beta1/webcomponentsjs/webcomponents-lite.js"></script>
    <link href="https://cdn.vaadin.com/vaadin-components/0.3.0-beta1/vaadin-grid/vaadin-grid.html" rel="import">
    
    <!-- Bower -->
    <!-- <script src="bower_components/webcomponentsjs/webcomponents-lite.js"></script>
    <link href="bower_components/vaadin-components/vaadin-grid/vaadin-grid.html" rel="import"> -->
    
    <!-- ZIP archive -->
    <!-- <script src="deps/vaadin-components/webcomponentsjs/webcomponents-lite.js"></script>
    <link href="deps/vaadin-components/vaadin-grid/vaadin-grid.html" rel="import"> -->
  </head>
  <body>
  
    <v-grid selection-mode="multi">
      <table>
        <!-- Define the columns -->
        <col name="index" header-text="#" width="48">
        <col name="user.picture.thumbnail" width="54">
        <col name="user.name.first" header-text="First Name">
        <col name="user.name.last" header-text="Last Name">
        <col name="user.email" header-text="Email" flex>
      </table>
    </v-grid>

    <script>
      // The Web Components polyfill introduces a custom event we can 
      // use to determine when the custom elements are ready to be used
      document.addEventListener("WebComponentsReady", function () {
        
        // Reference to the grid element
        var grid = document.querySelector("v-grid");
        
        // Fetch some JSON data from a URL
        var xhr = new XMLHttpRequest();
        xhr.onreadystatechange = function() {
          if (xhr.readyState == XMLHttpRequest.DONE ) {
            if(xhr.status == 200){
              var json = JSON.parse(xhr.responseText);
              
              // Use the returned data array directly as the data source
              // (keeping all the data source items in the browser's memory)
              grid.data.source = json.results;
            }
          }
        }
        xhr.open("GET", "http://api.randomuser.me/?results=100", true);
        xhr.send();
        
        // Add a renderer for the index column
        grid.columns[0].renderer = function(cell) {
            cell.element.innerHTML = cell.row.index;
        }
        
        // Add a renderer for the picture column
        grid.columns[1].renderer = function(cell) {
            cell.element.innerHTML = '<img src="'+cell.data+'" style="width:24px;">';
        }
      });
    </script>
  
  </body>
</html>
  ```
