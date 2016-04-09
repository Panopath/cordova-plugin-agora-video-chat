package com.panopath.plugin.agora;

import android.content.Context;

import io.agora.rtc.RtcEngine;

public class RtcEngineCreator {
    private String vendorKey;
    private MessageHandler messageHandler;

    private Context applicationContext;
    private RtcEngine rtcEngine;


    public RtcEngineCreator() {
        messageHandler = new MessageHandler();
    }

    public Context getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    public String getVendorKey() {
        return vendorKey;
    }

    public void setVendorKey(String vendorKey) {
        this.vendorKey = vendorKey;
    }

    public void setRtcEngine(){
        if(rtcEngine==null) {
            rtcEngine = RtcEngine.create(applicationContext, getVendorKey(), messageHandler);
        }
    }

    public RtcEngine getRtcEngine(){
        return rtcEngine;
    }

    public void setEngineEventHandlerActivity(BaseEngineEventHandlerActivity engineEventHandlerActivity){
        messageHandler.setActivity(engineEventHandlerActivity);
    }

    private static final RtcEngineCreator holder = new RtcEngineCreator();

    public static RtcEngineCreator getInstance() {
        return holder;
    }

}