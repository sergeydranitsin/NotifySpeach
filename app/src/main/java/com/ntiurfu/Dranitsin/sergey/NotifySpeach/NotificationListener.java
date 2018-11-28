package com.ntiurfu.Dranitsin.sergey.NotifySpeach;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.support.annotation.RequiresApi;
import android.support.v4.content.LocalBroadcastManager;

import java.util.HashSet;
import java.util.Set;


public class NotificationListener extends NotificationListenerService{

   public static Context context;
   public TtsFactory ttsImpl;
    public ExtendAppInfo info;


    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {

        final String packageName = sbn.getPackageName();
        Bundle extras = sbn.getNotification().extras;
        String title = extras.getString("android.title");
        String text = extras.getString("android.text");

        //String name = extras.getString("android.name");

//        Log.i("Package", packageName);
//        Log.i("Title", title);




        Intent MyIntent = new Intent("Msg");
        MyIntent.putExtra("package", packageName);
        MyIntent.putExtra("title", title);
        MyIntent.putExtra("text", text);

        Context context = getApplicationContext();
        ttsImpl = TtsFactory.getInstance();


        SharedPreferences sharedPreferences = context.getSharedPreferences("Apps",0);
        Set<String> app_set  = sharedPreferences.getStringSet("app",new HashSet<String>());
        SharedPreferences sharedPref = getSharedPreferences("Access_to_speach",0);


        if (ttsImpl != null && MainActivity.isAccess && app_set.contains(packageName)) {
            ttsImpl.onInit(0);
            ttsImpl.init(context);
            text = get_charSiquence(SettingsActivity.selected,text);
            ttsImpl.say("Уведомление от"+getApplicationName(packageName)+". Он говорит "+title+". "+text);


            if (sbn.isClearable()) {
                LocalBroadcastManager.getInstance(context).sendBroadcast(MyIntent);
            }
        }
    }
    public String get_charSiquence(int selected, String text){
        if(selected == 0 ){
            return text;
        }
        else {
            int sel = selected*10;
            if(sel>text.length())
                return text;
            text = text.substring(0,sel);
            return text;
        }
    }
    public void onNotificationRemoved(StatusBarNotification sbn){
        ttsImpl.shutdown();
        //Log.i("MSG","Уведомление удалено");
    }
    public String getApplicationName(String packageName) {

        PackageManager packageManager = getApplicationContext().getPackageManager();
        String appName = null;
        try {
            appName = (String) packageManager.getApplicationLabel(packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return " ";
        }
        return appName;
    }
}

