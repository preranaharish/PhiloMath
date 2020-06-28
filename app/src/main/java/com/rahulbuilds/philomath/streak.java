package com.rahulbuilds.philomath;


import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

public class streak extends AppCompatActivity {
    String TAG = "RemindMe";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_streak);
        final RadioButton five = (RadioButton)findViewById(R.id.five_words);
       final RadioButton ten = (RadioButton)findViewById(R.id.ten_words);
       final RadioButton fifteen = (RadioButton)findViewById(R.id.fifteen_words);
       final Button save = (Button)findViewById(R.id.streakcontinue);

        save.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               SharedPreferences.Editor editor1 = getSharedPreferences(TAG, MODE_PRIVATE).edit();
               editor1.putInt("set", 1);
               editor1.commit();
               Log.d(TAG, "onCheckedChanged: true");
               NotificationScheduler.setReminder(streak.this, AlarmReceiver.class, 10, 30);
               Log.d(TAG, "Yes: Reminder set");
               if(five.isChecked()){
                   SharedPreferences prefer = getSharedPreferences("streaks_count",MODE_PRIVATE);
                   SharedPreferences.Editor editor = prefer.edit();
                   editor.putInt("streaks_count",5);
                   editor.commit();
               }
               if(ten.isChecked()){
                   SharedPreferences prefer = getSharedPreferences("streaks_count",MODE_PRIVATE);
                   SharedPreferences.Editor editor = prefer.edit();
                   editor.putInt("streaks_count",10);
                   editor.commit();
               }
               if(fifteen.isChecked()){
                   SharedPreferences prefer = getSharedPreferences("streaks_count",MODE_PRIVATE);
                   SharedPreferences.Editor editor = prefer.edit();
                   editor.putInt("streaks_count",15);
                   editor.commit();
               }
               Intent intent = new Intent(streak.this,HomeScreen.class);
               startActivity(intent);
               finish();

           }
       });
    }

}
