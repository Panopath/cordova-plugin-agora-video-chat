package com.panopath.plugin.agora;

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

    @Override
    public void onLeaveChannel(IRtcEngineEventHandler.RtcStats stats) {
        try {
            super.onLeaveChannel(stats);
            Log.i(Agora.TAG, "calling finish");
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onFirstRemoteVideoDecoded(final int uid, int width, int height, int elapsed) {
        Log.i(Agora.TAG, "onFirstRemoteVideoDecoded");
        super.onFirstRemoteVideoDecoded(uid, width, height, elapsed);
    }

    @Override
    public void onUserJoined(int uid, int elapsed) {
//            setRemoteToUid(uid);
        Log.i(Agora.TAG, "onUserJoined:" + uid);
        setRemoteToUid(uid);
        super.onUserJoined(uid, elapsed);
    }

    @Override
    public void onJoinChannelSuccess(String channel, int uid, int elapsed) {
        Log.i(Agora.TAG, "onJoinChannelSuccess,uid=" + uid);
        super.onJoinChannelSuccess(channel, uid, elapsed);
    }

    @Override
    public void onUserOffline(final int uid) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                waitingNotification.setText("正在等待其他成员加入\nWaiting for other attendees");

                if (remoteView.getParent() != null)
                    ((ViewGroup) remoteView.getParent()).removeView(remoteView);

                if (localView.getParent() != null)
                    ((ViewGroup) localView.getParent()).removeView(localView);

                mainFrame.addView(localView, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
            }
        });
        super.onUserOffline(uid);
    }

    void setRemoteToUid(final int uid) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                waitingNotification.setText("");
                mRtcEngine.setupRemoteVideo(new VideoCanvas(remoteView, VideoCanvas.RENDER_MODE_ADAPTIVE, uid));


                if (localView.getParent() != null)
                    ((ViewGroup) localView.getParent()).removeView(localView);
                localVideoFrame.addView(localView, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));


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

        FakeR fakeR = new FakeR(this);

        setContentView(fakeR.getId("layout", "video"));

        final View goBackBtn =  findViewById(fakeR.getId("id", "btn_go_back"));

        mainFrame = (FrameLayout) findViewById(fakeR.getId("id", "mainFrame"));
        localVideoFrame = (FrameLayout) findViewById(fakeR.getId("id", "localVideoFrame"));
        waitingNotification = (TextView) findViewById(fakeR.getId("id", "waiting_notification"));

        goBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


//        mRtcEngine = RtcEngine.create(getApplicationContext(), Agora.key, handler);

        // setup engine
        RtcEngineCreator.getInstance().setRtcEngine();

        mRtcEngine = RtcEngineCreator.getInstance().getRtcEngine();

        // setup engine event activity
        RtcEngineCreator.getInstance().setEngineEventHandlerActivity(this);

        mRtcEngine.enableVideo();

        localView = RtcEngine.CreateRendererView(getApplicationContext());
        remoteView = RtcEngine.CreateRendererView(getApplicationContext());

        mRtcEngine.setupLocalVideo(new VideoCanvas(localView));
        mainFrame.addView(localView, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));

        mRtcEngine.joinChannel(RtcEngineCreator.getInstance().getVendorKey(),
                bundle.getString("channel"),
                bundle.getString("extraInfo"),
                0
        );

        Log.i(Agora.TAG, "calling join channel");

    }
}
