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

More features coming soon