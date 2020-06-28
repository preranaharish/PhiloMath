package com.rahulbuilds.philomath;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.rahulbuilds.philomath.HotwordsDB;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class splash extends AppCompatActivity {
    static DataSnapshot[] data;
    String json;
    Long lock;
    String words[] = new String[1000000];
    int lock1;
    int progress = 75;
    boolean connected = true;
    String myurl;
    ArrayList<String>arr=new ArrayList<>();
    public String questions[] = new String[5];
    public String options[][]= new String[5][3];
    public int answers[]= new int[5];
    Question1 q[] = new Question1[5];
    Intent intent;
    String word1,word2,word3,word4;
    public static DataSnapshot[] getInstance(){
        return data;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new CallbackTask2().execute(wordoftheday());
//        QuizDbHelper quizDbHelper = new QuizDbHelper(splash.this);
//        String countQuery = "SELECT  * FROM " + "quiz_questions";
//        SQLiteDatabase db1 = quizDbHelper.getReadableDatabase();
//        Cursor cursor1 = db1.rawQuery(countQuery, null);
//        int count = cursor1.getCount();
        SharedPreferences prefs2 = getSharedPreferences("words_file", MODE_PRIVATE);
        int filewritten = prefs2.getInt("words_file",0);
        if(filewritten==0){
        if(isStoragePermissionGranted()){
        try {
            Scanner scan = new Scanner(getAssets().open("words.txt"));
            int i=0;

            // scan through file to make sure that it holds the text
            // we think it does, and that scanner works.
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                line=line.trim();
                arr.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            save("wordsauto.txt");
            SharedPreferences prefs1 = getSharedPreferences("words_file", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs1.edit();
            editor.putInt("words_file",1);
            editor.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }}}
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
                    try {
                        MySingleton.arr=read("wordsauto.txt");
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                  Thread.sleep(100);
                    startActivity(intent);
                    finish();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }}
        }).start();

    }
    public String wordoftheday(){
        return "https://philomathapp.cf/wordoftheday/get";
    }
    public void save(String fileName) throws IOException {
        File path = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_MOVIES);
        File file = new File(path, "/" + fileName);
        FileOutputStream fout= new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fout);
        oos.writeObject(arr);
        fout.close();
    }
    public ArrayList<String> read(String fileName) throws IOException, ClassNotFoundException {
        File path = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_MOVIES);
        File file = new File(path, "/" + fileName);
        FileInputStream fin= new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fin);
        arr= (ArrayList<String>)ois.readObject();
        fin.close();
        return arr;
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
    public class CallbackTask2 extends AsyncTask<String, Integer, String> {



        protected void onPreExecute() {

            super.onPreExecute();

        }
        @Override
        protected String doInBackground(String... params) {
            myurl=params[0];
            //TODO: replace with your own app id and app key
            try {

                URL url = new URL(myurl);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("Accept","application/json");

                // read the output from the server
                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }

                return stringBuilder.toString();

            }
            catch (Exception e) {
                e.printStackTrace();
                return e.toString();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try{
                HotwordsDB hb = new HotwordsDB(splash.this);
                JSONObject JS=new JSONObject(result);
                Iterator<String> keys = JS.keys();
                while(keys.hasNext()){
                String value = keys.next();
                String value1 = JS.getString(value);
                JSONObject JS1 = new JSONObject(value1);
                String meaning = JS1.getString("definition");
                meaning=meaning.trim().replace('[',' ').replace(']',' ').replaceAll("\""," ");
                String category = JS1.getString("category");
                hb.insertWordDetails(value,meaning,category);
                }
hb.close();
            }catch(Exception e){
                e.printStackTrace();

            }


        }
    }
}
