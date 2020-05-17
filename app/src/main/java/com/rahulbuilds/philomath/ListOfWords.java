package com.rahulbuilds.philomath;

import android.content.BroadcastReceiver;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
    String synonym;
    int daycounter;
    DrawerLayout drawer;
    FloatingActionButton add1;
    LinearLayout linearlayout;
    Intent intent;
    SwitchCompat reminderSwitch;
    TextView tvTime;
    private TextToSpeech mTTS;
    private SeekBar mSeekBarPitch;
    private SeekBar mSeekBarSpeed;
    private Button mButtonSpeak;
    EditText inputSearch;
    SimpleAdapter adapter;
    ListView lv;
    LinearLayout ll_set_time, ll_terms;
    FloatingActionButton fab;
    int hour, min;
    String wordname,Example,sy1,sy2,sy3,sy4,note,meaning;
    ClipboardManager myClipboard;
    public final static String EXTRA_MESSAGE = "MESSAGE";
    private ListView obj;
    DBHelper mydb;
    MenuItem search;
    android.support.v7.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_words);
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        usernametext = prefs.getString("keyname", "Unknown");
        emailtext = prefs.getString("Email", "Unknown");
        int restoredText = prefs.getInt("set", 0);
        recyclerView = (RecyclerView) findViewById(R.id.rv_users);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.scheduleLayoutAnimation();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(userAdapter);
        NotificationScheduler.setReminder(ListOfWords.this, AlarmReceiver.class, 10, 30);
        Log.d(TAG, "Yes: Reminder set");
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
                userDetailsList.add(userDetailsItem);


            }}
        SharedPreferences sharedPreferences = getSharedPreferences("Streaks", Context.MODE_PRIVATE);
        daycounter = sharedPreferences.getInt("datecounter",0);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.settings_menu);
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
                if (dy > 0 || dy < 0 && fab.isShown()) {
                    fab.hide();
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    fab.show();
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

        NavigationView navigationView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(this);

        DBHelper dbHandler = new DBHelper(ListOfWords.this);
        sqlDB = dbHandler.getWritableDatabase();

    }
    @Override
    public boolean onSupportNavigateUp () {
        TextView username = (TextView) findViewById(R.id.username);
        username.setText(usernametext);
        TextView email = (TextView) findViewById(R.id.email);
        email.setText(emailtext);
        TextView streakhead =(TextView) findViewById(R.id.streakhead1);
        TextView streaks =(TextView) findViewById(R.id.streak1);
        String days =Integer.toString(daycounter);
        streaks.setText(days);
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.N) {
            streakhead.setVisibility(View.INVISIBLE);
            streaks.setVisibility(View.INVISIBLE);
        }

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {

            case R.id.nav_gallery: {
                Intent intent = new Intent(this,MainQuiz.class);
                startActivity(intent);
                break;
            }
        }
        //close navigation drawer
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String name= intent.getStringExtra("name");
            int position = intent.getIntExtra("pos",0);
            delete(name,position);
        }
    };
    public void delete(String word,int pos){
        DBHelper dbHandler = new DBHelper(ListOfWords.this);
        SQLiteDatabase sqlDB = dbHandler.getWritableDatabase();
        Cursor cursor = sqlDB.rawQuery("Delete FROM words WHERE name = ?", new String[]{ word });
        if (cursor != null && cursor.moveToFirst()) {
        }


        userAdapter.notifyItemRemoved(pos);
        userAdapter.notifyDataSetChanged();

        Toast.makeText(ListOfWords.this,word.toUpperCase() +"  will be deleted",Toast.LENGTH_LONG).show();
    }
    private void speak(String title1) {

        String text = title1;
        mTTS.setPitch(0.9f);
        mTTS.setSpeechRate(0.85f);
        mTTS.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }
    @Override
    public void onItemClicked(int position) {
        String word=userDetailsList.get(position).getName();
        String meaning=userDetailsList.get(position).getAddress();
        String ex=userDetailsList.get(position).getProfessiion();
        speak(word);
    }

    @Override
    public void onItemLongClicked(int position) {
        String word=userDetailsList.get(position).getName();
        Cursor cursor = sqlDB.rawQuery("SELECT * FROM words WHERE name = ?", new String[]{ word });
        if (cursor != null && cursor.moveToFirst()) {
            String wordna = cursor.getString(cursor.getColumnIndex("name"));
            String Meaning = cursor.getString(cursor.getColumnIndex("Meaning"));
            String example = cursor.getString(cursor.getColumnIndex("Examples"));
            String synonym1 = cursor.getString(cursor.getColumnIndex("synonym1"));
            String synonym2 = cursor.getString(cursor.getColumnIndex("synonym2"));
            String synonym3 = cursor.getString(cursor.getColumnIndex("synonym3"));
            String synonym4 = cursor.getString(cursor.getColumnIndex("synonym4"));
            String additionalnote = cursor.getString(cursor.getColumnIndex("Note"));
            Log.d("title",wordna);
            Log.d("Content",    Meaning);
            wordname=wordna;
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
        Intent intent = new Intent(ListOfWords.this, Word_Result.class);
        intent.putExtra("word",word);
        intent.putExtra("meaning",meaning);
        intent.putExtra("example",ex);
        intent.putExtra("visibility",0);
        intent.putExtra("synonyms",synonym);
        intent.putExtra("synonyms_array1",sy1);
        intent.putExtra("synonyms_array2",sy2);
        intent.putExtra("synonyms_array3",sy3);
        intent.putExtra("synonyms_array4",sy4);
        intent.putExtra("note",note);
        startActivity(intent);

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

}