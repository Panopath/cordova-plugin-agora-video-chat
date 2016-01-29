#import "Agora.h"
#import "AGDChatViewController.h"

@implementation AgoraPlugin

-(CDVPlugin*) initWithWebView:(UIWebView*)theWebView
{
    self = [super initWithWebView:theWebView];
    return self;
}

- (void)setKey:(CDVInvokedUrlCommand*)command
{

    NSString* callbackId = [command callbackId];
    NSString* name = [[command arguments] objectAtIndex:0];
    NSString* msg = [NSString stringWithFormat: @"Hello, %@", name];

    self.Key = name;
    CDVPluginResult* result = [CDVPluginResult
                               resultWithStatus:CDVCommandStatus_OK
                               messageAsString:msg];

    [self success:result callbackId:callbackId];
}

- (void)joinChannel:(CDVInvokedUrlCommand*)command
{
    UIStoryboard *sb = [UIStoryboard storyboardWithName:@"ChatView" bundle:nil];
    NSLog(@"hi");
    AGDChatViewController* chat = (AGDChatViewController*)[sb instantiateViewControllerWithIdentifier:@"ChatViewController"];
    [self.viewController presentViewController:chat animated:YES completion:nil];
}

@end
