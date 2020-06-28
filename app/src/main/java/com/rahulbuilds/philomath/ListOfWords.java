package com.rahulbuilds.philomath;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import com.google.android.gms.ads.MobileAds;
import android.speech.tts.Voice;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.NavigationView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.mikephil.charting.utils.Utils;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import com.google.android.gms.ads.MobileAds;
import com.google.gson.Gson;

import javax.net.ssl.HttpsURLConnection;

import static android.speech.tts.Voice.LATENCY_NORMAL;
import static android.speech.tts.Voice.QUALITY_VERY_HIGH;
import static com.rahulbuilds.philomath.SignIn.SHARED_PREFS;

public class ListOfWords extends AppCompatActivity implements RecyclerViewItemListener,NavigationView.OnNavigationItemSelectedListener{
    SQLiteDatabase sqlDB;
    private AppBarConfiguration mAppBarConfiguration;
String usernametext,emailtext;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter userAdapter;
    private RecyclerView.LayoutManager layoutManager;
    List<UserDetails> userDetailsList;
    String TAG = "RemindMe";
    LocalData localData;
    String word_final;
    String synonym;
    private  long backPressedTime;
    int catimportance=0,greimportance=0;
    AlertDialog alertDialog;
    int daycounter;
    DrawerLayout drawer;
    String graph_words[] = new String[4];
    String graph_score[] = new String[4];
    int graph=1,pie=1;
    FloatingActionButton add1;
    LinearLayout linearlayout;
    Intent intent;
     int brodcasttriggered=0;
    SwitchCompat reminderSwitch;
    TextView tvTime;
    private TextToSpeech mTTS;
    private SeekBar mSeekBarPitch;
    EditText name;
    String word1;
   String meaning;
    EditText Examples;
    Button saveBtn;
    String myurl;
    String def;
    CharSequence text;
    String synonyms;
    String def1;
    int importance;
    ProgressBar Progress;
    String word;
    TextView b1,b2,b3;
    AutoCompleteTextView actv;
    String senderFirstLetter;
    String Recents[]=new String[3];
    Button recent1,recent2,recent3;
    String[] synonyms_array = new String[4];
    private SeekBar mSeekBarSpeed;
    private Button mButtonSpeak;
    EditText inputSearch;
    SimpleAdapter adapter;
    ListView lv;
    LinearLayout ll_set_time, ll_terms;
FloatingActionButton fab;
    int hour, min;
String wordname,Example,sy1,sy2,sy3,sy4,note;

