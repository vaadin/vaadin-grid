System.register(['@angular/platform-browser-dynamic', '@angular/core', '../../directives/vaadin-grid'], function(exports_1, context_1) {
    "use strict";
    var __moduleName = context_1 && context_1.id;
    var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
        var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
        if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
        else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
        return c > 3 && r && Object.defineProperty(target, key, r), r;
    };
    var __metadata = (this && this.__metadata) || function (k, v) {
        if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
    };
    var platform_browser_dynamic_1, core_1, vaadin_grid_1;
    var TestApp;
    return {
        setters:[
            function (platform_browser_dynamic_1_1) {
                platform_browser_dynamic_1 = platform_browser_dynamic_1_1;
            },
            function (core_1_1) {
                core_1 = core_1_1;
            },
            function (vaadin_grid_1_1) {
                vaadin_grid_1 = vaadin_grid_1_1;
            }],
        execute: function() {
            TestApp = (function () {
                function TestApp(e, ref) {
                    this.items = [];
                    this._host = e.nativeElement;
                    this._ref = ref;
                }
                TestApp.prototype.detectChanges = function () {
                    this._ref.detectChanges();
                };
                TestApp.prototype.onGridReady = function (grid) {
                    grid.columns[0].flex = '2';
                    var event = new CustomEvent('readyForTests', { detail: this });
                    this._host.dispatchEvent(event);
                };
                TestApp = __decorate([
                    core_1.Component({
                        selector: 'test-app',
                        template: "\n    <vaadin-grid (grid-ready)=\"onGridReady($event)\">\n      <table>\n        <colgroup>\n          <col>\n          <col>\n          <col *ngIf=\"thirdColumn\" />\n        </colgroup>\n        <tbody>\n          <tr *ngFor=\"let item of items\">\n            <td>foo</td><td>bar</td>\n          </tr>\n        </tbody>\n      </table>\n    </vaadin-grid>\n    ",
                        directives: [vaadin_grid_1.VaadinGrid]
                    }), 
                    __metadata('design:paramtypes', [core_1.ElementRef, core_1.ChangeDetectorRef])
                ], TestApp);
                return TestApp;
            }());
            exports_1("TestApp", TestApp);
            document.body.addEventListener('bootstrap', function () {
                platform_browser_dynamic_1.bootstrap(TestApp);
            });
        }
    }
});

//# sourceMappingURL=main.js.map
