package com.rahulbuilds.philomath;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

public class streak extends AppCompatActivity {

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
