import {Directive, ElementRef, Input} from 'angular2/core';

@Directive({selector: 'vaadin-grid'})
export class VaadinGrid {

  constructor(el: ElementRef) {
    this.importHref('bower_components/vaadin-grid/vaadin-grid.html', el);
  }

  importHref(href, el: ElementRef) {
    if (!document.querySelector('head link[href="' + href + '"]')) {
      const link = document.createElement('link');
      link.rel = 'import';
      link.href = href;
      document.head.appendChild(link);
    }
    HTMLImports.whenReady(this.onImport.bind(this, el));
  }

  onImport(el: ElementRef) {
    const grid = el.nativeElement;

    // Configuration <table> might be placed in a wrong container.
    // Let's move it in the light dom programmatically to fix that.
    var localDomTable = grid.querySelector("table:not(.vaadin-grid)");
    if (localDomTable) {
      Polymer.dom(grid).appendChild(localDomTable);
    }

    // vaadin-grid 1.0 doesn't support placing a configuration table dynamically. A hacky workaround needed for now.
    var c;
    for (var i in grid._grid) {
      if (grid._grid[i] && grid._grid[i].tagName == 'VAADIN-GRID') {
       c = i;
       break;
      }
    }
    const _c = grid._grid[c];

    try {
      grid._grid[c] = null;
      grid._grid.init(grid, grid._findTableElement(Polymer.dom(grid).children), Polymer.dom(grid.root), grid.$.measureobject);
    } catch (e) {
      grid._grid[c] = _c;
    }
  }
}
