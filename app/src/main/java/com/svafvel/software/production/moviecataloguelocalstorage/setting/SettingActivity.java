package com.svafvel.software.production.moviecataloguelocalstorage.setting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.svafvel.software.production.moviecataloguelocalstorage.R;

public class SettingActivity extends AppCompatActivity {

    //View

    Switch releaseReminder, dailyReminder;
    Button btnChangeLanguage;


    //Save
    public static final String SHARED_PREFS = "sharedPrefs";

    public static final String DAILY_REMINDER = "dailyReminder";
    public static final String RELEASE_REMINDER = "releaseReminder";


    private boolean isDaily, isRelease;

    //

    NotificationReceiver notificationReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);


        if(getSupportActionBar() != null){

            getSupportActionBar().setTitle(getResources().getString(R.string.setting));
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }


        notificationReceiver = new NotificationReceiver(this);

        releaseReminder = findViewById(R.id.releaseReminderSwitch);
        dailyReminder = findViewById(R.id.dailyReminderSwitch);
        btnChangeLanguage = findViewById(R.id.changeLanguageSettings);

        releaseReminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b){

                    notificationReceiver.setReleaseTodayReminder();
                    saveData();

                }else {

                    notificationReceiver.cancelReleaseToday(getApplicationContext());
                    saveData();

                }

            }
        });

        dailyReminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b){

                    notificationReceiver.setDailyReminder();
                    saveData();

                }else {

                    notificationReceiver.cancelDailyReminder(getApplicationContext());
                    saveData();

                }

            }
        });



        btnChangeLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent settingIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(settingIntent);

            }
        });


        loadData();
        updateData();

    }


    public void saveData(){

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(DAILY_REMINDER, dailyReminder.isChecked());
        editor.putBoolean(RELEASE_REMINDER, releaseReminder.isChecked());

        editor.apply();

    }

    public void loadData(){

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        isDaily = sharedPreferences.getBoolean(DAILY_REMINDER, false);
        isRelease = sharedPreferences.getBoolean(RELEASE_REMINDER, false);
    }


    public void updateData(){

        dailyReminder.setChecked(isDaily);
        releaseReminder.setChecked(isRelease);

    }




}
