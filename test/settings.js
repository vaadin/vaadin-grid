const ie = !!window.MSStream;
const ios = !ie && /iPad|iPhone|iPod/.test(navigator.userAgent);
WCT = {
  mochaOptions: {
    timeout: ios || ie ? 40000 : 30000
  }
};