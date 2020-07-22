package com.rahuldevelops.philomathapp;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.rahuldevelops.philomathapp.R;

public class streak extends AppCompatActivity {
    String TAG = "RemindMe";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_streak);
    }
    public void curious(View view){
        SharedPreferences.Editor editor1 = getSharedPreferences(TAG, MODE_PRIVATE).edit();
        editor1.putInt("set", 1);
        editor1.commit();
        Log.d(TAG, "onCheckedChanged: true");
        NotificationScheduler.setReminder(streak.this, AlarmReceiver.class, 10, 30);
        Log.d(TAG, "Yes: Reminder set");
        SharedPreferences prefer = getSharedPreferences("streaks_count",MODE_PRIVATE);
        SharedPreferences.Editor editor = prefer.edit();
        editor.putInt("streaks_count",2);
        editor.commit();
        Toast.makeText(streak.this,"Regular word reminders enabled",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(streak.this, HomeScreen.class);
        startActivity(intent);
        finish();

    }

    public void avid(View view){
        SharedPreferences.Editor editor1 = getSharedPreferences(TAG, MODE_PRIVATE).edit();
        editor1.putInt("set", 1);
        editor1.commit();
        Log.d(TAG, "onCheckedChanged: true");
        NotificationScheduler.setReminder(streak.this, AlarmReceiver.class, 10, 30);
        Log.d(TAG, "Yes: Reminder set");
        SharedPreferences prefer = getSharedPreferences("streaks_count",MODE_PRIVATE);
        SharedPreferences.Editor editor = prefer.edit();
        editor.putInt("streaks_count",4);
        editor.commit();
        Toast.makeText(streak.this,"Regular word reminders enabled",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(streak.this, HomeScreen.class);
        startActivity(intent);
        finish();
    }

    public void cat(View view){
        SharedPreferences.Editor editor1 = getSharedPreferences(TAG, MODE_PRIVATE).edit();
        editor1.putInt("set", 1);
        editor1.commit();
        Log.d(TAG, "onCheckedChanged: true");
        NotificationScheduler.setReminder(streak.this, AlarmReceiver.class, 10, 30);
        Log.d(TAG, "Yes: Reminder set");
        SharedPreferences prefer = getSharedPreferences("streaks_count",MODE_PRIVATE);
        SharedPreferences.Editor editor = prefer.edit();
        editor.putInt("streaks_count",10);
        editor.commit();
        Toast.makeText(streak.this,"Regular word reminders enabled",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(streak.this, HomeScreen.class);
        startActivity(intent);
        finish();
    }

    public void gre(View view){
        SharedPreferences.Editor editor1 = getSharedPreferences(TAG, MODE_PRIVATE).edit();
        editor1.putInt("set", 1);
        editor1.commit();
        Log.d(TAG, "onCheckedChanged: true");
        NotificationScheduler.setReminder(streak.this, AlarmReceiver.class, 10, 30);
        Log.d(TAG, "Yes: Reminder set");
        SharedPreferences prefer = getSharedPreferences("streaks_count",MODE_PRIVATE);
        SharedPreferences.Editor editor = prefer.edit();
        editor.putInt("streaks_count",15);
        editor.commit();
        Toast.makeText(streak.this,"Regular word reminders enabled",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(streak.this, HomeScreen.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SharedPreferences.Editor editor1 = getSharedPreferences(TAG, MODE_PRIVATE).edit();
        editor1.putInt("set", 1);
        editor1.commit();
        Log.d(TAG, "onCheckedChanged: true");
        NotificationScheduler.setReminder(streak.this, AlarmReceiver.class, 10, 30);
        Log.d(TAG, "Yes: Reminder set");
        SharedPreferences prefer = getSharedPreferences("streaks_count",MODE_PRIVATE);
        SharedPreferences.Editor editor = prefer.edit();
        editor.putInt("streaks_count",2);
        editor.commit();
        Toast.makeText(streak.this,"Regular word reminders enabled",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(streak.this, HomeScreen.class);
        startActivity(intent);
        finish();
    }
}
