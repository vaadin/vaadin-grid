import {bootstrap}    from 'angular2/platform/browser';
import {Component, ElementRef, ChangeDetectorRef} from 'angular2/core';
import {VaadinGrid} from '../../directives/vaadin-grid';

@Component({
  selector: 'test-app',
  template: `
    <vaadin-grid (grid-ready)="onGridReady($event.detail)">
      <table>
        <colgroup>
          <col>
          <col>
          <col *ngIf="thirdColumn" />
        </colgroup>
        <tbody>
          <tr *ngFor="#item of items">
            <td>foo</td><td>bar</td>
          </tr>
        </tbody>
      </table>
    </vaadin-grid>
    `,
  directives: [VaadinGrid]
})
export class TestApp {

  private _host;
  private _ref;
  public items = [];
  public thirdColumn;

  constructor(e: ElementRef, ref: ChangeDetectorRef) {
    this._host = e.nativeElement;
    this._ref = ref;
  }

  public detectChanges() {
    this._ref.detectChanges();
  }

  onGridReady(grid) {
    grid.columns[0].flex = '2';
    var event = new CustomEvent('readyForTests', {detail: this});
    this._host.dispatchEvent(event);
  }
}

document.body.addEventListener('bootstrap', () => {
  bootstrap(TestApp);
});
