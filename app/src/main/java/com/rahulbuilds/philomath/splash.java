package com.rahulbuilds.philomath;

import android.content.Intent;
import android.content.SharedPreferences;
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

import java.util.List;

public class splash extends AppCompatActivity {
    static DataSnapshot[] data;
    String json;
    Long lock;
    int lock1;
    int progress = 75;
    boolean connected = true;
    public static DataSnapshot[] getInstance(){
        return data;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true); //keeping a local copy
        FirebaseDatabase.getInstance().setLogLevel(Logger.Level.DEBUG);

        FirebaseDatabase instance2 = FirebaseDatabase.getInstance();
        DatabaseReference ref2 = instance2.getReference();
// Attach a listener to read the data at our posts reference
        ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long version = (Long) dataSnapshot.child("lockerversion").getValue(Long.TYPE);
                MySingleton.lock = version.intValue();
                MySingleton.msg = (String) dataSnapshot.child("message").getValue();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });











        FirebaseDatabase instance1 = FirebaseDatabase.getInstance();
        DatabaseReference ref1 = instance1.getReference();
// Attach a listener to read the data at our posts reference
        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long version = (Long) dataSnapshot.child("databaseversion").getValue();
                MySingleton.databaseversion = version.intValue();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        FirebaseDatabase instance = FirebaseDatabase.getInstance();
        DatabaseReference ref = instance.getReference();
        final GenericTypeIndicator<List<holder>> t = new GenericTypeIndicator<List<holder>>() {
        };
        final DatabaseReference endpoint = ref.getRoot();
        final DataSnapshot[] data = new DataSnapshot[1];
        endpoint.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                connected = false;
                json = new Gson().toJson(dataSnapshot.getValue());
                String base = (String) dataSnapshot.child("base").getValue();
                MySingleton.setUrl(base);
                SharedPreferences.Editor editor = getSharedPreferences("test", MODE_PRIVATE).edit();
                SharedPreferences.Editor prevEdit = getSharedPreferences("prev", MODE_PRIVATE).edit();
                SharedPreferences extract = getSharedPreferences("test",MODE_PRIVATE);
                prevEdit.putString("Data",extract.getString("Data",null));
                editor.putString("Data",json);
                editor.putString("Url",base);
                editor.apply();
                prevEdit.apply();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1500);
                    Intent intent = new Intent(splash.this,MainQuiz.class);
                    startActivity(intent);
                    finish();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
