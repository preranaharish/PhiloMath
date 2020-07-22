package com.rahuldevelops.philomathapp;



import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.rahuldevelops.philomathapp.R;

public class Quiz_start extends AppCompatActivity {
int questions;
private long backPressedTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_quiz_start);






        DBHelper quizDbHelper = new DBHelper(Quiz_start.this);
        String countQuery = "SELECT  * FROM " + "words";
        SQLiteDatabase db1 = quizDbHelper.getReadableDatabase();
        Cursor cursor1 = db1.rawQuery(countQuery, null);
        questions = cursor1.getCount();

        RCDB rcdb = new RCDB(Quiz_start.this);
        String countQuery1 = "SELECT * FROM "+" rcpassage ";
        SQLiteDatabase db2 = rcdb.getReadableDatabase();
        final Cursor c2 = db2.rawQuery(countQuery1,null);
        final int count2 = c2.getCount();
        Button RC = (Button)findViewById(R.id.RCquiz);
        RC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count2>=5){
                    Intent intent = new Intent(Quiz_start.this, RCQUIZ.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(Quiz_start.this,"No RC available, please make sure you have given storage access",Toast.LENGTH_LONG).show();
                }
            }
        });
        Button start = (Button)findViewById(R.id.start_quiz);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(questions>8){
                Intent intent = new Intent(Quiz_start.this, MainQuiz.class);
                startActivity(intent);
                finish();
                }else{
                    Toast.makeText(Quiz_start.this,"You should learn "+ (9-questions) +" more words to unlock the quiz",Toast.LENGTH_LONG).show();
                }
            }
        });
        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.navigationView);
        bottomNavigationView.setSelectedItemId(R.id.nav_quiz);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        Intent intent1 = new Intent(Quiz_start.this, HomeScreen.class);
                        startActivity(intent1);
                        finish();
                        break;
                    case R.id.nav_list:
                        Intent intent = new Intent(Quiz_start.this, ListOfWords.class);
                        startActivity(intent);
                        finish();
                        break;

                    case R.id.nav_search:
                        Intent intent3 = new Intent(Quiz_start.this, add.class);
                        startActivity(intent3);
                        finish();
                        break;

                    case R.id.nav_quiz:

                        break;

                    case R.id.nav_settings:
                        Intent intent4 = new Intent(Quiz_start.this, settings_screen.class);
                        startActivity(intent4);
                        finish();
                        break;
                }
                return false;
            }
        });
    }
    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            finishAffinity();
            finish();
        } else {
            Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();
        }

        backPressedTime = System.currentTimeMillis();
    }
}
