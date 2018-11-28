package com.ntiurfu.Dranitsin.sergey.NotifySpeach;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;


public class MainActivity extends AppCompatActivity {


    public PackageManager packageManager;
    public RecyclerView mListView;
    public Context context;
    public static boolean isAccess;
    public View parentView;
    public TtsFactory TtsImpl;
    public ProgressBar progressBar;
    public SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progressBar);
        parentView = findViewById(R.id.constraint);

        TtsImpl= TtsFactory.getInstance();
        TtsImpl.onInit(0);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            boolean weHaveNotificationListenerPermission = false;
            for (String service : NotificationManagerCompat.getEnabledListenerPackages(this)) {
                if (service.equals(getPackageName())) {
                    weHaveNotificationListenerPermission = true;
                }
            }
            if (!weHaveNotificationListenerPermission) {        //ask for permission
                requestPermissionWithRationale();
            }
        }

        mListView = findViewById(R.id.list);
        context = getApplicationContext();
        //Log.d("context",context.toString());
        packageManager = context.getPackageManager();

        mListView.setLayoutManager(new LinearLayoutManager(context));

        AppListAdapter mAdapter = new AppListAdapter(context);
        mListView.setAdapter(mAdapter);

        new GetAppsTask(context, mListView, this).execute();


        SharedPreferences sharedPref = getSharedPreferences("Access_to_speach",0);
        isAccess = sharedPref.getBoolean("isAccess",true);

        //tts.onInit(0);
    }
    @Override
    public void onResume() {

        super.onResume();
        SharedPreferences sharedPref = getSharedPreferences("Access_to_speach",0);
        isAccess = sharedPref.getBoolean("isAccess",true);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_menu, menu);


        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
//            case R.id.access:
//                startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
//                return true;

            case  R.id.settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void requestPermissionWithRationale() {
        if (!ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.BIND_NOTIFICATION_LISTENER_SERVICE)) {
            final String message = "Предоставьте доступ к уведомлениям";
            Snackbar.make(parentView, message, Snackbar.LENGTH_INDEFINITE)
                    .setAction("ОТКРЫТЬ", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
                        }
                    })
                    .show();
        }
    }

}

