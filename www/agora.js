var exec = require('cordova/exec'),
    cordova = require('cordova'),
    channel = require('cordova/channel'),
    utils = require('cordova/utils');

var self = this;
this.vendorKey = "";
this.logAllEvents = false;

channel.onCordovaReady.subscribe(function () {
    cordova.exec(function (event) {
        if (event !== 0) {
            event = JSON.parse(event);
            if (self.logAllEvents)
                console.info("CordovaAgora." + event.eventName, JSON.parse(event.data));

            cordova.fireDocumentEvent("CordovaAgora." + event.eventName, JSON.parse(event.data));
        }
    }, function () {
        console.error("CordovaAgora: Failed to listen for events.");
    }, 'Agora', 'listenForEvents', []);
});

module.exports = {
    setKey: function (vendorKey, successCallback, failCallback) {
        cordova.exec(successCallback, failCallback, 'Agora', 'setKey', [vendorKey]);
        self.vendorKey = vendorKey;
        if (typeof successCallback == 'function') {
            successCallback('call setKey() first!');
        }
    },

    startLoggingAllEvents: function () {
        self.logAllEvents = true;
    },

    stopLoggingAllEvents: function () {
        self.logAllEvents = false;
    },

    joinChannel: function (channel, optionalUID, successCallback, failCallback) {
        if (!optionalUID)
            optionalUID = 0;

        if (!self.vendorKey) {
            if (typeof failCallback == 'function') {
                failCallback('call setKey() first!');
            }
        }
        cordova.exec(successCallback, failCallback, 'Agora', 'joinChannel', [channel, optionalUID]);
    },

    leaveChannel: function (successCallback, failCallback) {
        cordova.exec(successCallback, failCallback, 'Agora', 'leaveChannel', []);
    },

    startLecture: function (channel, successCallback, failCallback) {
        cordova.exec(successCallback, failCallback, 'Agora', 'startLecture', [channel]);
    },

    joinLecture: function (channel, successCallback, failCallback) {
        cordova.exec(successCallback, failCallback, 'Agora', 'joinLecture', [channel]);
    },

    Enum: {

        UserOfflineReason: {
            USER_OFFLINE_QUIT: 0,
            USER_OFFLINE_DROPPED: 1
        },

        ChannelProfile: {
            CHANNEL_PROFILE_FREE: 0,
            CHANNEL_PROFILE_BROADCASTER: 1,
            CHANNEL_PROFILE_AUDIENCE: 2
        },

        VideoProfile: {
            VIDEO_PROFILE_120P: 0,
            VIDEO_PROFILE_120P_2: 1,
            VIDEO_PROFILE_120P_3: 2,
            VIDEO_PROFILE_180P: 1,
            VIDEO_PROFILE_180P_2: 1,
            VIDEO_PROFILE_180P_3: 1,
            VIDEO_PROFILE_240P: 2,
            VIDEO_PROFILE_240P_2: 2,
            VIDEO_PROFILE_240P_3: 2,
            VIDEO_PROFILE_360P: 3,
            VIDEO_PROFILE_360P_2: 3,
            VIDEO_PROFILE_360P_3: 3,
            VIDEO_PROFILE_360P_4: 3,
            VIDEO_PROFILE_360P_5: 3,
            VIDEO_PROFILE_360P_6: 3,
            VIDEO_PROFILE_480P: 4,
            VIDEO_PROFILE_480P_2: 4,
            VIDEO_PROFILE_480P_3: 4,
            VIDEO_PROFILE_480P_4: 4,
            VIDEO_PROFILE_480P_5: 4,
            VIDEO_PROFILE_480P_6: 4,
            VIDEO_PROFILE_720P: 5,
            VIDEO_PROFILE_720P_2: 5,
            VIDEO_PROFILE_720P_3: 5,
            VIDEO_PROFILE_720P_4: 5,
            VIDEO_PROFILE_1080P: 6,
            VIDEO_PROFILE_1080P_2: 6,
            VIDEO_PROFILE_1080P_3: 6,
            VIDEO_PROFILE_1080P_4: 6,
            VIDEO_PROFILE_1080P_5: 6,
            VIDEO_PROFILE_1080P_6: 6,
            VIDEO_PROFILE_4K: 7,
            VIDEO_PROFILE_4K_2: 7,
            VIDEO_PROFILE_4K_3: 7,
            VIDEO_PROFILE_4K_4: 7,
            VIDEO_PROFILE_DEFAULT: 3
        },

        ErrorCode: {
            ERR_OK: 0,
            ERR_FAILED: 1,
            ERR_INVALID_ARGUMENT: 2,
            ERR_NOT_READY: 3,
            ERR_NOT_SUPPORTED: 4,
            ERR_REFUSED: 5,
            ERR_BUFFER_TOO_SMALL: 6,
            ERR_NOT_INITIALIZED: 7,
            ERR_INVALID_VIEW: 8,
            ERR_NO_PERMISSION: 9,
            ERR_TIMEDOUT: 1,
            ERR_CANCELED: 1,
            ERR_TOO_OFTEN: 1,
            ERR_BIND_SOCKET: 1,
            ERR_NET_DOWN: 1,
            ERR_NET_NOBUFS: 1,
            ERR_INIT_VIDEO: 1,
            ERR_JOIN_CHANNEL_REJECTED: 1,
            ERR_LEAVE_CHANNEL_REJECTED: 1,
            ERR_INVALID_VENDOR_KEY: 1,
            ERR_INVALID_CHANNEL_NAME: 1,
            ERR_DYNAMIC_KEY_TIMEOUT: 1,
            ERR_INVALID_DYNAMIC_KEY: 1,
            ERR_LOAD_MEDIA_ENGINE: 1,
            ERR_START_CALL: 1,
            ERR_START_CAMERA: 1,
            ERR_START_VIDEO_RENDER: 1,
            ERR_ADM_GENERAL_ERROR: 1,
            ERR_ADM_JAVA_RESOURCE: 1,
            ERR_ADM_SAMPLE_RATE: 1,
            ERR_ADM_INIT_PLAYOUT: 1,
            ERR_ADM_START_PLAYOUT: 1,
            ERR_ADM_STOP_PLAYOUT: 1,
            ERR_ADM_INIT_RECORDING: 1,
            ERR_ADM_START_RECORDING: 1,
            ERR_ADM_STOP_RECORDING: 1,
            ERR_ADM_RUNTIME_PLAYOUT_ERROR: 1,
            ERR_ADM_RUNTIME_RECORDING_ERROR: 1,
            ERR_ADM_RECORD_AUDIO_FAILED: 1,
            ERR_ADM_INIT_LOOPBACK: 1,
            ERR_ADM_START_LOOPBACK: 1,
            ERR_VDM_CAMERA_NOT_AUTHORIZED: 1
        },

        WarnCode: {
            WARN_NO_AVAILABLE_CHANNEL: 1,
            WARN_LOOKUP_CHANNEL_TIMEOUT: 1,
            WARN_LOOKUP_CHANNEL_REJECTED: 1,
            WARN_OPEN_CHANNEL_TIMEOUT: 1,
            WARN_OPEN_CHANNEL_REJECTED: 1,
            WARN_REQUEST_DEFERRED: 1,
            WARN_ADM_RUNTIME_PLAYOUT_WARNING: 1,
            WARN_ADM_RUNTIME_RECORDING_WARNING: 1,
            WARN_ADM_RECORD_AUDIO_SILENCE: 1,
            WARN_ADM_PLAYOUT_MALFUNCTION: 1,
            WARN_ADM_RECORD_MALFUNCTION: 1,
            WARN_APM_HOWLING: 1
        },

        Quality: {
            UNKNOWN: 0,
            EXCELLENT: 1,
            GOOD: 2,
            POOR: 3,
            BAD: 4,
            VBAD: 5,
            DOWN: 6
        }
    }
};
