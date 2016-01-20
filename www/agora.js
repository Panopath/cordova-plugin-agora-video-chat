var key = "";
var self = this;
module.exports = {
  setKey: function (key, successCallback, failCallback) {
    cordova.exec(successCallback, failCallback, 'Agora', 'setKey', [key]);
    console.log("Key set to " + key);
    self.key = key;
    if (typeof successCallback == 'function') {
      successCallback('call setKey() first!');
    }
  },
  joinChannel: function (channel, extraInfo, successCallback, failCallback) {
    if (!self.key) {
      if (typeof failCallback == 'function') {
        failCallback('call setKey() first!');
      }
    }
    cordova.exec(successCallback, failCallback, 'Agora', 'joinChannel', [channel, extraInfo]);
  }
};
