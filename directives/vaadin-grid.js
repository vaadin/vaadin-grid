System.register(['@angular/core'], function(exports_1) {
    var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
        if (typeof Reflect === "object" && typeof Reflect.decorate === "function") return Reflect.decorate(decorators, target, key, desc);
        switch (arguments.length) {
            case 2: return decorators.reduceRight(function(o, d) { return (d && d(o)) || o; }, target);
            case 3: return decorators.reduceRight(function(o, d) { return (d && d(target, key)), void 0; }, void 0);
            case 4: return decorators.reduceRight(function(o, d) { return (d && d(target, key, o)) || o; }, desc);
        }
    };
    var __metadata = (this && this.__metadata) || function (k, v) {
        if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
    };
    var core_1;
    var Polymer, VaadinGrid;
    return {
        setters:[
            function (core_1_1) {
                core_1 = core_1_1;
            }],
        execute: function() {
            Polymer = window.Polymer;
            VaadinGrid = (function () {
                function VaadinGrid(el) {
                    this.gridReady = new core_1.EventEmitter(false);
                    if (!Polymer || !Polymer.isInstance(el.nativeElement)) {
                        console.error("vaadin-grid has not been registered yet, please remember to import vaadin-grid.html in your main HTML page.");
                        return;
                    }
                    this.grid = el.nativeElement;
                }
                VaadinGrid.prototype.ngAfterViewInit = function () {
                    var _this = this;
                    // Configuration <table> might be placed in a wrong container.
                    // Let's move it in the light dom programmatically to fix that.
                    var localDomTable = this.grid.querySelector("table:not(.vaadin-grid)");
                    if (localDomTable) {
                        Polymer.dom(this.grid).appendChild(localDomTable);
                    }
                    this.grid.then(function () {
                        _this.gridReady.emit(_this.grid);
                    });
                };
                __decorate([
                    core_1.Output('grid-ready'), 
                    __metadata('design:type', core_1.EventEmitter)
                ], VaadinGrid.prototype, "gridReady");
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