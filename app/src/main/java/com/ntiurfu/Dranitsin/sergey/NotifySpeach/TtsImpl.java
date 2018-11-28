package com.ntiurfu.Dranitsin.sergey.NotifySpeach;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.Locale;

public class TtsImpl extends TtsFactory implements TextToSpeech.OnInitListener {

    private TextToSpeech tts;

    public void init(Context context) {

        if (tts == null) {
            tts = new TextToSpeech(context, this);
        }
        Locale loc = new Locale("ROOT");

        tts.setLanguage(loc);
    }

    @Override
    public void say(String sayThis) {


        tts.speak(sayThis, TextToSpeech.QUEUE_FLUSH,null);
    }


    @Override
    public void onInit(int status) {
        //Log.d("status", String.valueOf(status));
        if (status == TextToSpeech.SUCCESS) {
            Log.d("TTS","Ready");
        }


    }

    public void shutdown() {
        tts.stop();
    }}