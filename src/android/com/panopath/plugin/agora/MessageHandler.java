package com.panopath.plugin.agora;

import org.json.JSONException;
import org.json.JSONStringer;

import io.agora.rtc.IRtcEngineEventHandler;

public class MessageHandler extends IRtcEngineEventHandler {

    private BaseEngineEventHandlerActivity mHandlerActivity;

    @Override
    public void onJoinChannelSuccess(String channel, int uid, int elapsed) {
        JSONStringer jsonText = new JSONStringer();

        try {
            jsonText.object();
            jsonText.key("channel");
            jsonText.value(channel);
            jsonText.key("uid");
            jsonText.value(uid);
            jsonText.key("elapsed");
            jsonText.value(elapsed);
            jsonText.endObject();
        } catch (JSONException ignored) {
        }

        Agora.notifyEvent("onJoinChannelSuccess", jsonText.toString());

        BaseEngineEventHandlerActivity activity = getActivity();

        if (activity != null) {
            activity.onJoinChannelSuccess(channel, uid, elapsed);
        }
    }

    @Override
    public void onRejoinChannelSuccess(String channel, int uid, int elapsed) {
        JSONStringer jsonText = new JSONStringer();

        try {
            jsonText.object();
            jsonText.key("channel");
            jsonText.value(channel);
            jsonText.key("uid");
            jsonText.value(uid);
            jsonText.key("elapsed");
            jsonText.value(elapsed);
            jsonText.endObject();
        } catch (JSONException ignored) {
        }

        Agora.notifyEvent("onRejoinChannelSuccess", jsonText.toString());

        BaseEngineEventHandlerActivity activity = getActivity();

        if (activity != null) {
            activity.onRejoinChannelSuccess(channel, uid, elapsed);
        }
    }

    @Override
    public void onWarning(int warn) {
        JSONStringer jsonText = new JSONStringer();

        try {
            jsonText.object();
            jsonText.key("warn");
            jsonText.value(warn);
            jsonText.endObject();
        } catch (JSONException ignored) {
        }

        Agora.notifyEvent("onWarning", jsonText.toString());

        BaseEngineEventHandlerActivity activity = getActivity();

        if (activity != null) {
            activity.onWarning(warn);
        }
    }


    @Override
    public void onApiCallExecuted(String api, int error) {
        JSONStringer jsonText = new JSONStringer();

        try {
            jsonText.object();
            jsonText.key("api");
            jsonText.value(api);
            jsonText.key("error");
            jsonText.value(error);
            jsonText.endObject();
        } catch (JSONException ignored) {
        }

        Agora.notifyEvent("onApiCallExecuted", jsonText.toString());

        BaseEngineEventHandlerActivity activity = getActivity();

        if (activity != null) {
            activity.onApiCallExecuted(api, error);
        }
    }

    @Override
    public void onCameraReady() {
        JSONStringer jsonText = new JSONStringer();

        try {
            jsonText.object();
            jsonText.endObject();
        } catch (JSONException ignored) {
        }

        Agora.notifyEvent("onCameraReady", jsonText.toString());

        BaseEngineEventHandlerActivity activity = getActivity();

        if (activity != null) {
            activity.onCameraReady();
        }
    }

    @Override
    public void onVideoStopped() {
        JSONStringer jsonText = new JSONStringer();

        try {
            jsonText.object();
            jsonText.endObject();
        } catch (JSONException ignored) {
        }

        Agora.notifyEvent("onVideoStopped", jsonText.toString());

        BaseEngineEventHandlerActivity activity = getActivity();

        if (activity != null) {
            activity.onVideoStopped();
        }
    }

    @Override
    public void onAudioQuality(int uid, int quality, short delay, short lost) {
        JSONStringer jsonText = new JSONStringer();

        try {
            jsonText.object();
            jsonText.key("uid");
            jsonText.value(uid);
            jsonText.key("quality");
            jsonText.value(quality);
            jsonText.key("delay");
            jsonText.value(delay);
            jsonText.key("lost");
            jsonText.value(lost);
            jsonText.endObject();
        } catch (JSONException ignored) {
        }

        Agora.notifyEvent("onAudioQuality", jsonText.toString());

        BaseEngineEventHandlerActivity activity = getActivity();

        if (activity != null) {
            activity.onAudioQuality(uid, quality, delay, lost);
        }
    }

