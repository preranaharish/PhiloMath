package com.rahulbuilds.philomath;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.RequiresPermission;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Logger;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import static com.rahulbuilds.philomath.SignIn.SHARED_PREFS;

public class splash extends AppCompatActivity {
    static DataSnapshot[] data;
    String json;
    Long lock;
    String words[] = new String[1000000];
    int lock1;
    int progress = 75;
    boolean connected = true;
    public String questions[] = new String[5];
    public String options[][]= new String[5][3];
    public int answers[]= new int[5];
    Question1 q[] = new Question1[5];
    Intent intent;
    public static DataSnapshot[] getInstance(){
        return data;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
//        QuizDbHelper quizDbHelper = new QuizDbHelper(splash.this);
//        String countQuery = "SELECT  * FROM " + "quiz_questions";
//        SQLiteDatabase db1 = quizDbHelper.getReadableDatabase();
//        Cursor cursor1 = db1.rawQuery(countQuery, null);
//        int count = cursor1.getCount();
        SharedPreferences prefer = getSharedPreferences("streaks_count",MODE_PRIVATE);
        int count = prefer.getInt("streaks_count",0);
        if (count == 0){
            intent = new Intent(splash.this,streak.class);
        }
        else{
            intent = new Intent(splash.this,HomeScreen.class);
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                  Thread.sleep(1500);
                    startActivity(intent);
                    finish();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }}
        }).start();

    }
}
