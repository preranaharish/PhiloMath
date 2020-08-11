package com.rahuldevelops.philomathapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

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
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.util.Pair;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

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
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import static com.rahuldevelops.philomathapp.NotificationScheduler.DAILY_REMINDER_REQUEST_CODE;

public class HomeScreenNew extends AppCompatActivity {
static ViewPager viewPager;
static Adapter adapter;
List<Model> model;
static int position1=0;
    String myurl;
    String def;
    String appversion,version;
    CharSequence text;
    String synonyms;
    static String graph_words[] = new String[4];
    static String graph_score[] = new String[4];
    static String[] synonyms_array = new String[4];
    int graph=1,pie=1;
    static String def1;
    int importance;
    static String word;
    private long backPressedTime;
    int catimportance=0,greimportance=0;
String words[]=new String[4];
    String p1[]=new String[4];
    String c[]=new String[4];
    String e1[]=new String[4];
    String d1[]=new String[4];
    public static BottomNavigationView bottomNavigationView;
    ArrayList<String> arr=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen_new);
        getWindow().setExitTransition(null);
        getWindow().setEnterTransition(null);
        Window window =   this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.my_statusbar_color));

        try {
            PackageInfo pInfo = HomeScreenNew.this.getPackageManager().getPackageInfo(getPackageName(), 0);
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

                    Context context = HomeScreenNew.this;
                    Class cls = HomeScreenNew.class;

                    String appId = HomeScreenNew.this.getPackageName();
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
                            .setContentText("Please update the Philomath app for the best learning  experience")
                            .setColor(ContextCompat.getColor(context, R.color.notification))
                            .setAutoCancel(true)
                            .setStyle(new NotificationCompat.BigTextStyle().bigText("Please update the Philomath app for the best learning experience"))
                            .setSmallIcon(R.drawable.philomath_logo)
                            .setContentIntent(pendingIntent).build();

                    NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        String channelId = "channel_id";
                        NotificationChannel channel = new NotificationChannel(channelId, "channel name", NotificationManager.IMPORTANCE_HIGH);
                        channel.enableVibration(true);
                        channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                        notificationManager.createNotificationChannel(channel);
                        builder.setChannelId(channelId);
                        builder.setColor(Color.BLUE);
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


        HotwordsDB db = new HotwordsDB(this);
        SQLiteDatabase userList = db.getReadableDatabase();
        Cursor c1 = userList.rawQuery("SELECT * FROM " + "hotwords" + " ORDER by "+ "id" +" DESC LIMIT 4;" , null);
        if (c1 != null && c1.getCount() != 0) {
            int i=0;
            while (c1.moveToNext()) {
                if(i==4)
                    break;

                words[i]=(c1.getString(c1.getColumnIndex("word")).trim());
                d1[i]=(c1.getString(c1.getColumnIndex("Meaning")).trim());
                c[i]=(c1.getString(c1.getColumnIndex("category")).trim());
                e1[i]=("Eg:"+c1.getString(c1.getColumnIndex("examples")).trim());
                p1[i]=("/"+c1.getString(c1.getColumnIndex("phonetics")).trim()+"/");
                i++;

            }}

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigationView);
        bottomNavigationView.setSelectedItemId(R.id.nav_home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        //Add your action onClick
                        break;
                    case R.id.nav_list:
                        Intent intent = new Intent(HomeScreenNew.this, ListOfWords.class);
                        startActivity(intent);
                        break;

                    case R.id.nav_search:
                        Intent intent1 = new Intent(HomeScreenNew.this, add.class);
                        intent1.putExtra("words",arr);
                        startActivity(intent1);
                        break;

                    case R.id.nav_quiz:
                        Intent intent3 = new Intent(HomeScreenNew.this, Quiz_start.class);
                        startActivity(intent3);
                        break;

                    case R.id.nav_settings:
                        Intent intent4 = new Intent(HomeScreenNew.this, settings_screen.class);
                        startActivity(intent4);
                        break;
                }
                return false;
            }
        });


        Button button =(Button)findViewById(R.id.explore);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                word=model.get(position1).getWORD();
                try {
                    new HomeScreenNew.CallbackTask2().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,wordcompare(model.get(position1).getWORD()));
                    new HomeScreenNew.CallbackTask1().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,wordimportance(model.get(position1).getWORD()));
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),"Can't get graph data",Toast.LENGTH_LONG).show();
                }
            }
        });

        model = new ArrayList<>();
        model.add(new Model(words[0],d1[0],e1[0],p1[0],c[0]));
        model.add(new Model(words[1],d1[1],e1[1],p1[1],c[1]));
        model.add(new Model(words[2],d1[2],e1[2],p1[2],c[2]));
        model.add(new Model(words[3],d1[3],e1[3],p1[3],c[3]));


        adapter = new Adapter(model,this);

        viewPager = findViewById(R.id.viewpagerhome);
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        viewPager.setAdapter(adapter);

        viewPager.setPadding(100,0,100,0);




        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            position1=position;

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });





    }

    public class ZoomOutPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.85f;
        private static final float MIN_ALPHA = 0.5f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();
            int pageHeight = view.getHeight();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(1);

            } else if (position <= 1) { // [-1,1]
                // Modify the default slide transition to shrink the page as well
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                if (position < 0) {
                    view.setTranslationX(horzMargin - vertMargin / 2);
                } else {
                    view.setTranslationX(-horzMargin + vertMargin / 2);
                }

                // Scale the page down (between MIN_SCALE and 1)
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);


            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(1);
            }
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
        ProgressDialog dialog = new ProgressDialog(HomeScreenNew.this);
        protected void onPreExecute() {

            super.onPreExecute();
            if (!HomeScreenNew.this.isFinishing()){

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
                Intent intent = new Intent(HomeScreenNew.this, Word_Result.class);
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
            new HomeScreenNew.CallbackTask().execute(dictionaryEntries(model.get(position1).getWORD()));
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
        ProgressDialog dialog2 = new ProgressDialog(HomeScreenNew.this);

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