    @Override
    public void onAudioVolumeIndication(IRtcEngineEventHandler.AudioVolumeInfo[] speakers, int totalVolume) {
        JSONStringer jsonText = new JSONStringer();

        try {
            jsonText.object();
            jsonText.key("speakers");
            jsonText.array();
            for (IRtcEngineEventHandler.AudioVolumeInfo speaker : speakers) {
                flattenAudioVolumeInfo(speaker, jsonText);
            }
            jsonText.value(speakers);
            jsonText.endArray();
            jsonText.key("totalVolume");
            jsonText.value(totalVolume);
            jsonText.endObject();
        } catch (JSONException ignored) {
        }

        Agora.notifyEvent("onAudioVolumeIndication", jsonText.toString());

        BaseEngineEventHandlerActivity activity = getActivity();

        if (activity != null) {
            activity.onAudioVolumeIndication(speakers, totalVolume);
        }
    }

    @Override
    public void onNetworkQuality(int quality) {
        JSONStringer jsonText = new JSONStringer();

        try {
            jsonText.object();
            jsonText.key("quality");
            jsonText.value(quality);
            jsonText.endObject();
        } catch (JSONException ignored) {
        }

        Agora.notifyEvent("onNetworkQuality", jsonText.toString());

        BaseEngineEventHandlerActivity activity = getActivity();

        if (activity != null) {
            activity.onNetworkQuality(quality);
        }
    }

    @Override
    public void onUserMuteAudio(int uid, boolean muted) {
        JSONStringer jsonText = new JSONStringer();

        try {
            jsonText.object();
            jsonText.key("uid");
            jsonText.value(uid);
            jsonText.key("muted");
            jsonText.value(muted);
            jsonText.endObject();
        } catch (JSONException ignored) {
        }

        Agora.notifyEvent("onUserMuteAudio", jsonText.toString());

        BaseEngineEventHandlerActivity activity = getActivity();

        if (activity != null) {
            activity.onUserMuteAudio(uid, muted);
        }
    }

    @Override
    public void onRemoteVideoStat(int uid, int delay, int receivedBitrate, int receivedFrameRate) {
        JSONStringer jsonText = new JSONStringer();

        try {
            jsonText.object();
            jsonText.key("uid");
            jsonText.value(uid);
            jsonText.key("delay");
            jsonText.value(delay);
            jsonText.key("receivedBitrate");
            jsonText.value(receivedBitrate);
            jsonText.key("receivedFrameRate");
            jsonText.value(receivedFrameRate);
            jsonText.endObject();
        } catch (JSONException ignored) {
        }

        Agora.notifyEvent("onRemoteVideoStat", jsonText.toString());

        BaseEngineEventHandlerActivity activity = getActivity();

        if (activity != null) {
            activity.onRemoteVideoStat(uid, delay, receivedBitrate, receivedFrameRate);
        }
    }

    @Override
    public void onLocalVideoStat(int sentBitrate, int sentFrameRate) {
        JSONStringer jsonText = new JSONStringer();

        try {
            jsonText.object();
            jsonText.key("sentBitrate");
            jsonText.value(sentBitrate);
            jsonText.key("sentFrameRate");
            jsonText.value(sentFrameRate);
            jsonText.endObject();
        } catch (JSONException ignored) {
        }

        Agora.notifyEvent("onLocalVideoStat", jsonText.toString());

        BaseEngineEventHandlerActivity activity = getActivity();

        if (activity != null) {
            activity.onLocalVideoStat(sentBitrate, sentFrameRate);
        }
    }

