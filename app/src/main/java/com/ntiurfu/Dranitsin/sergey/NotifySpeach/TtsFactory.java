package com.ntiurfu.Dranitsin.sergey.NotifySpeach;

import android.content.Context;
import android.os.Build;


public abstract class TtsFactory {
    public abstract void say(String sayThis);

    public abstract void init(Context context);

    public abstract void shutdown();

    public abstract void onInit(int status);


    private static TtsFactory sInstance;

    public static TtsFactory getInstance() {
        if (sInstance == null) {
            int sdkVersion = Integer.parseInt(Build.VERSION.SDK);
            if (sdkVersion < Build.VERSION_CODES.DONUT) {
                return null;
            }

            try {
                String className = "TtsImpl";
                Class<? extends TtsFactory> clazz =
                        Class.forName(TtsFactory.class.getPackage().getName() + "." + className)
                                .asSubclass(TtsFactory.class);
                sInstance = clazz.newInstance();
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }
        return sInstance;

    }}
