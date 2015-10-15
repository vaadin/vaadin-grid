var grid, wrapper;

describe.feature = function(description, suite) {
  describe(description, function() {
    before(function(done) {
      HTMLImports.whenReady(function() {
        initializeGrid(done);
      });
    });

    after(function() {
      //make sure everything is finished before moving on.
      grid.then(function() {
        return grid;
      });
    });

    suite();
  });
};

function gridContainsText(_grid, text) {
  return Polymer.dom(_grid.root).querySelector(".vaadin-grid-tablewrapper").parentElement.innerHTML.indexOf(text) > -1;
}

function onRegister(e, cb) {
  waitUntil(function() {
    return !!e && e.constructor !== window.HTMLElement && e.constructor != window.HTMLUnknownElement;
  }, function() {
    e.then && e.then(cb) ||  cb();
  });
}

function waitUntil(check, exec, onTimeout) {
  var id = setInterval(function() {
    if (check()) {
      clearInterval(id);
      clearTimeout(timeoutId);
      exec();
    }
  }, 50);

  var timeoutId = setTimeout(function() {
    clearInterval(id);
    onTimeout(new Error('Test timed out waiting for:' + check));
  }, 5000);
}

function triggerMouseEvent(node, eventType, properties, shiftKey) {
  var clickEvent = document.createEvent('MouseEvents');
  clickEvent.initMouseEvent(eventType, true, true, undefined, undefined,
    undefined, undefined, undefined, undefined, undefined, undefined, shiftKey,
    undefined, undefined, undefined);

  for (var property in properties) {
    if (properties.hasOwnProperty(property)) {
      clickEvent[property] = properties[property];
    }
  }

  node.dispatchEvent(clickEvent);
}

function initializeGrid(cb) {
  wrapper = document.getElementById("gridwrapper");
  wrapper.innerHTML = "<vaadin-grid>" +
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
    "                     </tfoot>" +
    "                     </table>" +
    "                     </vaadin-grid>";
  grid = wrapper.querySelector("vaadin-grid");
  onRegister(grid, cb);
}

function infiniteDataSource(req) {
  var data = [];
  for (var i = req.index; i < req.index + req.count; i++) {
    data.push(["foo " + i, "bar " + i]);
  }
  req.success(data, this.size);
}

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
