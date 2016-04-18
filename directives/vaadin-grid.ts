import {Directive, ElementRef, Input} from 'angular2/core';

declare var HTMLImports;
declare var Polymer;

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

  }
}
