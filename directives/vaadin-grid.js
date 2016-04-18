System.register(['angular2/core'], function(exports_1) {
    var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
        var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
        if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
        else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
        return c > 3 && r && Object.defineProperty(target, key, r), r;
    };
    var __metadata = (this && this.__metadata) || function (k, v) {
        if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
    };
    var core_1;
    var VaadinGrid;
    return {
        setters:[
            function (core_1_1) {
                core_1 = core_1_1;
            }],
        execute: function() {
            VaadinGrid = (function () {
                function VaadinGrid(el) {
                    this.importHref('bower_components/vaadin-grid/vaadin-grid.html', el);
                }
                VaadinGrid.prototype.importHref = function (href, el) {
                    if (!document.querySelector('head link[href="' + href + '"]')) {
                        var link = document.createElement('link');
                        link.rel = 'import';
                        link.href = href;
                        document.head.appendChild(link);
                    }
                    HTMLImports.whenReady(this.onImport.bind(this, el));
                };
                VaadinGrid.prototype.onImport = function (el) {
                    var grid = el.nativeElement;
                    // Configuration <table> might be placed in a wrong container.
                    // Let's move it in the light dom programmatically to fix that.
                    var localDomTable = grid.querySelector("table:not(.vaadin-grid)");
                    if (localDomTable) {
                        Polymer.dom(grid).appendChild(localDomTable);
                    }
                };
                VaadinGrid = __decorate([
                    core_1.Directive({ selector: 'vaadin-grid' }), 
                    __metadata('design:paramtypes', [core_1.ElementRef])
                ], VaadinGrid);
                return VaadinGrid;
            })();
            exports_1("VaadinGrid", VaadinGrid);
        }
    }
});
//# sourceMappingURL=vaadin-grid.js.map