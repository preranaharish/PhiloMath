package com.rahuldevelops.philomathapp;

import android.app.ActivityOptions;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;
import androidx.appcompat.app.AppCompatActivity;

import android.transition.Explode;
import android.util.Log;
import android.util.Pair;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.rahuldevelops.philomathapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import static com.rahuldevelops.philomathapp.NotificationScheduler.DAILY_REMINDER_REQUEST_CODE;


public class HomeScreen extends AppCompatActivity {
    TextView words[]=new TextView[4];
    String myurl;
    String def;
    CharSequence text;
    String synonyms;
    String def1;
    int importance;
    String word;
    ScrollView scrollView;
    private long backPressedTime;
    String word1,word2,word3,word4;
    int catimportance=0,greimportance=0;
    BottomNavigationView bottomNavigationView;
    TextView p1[]=new TextView[4];
    TextView c[]=new TextView[4];
    TextView e1[]=new TextView[4];
    TextView d1[]=new TextView[4];
    String appversion,version;
    CardView cv;
    String graph_words[] = new String[4];
    String graph_score[] = new String[4];
    String[] synonyms_array = new String[4];
    int graph=1,pie=1;
    ArrayList<String> arr=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Attach a callback used to capture the shared elements from this Activity to be used
        // by the container transform transition


        // Keep system bars (status bar, navigation bar) persistent throughout the transition.

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

//        FloatingActionButton fl =(FloatingActionButton)findViewById(R.id.skip);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigationView);
        words[0] = (TextView) findViewById(R.id.w1);
        words[1] = (TextView) findViewById(R.id.w2);
        words[2] = (TextView) findViewById(R.id.w3);
        words[3] = (TextView) findViewById(R.id.w4);
        p1[0]=(TextView)findViewById(R.id.phonetic1);
        p1[1]=(TextView)findViewById(R.id.phonetic2);
        p1[2]=(TextView)findViewById(R.id.phonetic3);
        p1[3]=(TextView)findViewById(R.id.phonetic4);
        c[0]=(TextView)findViewById(R.id.category1);
        c[1]=(TextView)findViewById(R.id.category2);
        c[2]=(TextView)findViewById(R.id.category3);
        c[3]=(TextView)findViewById(R.id.category4);
        d1[0]=(TextView)findViewById(R.id.meaning1);
        d1[1]=(TextView)findViewById(R.id.meaning2);
        d1[2]=(TextView)findViewById(R.id.meaning3);
        d1[3]=(TextView)findViewById(R.id.meaning4);
        e1[0]=(TextView)findViewById(R.id.example1);
        e1[1]=(TextView)findViewById(R.id.example2);
        e1[2]=(TextView)findViewById(R.id.example3);
        e1[3]=(TextView)findViewById(R.id.example4);

        try {
            PackageInfo pInfo = HomeScreen.this.getPackageManager().getPackageInfo(getPackageName(), 0);
            appversion = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
version=appversion;

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("AppVersion");

// Attach a listener to read the data at our posts reference
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
              version = (String) dataSnapshot.child("version").getValue();

                if(!version.equals(appversion)){

                Context context = HomeScreen.this;
                Class cls = HomeScreen.class;

                    String appId = HomeScreen.this.getPackageName();
                    Intent rateIntent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("market://details?id=" + appId));

                    Intent notificationIntent = new Intent(context, cls);
                    notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
                    stackBuilder.addParentStack(cls);
                    stackBuilder.addNextIntent(rateIntent);

                    PendingIntent pendingIntent = stackBuilder.getPendingIntent(DAILY_REMINDER_REQUEST_CODE, PendingIntent.FLAG_UPDATE_CURRENT);

                    NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

                    Notification notification = builder.setContentTitle("App needs an update")
                            .setContentText("Please update the app for best app experience")

                            .setAutoCancel(true)
                            .setStyle(new NotificationCompat.BigTextStyle().bigText("Please update the Philomath app for best learning experience"))
                            .setSmallIcon(R.drawable.icon)
                            .setContentIntent(pendingIntent).build();

                    NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        String channelId = "channel_id";
                        NotificationChannel channel = new NotificationChannel(channelId, "channel name", NotificationManager.IMPORTANCE_HIGH);
                        channel.enableVibration(true);
                        channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                        notificationManager.createNotificationChannel(channel);
                        builder.setChannelId(channelId);
                    }