    @Override
    public void onFirstRemoteVideoFrame(int uid, int width, int height, int elapsed) {
        JSONStringer jsonText = new JSONStringer();

        try {
            jsonText.object();
            jsonText.key("uid");
            jsonText.value(uid);
            jsonText.key("width");
            jsonText.value(width);
            jsonText.key("height");
            jsonText.value(height);
            jsonText.key("elapsed");
            jsonText.value(elapsed);
            jsonText.endObject();
        } catch (JSONException ignored) {
        }

        Agora.notifyEvent("onFirstRemoteVideoFrame", jsonText.toString());

        BaseEngineEventHandlerActivity activity = getActivity();

        if (activity != null) {
            activity.onFirstRemoteVideoFrame(uid, width, height, elapsed);
        }
    }

    @Override
    public void onFirstLocalVideoFrame(int width, int height, int elapsed) {
        JSONStringer jsonText = new JSONStringer();

        try {
            jsonText.object();
            jsonText.key("width");
            jsonText.value(width);
            jsonText.key("height");
            jsonText.value(height);
            jsonText.key("elapsed");
            jsonText.value(elapsed);
            jsonText.endObject();
        } catch (JSONException ignored) {
        }

        Agora.notifyEvent("onFirstLocalVideoFrame", jsonText.toString());

        BaseEngineEventHandlerActivity activity = getActivity();

        if (activity != null) {
            activity.onFirstLocalVideoFrame(width, height, elapsed);
        }
    }

    @Override
    public void onConnectionInterrupted() {
        JSONStringer jsonText = new JSONStringer();

        try {
            jsonText.object();
            jsonText.endObject();
        } catch (JSONException ignored) {
        }

        Agora.notifyEvent("onConnectionInterrupted", jsonText.toString());

        BaseEngineEventHandlerActivity activity = getActivity();

        if (activity != null) {
            activity.onConnectionInterrupted();
        }
    }

    @Override
    public void onMediaEngineEvent(int code) {
        JSONStringer jsonText = new JSONStringer();

        try {
            jsonText.object();
            jsonText.key("code");
            jsonText.value(code);
            jsonText.endObject();
        } catch (JSONException ignored) {
        }

        Agora.notifyEvent("onMediaEngineEvent", jsonText.toString());

        BaseEngineEventHandlerActivity activity = getActivity();

        if (activity != null) {
            activity.onMediaEngineEvent(code);
        }
    }

    @Override
    public void onConnectionLost() {
        JSONStringer jsonText = new JSONStringer();

        try {
            jsonText.object();
            jsonText.endObject();
        } catch (JSONException ignored) {
        }

        Agora.notifyEvent("onConnectionLost", jsonText.toString());

        BaseEngineEventHandlerActivity activity = getActivity();

        if (activity != null) {
            activity.onConnectionLost();
        }
    }

    @Override
    public void onError(int err) {
        JSONStringer jsonText = new JSONStringer();

        try {
            jsonText.object();
            jsonText.key("err");
            jsonText.value(err);
            jsonText.endObject();
        } catch (JSONException ignored) {
        }

        Agora.notifyEvent("onError", jsonText.toString());

        BaseEngineEventHandlerActivity activity = getActivity();

        if (activity != null) {
            activity.onError(err);
        }
    }

    @Override
    public void onFirstRemoteVideoDecoded(int uid, int width, int height, int elapsed) {
        JSONStringer jsonText = new JSONStringer();

        try {
            jsonText.object();
            jsonText.key("uid");
            jsonText.value(uid);
            jsonText.key("width");
            jsonText.value(width);
            jsonText.key("height");
            jsonText.value(height);
            jsonText.key("elapsed");
            jsonText.value(elapsed);
            jsonText.endObject();
        } catch (JSONException ignored) {
        }

        Agora.notifyEvent("onFirstRemoteVideoDecoded", jsonText.toString());

        BaseEngineEventHandlerActivity activity = getActivity();

        if (activity != null) {
            activity.onFirstRemoteVideoDecoded(uid, width, height, elapsed);
        }
    }