    ClipboardManager myClipboard;
    public final static String EXTRA_MESSAGE = "MESSAGE";
    private ListView obj;
    DBHelper mydb;
    MenuItem search;
    android.support.v7.widget.Toolbar toolbar;
    String url;
BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_words);
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        usernametext = prefs.getString("keyname", "Unknown");
        emailtext = prefs.getString("Email", "Unknown");
        url = prefs.getString("url","Unknown");
        int restoredText = prefs.getInt("set", 0);
        recyclerView = (RecyclerView) findViewById(R.id.rv_users);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.scheduleLayoutAnimation();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(userAdapter);
         bottomNavigationView = (BottomNavigationView)findViewById(R.id.navigationView);
        bottomNavigationView.setSelectedItemId(R.id.nav_list);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        Intent intent = new Intent(ListOfWords.this,HomeScreen.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.nav_list:

                        break;

                    case R.id.nav_search:
                        Intent intent1 = new Intent(ListOfWords.this,add.class);
                        startActivity(intent1);
                        finish();
                        break;

                    case R.id.nav_quiz:
                        Intent intent3 = new Intent(ListOfWords.this,Quiz_start.class);
                        startActivity(intent3);
                        finish();
                        break;

                    case R.id.nav_settings:
                        Intent intent4 = new Intent(ListOfWords.this,settings_screen.class);
                        startActivity(intent4);
                        finish();
                        break;
                }
                return false;
            }
        });

        TextView tv = (TextView)findViewById(R.id.newtext);
        DBHelper db = new DBHelper(this);
        SQLiteDatabase userList = db.getReadableDatabase();
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("message_subject_intent"));
        userDetailsList = new ArrayList<UserDetails>();
        userDetailsList.clear();
        Cursor c1 = userList.query("words", null, null, null, null, null, null);
        if (c1.getCount()==0){
            tv.setVisibility(View.VISIBLE);
        }
        if (c1 != null && c1.getCount() != 0) {
            userDetailsList.clear();
            while (c1.moveToNext()) {
                UserDetails userDetailsItem = new UserDetails();

                userDetailsItem.setUserId(c1.getInt(c1.getColumnIndex("id")));
                userDetailsItem.setName(c1.getString(c1.getColumnIndex("name")));
                userDetailsItem.setAddress(c1.getString(c1.getColumnIndex("Meaning")));
                userDetailsItem.setProfessiion(c1.getString(c1.getColumnIndex("Examples")));
                userDetailsItem.setNote(c1.getString(c1.getColumnIndex("Note")));
                userDetailsList.add(userDetailsItem);


            }}
            SharedPreferences sharedPreferences = getSharedPreferences("Streaks", Context.MODE_PRIVATE);
             daycounter = sharedPreferences.getInt("datecounter",0);
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            fab = findViewById(R.id.add);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(ListOfWords.this, add.class);
                    startActivity(intent);

                }
            });
            layoutManager = new LinearLayoutManager(this);
            recyclerView.scheduleLayoutAnimation();
            recyclerView.setLayoutManager(layoutManager);
            userAdapter = new UserDetailsAdapter(userDetailsList, this);
            recyclerView.setAdapter(userAdapter);
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    if (dy > 0 ) {
                        bottomNavigationView.setVisibility(View.INVISIBLE);

                    }
                    if(dy<0 ){
                        bottomNavigationView.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {

                    }

                    super.onScrollStateChanged(recyclerView, newState);
                }
            });
            mTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    if (status == TextToSpeech.SUCCESS) {
                        int result = mTTS.setLanguage(Locale.ENGLISH);

                        if (result == TextToSpeech.LANG_MISSING_DATA
                                || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                            Log.e("TTS", "Language not supported");
                        } else {
                        }
                    } else {
                        Log.e("TTS", "Initialization failed");
                    }
                }
            });
            localData = new LocalData(getApplicationContext());

            myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

            //reminderSwitch = (SwitchCompat) findViewById(R.id.timerSwitch);

            hour = localData.get_hour();

            min = localData.get_min();

             drawer =(DrawerLayout) findViewById(R.id.drawer_layout);


        DBHelper dbHandler = new DBHelper(ListOfWords.this);
         sqlDB = dbHandler.getWritableDatabase();

    }
        @Override
        public boolean onSupportNavigateUp () {
            TextView username = (TextView) findViewById(R.id.username);
            username.setText(usernametext);
            TextView email = (TextView) findViewById(R.id.email);
            email.setText(emailtext);
            ImageView imgProfilePic = (ImageView)findViewById(R.id.profilepic);
            TextView streakhead =(TextView) findViewById(R.id.streakhead1);
            TextView streaks =(TextView) findViewById(R.id.streak1);
            String days =Integer.toString(daycounter);
            streaks.setText(days);
            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.N) {
                streakhead.setVisibility(View.INVISIBLE);
                streaks.setVisibility(View.INVISIBLE);
            }
            try{

//                Glide.with(getApplicationContext()).load(url)
//                        .thumbnail(0.5f)
//                        .crossFade()
//                        .diskCacheStrategy(DiskCacheStrategy.ALL)
//                        .into(imgProfilePic);
            }catch (Exception e){
                imgProfilePic.setVisibility(View.INVISIBLE);
            }

            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
            return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                    || super.onSupportNavigateUp();

        }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.revise:
               reviser();
                break;
            default:
                break;
        }
        return true;
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {

            case R.id.nav_list:{
                Intent intent3 = new Intent(this,ListOfWords.class);
                startActivity(intent3);
                finish();
                break;
            }

            case R.id.nav_home:{
                Intent intent2 = new Intent(this,HomeScreen.class);
                startActivity(intent2);
                finish();
                break;
            }

            case R.id.nav_quiz: {
               Intent intent = new Intent(this,Quiz_start.class);
               startActivity(intent);
                break;
            }
            case R.id.nav_slideshow:{
                Intent intent1 = new Intent(this,settings_screen.class);
                startActivity(intent1);
                break;
            }
        }
        //close navigation drawer
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onItemClicked(int position) {

    }

    @Override
    public void onItemLongClicked(int position) {

    }


    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {


            String name= intent.getStringExtra("name");
            int option = intent.getIntExtra("option",0);
            int position = intent.getIntExtra("pos",0);
            if(option==0){
                Search(position);
            }
            if(option==1){
                say(position);
            }
            if(option==2) {

                delete(name, position);

        }}
    };
    public void delete(String word,int pos){
        try{
        DBHelper dbHandler = new DBHelper(ListOfWords.this);
        SQLiteDatabase sqlDB = dbHandler.getWritableDatabase();
        Cursor cursor = sqlDB.rawQuery("Delete FROM words WHERE name = ?", new String[]{ word });
        if (cursor != null && cursor.moveToFirst()) {
        }
        Toast.makeText(ListOfWords.this,"Deleted "+word.toLowerCase(),Toast.LENGTH_SHORT).show();
        userDetailsList.remove(pos);
        recyclerView.getRecycledViewPool().clear();
        userAdapter.notifyDataSetChanged();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private void speak(String title1) {

        String text = title1;
        mTTS.setPitch(0.9f);
        mTTS.setSpeechRate(0.85f);
        mTTS.speak(text, TextToSpeech.QUEUE_ADD, null);


    }
    private void stop(){
        mTTS.stop();
    }
    public void say(int position) {
        String word=userDetailsList.get(position).getName();
        String meaning=userDetailsList.get(position).getAddress();
        String ex=userDetailsList.get(position).getProfessiion();
       speak(word);
    }


    public void Search(int position) {
        String word=userDetailsList.get(position).getName();
        Cursor cursor = sqlDB.rawQuery("SELECT * FROM words WHERE name = ?", new String[]{ word });
        if (cursor != null && cursor.moveToFirst()) {
            word = cursor.getString(cursor.getColumnIndex("name"));
            String Meaning = cursor.getString(cursor.getColumnIndex("Meaning"));
            String example = cursor.getString(cursor.getColumnIndex("Examples"));
            String synonym1 = cursor.getString(cursor.getColumnIndex("synonym1"));
            String synonym2 = cursor.getString(cursor.getColumnIndex("synonym2"));
            String synonym3 = cursor.getString(cursor.getColumnIndex("synonym3"));
            String synonym4 = cursor.getString(cursor.getColumnIndex("synonym4"));
            String additionalnote = cursor.getString(cursor.getColumnIndex("Note"));
            Log.d("title",word);
            Log.d("Content",    Meaning);
            wordname=word;
            meaning=Meaning;
            Example=example;
            sy1=synonym1;
            sy2=synonym2;
            sy3=synonym3;
            sy4=synonym4;
            note=additionalnote;
            if(sy1=="Not found" | sy1==null){
                synonym="Not found";
            }
            else{
                synonym=sy1+' '+sy2+' '+sy3+' '+' '+sy4;
            }
        }
        String meaning=userDetailsList.get(position).getAddress();
        String ex=userDetailsList.get(position).getProfessiion();
        try {
            word_final=word;
            new ListOfWords.CallbackTask2().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,wordcompare(word));
            new ListOfWords.CallbackTask1().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,wordimportance(word));
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"Can't get graph data",Toast.LENGTH_LONG).show();
        }



    }
    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted");
                return true;
            } else {

                Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Log.v(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);
            //resume tasks needing this permission
        }
    }

            public void onCheckedChanged(boolean isChecked) {
                localData.setReminderStatus(isChecked);
                if (isChecked) {
                    SharedPreferences.Editor editor = getSharedPreferences(TAG, MODE_PRIVATE).edit();
                    editor.putInt("set", 1);
                    editor.apply();
                    Log.d(TAG, "onCheckedChanged: true");
                    NotificationScheduler.setReminder(ListOfWords.this, AlarmReceiver.class, 10, 30);
                    Log.d(TAG, "Yes: Reminder set");
                } else {
                    SharedPreferences.Editor editor = getSharedPreferences(TAG, MODE_PRIVATE).edit();
                    editor.putInt("set", 0);
                    editor.apply();
                    Log.d(TAG, "onCheckedChanged: false");
                    NotificationScheduler.cancelReminder(ListOfWords.this, AlarmReceiver.class);
                }

            }
    public String dictionaryEntries(String word) {
        this.word=word;
        final String language = "en-gb";
        final String word_id = word.toLowerCase();
        return "https://od-api.oxforddictionaries.com:443/api/v2/entries/" + language + "/" + word;
    }
    public String wordimportance(String word1){
        return "https://philomathapp.cf/words/exam?name="+word1;
    }
    public String wordcompare(String word){
        final String language = "en-gb";
        word=word.trim();
        final String word_id = word.toLowerCase();
        return "https://api.datamuse.com/words?ml="+word_id+"&max=4";
    }
    public class CallbackTask extends AsyncTask<String, Integer, String> {

        ProgressDialog dialog = new ProgressDialog(ListOfWords.this);
        protected void onPreExecute() {

            super.onPreExecute();
            if (!ListOfWords.this.isFinishing()){

                dialog.setMessage("Retrieving statistics for "+word+" from Philomath servers...");
                dialog.show();
            }

        }

        @Override
        protected String doInBackground(String... params) {
            myurl=params[0];
            //TODO: replace with your own app id and app key
            final String app_id = "7c39cce3";
            final String app_key = "5a5aae6addef9d704c567a8c152211f7";
            try {

                URL url = new URL(myurl);
                HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
                urlConnection.setRequestProperty("Accept","application/json");
                urlConnection.setRequestProperty("app_id",app_id);
                urlConnection.setRequestProperty("app_key",app_key);

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
                result.trim();
                JSONObject jsonObject =new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("results");
                JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                JSONArray js1 = jsonObject1.getJSONArray("lexicalEntries");
                JSONObject jsonObject2 =js1.getJSONObject(0);
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
                        synonyms_array[0]=jsonObject7.getString("text");
                        synonyms+=", "+"\t"+jsonObject8.getString("text");
                        synonyms_array[1]=jsonObject8.getString("text");
                        synonyms+=", "+"\n"+jsonObject9.getString("text");
                        synonyms_array[2]=jsonObject9.getString("text");
                        synonyms+=", "+"\t"+jsonObject10.getString("text");
                        synonyms_array[3]=jsonObject10.getString("text");

                    }
                    catch (Exception j){
                        Toast.makeText(getApplicationContext(),"Synonoyms not found",Toast.LENGTH_LONG).show();
                        for(int i=0;i<4;i++){
                            synonyms_array[i]="Not found";
                        }
                    }

                }
                catch (Exception i){
                    Toast.makeText(getApplicationContext(),"Example not found",Toast.LENGTH_SHORT).show();
                }
                if (def1==null){
                    def1="Examples not found";
                }
                if(synonyms==null){
                    synonyms="synonyms not found";
                }
                SimpleDateFormat simpleDate =  new SimpleDateFormat("dd/MM/yyyy");
                Calendar cal = Calendar.getInstance();
                String strDt = simpleDate.format(cal.DATE);
                strDt=strDt.replaceAll("/","");
                SharedPreferences prefs = getSharedPreferences(strDt, MODE_PRIVATE);
                int noofwordstoday=prefs.getInt(strDt,0);
                noofwordstoday+=1;
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt(strDt,noofwordstoday);
                editor.commit();
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                Intent intent = new Intent(ListOfWords.this, Word_Result.class);
                word=word.trim();
                word=word.toUpperCase();
                intent.putExtra("word",word);
                intent.putExtra("meaning",def);
                intent.putExtra("example",def1);
                intent.putExtra("synonyms",synonyms);
                intent.putExtra("visibility",1);
                intent.putExtra("synonyms_array1",synonyms_array[0]);
                intent.putExtra("synonyms_array2",synonyms_array[1]);
                intent.putExtra("synonyms_array3",synonyms_array[2]);
                intent.putExtra("synonyms_array4",synonyms_array[3]);
                intent.putExtra("note",note);
                intent.putExtra("pie",pie);
                intent.putExtra("cat",catimportance);
                intent.putExtra("gre",greimportance);
                intent.putExtra("graph",graph);
                intent.putExtra("graph_words",graph_words);
                intent.putExtra("graph_score",graph_score);
                startActivity(intent);
                finish();
                startActivity(intent);
                finish();

            }catch(JSONException e){
                e.printStackTrace();
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
            }
            System.out.println(result);
        }
    }

    public class CallbackTask1 extends AsyncTask<String, Integer, String> {
        ProgressDialog dialog1 = new ProgressDialog(ListOfWords.this);
        protected void onPreExecute() {
            super.onPreExecute();
            if (!ListOfWords.this.isFinishing()){
                dialog1.setMessage("Retrieving statistics for "+word_final+" from Philomath servers");
                dialog1.show();
            }
        }
        @Override
        protected String doInBackground(String... params) {
            myurl=params[0];
            //TODO: replace with your own app id and app key
            try {

                URL url = new URL(myurl);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
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
            word_final=word_final.trim();
            word_final=word_final.toLowerCase();
            new ListOfWords.CallbackTask().execute(dictionaryEntries(word_final));
            super.onPostExecute(result);
            try{

                Log.d("api result:",result);
                result=result.trim();
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
                if(dialog1.isShowing()){
                    dialog1.dismiss();
                }
            }catch(Exception e){
                e.printStackTrace();
                if(dialog1.isShowing()){
                    dialog1.dismiss();
                }
                pie=0;
                Toast.makeText(getApplicationContext(),"Philomath servers unreachable",Toast.LENGTH_SHORT).show();
            }
            System.out.println(result);
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        stop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);

    }
@Override
    public void onDestroy(){
        super.onDestroy();
        stop();
    LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
}

public void reviser(){
    DBHelper db = new DBHelper(this);
    SQLiteDatabase userList = db.getReadableDatabase();
    LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("message_subject_intent"));
    Cursor c1 = userList.query("words", null, null, null, null, null, null);
    if (c1.getCount()==0){
       speak("No words to revise");
    }
    if (c1 != null && c1.getCount() != 0) {
        while (c1.moveToNext()) {
            UserDetails userDetailsItem = new UserDetails();


            String word=c1.getString(c1.getColumnIndex("name"));
            String meaning=c1.getString(c1.getColumnIndex("Meaning"));
           speak(word + meaning);




        }
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
            super.onBackPressed();
        } else {
            Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();
        }

        backPressedTime = System.currentTimeMillis();
    }
}
