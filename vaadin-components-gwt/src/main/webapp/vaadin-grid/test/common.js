var grid, wrapper;

describe.feature = function(description, suite) {
  describe(description, function() {
    before(function(done) {
      initializeGrid();

      waitUntil(function() {
        return grid.then;
      }, done, done);
    });

    afterEach(function() {
      return grid.then(function() {

        grid.selection.mode = 'single';
        grid.frozenColumns = 0;
        grid.columns = [{name: 'Name', headerContent: 'Name'}, {name: 'Value', headerContent: 'Value'}];
        grid.data.source = [['Grid', '10000'], ['VaadinX', '1000']];

        grid.disabled = false;

        return grid;
      });
    });

    suite();
  });
};

function waitUntil(check, exec, onTimeout) {
  var id = setInterval(function() {
    if (check()) {
      clearInterval(id);
      clearTimeout(timeoutId);
      exec();
    }
  }, 100);

  var timeoutId = setTimeout(function() {
    clearInterval(id);
    assert.fail();
    onTimeout();
  }, 5000);
}


function initializeGrid() {
  wrapper = document.getElementById("gridwrapper");
  wrapper.innerHTML = "<v-grid>" +
  "                     <table>" +
  "                       <thead>" +
  "                         <tr>" +
  "                           <th>Name</th>" +
  "                           <th>Value</th>" +
  "                         </tr>" +
  "                       </thead>" +
  "                       <tbody>" +
  "                         <tr>" +
  "                           <td>Grid</td>" +
  "                           <td>10000</td>" +
  "                         </tr>" +
  "                         <tr>" +
  "                           <td>VaadinX</td>" +
  "                           <td>1000</td>" +
  "                         </tr>" +
  "                       </tbody>" +
  "                     <tfoot>" +
  "                       <tr>" +
  "                         <td>Name</td>" +
  "                         <td>Value</td>" +
  "                       </tr>" +
  "                     </tfoot>"+
  "                     </table>" +
  "                     </v-grid>";
  grid = wrapper.querySelector("v-grid");

  return grid;
};

var local = function() {
  return Polymer.dom(grid.root);
};
var light = function() {
  return Polymer.dom(grid);
};

var qLocal = function(selector) {
  return local().querySelector(selector);
};
var qaLocal = function(selector) {
  return local().querySelectorAll(selector);
};
var qLight = function(selector) {
  return light().querySelector(selector);
};
