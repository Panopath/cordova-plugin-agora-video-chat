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
    protected RtcEngine mRtcEngine;
    protected SurfaceView surfaceView;
    protected Activity appActivity;
    protected Context appContext;

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
            RtcEngineCreator.getInstance().setApplicationContext(this.cordova.getActivity().getApplicationContext());
            RtcEngineCreator.getInstance().setVendorKey(args.getString(0));
            return true;

        }

        if (action.equals("joinChannel")) {
            if (RtcEngineCreator.getInstance().getVendorKey().equals("")) {
                callbackContext.error("call setKey() first!");
                return false;
            }
            final String channel = args.getString(0);
            final String extraInfo = args.getString(1);

            Log.d(TAG, "Joining Channel " + channel);

            appActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Intent myIntent = new Intent(appActivity, VideoActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("channel", channel);
                    bundle.putString("extraInfo", extraInfo);
                    myIntent.putExtras(bundle);
                    appActivity.startActivity(myIntent);
                }
            });
            return true;
        }

        return super.execute(action, args, callbackContext);
    }
}
