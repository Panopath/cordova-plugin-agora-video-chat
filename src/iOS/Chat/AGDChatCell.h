//
//  AGDChatCell.h
//  AgoraDemo
//
//  Created by Shilong on 9/10/15.
//  Copyright (c) 2015 Agora. All rights reserved.
//

#import <UIKit/UIKit.h>

typedef NS_ENUM(NSInteger, AGDChatType) {
    AGDChatTypeVideo,
    AGDChatTypeAudio,
    AGDChatTypeDefault = AGDChatTypeVideo
};

@interface AGDChatCell : UICollectionViewCell
@property (weak, nonatomic) IBOutlet UIView *videoView;
@property (weak, nonatomic) IBOutlet UILabel *nameLabel;
@property (assign, nonatomic) AGDChatType type;
@end
