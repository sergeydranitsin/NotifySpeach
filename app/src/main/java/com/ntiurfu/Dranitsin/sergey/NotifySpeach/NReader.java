package com.ntiurfu.Dranitsin.sergey.NotifySpeach;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.Locale;


public class NReader {
    private TextToSpeech tts;
    private boolean isLoaded = false;

    public void init(Context context){
        try {
            tts = new TextToSpeech(context, onInitListener);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private TextToSpeech.OnInitListener onInitListener = new TextToSpeech.OnInitListener() {

        @Override
        public void onInit(int status) {
            if (status == TextToSpeech.SUCCESS) {
                Locale locale = new Locale("ru");
                int result = tts.setLanguage(locale);
                isLoaded = true;

                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e("error", "Извините, этот язык не поддерживается");
                }
            } else {
                Log.e("error", "Ошибка инициализации!");
            }
        }

    };

    public void speak(String text) {
        if (isLoaded)
            tts.speak(text, TextToSpeech.QUEUE_ADD, null);
        else
            Log.e("error", "TTS не инициализирован!");
    }

    public void pause(int duration) {
        if (isLoaded)
            tts.playSilence(duration, TextToSpeech.QUEUE_ADD, null);
        else
            Log.e("error", "TTS не инициализирован!");
    }
}
