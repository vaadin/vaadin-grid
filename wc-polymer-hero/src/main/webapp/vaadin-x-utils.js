function waitUntil(check, exec) {
  var id = setInterval(function() {
    if (r = check()) {
      clearInterval(id);
      exec(r);
    }
  }, 100);
}
function waitUntilGridReady(grid, exec) {
  waitUntil(function() {
    return grid && grid.dataSource && grid;
  }, exec);
}