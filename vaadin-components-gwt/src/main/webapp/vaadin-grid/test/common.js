var grid, wrapper;

describe.feature = function(description, suite) {
  describe(description, function() {
    beforeEach(function(done) {
      waitUntilGridReady(grid, function() {
        initializeGrid(done);
      });
    });

    suite();
  });
};

function waitUntilGridReady(grid, done) {
  waitUntil(function() {
    return !grid || grid.grid.isWorkPending() === false;
  }, done, done);
}

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
};

function gridContainsText(_grid, text) {
  return Polymer.dom(_grid.root).querySelector(".v-grid").innerHTML.indexOf(text) > -1;
};

function initializeGrid(done) {
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

  waitUntilGridReady(grid, done);
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
