import {bootstrap}    from '@angular/platform-browser-dynamic';
import {Component, ElementRef, ChangeDetectorRef, ViewChild, enableProdMode} from '@angular/core';
import {PolymerElement} from '@vaadin/angular2-polymer';
enableProdMode();

@Component({
  selector: 'test-app',
  template: `
    <vaadin-grid #grid>
      <table>
        <colgroup>
          <col>
          <col>
          <col *ngIf="thirdColumn" />
        </colgroup>
        <tbody>
          <tr *ngFor="let item of items">
            <td>foo</td><td>bar</td>
          </tr>
        </tbody>
      </table>
    </vaadin-grid>
    `,
  directives: [PolymerElement('vaadin-grid')]
})
export class TestApp {

  private _host;
  private _ref;
  public items = [];
  public thirdColumn;

  @ViewChild('grid') grid: any;

  constructor(e: ElementRef, ref: ChangeDetectorRef) {
    this._host = e.nativeElement;
    this._ref = ref;
  }

  public detectChanges() {
    this._ref.detectChanges();
  }

  ngAfterViewInit() {
    var grid = this.grid.nativeElement;
    grid.then(() => {
      grid.columns[0].flex = '2';
      var event = new CustomEvent('readyForTests', {detail: this});
      this._host.dispatchEvent(event);
    });
  }
}

document.body.addEventListener('bootstrap', () => {
  bootstrap(TestApp);
});
