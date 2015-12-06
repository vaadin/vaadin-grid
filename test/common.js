var grid, wrapper;

var isSDM = /:8888$/.test(window.location.host);

describe.feature = function(description, suite, htmlGrid) {
  describe(description, function() {
    before(function(done) {
      HTMLImports.whenReady(function() {
        initializeGrid(done, htmlGrid);
      });
    });

    after(function() {
      //make sure everything is finished before moving on.
      grid.then(function() {
        return grid;
      });
    });

    // Add skipIf function to it
    it.skipIf = function(bool, title, test) {
      bool = typeof bool == 'function' ? bool() : bool;
      (bool ? it.skip : it)(title, test);
    }

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

function initializeGrid(cb, htmlGrid) {
  wrapper = document.getElementById("gridwrapper");
  wrapper.innerHTML = htmlGrid || "<vaadin-grid>" +
    "                     <table>" +
    "                       <col>" +
    "                       <col>" +
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

function infiniteDataSource(params, callback) {
  var data = [];
  // data.length should never be greater than size, otherwise in SDM or
  // compiled code with assertions we get an exception in
  // AbstractRemoteDataSource::setRowData
  for (var i = params.index; i < infiniteDataSource.size && i < params.index + params.count; i++) {
    data.push(["foo " + i, "bar " + i]);
  }
  callback(data, infiniteDataSource.size);
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
