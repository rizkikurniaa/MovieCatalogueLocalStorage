package com.kikulabs.moviecataloguelocalstorage.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.kikulabs.moviecataloguelocalstorage.R;
import com.kikulabs.moviecataloguelocalstorage.reminder.DailyReminder;
import com.kikulabs.moviecataloguelocalstorage.reminder.ReleaseReminder;

public class SettingsActivity extends AppCompatActivity {
    private LinearLayout llChangeLanguage;
    private DailyReminder dailyReminder;
    private ReleaseReminder releaseReminder;
    private SharedPreferences sharedpreferences;
    private String my_shared_preferences = "my_shared_preferences";
    private String daily_status = "daily_status";
    private String release_status  = "release_status";
    private SwitchCompat switchDaily, switchRelease;
    Boolean daily = false;
    Boolean release = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        dailyReminder = new DailyReminder();
        releaseReminder = new ReleaseReminder();

        llChangeLanguage = findViewById(R.id.ll_change_language);
        switchDaily = findViewById(R.id.switch_daily);
        switchRelease = findViewById(R.id.switch_release);

        llChangeLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(mIntent);
            }
        });

        sharedpreferences = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        daily = sharedpreferences.getBoolean(daily_status, false);
        release = sharedpreferences.getBoolean(release_status, false);


        if (daily) {
            switchDaily.setChecked(true);
        } else {
            switchDaily.setChecked(false);
        }
        if (release) {
            switchRelease.setChecked(true);
        } else {
            switchRelease.setChecked(false);
        }

        switchDaily.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    setDailyReminder();
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putBoolean(daily_status, isChecked);
                    editor.apply();
                } else {
                   cancelDailyReminder();
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.remove(daily_status);
                    editor.apply();
                }
            }
        });

        switchRelease.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    setDailyRelease();
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putBoolean(release_status, isChecked);
                    editor.apply();
                } else {
                    cancelDailyRelease();
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.remove(release_status);
                    editor.apply();
                }
            }
        });


    }

    private void setDailyReminder() {
        dailyReminder.setDailyReminder(this);
    }

    private void cancelDailyReminder() {
        dailyReminder.cancelReminder(this);
    }

    private void setDailyRelease() {
        releaseReminder.setReleaseReminder(this);
    }

    private void cancelDailyRelease() {
        releaseReminder.cancelReleaseNotif(this);
    }
}
