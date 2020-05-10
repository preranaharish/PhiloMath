package com.rahulbuilds.philomath;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements RecyclerViewItemListener {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter userAdapter;
    private RecyclerView.LayoutManager layoutManager;
    String wordname,Example,sy1,sy2,sy3,sy4,note,meaning;
    List<UserDetails> userDetailsList;
    String TAG = "RemindMe";
    LocalData localData;
    FloatingActionButton add1;
    LinearLayout linearlayout;
    SQLiteDatabase sqlDB;
    Intent intent;
    String synonym;
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

    int hour, min;

    ClipboardManager myClipboard;
    public final static String EXTRA_MESSAGE = "MESSAGE";
    private ListView obj;
    DBHelper mydb;
    MenuItem search;
    android.support.v7.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.rv_users1);
        DBHelper db = new DBHelper(this);
        SQLiteDatabase userList= db.getReadableDatabase();
        SharedPreferences prefs = getSharedPreferences(TAG, MODE_PRIVATE);
        int restoredText = prefs.getInt("set", 0);
        DBHelper dbHandler = new DBHelper(MainActivity.this);
        sqlDB = dbHandler.getWritableDatabase();
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("message_subject_intent"));
        userDetailsList = new ArrayList<UserDetails>();
        userDetailsList.clear();
        Cursor c1 = userList.query("words", null, null, null, null, null, null);

        if (c1 != null && c1.getCount() != 0) {
            userDetailsList.clear();
            while (c1.moveToNext()) {
                UserDetails userDetailsItem = new UserDetails();

                userDetailsItem.setUserId(c1.getInt(c1.getColumnIndex("id")));
                userDetailsItem.setName(c1.getString(c1.getColumnIndex("name")));
                userDetailsItem.setAddress(c1.getString(c1.getColumnIndex("Meaning")));
                userDetailsItem.setProfessiion(c1.getString(c1.getColumnIndex("Examples")));
                userDetailsList.add(userDetailsItem);


            }
        linearlayout=(LinearLayout)findViewById(R.id.search_option);

        }

        c1.close();
       add1 = (FloatingActionButton) findViewById(R.id.add);

        add1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, add.class);
                startActivity(intent);

            }
        });
        layoutManager = new LinearLayoutManager(this);
        userAdapter = new UserDetailsAdapter(userDetailsList,this);
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(this, R.anim.animation_recycler);

        recyclerView.setLayoutAnimation(controller);
        recyclerView.scheduleLayoutAnimation();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(userAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                if (dy > 0 ||dy<0 && add1.isShown())
                {
                    add1.hide();
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState)
            {
                if (newState == RecyclerView.SCROLL_STATE_IDLE)
                {
                    add1.show();
                }

                super.onScrollStateChanged(recyclerView, newState);
            }
        });
        localData = new LocalData(getApplicationContext());

        myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

        //reminderSwitch = (SwitchCompat) findViewById(R.id.timerSwitch);

        hour = localData.get_hour();

        min = localData.get_min();
        if(restoredText==0){
//reminderSwitch.setChecked(false);
        }
        else {
           // reminderSwitch.setChecked(true);
        }
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



       /* reminderSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                localData.setReminderStatus(isChecked);
                if (isChecked) {
                    SharedPreferences.Editor editor = getSharedPreferences(TAG, MODE_PRIVATE).edit();
                    editor.putInt("set", 1);
                    editor.apply();
                    Log.d(TAG, "onCheckedChanged: true");
                    NotificationScheduler.setReminder(MainActivity.this, AlarmReceiver.class, 10, 30);
                    Log.d(TAG, "Yes: Reminder set");
                } else {
                    SharedPreferences.Editor editor = getSharedPreferences(TAG, MODE_PRIVATE).edit();
                    editor.putInt("set", 0);
                    editor.apply();
                    Log.d(TAG, "onCheckedChanged: false");
                    NotificationScheduler.cancelReminder(MainActivity.this, AlarmReceiver.class);
                }

            }
        });*/


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bar, menu);

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
             DBHelper dbHandler = new DBHelper(MainActivity.this);
           SQLiteDatabase sqlDB = dbHandler.getWritableDatabase();
              Cursor cursor = sqlDB.rawQuery("Delete FROM words WHERE name = ?", new String[]{ word });
        if (cursor != null && cursor.moveToFirst()) {
                }


        userAdapter.notifyItemRemoved(pos);
        userAdapter.notifyDataSetChanged();

             Toast.makeText(MainActivity.this,word.toUpperCase() +"  will be deleted",Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.Share:
                if (isStoragePermissionGranted()){
                    if(Backup.exportDB(MainActivity.this)){
                        Toast.makeText(this,"Existing Data Saved Locally",Toast.LENGTH_LONG).show();
                    }
                    else{
                    Toast.makeText(this,"something went wrong",Toast.LENGTH_LONG).show();
                }}

                return true;
            case R.id.Setting:
                if (isStoragePermissionGranted()){
                    if(Backup.importDB(MainActivity.this)){
                        Toast.makeText(this,"Database imported, Reopen the app",Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(this,"something went wrong",Toast.LENGTH_LONG).show();
                    }
                }

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
    private void speak(String title1) {

        String text = title1;
        mTTS.setPitch(0.9f);
        mTTS.setSpeechRate(0.85f);
        mTTS.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }




    @Override
    protected void onDestroy() {
        if (mTTS != null) {
            mTTS.stop();
            mTTS.shutdown();
        }

        super.onDestroy();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
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
                synonym=sy1+','+sy2+','+sy3+','+','+sy4;
            }
        }
        String meaning=userDetailsList.get(position).getAddress();
        String ex=userDetailsList.get(position).getProfessiion();
        Intent intent = new Intent(MainActivity.this, Word_Result.class);
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

}
