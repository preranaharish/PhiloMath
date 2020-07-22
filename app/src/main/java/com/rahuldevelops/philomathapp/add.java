package com.rahuldevelops.philomathapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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
import java.util.Calendar;

import javax.net.ssl.HttpsURLConnection;


public class add extends AppCompatActivity {
    EditText name;
    String word1;
    EditText meaning;
    EditText Examples;
    Button saveBtn;
    Intent intent;
    String myurl;
    String def;
    int finish1=0,finish2=0;
    String graph_words[] = new String[4];
    String graph_score[] = new String[4];
    String sword1,sword2,sword3,sword4,sword5,sword6;
    CharSequence text;
    String synonyms;
    String def1;
    int catimportance=0,greimportance=0;
    ProgressBar Progress;
    ArrayList<String>arr=new ArrayList<>();
    String word;
    TextView b1,b2,b3;
    AutoCompleteTextView actv;
    String senderFirstLetter;
    String Recents[]=new String[3];
    Button recent1,recent2,recent3;
    int graph=1,pie=1;
    private long backPressedTime;
    String[] synonyms_array = new String[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        FirebaseDatabase instance2 = FirebaseDatabase.getInstance();
        DatabaseReference mDatabase = instance2.getInstance().getReference("wordsfortheday");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String sxword1 = (String) dataSnapshot.child("word1").getValue();
                String sxword2 = (String) dataSnapshot.child("word2").getValue();
                String sxword3 = (String) dataSnapshot.child("word3").getValue();
                String sxword4 = (String) dataSnapshot.child("word4").getValue();
                SharedPreferences shared = getSharedPreferences("words",MODE_PRIVATE);
                SharedPreferences.Editor editor = shared.edit();
                editor.putString("word1",sxword1);
                editor.putString("word2",sxword2);
                editor.putString("word3",sxword3);
                editor.putString("word4",sxword4);
                editor.commit();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot!=null && dataSnapshot.exists()){
                    dataSnapshot.getValue();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        SharedPreferences shared = getSharedPreferences("words",MODE_PRIVATE);
        sword1 = shared.getString("word1","Ostentatious");
        sword2 = shared.getString("word2","Ostensible");
        sword3 = shared.getString("word3","Insidious");
        sword4 = shared.getString("word4","Ingenuity");
        text = getIntent()
                .getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT);
        Bundle extras = getIntent().getExtras();
        arr= MySingleton.getStringArrayList();
        CardView c1 = (CardView)findViewById(R.id.recentcard);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this,android.R.layout.select_dialog_item,arr);
        //Getting the instance of AutoCompleteTextView
        actv =  (AutoCompleteTextView) findViewById(R.id.txtName);
        actv.setThreshold(2);//will start working from first character
        actv.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