                    notification = builder.build();
                    notificationManager.notify(1088, notification);

                }



                                  }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        SharedPreferences preferences6 = getSharedPreferences("wordsshared",MODE_PRIVATE);
        String wordsshared = preferences6.getString("wordsshared","instigate,,allure,,Impetuous,,Plethora,,");
        String[] wordsoftheday = wordsshared.split(",,");


        HotwordsDB db = new HotwordsDB(this);
        SQLiteDatabase userList = db.getReadableDatabase();
        Cursor c1 = userList.rawQuery("SELECT * FROM " + "hotwords" + " ORDER by "+ "id" +" DESC LIMIT 4;" , null);
        if (c1 != null && c1.getCount() != 0) {
            int i=0;
            while (c1.moveToNext()) {
                if(i==4)
                    break;

                words[i].setText(c1.getString(c1.getColumnIndex("word")).trim());
                d1[i].setText(c1.getString(c1.getColumnIndex("Meaning")).trim());
                c[i].setText(c1.getString(c1.getColumnIndex("category")).trim());
                e1[i].setText(c1.getString(c1.getColumnIndex("examples")).trim());
                p1[i].setText("/"+c1.getString(c1.getColumnIndex("phonetics")).trim()+"/");
                i++;

            }}


        bottomNavigationView.setSelectedItemId(R.id.nav_home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        //Add your action onClick
                        break;
                    case R.id.nav_list:
                        ActivityOptions option = ActivityOptions.makeSceneTransitionAnimation(HomeScreen.this);
                        Intent intent = new Intent(HomeScreen.this, ListOfWords.class);
                        startActivity(intent,option.toBundle());
                        break;

                    case R.id.nav_search:
                        ActivityOptions option1 = ActivityOptions.makeSceneTransitionAnimation(HomeScreen.this);
                        Intent intent1 = new Intent(HomeScreen.this, add.class);
                        intent1.putExtra("words",arr);
                        startActivity(intent1,option1.toBundle());
                        break;

                    case R.id.nav_quiz:
                        ActivityOptions option2 = ActivityOptions.makeSceneTransitionAnimation(HomeScreen.this);
                        Intent intent3 = new Intent(HomeScreen.this, Quiz_start.class);
                        startActivity(intent3,option2.toBundle());
                        break;

                    case R.id.nav_settings:
                        ActivityOptions option3 = ActivityOptions.makeSceneTransitionAnimation(HomeScreen.this);
                        Intent intent4 = new Intent(HomeScreen.this, settings_screen.class);
                        startActivity(intent4,option3.toBundle());
                        break;
                }
                return false;
            }
        });
        scrollView = (ScrollView) findViewById(R.id.scrollview1);
        scrollView.getViewTreeObserver()
                .addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                    @Override
                    public void onScrollChanged() {
                        if (scrollView.getChildAt(0).getBottom()
                                <= (scrollView.getHeight() + scrollView.getScrollY())) {
                            bottomNavigationView.setVisibility(View.INVISIBLE);
                        } else {
                            //scroll view is not at bottom
                            bottomNavigationView.setVisibility(View.VISIBLE);
                        }
                    }
                });
        SharedPreferences sharedPreferences = getSharedPreferences("Streaks", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = sharedPreferences.edit();
        Calendar c = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            c = Calendar.getInstance();


            int thisDay = c.get(Calendar.DAY_OF_YEAR); // GET THE CURRENT DAY OF THE YEAR

            int lastDay = sharedPreferences.getInt("datekey", 0); //If we don't have a saved value, use 0.

            int counterOfConsecutiveDays = sharedPreferences.getInt("datecounter", 0); //If we don't have a saved value, use 0.

            if (lastDay == thisDay - 1) {
                // CONSECUTIVE DAYS
                counterOfConsecutiveDays = counterOfConsecutiveDays + 1;

                prefEditor.putInt("datekey", thisDay);

                prefEditor.putInt("datecounter", counterOfConsecutiveDays);
                prefEditor.commit();
            } else {

                prefEditor.putInt("datekey", thisDay);

                prefEditor.putInt("datecounter", 1).commit();
            }
        }
//        fl.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(HomeScreen.this,ListOfWords.class);
//                startActivity(intent);
//                finish();
//            }
//        });

