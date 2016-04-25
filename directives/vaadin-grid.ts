import {Directive, ElementRef, Input, Output, EventEmitter} from 'angular2/core';

declare var Polymer;

@Directive({selector: 'vaadin-grid'})
export class VaadinGrid {

  @Output() ready: EventEmitter<any> = new EventEmitter(false);

  constructor(el: ElementRef) {
    if (!(<any>window).Polymer || !Polymer.isInstance(el)) {
      console.error("vaadin-grid has not been registered yet, please remember to import vaadin-grid.html in your main HTML page.");
      return;
    }
    this.init(el);
  }

  init(el: ElementRef) {
    const grid = el.nativeElement;

    // Configuration <table> might be placed in a wrong container.
    // Let's move it in the light dom programmatically to fix that.
    var localDomTable = grid.querySelector("table:not(.vaadin-grid)");
    if (localDomTable) {
      Polymer.dom(grid).appendChild(localDomTable);
    }

    grid.then(() => {
      this.ready.emit(grid);
    });
  }
}