//        text = getIntent()
//                .getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT);
//        name.setText(text);
        actv.setText(text);
        actv.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                   clicked();
                    return true;
                }
                return false;
            }
        });
        actv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (actv.getRight() - actv.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        clicked();
                        return true;
                    }}
                return false;
            }
        });
       b1=(TextView)findViewById(R.id.B);
       b2=(TextView)findViewById(R.id.B1);
       b3=(TextView)findViewById(R.id.B2);
       recent1=(Button)findViewById(R.id.recent1);
       recent2=(Button)findViewById(R.id.recent2);
       recent3=(Button)findViewById(R.id.recent3);
       Button trending1 = (Button)findViewById(R.id.trending1);
       trending1.setText(sword1);
       trending1.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               actv.setText(sword1.trim());
           }
       });
       Button trending2 = (Button)findViewById(R.id.trending2);
       trending2.setText(sword2);
       trending2.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               actv.setText(sword2.trim());
           }
       });
       Button trending4 = (Button)findViewById(R.id.trending4);
       trending4.setText(sword3);
       trending4.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               actv.setText(sword3.trim());
           }
       });
       Button trending5 = (Button)findViewById(R.id.trending5);
       trending5.setText(sword4);
       trending5.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               actv.setText(sword4.trim());
           }
       });
       for(int i=0;i<3;i++){
           Recents[i]="empty";
       }
        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.navigationView);
        bottomNavigationView.setSelectedItemId(R.id.nav_search);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        Intent intent1 = new Intent(add.this, HomeScreen.class);
                        startActivity(intent1);
                        finish();
                        break;
                    case R.id.nav_list:
                        Intent intent = new Intent(add.this, ListOfWords.class);
                        startActivity(intent);
                        finish();
                        break;

                    case R.id.nav_search:

                        break;

                    case R.id.nav_quiz:
                        Intent intent3 = new Intent(add.this, Quiz_start.class);
                        startActivity(intent3);
                        finish();
                        break;

                    case R.id.nav_settings:
                        Intent intent4 = new Intent(add.this, settings_screen.class);
                        startActivity(intent4);
                        finish();
                        break;
                }
                return false;
            }
        });
        recentdb dbHelper = new recentdb(add.this);
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + "recents" + " ORDER by "+ "id" +" DESC LIMIT 3;" , null);
            int i = 0;
            if (c.moveToFirst()) {
                do {
                    Recents[i] = c.getString(c.getColumnIndex("word"));
                    i = i + 1;
                } while (c.moveToNext());
            }
            db.close();
            if (i==0){
                c1.setVisibility(View.INVISIBLE);
            }
            senderFirstLetter = (String) Recents[0].subSequence(0, 1);
            senderFirstLetter = senderFirstLetter.toUpperCase();
            if(Recents[0]=="empty")
            {
                b1.setVisibility(View.INVISIBLE);
                recent1.setVisibility(View.INVISIBLE);
            }
            else {
                b1.setText(senderFirstLetter);
                recent1.setText(Recents[0]);
            }
            senderFirstLetter = (String) Recents[1].subSequence(0, 1);
            senderFirstLetter = senderFirstLetter.toUpperCase();
            if(Recents[1]=="empty")
            {
                b2.setVisibility(View.INVISIBLE);
                recent2.setVisibility(View.INVISIBLE);
            }
            else {
                b2.setText(senderFirstLetter);
                recent2.setText(Recents[1]);
            }
            senderFirstLetter = (String) Recents[2].subSequence(0, 1);
            senderFirstLetter = senderFirstLetter.toUpperCase();
            if(Recents[2]=="empty")
            {
                b3.setVisibility(View.INVISIBLE);
                recent3.setVisibility(View.INVISIBLE);
            }
            else {
                b3.setText(senderFirstLetter);
                recent3.setText(Recents[2]);
            }

        recent1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rec1= (String) recent1.getText();
                actv.setText(rec1.trim());
            }
        });
