package com.rahuldevelops.philomathapp;


import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

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
        isStoragePermissionGranted();
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
    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            //resume tasks needing this permission
        }
    }
}