    //用户进入
    @Override
    public void onUserJoined(int uid, int elapsed) {
        JSONStringer jsonText = new JSONStringer();

        try {
            jsonText.object();
            jsonText.key("uid");
            jsonText.value(uid);
            jsonText.key("elapsed");
            jsonText.value(elapsed);
            jsonText.endObject();
        } catch (JSONException ignored) {
        }

        Agora.notifyEvent("onUserJoined", jsonText.toString());

        BaseEngineEventHandlerActivity activity = getActivity();

        if (activity != null) {
            activity.onUserJoined(uid, elapsed);
        }
    }

    //用户退出
    @Override
    public void onUserOffline(int uid, int reason) {
        JSONStringer jsonText = new JSONStringer();

        try {
            jsonText.object();
            jsonText.key("uid");
            jsonText.value(uid);
            jsonText.key("reason");
            jsonText.value(reason);
            jsonText.endObject();
        } catch (JSONException ignored) {
        }

        Agora.notifyEvent("onUserOffline", jsonText.toString());

        BaseEngineEventHandlerActivity activity = getActivity();

        if (activity != null) {
            activity.onUserOffline(uid);
        }
    }

    //监听其他用户是否关闭视频
    @Override
    public void onUserMuteVideo(int uid, boolean muted) {

        JSONStringer jsonText = new JSONStringer();

        try {
            jsonText.object();
            jsonText.key("uid");
            jsonText.value(uid);
            jsonText.key("muted");
            jsonText.value(muted);
            jsonText.endObject();
        } catch (JSONException ignored) {
        }

        Agora.notifyEvent("onUserMuteVideo", jsonText.toString());

        BaseEngineEventHandlerActivity activity = getActivity();

        if (activity != null) {
            activity.onUserMuteVideo(uid, muted);
        }
    }

    //更新聊天数据
    @Override
    public void onRtcStats(RtcStats stats) {
        JSONStringer jsonText = new JSONStringer();

        try {
            jsonText.object();
            flattenRtcStats(stats, jsonText);
            jsonText.endObject();
        } catch (JSONException ignored) {
        }

        Agora.notifyEvent("onRtcStats", jsonText.toString());

        BaseEngineEventHandlerActivity activity = getActivity();

        if (activity != null) {
            activity.onUpdateSessionStats(stats);
        }
    }


    @Override
    public void onLeaveChannel(RtcStats stats) {
        JSONStringer jsonText = new JSONStringer();

        try {
            jsonText.object();
            flattenRtcStats(stats, jsonText);
            jsonText.endObject();
        } catch (JSONException ignored) {
        }

        Agora.notifyEvent("onLeaveChannel", jsonText.toString());

        BaseEngineEventHandlerActivity activity = getActivity();

        if (activity != null) {
            activity.onLeaveChannel(stats);
        }
    }


    public void setActivity(BaseEngineEventHandlerActivity activity) {
        this.mHandlerActivity = activity;
    }

    public BaseEngineEventHandlerActivity getActivity() {
        return mHandlerActivity;
    }


    private void flattenRtcStats(RtcStats stats, JSONStringer stringer) {
        try {

            stringer.key("totalDuration");
            stringer.value(stats.totalDuration);

            stringer.key("txBytes");
            stringer.value(stats.txBytes);

            stringer.key("rxBytes");
            stringer.value(stats.rxBytes);

            stringer.key("txKBitRate");
            stringer.value(stats.txKBitRate);

            stringer.key("rxKBitRate");
            stringer.value(stats.rxKBitRate);

            stringer.key("lastmileQuality");
            stringer.value(stats.lastmileQuality);

            stringer.key("users");
            stringer.value(stats.users);

            stringer.key("cpuTotalUsage");
            stringer.value(stats.cpuTotalUsage);

            stringer.key("cpuAppUsage");
            stringer.value(stats.cpuAppUsage);
        } catch (JSONException ignored) {

        }
    }

    private void flattenAudioVolumeInfo(AudioVolumeInfo audioVolumeInfo, JSONStringer stringer) {
        try {
            stringer.key("uid");
            stringer.value(audioVolumeInfo.uid);

            stringer.key("volume");
            stringer.value(audioVolumeInfo.volume);
        } catch (JSONException ignored) {

        }
    }
}
