# Cordova Agora Video Chat Plugin
**Still a WIP! with bugs not yet fixed, features not yet finished.
  Only supports Android for the time being, iOS version to be added soon**
  
![Preview](http://7xn0vy.dl1.z0.glb.clouddn.com/cordova-agora.jpg)

Cordova Agora Plugin is based on [Agora Media SDK 1.1.5 (http://agora.io/)](http://agora.io/) to achieve in-app real-time video chat for your cordova app.

## Installation
    cordova plugin add https://github.com/KevinWang15/cordova-plugin-agora-video-chat.git
    
## API
After installing this plugin, a global variable `agora` is created and is accessible through Javascript

1. Setup SDK Key     **Call it after deviceReady !**

        agora.setKey('*********************************');
    
    If you don't have an API key yet, head to [http://agora.io/](http://agora.io/) for one.

2. Join Channel

        agora.joinChannel('ChannelName')
    Joins a specific channel (channel name is arbitrary) and starts video-chatting with other people on the same channel


## Enum

Enumeration values used in Agora SDK are stored in: ```agora.Enum``` .
Specifically, there are

1. ```agora.Enum.UserOfflineReason```
2. ```agora.Enum.ChannelProfile```
3. ```agora.Enum.VideoProfile```
4. ```agora.Enum.ErrorCode```
5. ```agora.Enum.WarnCode```
6. ```agora.Enum.Quality```



# 以下内容仅完成了安卓版本（iOS版本正在制作中）
3. Leave Channel

        agora.leaveChannel()

## 事件

### 使用方法：

        document.addEventListener(EVENT_NAME,function(data){})

|EVENT_NAME|data|
|---|---|--|
|onJoinChannelSuccess|String channel, int uid, int elapsed|
|onRejoinChannelSuccess|String channel, int uid, int elapsed|
|onWarning|int warn|
|onApiCallExecuted|String api, int error|
|onCameraReady|无|
|onVideoStopped|无|
|onAudioQuality|int uid, int quality, short delay, short lost|
|onAudioVolumeIndication|(int uid,int volume)[] speakers, int totalVolume|
|onNetworkQuality|int quality|
|onUserMuteAudio|int uid, boolean muted|
|onRemoteVideoStat|int uid, int delay, int receivedBitrate, int receivedFrameRate|
|onLocalVideoStat|int sentBitrate, int sentFrameRate|
|onFirstRemoteVideoFrame|int uid, int width, int height, int elapsed|
|onFirstLocalVideoFrame|int width, int height, int elapsed|
|onConnectionInterrupted|无|
|onConnectionLost|无|
|onError|int err|
|onFirstRemoteVideoDecoded|int uid, int width, int height, int elapsed|
|onUserJoined|int uid, int elapsed|
|onUserOffline|int uid, int reason|
|onUserMuteVideo|int uid, boolean muted|
|onRtcStats|int totalDuration, int txBytes, int rxBytes, int txKBitRate, int rxKBitRate, int lastmileQuality, int users, double cpuTotalUsage, double cpuAppUsage|
|onLeaveChannel|int totalDuration, int txBytes, int rxBytes, int txKBitRate, int rxKBitRate, int lastmileQuality, int users, double cpuTotalUsage, double cpuAppUsage|

具体请参考 agora.io 的文档

### 在Console中打出所有的事件

    agora.startLoggingAllEvents()

### 在Console中停止打出所有的事件

    agora.stopLoggingAllEvents()


## 讲座模式

只有一位用户（主讲人），发送视频给其他用户。
其他用户连接，不发送视频，但可以和主讲人语音互动。

    agora.startLecture(channel);
    //作为主讲人
    agora.joinLecture(channel);
    //作为听众

## 其他
### Android 6.0+ 权限问题

Android 6.0+ 将权限改成了运行时动态向用户询问获取，可能导致插件崩溃。
解决方法：将android:targetSdkVersion设置为19

More features coming soon

