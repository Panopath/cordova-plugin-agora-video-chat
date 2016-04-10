package com.panopath.plugin.agora;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import io.agora.rtc.*;
import io.agora.rtc.video.VideoCanvas;

public class VideoActivity extends BaseEngineEventHandlerActivity {

    Activity self = this;
    FrameLayout mainFrame, localVideoFrame;
    TextView waitingNotification;
    RtcEngine mRtcEngine;
    SurfaceView localView, remoteView;
    String lectureMode = "";
    Integer userCount = 0;
    Boolean lecturerInRoom = false;

    @SuppressLint("SetTextI18n")
    private void updateNotificationText() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (userCount == 0) {
                    if (lectureMode.equals("no")) {
                        waitingNotification.setText("正在等待其他成员加入\nWaiting for other attendees");
                    } else if (lectureMode.equals("start")) {
                        waitingNotification.setText("正在等待听众\nWaiting for audience");
                    }
                } else {
                    if (lectureMode.equals("no") || lectureMode.equals("join")) {
                        waitingNotification.setText("");
                    } else {
                        waitingNotification.setText("当前有" + userCount + "位听众\n" + userCount + " " + (userCount == 1 ? "person" : "people") + " in the room.");
                    }
                }

                if (lectureMode.equals("join") && (!lecturerInRoom)) {
                    waitingNotification.setText("正在等待主讲人\nWaiting for the lecturer");
                }
            }
        });
    }

    @Override
    public void onLeaveChannel(IRtcEngineEventHandler.RtcStats stats) {
        try {
            super.onLeaveChannel(stats);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFirstRemoteVideoDecoded(final int uid, int width, int height, int elapsed) {
        super.onFirstRemoteVideoDecoded(uid, width, height, elapsed);
    }

    @Override
    public void onUserJoined(int uid, int elapsed) {
        if (lectureMode.equals("start")) {
            //发起讲座时不设置remote
        } else if (lectureMode.equals("join")) {
            //参与讲座时，
            //只有10000加入时才设置
            if (uid == 10000)
                setRemoteToUid(uid);
        } else {
            //普通聊天时均设置
            setRemoteToUid(uid);
        }


        userCount++;

        if (lectureMode.equals("join") && uid == 10000) {
            lecturerInRoom = true;
        }

        updateNotificationText();
        super.onUserJoined(uid, elapsed);

    }

    @Override
    public void onJoinChannelSuccess(String channel, int uid, int elapsed) {
        super.onJoinChannelSuccess(channel, uid, elapsed);
    }

    @Override
    public void onUserOffline(final int uid) {

        if (lectureMode.equals("join") && uid == 10000) {
            lecturerInRoom = false;
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                userCount--;
                updateNotificationText();

                if (lectureMode.equals("no")) {

                    //非讲座模式，需要移除remoteView
                    //如果是讲座模式,
                    //是主讲人的话，根本没有remoteView，无需移除
                    //是参与者的话，要判断uid是否为10000
                    if (remoteView.getParent() != null)
                        ((ViewGroup) remoteView.getParent()).removeView(remoteView);

                    //刷新localView (否则程序会崩溃)
                    if (localView.getParent() != null)
                        ((ViewGroup) localView.getParent()).removeView(localView);

                    mainFrame.addView(localView, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));

                } else if (lectureMode.equals("join")) {

                    //讲座模式，判断uid是否为10000，是的话移除remoteView
                    if (uid == 10000)
                        if (remoteView.getParent() != null)
                            ((ViewGroup) remoteView.getParent()).removeView(remoteView);

                }
            }
        });
        super.onUserOffline(uid);
    }

    void setRemoteToUid(final int uid) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                //localView 在 remoteView 前先加，否则remoteView会把localView覆盖掉
                if (lectureMode.equals("no")) {
                    //非讲座模式时，要有左下角小框能看到自己，所以设置localView

                    if (localView.getParent() != null)
                        ((ViewGroup) localView.getParent()).removeView(localView);

                    localVideoFrame.addView(localView, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
                }


                //设置remoteView （这仅仅在非讲座模式/参与别人讲座时会触发。自己开讲座时不会触发。）
                mRtcEngine.setupRemoteVideo(new VideoCanvas(remoteView, VideoCanvas.RENDER_MODE_ADAPTIVE, uid));

                if (remoteView.getParent() != null)
                    ((ViewGroup) remoteView.getParent()).removeView(remoteView);

                mainFrame.addView(remoteView, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
            }
        });
    }


    @Override
    public void onBackPressed() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                mRtcEngine.leaveChannel();
            }
        }).run();

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        Bundle bundle = getIntent().getExtras();
        lectureMode = bundle.getString("lectureMode", "no");

        FakeR fakeR = new FakeR(this);

        setContentView(fakeR.getId("layout", "video"));

        mainFrame = (FrameLayout) findViewById(fakeR.getId("id", "mainFrame"));
        localVideoFrame = (FrameLayout) findViewById(fakeR.getId("id", "localVideoFrame"));
        waitingNotification = (TextView) findViewById(fakeR.getId("id", "waiting_notification"));

        RtcEngineCreator.getInstance().setRtcEngine();
        mRtcEngine = RtcEngineCreator.getInstance().getRtcEngine();

        RtcEngineCreator.getInstance().setEngineEventHandlerActivity(this);

        if (!lectureMode.equals("join"))
            mRtcEngine.enableVideo();

        localView = RtcEngine.CreateRendererView(getApplicationContext());
        remoteView = RtcEngine.CreateRendererView(getApplicationContext());

        if (lectureMode.equals("join")) {
            localVideoFrame.setVisibility(View.GONE);
        } else {
            mRtcEngine.setupLocalVideo(new VideoCanvas(localView));
            mainFrame.addView(localView, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        }

        Integer UID = 0;

        if (lectureMode.equals("no")) {
            UID = bundle.getInt("optionalUID", 0);
        } else if (lectureMode.equals("start")) {
            UID = 10000;
        } else if (lectureMode.equals("join")) {
            UID = 0;
        }

        updateNotificationText();
        mRtcEngine.joinChannel(RtcEngineCreator.getInstance().getVendorKey(),
                bundle.getString("channel"),
                "",
                UID
        );


        final View goBackBtn = findViewById(fakeR.getId("id", "btn_go_back"));
        goBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        final View switchCameraBtn = findViewById(fakeR.getId("id", "btn_switch_camera"));
        switchCameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRtcEngine.switchCamera();
            }
        });
    }
}
