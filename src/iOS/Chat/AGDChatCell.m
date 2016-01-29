//
//  AGDChatCell.m
//  AgoraDemo
//
//  Created by Shilong on 9/10/15.
//  Copyright (c) 2015 Agora. All rights reserved.
//

#import "AGDChatCell.h"

@interface AGDChatCell ()
@property (weak, nonatomic) IBOutlet UIView *audioView;
@end

@implementation AGDChatCell

- (void)setType:(AGDChatType)type
{
    _type = type;
    
    if (type == AGDChatTypeAudio) {
        self.videoView.hidden = YES;
        self.audioView.hidden = NO;
    } else if (type == AGDChatTypeVideo) {
        self.videoView.hidden = NO;
        self.audioView.hidden = YES;
    } else {
        NSLog(@"error: control type not correct");
    }
}

@end
