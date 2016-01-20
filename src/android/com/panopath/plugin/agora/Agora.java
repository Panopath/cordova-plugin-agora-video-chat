package com.panopath.plugin.agora;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.SurfaceView;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import io.agora.rtc.*;
import io.agora.rtc.video.VideoCanvas;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

public class Agora extends CordovaPlugin {
  public static final String TAG = "CDVAgora";
  public static String key = "";
  protected RtcEngine mRtcEngine;
  protected SurfaceView surfaceView;
  protected Activity appActivity;
  protected Context appContext;

  class RtcEngineEventHandler extends IRtcEngineEventHandler {

    public void onLogEvent(int i, String s) {
      Log.d(TAG, "onLogEvent");
    }

    public void onJoinSuccess(String s, int i, int i1) {
      Log.d(TAG, "onJoinSuccess");
    }

    public void onRejoinSuccess(String s, int i, int i1) {
      Log.d(TAG, "onRejoinSuccess");
    }

    public void onError(int i) {
      Log.d(TAG, "onError:" + i);
    }

    public void onLoadAudioEngineSuccess() {
      Log.d(TAG, "onLoadAudioEngineSuccess");
    }

    public void onAudioQuality(int i, int i1, short i2, short i3, short i4, short i5) {
      Log.d(TAG, "onAudioQuality");
    }

    public void onRecapStat(byte[] bytes) {
      Log.d(TAG, "onRecapStat");
    }

    public void onNetworkQuality(int i) {
      Log.d(TAG, "onNetworkQuality");
    }

    public void onUserJoined(int i, int i1) {
      Log.d(TAG, "onUserJoined");
    }

    public void onUserOffline(int i) {
      Log.d(TAG, "onUserOffline");
    }

    public void onUserMuteAudio(int i, boolean b) {
      Log.d(TAG, "onUserMuteAudio");
    }

    public void onUserMuteVideo(int i, boolean b) {
      Log.d(TAG, "onUserMuteVideo");
    }

    public void onRemoteVideoStat(int i, int i1, int i2, int i3) {
      Log.d(TAG, "onRemoteVideoStat");
    }

    public void onLocalVideoStat(int i, int i1, int i2, int i3, int i4) {
      Log.d(TAG, "onLocalVideoStat");
    }

    public void onFirstVideoFrame(int i, int i1, int i2) {
      Log.d(TAG, "onFirstVideoFrame");
    }
  }

  @Override
  protected void pluginInitialize() {
    appContext = this.cordova.getActivity().getApplicationContext();
    appActivity = cordova.getActivity();
    super.pluginInitialize();
  }

  @Override
  public boolean execute(String action, JSONArray args,
                         final CallbackContext callbackContext) throws JSONException {

    Log.d(TAG, action + " called");

    if (action.equals("setKey")) {
      if (!key.equals("")) {
        callbackContext.error("Key can only be set once");
      } else {
        key = args.getString(0);
        return true;
      }
    }

    if (action.equals("joinChannel")) {
      if (key.equals("")) {
        callbackContext.error("call setKey() first!");
        return false;
      }
      final String channel = args.getString(0);
      final String extraInfo = args.getString(1);

      Log.d(TAG, "Joining Channel " + channel + " with key " + key);
      appActivity.runOnUiThread(new Runnable() {
        @Override
        public void run() {
          Intent myIntent = new Intent(appActivity, VideoActivity.class);
          Bundle bundle = new Bundle();
          bundle.putString("channel", channel);
          bundle.putString("extraInfo", extraInfo);
          myIntent.putExtras(bundle);
          appActivity.startActivity(myIntent);
//          surfaceView = RtcEngine.CreateRendererView(appContext);
//          LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//            ViewGroup.LayoutParams.MATCH_PARENT,
//            ViewGroup.LayoutParams.MATCH_PARENT,
//            Gravity.CENTER);
//          appActivity.addContentView(surfaceView, params);
//          mRtcEngine.setupLocalVideo(new VideoCanvas(surfaceView));
//          mRtcEngine.joinChannel(key, channel, extraInfo, 0);
        }
      });
//      Context context = cordova.getActivity().getApplicationContext();
//      mRtcEngine.joinChannel(key, channel, extraInfo, 0);
      return true;
    }

    return super.execute(action, args, callbackContext);
  }
}
