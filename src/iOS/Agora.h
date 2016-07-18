#import <Cordova/CDVPlugin.h>
#import "AGDChatViewController.h"
@interface AgoraPlugin : CDVPlugin
@property (nonatomic, retain) NSString *key;
@property (nonatomic, retain) AGDChatViewController* chat_instance;
@property (nonatomic, retain) void (^_completionHandler)(NSString* someParameter);

- (void) setKey:(CDVInvokedUrlCommand*)command;
- (void) joinChannel: (CDVInvokedUrlCommand*)command;
- (void) startLecture:(CDVInvokedUrlCommand *)command;
- (void) joinLecture:(CDVInvokedUrlCommand *)command;
- (void) listenForEvents:(CDVInvokedUrlCommand *)command;

@end
