package com.ntiurfu.Dranitsin.sergey.NotifySpeach;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;

import static android.content.SharedPreferences.Editor;

public class SettingsActivity extends AppCompatActivity {

    public static int selected;
    public static Switch mSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_settings);
        mSwitch = findViewById(R.id.switch1);
        final Spinner spinner = findViewById(R.id.spinner);

        SharedPreferences sharedPreferences = getSharedPreferences("Spinner", 0);
        int select = sharedPreferences.getInt("spinn",0);
        spinner.setSelection(select);
        selected = spinner.getSelectedItemPosition();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences preferences = getSharedPreferences("Spinner",0);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("spinn",position);
                editor.apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





        mSwitch.setChecked(MainActivity.isAccess);
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                //SharedPreferences sharedPref = getSharedPreferences("Access_to_speach",0);
                //boolean isAccess = sharedPref.getBoolean("isAccess",true);

                SharedPreferences sharedPref = getSharedPreferences("Access_to_speach",0);
                Editor editor = sharedPref.edit();
                editor.putBoolean("isAccess", isChecked);
                editor.apply();

            }
        });




    }

}
