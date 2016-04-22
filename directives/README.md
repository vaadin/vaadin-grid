## Angular 2 Directive

This directory contains the `VaadinGrid` directive to be used with
Angular 2 framework.

## Installation

1) First install the `vaadin-grid` through Bower.

```bash
bower install --save vaadin-grid
```


2) Add the webcomponents-lite.min.js polyfill to the `<head>` section of your
page and configure SystemJS as follows.

```html
<script src="bower_components/webcomponentsjs/webcomponents-lite.min.js"></script>
```

```javascript
System.config({
  map: {
    'vaadin-grid': 'bower_components/vaadin-grid/directives'
  },
  packages: {
    'vaadin-grid': {
      defaultExtension: 'js',
      main: 'vaadin-grid.js'
    }
  }
});
```

3) If you are using the `lite-server` or `browser-sync`, create a file called
`bs-config.json` in the root of your project folder with the following contents.

```json
{
  "snippetOptions": {
    "ignorePaths": "bower_components/**/*.html"
  }
}
```

## Usage

After the configuration is in place, you can import the directive into your
own Angular 2 component as follows.

```javascript
import { VaadinGrid } from '../bower_components/vaadin-grid/directives/vaadin-grid';


@Component({
  selector: 'my-component',
  template: '<vaadin-grid [items]="dataItems"><table><colgroup><col></colgroup></table></vaadin-grid>',
  directives: [VaadinGrid]
})
```

The table element is used to declaratively configure columns, headers and footers.
Items can be either a data array or a function providing the data.

In case you'll need to apply modifications to the declaratively configured
`vaadin-grid` columns, you must wait for the component to be fully initialized first.
Use the `vaadin-grid`'s `ready` event for this as follows:

```html
<vaadin-grid (ready)="gridReady($event)" [items]="dataItems">
  <table>
    <colgroup>
      <col>
    </colgroup>
  </table>
</vaadin-grid>
```

```javascript
gridReady(grid) {
  grid.columns[0].renderer = (cell) => {
    cell.element.innerHTML = 'row-' + cell.row.index;
  }
}
```
