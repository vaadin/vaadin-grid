import {Directive, ElementRef, Input, Output, EventEmitter} from 'angular2/core';

declare var Polymer;

@Directive({selector: 'vaadin-grid'})
export class VaadinGrid {

  @Output('grid-ready') gridReady: EventEmitter<any> = new EventEmitter(false);

  private grid: any;

  constructor(el: ElementRef) {
    if (!(<any>window).Polymer || !Polymer.isInstance(el.nativeElement)) {
      console.error("vaadin-grid has not been registered yet, please remember to import vaadin-grid.html in your main HTML page.");
      return;
    }
    this.grid = el.nativeElement;
  }

  ngAfterViewInit() {
    // Configuration <table> might be placed in a wrong container.
    // Let's move it in the light dom programmatically to fix that.
    var localDomTable = this.grid.querySelector("table:not(.vaadin-grid)");
    if (localDomTable) {
      Polymer.dom(this.grid).appendChild(localDomTable);
    }

    this.grid.then(() => {
      this.gridReady.emit(this.grid);
    });
  }
}
