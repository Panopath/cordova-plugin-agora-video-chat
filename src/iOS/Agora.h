#import <Cordova/CDV.h>

@interface AgoraPlugin : CDVPlugin

- (void) setKey:(CDVInvokedUrlCommand*)command;
- (void) joinChannel: (CDVInvokedUrlCommand*)command;

@end
