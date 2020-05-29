package com.rahulbuilds.philomath;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.JsonObjectRequest;
import com.github.mikephil.charting.utils.Utils;
import com.rahulbuilds.philomath.DBHelper;
import com.rahulbuilds.philomath.MainActivity;
import com.rahulbuilds.philomath.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

import static com.rahulbuilds.philomath.SignIn.SHARED_PREFS;


public class add extends AppCompatActivity {
    EditText name;
    String word1;
    EditText meaning;
    EditText Examples;
    Button saveBtn;
    Intent intent;
    String myurl;
    String def;
    CharSequence text;
    String synonyms;
    String def1;
    int importance;
    ProgressBar Progress;
    ArrayList<String>arr=new ArrayList<>();
    String word;
    TextView b1,b2,b3;
    AutoCompleteTextView actv;
    String senderFirstLetter;
    String Recents[]=new String[3];
    Button recent1,recent2,recent3;
    String[] synonyms_array = new String[4];
    String[] words = new String[60000];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        String[] word = {"rahul"};
        try {
            Scanner scan = new Scanner(getAssets().open("words_english.txt"));
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
        actv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicked();
            }
        });
       b1=(TextView)findViewById(R.id.B);
       b2=(TextView)findViewById(R.id.B1);
       b3=(TextView)findViewById(R.id.B2);
       recent1=(Button)findViewById(R.id.recent1);
       recent2=(Button)findViewById(R.id.recent2);
       recent3=(Button)findViewById(R.id.recent3);
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
                        Intent intent1 = new Intent(add.this,HomeScreen.class);
                        startActivity(intent1);
                        finish();
                        break;
                    case R.id.nav_list:
                        Intent intent = new Intent(add.this,ListOfWords.class);
                        startActivity(intent);
                        finish();
                        break;

                    case R.id.nav_search:

                        break;

                    case R.id.nav_quiz:
                        Intent intent3 = new Intent(add.this,Quiz_start.class);
                        startActivity(intent3);
                        finish();
                        break;

                    case R.id.nav_settings:
                        Intent intent4 = new Intent(add.this,settings_screen.class);
                        startActivity(intent4);
                        finish();
                        break;
                }
                return false;
            }
        });
        DBHelper dbHelper = new DBHelper(add.this);
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + "words" + " ORDER by "+ "id" +" DESC LIMIT 3;" , null);
            int i = 0;
            if (c.moveToFirst()) {
                do {
                    Recents[i] = c.getString(c.getColumnIndex("name"));
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
                actv.setText(rec1);
            }
        });
recent2.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String rec2= (String) recent2.getText();
        actv.setText(rec2);
    }
});
recent3.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String rec3= (String) recent3.getText();
        actv.setText(rec3);
    }
});
    }



    public void clicked(){

         word1 = actv.getText().toString();
        if(word1.isEmpty()){
            Toast.makeText(getApplicationContext(),"Provide word to search",Toast.LENGTH_LONG).show();
        }
        else{
            new CallbackTask1().execute(wordimportance(word1));
            new CallbackTask().execute(dictionaryEntries(word1));
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
                Calendar calendar=Calendar.getInstance();
                SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
                String currentTime=sdf.format(calendar.getTime());
                SharedPreferences prefs = getSharedPreferences(currentTime, MODE_PRIVATE);
                int noofwordstoday=prefs.getInt(currentTime,0);
                noofwordstoday+=1;
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt(currentTime,noofwordstoday);
                editor.commit();
                if (this.dialog.isShowing()) {
                    this.dialog.dismiss();
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
                intent.putExtra("imp",importance);
                startActivity(intent);
                finish();

            }catch(JSONException e){
                if (this.dialog.isShowing()) {
                    this.dialog.dismiss();
                }
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
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
            super.onPostExecute(result);
            try{
              Log.d("api result:",result);
                result.trim();
                JSONObject jsonObject = new JSONObject(result);
                String i = jsonObject.getString("total");
                float inum = Float.parseFloat(i);
                inum=inum*100;
                importance = (int)inum;
                if(dialog1.isShowing()){
                    dialog1.dismiss();
                }
            }catch(Exception e){
                e.printStackTrace();
                if(dialog1.isShowing()){
                    dialog1.dismiss();
                }
                Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
            }
            System.out.println(result);
        }
    }

}
