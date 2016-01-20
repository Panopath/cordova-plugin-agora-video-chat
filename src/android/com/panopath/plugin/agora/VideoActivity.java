package com.panopath.plugin.agora;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import io.agora.rtc.*;
import io.agora.rtc.video.VideoCanvas;

public class VideoActivity extends Activity {

    Activity self = this;
    FrameLayout mainFrame, localVideoFrame;
    TextView waitingNotification;
    RtcEngine mRtcEngine;
    SurfaceView localView, remoteView;
    RtcEventHandler handler;

    class RtcEventHandler extends IRtcEngineEventHandler {
        @Override
        public void onFirstRemoteVideoDecoded(final int uid, int width, int height, int elapsed) {
            Log.i(Agora.TAG, "onFirstRemoteVideoDecoded");
            setRemoteToUid(uid);
            super.onFirstRemoteVideoDecoded(uid, width, height, elapsed);
        }

        @Override
        public void onUserJoined(int uid, int elapsed) {
//            setRemoteToUid(uid);
            Log.i(Agora.TAG, "onUserJoined:" + uid);
            super.onUserJoined(uid, elapsed);
        }

        @Override
        public void onJoinChannelSuccess(String channel, int uid, int elapsed) {
            Log.i(Agora.TAG, "onJoinChannelSuccess,uid=" + uid);
            super.onJoinChannelSuccess(channel, uid, elapsed);
        }

        @Override
        public void onUserOffline(final int uid, final int reason) {
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
            super.onUserOffline(uid, reason);
        }
    }

    void setRemoteToUid(final int uid) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                waitingNotification.setText("视频中");
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        FakeR fakeR = new FakeR(this);
        setContentView(fakeR.getId("layout", "video"));
        final Button hangup_btn = (Button) findViewById(fakeR.getId("id", "btn_hang_up"));
        mainFrame = (FrameLayout) findViewById(fakeR.getId("id", "mainFrame"));
        localVideoFrame = (FrameLayout) findViewById(fakeR.getId("id", "localVideoFrame"));
        waitingNotification = (TextView) findViewById(fakeR.getId("id", "waiting_notification"));
        hangup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRtcEngine.leaveChannel();
                self.finish();
            }
        });

        handler = new RtcEventHandler();
        mRtcEngine = RtcEngine.create(getApplicationContext(), Agora.key, handler);

        mRtcEngine.enableVideo();

        localView = RtcEngine.CreateRendererView(getApplicationContext());
        remoteView = RtcEngine.CreateRendererView(getApplicationContext());

        mRtcEngine.setupLocalVideo(new VideoCanvas(localView));
        mainFrame.addView(localView, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
//        mRtcEngine.startPreview();
//        alert("INFO", "JOIN CHANNEL:" + bundle.getString("channel") + "\nKEY:" + Agora.key, "OK");
        mRtcEngine.joinChannel(Agora.key, bundle.getString("channel"), bundle.getString("extraInfo"), 0);
    }

    private synchronized void alert(final String title,
                                    final String message,
                                    final String buttonLabel) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setNeutralButton(buttonLabel, new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                    }
                })
                .create()
                .show();
    }
}