recent2.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String rec2= (String) recent2.getText();
        actv.setText(rec2.trim());
    }
});
recent3.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String rec3= (String) recent3.getText();
        actv.setText(rec3.trim());
    }
});

        if(text!=null){
            clicked();
        }
    }



    public void clicked(){

         word1 = actv.getText().toString();
        if(word1.isEmpty()){
            Toast.makeText(getApplicationContext(),"Provide word to search",Toast.LENGTH_LONG).show();
        }
        else{
            new CallbackTask2().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,wordcompare(word1));
            new CallbackTask1().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,wordimportance(word1));

        }

    }
    public String dictionaryEntries(String word) {
        final String language = "en-gb";
        final String word_id = word.toLowerCase();
        return "https://od-api.oxforddictionaries.com:443/api/v2/entries/" + language + "/" + word_id;
    }
    public String wordimportance(String word1){
        return "https://philomathapp.cf/words/exam?name="+word1;
    }
    public String wordcompare(String word){
        final String language = "en-gb";
        final String word_id = word.toLowerCase();
        return "https://api.datamuse.com/words?ml="+word_id+"&max=4";
    }

    public class CallbackTask extends AsyncTask<String, Integer, String> {
        ProgressDialog dialog = new ProgressDialog(add.this);
        protected void onPreExecute() {

            super.onPreExecute();
            if (!add.this.isFinishing()){

                dialog.setMessage("Searching "+word1+" in Oxford Dictionary...");
                dialog.show();
            }}
        @Override
        protected String doInBackground(String... params) {
            myurl=params[0];
            //TODO: replace with your own app id and app key
            final String app_id = "febdebda";
            final String app_key = "6c8770803de94a392b39b165e8b855d8";
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
                        for(int i=0;i<4;i++){
                            synonyms_array[i]="Not found";
                        }
                    }

                }
                catch (Exception i){
                }
                if (def1==null){
                    def1="Examples not found";
                }
                if(synonyms==null){
                    synonyms="synonyms not found";
                }
                Calendar calendar=Calendar.getInstance();
                SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
                String currentTime=sdf.format(calendar.getTime());
                SharedPreferences prefs = getSharedPreferences(currentTime, MODE_PRIVATE);
                int noofwordstoday=prefs.getInt(currentTime,0);
                noofwordstoday+=1;
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt(currentTime,noofwordstoday);
                editor.commit();
                recentdb r = new recentdb(add.this);
                r.insertWordDetails(word1);
                if (this.dialog.isShowing()) {
                    this.dialog.dismiss();
                }
                if(graph_words[0]==null || graph_words[1]==null || graph_words[2]==null|| graph_words[3]==null){
                    graph=0;
                }
                Intent intent = new Intent(add.this, Word_Result.class);
                intent.putExtra("word",word1);
                intent.putExtra("meaning",def);
                intent.putExtra("example",def1);
                intent.putExtra("synonyms",synonyms);
                intent.putExtra("visibility",1);
                intent.putExtra("synonyms_array1",synonyms_array[0]);
                intent.putExtra("synonyms_array2",synonyms_array[1]);
                intent.putExtra("synonyms_array3",synonyms_array[2]);
                intent.putExtra("synonyms_array4",synonyms_array[3]);
                intent.putExtra("pie",pie);
                intent.putExtra("cat",catimportance);
                intent.putExtra("gre",greimportance);
                intent.putExtra("graph",graph);
                intent.putExtra("graph_words",graph_words);
                intent.putExtra("graph_score",graph_score);
                startActivity(intent);
                finish();

            }catch(JSONException e){
                if (this.dialog.isShowing()) {
                    this.dialog.dismiss();
                }
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),"I am afraid that I couldn't get results for the word",Toast.LENGTH_SHORT).show();
            }
            System.out.println(result);
        }
    }

    public class CallbackTask1 extends AsyncTask<String, Integer, String> {
        ProgressDialog dialog1 = new ProgressDialog(add.this);
        protected void onPreExecute() {

            super.onPreExecute();
            if (!add.this.isFinishing()){

                dialog1.setMessage("Retrieving statistics for "+word1+" from Philomath servers...");
                dialog1.show();
            }}
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
            new CallbackTask().execute(dictionaryEntries(word1));
            super.onPostExecute(result);
            try{

              Log.d("api result:",result);
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
                finish1=1;
                if(dialog1.isShowing()){
                    dialog1.dismiss();
                }
            }catch(Exception e){
                pie=0;
                e.printStackTrace();
                if(dialog1.isShowing()){
                    dialog1.dismiss();
                }
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
                graph=0;
                e.printStackTrace();
                return e.toString();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try{

                Log.d("api result:",result);
                result=result.trim();
                if(result.equals("[]")){
                    graph=0;
                }
                else{
                JSONArray js = new JSONArray(result);
                for(int i=0;i<js.length();i++) {

                    JSONObject js1 = (JSONObject) js.get(i);
                    graph_words[i]=js1.getString("word");
                    graph_score[i]=js1.getString("score");
                    finish2=1;
                }}
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