//       w1.setText("Ostentatious");
//       w2.setText("Sacrosanct");
//       w3.setText("Ostensible");
//       w4.setText("Catastrophic");
//        Button button = (Button)findViewById(R.id.skip);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Skip(v);
//            }
//        });



    }

    public void Skip(View view) {

    }

    public void card1(View view) {
        word = words[0].getText().toString();
        if (word.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Check Internet Connection", Toast.LENGTH_LONG).show();
        } else {
            try {
                new HomeScreen.CallbackTask2().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,wordcompare(word));
                new HomeScreen.CallbackTask1().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,wordimportance(word));
            }catch (Exception e){
                Toast.makeText(getApplicationContext(),"Can't get graph data",Toast.LENGTH_LONG).show();
            }

        }
    }

    public void card2(View view) {
        word = words[1].getText().toString();
        if (word.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Check Internet Connection", Toast.LENGTH_LONG).show();
        } else {
            try {
                new HomeScreen.CallbackTask2().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,wordcompare(word));
                new HomeScreen.CallbackTask1().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,wordimportance(word));
            }catch (Exception e){
                Toast.makeText(getApplicationContext(),"Can't get graph data",Toast.LENGTH_LONG).show();
            }


        }
    }

    public void card3(View view) {
        word = words[2].getText().toString();
        if (word.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Check Internet Connection", Toast.LENGTH_LONG).show();
        } else {
            try {
                new HomeScreen.CallbackTask2().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,wordcompare(word));
                new HomeScreen.CallbackTask1().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,wordimportance(word));
            }catch (Exception e){
                Toast.makeText(getApplicationContext(),"Can't get graph data",Toast.LENGTH_LONG).show();
            }
        }
    }

    public void card4(View view) {
        word = words[3].getText().toString();
        if (word.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Check Internet Connection", Toast.LENGTH_LONG).show();
        } else {
                new HomeScreen.CallbackTask2().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,wordcompare(word));
                new HomeScreen.CallbackTask1().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,wordimportance(word));

        }
    }

    public String dictionaryEntries(String word) {
        final String language = "en-gb";
        final String word_id = word.toLowerCase();
        return "https://od-api.oxforddictionaries.com:443/api/v2/entries/" + language + "/" + word_id;
    }

    public String wordimportance(String word1) {
        return "https://philomathapp.cf/words/exam?name=" + word1;
    }
    public String wordcompare(String word){
        final String language = "en-gb";
        final String word_id = word.toLowerCase();
        return "https://api.datamuse.com/words?ml="+word_id+"&max=4";
    }


    public class CallbackTask extends AsyncTask<String, Integer, String> {
        ProgressDialog dialog = new ProgressDialog(HomeScreen.this);
        protected void onPreExecute() {

            super.onPreExecute();
            if (!HomeScreen.this.isFinishing()){

                dialog.setMessage("Retrieving statistics for "+word+" from Philomath servers...");
                dialog.show();
            }}

        @Override
        protected String doInBackground(String... params) {
            myurl = params[0];
            //TODO: replace with your own app id and app key
            final String app_id = "7c39cce3";
            final String app_key = "5a5aae6addef9d704c567a8c152211f7";
            try {

                URL url = new URL(myurl);
                HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
                urlConnection.setRequestProperty("Accept", "application/json");
                urlConnection.setRequestProperty("app_id", app_id);
                urlConnection.setRequestProperty("app_key", app_key);

                // read the output from the server
                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }

                return stringBuilder.toString();

            } catch (Exception e) {
                e.printStackTrace();
                return e.toString();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                result.trim();
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("results");
                JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                JSONArray js1 = jsonObject1.getJSONArray("lexicalEntries");
                JSONObject jsonObject2 = js1.getJSONObject(0);
                JSONArray js2 = jsonObject2.getJSONArray("entries");
                JSONObject jsonObject3 = js2.getJSONObject(0);
                JSONArray js3 = jsonObject3.getJSONArray("senses");
                JSONObject jsonObject4 = js3.getJSONObject(0);
                JSONArray js4 = jsonObject4.getJSONArray("definitions");
                def = js4.getString(0);
                try {
                    JSONObject jsonObject5 = js3.getJSONObject(0);
                    JSONArray js5 = jsonObject5.getJSONArray("examples");
                    JSONObject jsonObject6 = js5.getJSONObject(0);
                    def1 = jsonObject6.getString("text");
                    JSONArray js6 = jsonObject5.getJSONArray("synonyms");
                    try {
                        JSONObject jsonObject7 = js6.getJSONObject(0);
                        JSONObject jsonObject8 = js6.getJSONObject(1);
                        JSONObject jsonObject9 = js6.getJSONObject(2);
                        JSONObject jsonObject10 = js6.getJSONObject(3);
                        synonyms = jsonObject7.getString("text");
                        synonyms_array[0] = jsonObject7.getString("text");
                        synonyms += ", " + "\t" + jsonObject8.getString("text");
                        synonyms_array[1] = jsonObject8.getString("text");
                        synonyms += ", " + "\n" + jsonObject9.getString("text");
                        synonyms_array[2] = jsonObject9.getString("text");
                        synonyms += ", " + "\t" + jsonObject10.getString("text");
                        synonyms_array[3] = jsonObject10.getString("text");

                    } catch (Exception j) {

                        for (int i = 0; i < 4; i++) {
                            synonyms_array[i] = "Not found";
                        }
                    }

                } catch (Exception i) {

                }
                if (def1 == null) {
                    def1 = "Examples not found";
                }
                if (synonyms == null) {
                    synonyms = "synonyms not found";
                }
                SimpleDateFormat simpleDate = new SimpleDateFormat("dd/MM/yyyy");
                java.util.Calendar cal = java.util.Calendar.getInstance();
                String strDt = simpleDate.format(cal.DATE);
                strDt = strDt.replaceAll("/", "");
                SharedPreferences prefs = getSharedPreferences(strDt, MODE_PRIVATE);
                int noofwordstoday = prefs.getInt(strDt, 0);
                noofwordstoday += 1;
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt(strDt, noofwordstoday);
                editor.commit();
                Intent intent = new Intent(HomeScreen.this, Word_Result.class);
                intent.putExtra("word", word);
                intent.putExtra("meaning", def);
                intent.putExtra("example", def1);
                intent.putExtra("synonyms", synonyms);
                intent.putExtra("visibility", 1);
                intent.putExtra("synonyms_array1", synonyms_array[0]);
                intent.putExtra("synonyms_array2", synonyms_array[1]);
                intent.putExtra("synonyms_array3", synonyms_array[2]);
                intent.putExtra("synonyms_array4", synonyms_array[3]);
                intent.putExtra("cat",catimportance);
                intent.putExtra("pie",pie);
                intent.putExtra("cat",catimportance);
                intent.putExtra("gre",greimportance);
                intent.putExtra("graph",graph);
                intent.putExtra("graph_words",graph_words);
                intent.putExtra("graph_score",graph_score);
                startActivity(intent);
                finish();

            } catch (JSONException e) {
                e.printStackTrace();
                dialog.dismiss();
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
            System.out.println(result);
        }
    }

    public class CallbackTask1 extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            myurl = params[0];
            //TODO: replace with your own app id and app key
            try {

                URL url = new URL(myurl);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestProperty("Accept", "application/json");
                // read the output from the server
                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }

                return stringBuilder.toString();

            } catch (Exception e) {
                e.printStackTrace();
                return e.toString();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            new HomeScreen.CallbackTask().execute(dictionaryEntries(word));
            super.onPostExecute(result);
            try {
                Log.d("api result:", result);
                result.trim();
                JSONObject jsonObject = new JSONObject(result);
                String total = jsonObject.getString("total");
                JSONObject jsonObj = new JSONObject(total);
                String cat = jsonObj.getString("CAT");
                String gre = jsonObj.getString("GRE");
                float catscore = Float.parseFloat(cat);
                float grescore = Float.parseFloat(gre);
                catscore=catscore*100;
                grescore=grescore*100;
                catimportance = (int)catscore;
                greimportance = (int)grescore;
            }catch(Exception e){
                pie=0;
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),"Philomath servers unreachable",Toast.LENGTH_SHORT).show();
            }
            System.out.println(result);
        }
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

    public class CallbackTask2 extends AsyncTask<String, Integer, String> {
        private Gson gson = new Gson();
ProgressDialog dialog2 = new ProgressDialog(HomeScreen.this);

        protected void onPreExecute() {

            super.onPreExecute();
            dialog2.setMessage("Searching Oxford Dictionary...");
            dialog2.show();

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
                if(dialog2.isShowing())
                    dialog2.dismiss();
                e.printStackTrace();
                return e.toString();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if(dialog2.isShowing())
                dialog2.dismiss();
            super.onPostExecute(result);
            try{
                Log.d("api result:",result);
                result.trim();
                JSONArray js = new JSONArray(result);
                for(int i=0;i<js.length();i++) {

                    JSONObject js1 = (JSONObject) js.get(i);
                    graph_words[i]=js1.getString("word");
                    graph_score[i]=js1.getString("score");
                }
            }catch(Exception e){
                graph=0;
                e.printStackTrace();

            }


        }
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
