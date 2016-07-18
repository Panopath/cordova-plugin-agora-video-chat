//
//  AGDChatViewController.h
//  AgoraDemo
//
//  Created by apple on 15/9/9.
//  Copyright (c) 2015年 Agora. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "AGDChatCell.h"
#import <AgoraRtcEngineKit/AgoraRtcEngineKit.h>

@interface AGDChatViewController : UIViewController <UICollectionViewDataSource, UICollectionViewDelegate, AgoraRtcEngineDelegate>
-(void) setKey: (NSString*) key;
-(void) setChn: (NSString*) channel;
-(void) setCallback: (void(^)(NSString*))handler;
-(void) setLecture: (BOOL) is_lecture;
-(void) setUid: (int) uid;
@property(nonatomic,retain) NSDictionary *dictionary;

@property (assign, nonatomic) AGDChatType chatType;

@end

static NSString * const AGDKeyChannel = @"Channel";
static NSString * const AGDKeyVendorKey = @"VendorKey";
