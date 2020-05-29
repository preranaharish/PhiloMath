package com.rahulbuilds.philomath;



import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class Quiz_start extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_start);
        Button start = (Button)findViewById(R.id.start_quiz);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Quiz_start.this,MainQuiz.class);
                startActivity(intent);
                finish();
            }
        });
        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.navigationView);
        bottomNavigationView.setSelectedItemId(R.id.nav_quiz);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        Intent intent1 = new Intent(Quiz_start.this,HomeScreen.class);
                        startActivity(intent1);
                        finish();
                        break;
                    case R.id.nav_list:
                        Intent intent = new Intent(Quiz_start.this,ListOfWords.class);
                        startActivity(intent);
                        finish();
                        break;

                    case R.id.nav_search:
                        Intent intent3 = new Intent(Quiz_start.this,add.class);
                        startActivity(intent3);
                        finish();
                        break;

                    case R.id.nav_quiz:

                        break;

                    case R.id.nav_settings:
                        Intent intent4 = new Intent(Quiz_start.this,settings_screen.class);
                        startActivity(intent4);
                        finish();
                        break;
                }
                return false;
            }
        });
    }
}
