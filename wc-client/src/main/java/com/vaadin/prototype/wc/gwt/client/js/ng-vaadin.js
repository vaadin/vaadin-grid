
(function(angular) {

  if (!angular) return;

  console.log("Initialised Vaadin Components module for AngularJS");

  var vModule = angular.module('vaadin-components', []);

  var attrFnc = function() {
    return {
      restrict : 'E',
      link : function(scope, element, attrs) {

        var attrMap = {};
        for ( var prop in attrs.$attr) {
          var _attr = attrs.$attr[prop];
          var _match = element.attr(_attr).match(/\{\{\s*([\.\w]+)\s*\}\}/);
          if (_match) {
            attrMap[_attr] = _match[1];
          }
        }

        new MutationObserver(function() {
          scope.$apply();
        }).observe(element[0], {
          attributes : true
        });

        scope.$watch(function() {
          for ( var attr in attrMap) {
            var value = element.attr(attr);
            var tokens = attrMap[attr].split(/\./);
            var parent = scope;
            for (var i = 0; i < tokens.length - 1; i++) {
              if (typeof (parent[tokens[i]]) == 'undefined') {
                parent[tokens[i]] = {};
              }
              parent = parent[tokens[i]];
            }
            // console.log(tokens[tokens.length - 1] + " " + value);
            parent[tokens[tokens.length - 1]] = value;
          }
        });
      }
    };
  };

  var vaadinComponents = ['vSlider', 'vProgress', 'vGrid'];

  angular.forEach(vaadinComponents, function(tag) {
    vModule.directive(tag, attrFnc);

    angular.forEach(document.querySelectorAll('[ng-app]'), function(element) {
      var app = element.getAttribute('ng-app');
      // TODO: This causes a lot of errors when starting angular requiring 'vaadin-components'
      angular.bootstrap(element, [ app ]);

      // TODO: This should be a way to add behavior to a module but does not work
      // var module = angular.module(app);
      // vModule.directive(tag, attrFnc);
    });
  });

})(window.angular);