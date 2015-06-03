var grid, wrapper, vGridReady = false;

window.addEventListener("WebComponentsReady", function(){
  if ("VGrid" in window) {
    vGridReady = true;
  } else {
    webComponentHref = "../vaadin-grid.html";
    handleSDMinit(function(){
      vGridReady = true;
    });
  }
});

describe.feature = function(description, suite) {
  describe(description, function() {
    before(function(done) {
      initializeGrid();

      waitUntil(function() {
        return vGridReady && grid.then;
      }, done, done);
    });

    after(function() {
      return grid; //make sure everything is finished before moving on.
    });

    suite();
  });
};

function gridContainsText(_grid, text) {
  return Polymer.dom(_grid.root).querySelector(".v-grid-tablewrapper").parentElement.innerHTML.indexOf(text) > -1;
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
}

function triggerMouseEvent (node, eventType) {
  var clickEvent = document.createEvent('MouseEvents');
  clickEvent.initEvent(eventType, true, true);
  node.dispatchEvent(clickEvent);
}

function initializeGrid() {
  wrapper = document.getElementById("gridwrapper");
  wrapper.innerHTML = "<v-grid>" +
  "                     <table>" +
  "                       <col header-text='Name'>" +
  "                       <col header-text='Value'>" +
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
