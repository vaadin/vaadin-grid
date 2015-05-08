# Vaadin Components

A set of high-quality Web Components built using Polymer.

Learn more about [Vaadin Components.](https://vaadin.com/labs-components/)

For contributions and issues, see our project [Github Repository.](https://github.com/vaadin/components/)

## Installation
- Bower
```shell
$ bower install vaadin-components#0.2.0
```
```html
<!-- Import web component polyfills and the component you want into your HTML -->
<head>
  ...
  <script src="bower_components/webcomponentsjs/webcomponents-lite.js"></script>
  <link href="bower_components/vaadin-components/vaadin-grid/vaadin-grid.html" rel="import">
  ...
</head>
```
- CDN
```html
<!-- Import web component polyfills and the component you want into your HTML -->
<head>
  ...
  <script src="https://cdn.vaadin.com/vaadin-components/0.2.0/webcomponentsjs/webcomponents-lite.js"></script>
  <link href="https://cdn.vaadin.com/vaadin-components/0.2.0/vaadin-grid.html" rel="import">
  ...
</head>
```
- Downloading the zip archive
    - Head over to https://vaadin.com/download#components
    - Download the version you wish to use
    - Extract the zip under your project folder
```html
<!-- Import web component polyfills and the component you want into your HTML -->
<head>
  ...
  <script src="deps/vaadin-components/webcomponentsjs/webcomponents-lite.js"></script>
  <link href="deps/vaadin-components/vaadin-grid.html" rel="import">
  ...
</head>
```
## Usage
```html
<!-- After adding the required imports, just add a <v-grid> into your HTML -->
<v-grid>
  <table>
   <colgroup>
     <col header-text="Name">
     <col header-text="Value">
     <col header-text="Progress">
   </colgroup>
   <tbody>
     <tr>
       <td>Grid</td>
       <td>10000</td>
       <td>0.8</td>
     </tr>
     <tr>
       <td>Vaadin X</td>
       <td>999999</td>
       <td>0.8</td>
     </tr>
   </tbody>
  </table>
</v-grid>
```

For more detailed examples on usage, see the [components-examples](https://tomivirkki.github.io/components-examples)