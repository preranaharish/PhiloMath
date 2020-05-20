package com.rahulbuilds.philomath;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.AsyncTask;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;



public class HomeScreen extends AppCompatActivity {
    TextView w1,w2,w3,w4;
    String myurl;
    String def;
    CharSequence text;
    String synonyms;
    String def1;
    String word;
    String[] synonyms_array = new String[4];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        FloatingActionButton fl =(FloatingActionButton)findViewById(R.id.skip);
        SharedPreferences sharedPreferences = getSharedPreferences("Streaks", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = sharedPreferences.edit();
        Calendar c = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            c = Calendar.getInstance();


        int thisDay = c.get(Calendar.DAY_OF_YEAR); // GET THE CURRENT DAY OF THE YEAR

        int lastDay = sharedPreferences.getInt("datekey", 0); //If we don't have a saved value, use 0.

        int counterOfConsecutiveDays = sharedPreferences.getInt("datecounter", 0); //If we don't have a saved value, use 0.

        if(lastDay == thisDay -1){
            // CONSECUTIVE DAYS
            counterOfConsecutiveDays = counterOfConsecutiveDays + 1;

            prefEditor.putInt("datekey", thisDay);

            prefEditor.putInt("datecounter", counterOfConsecutiveDays);
            prefEditor.commit();
        } else {

            prefEditor.putInt("datekey", thisDay);

            prefEditor.putInt("datecounter", 1).commit();
        }}
        fl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeScreen.this,ListOfWords.class);
                startActivity(intent);
                finish();
            }
        });
       w1=(TextView)findViewById(R.id.w1);
       w2=(TextView)findViewById(R.id.w2);
       w3=(TextView)findViewById(R.id.w3);
       w4=(TextView)findViewById(R.id.w4);
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
    public void Skip(View view){

    }
    public void card1(View view){
       word = w1.getText().toString();
        if(word.isEmpty()){
            Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this,"Revealing "+word.toUpperCase()+ " Please Wait",Toast.LENGTH_LONG).show();
            new HomeScreen.CallbackTask().execute(dictionaryEntries(word));
        }
    }
    public void card2(View view){
        word = w2.getText().toString();
        if(word.isEmpty()){
            Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this,"Revealing "+word.toUpperCase()+ " Please Wait",Toast.LENGTH_LONG).show();
            new HomeScreen.CallbackTask().execute(dictionaryEntries(word));
        }
    }
    public void card3(View view){
       word = w3.getText().toString();
        if(word.isEmpty()){
            Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this,"Revealing "+word.toUpperCase()+ " Please Wait",Toast.LENGTH_LONG).show();
            new HomeScreen.CallbackTask().execute(dictionaryEntries(word));
        }
    }
    public void card4(View view){
        word = w4.getText().toString();
        if(word.isEmpty()){
            Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this,"Revealing "+word.toUpperCase()+ " Please Wait",Toast.LENGTH_LONG).show();
            new HomeScreen.CallbackTask().execute(dictionaryEntries(word));
        }
    }
    private String dictionaryEntries(String word) {
        final String language = "en-gb";
        final String word_id = word.toLowerCase();
        return "https://od-api.oxforddictionaries.com:443/api/v2/entries/" + language + "/" + word_id;
    }
    private class CallbackTask extends AsyncTask<String, Integer, String> {

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
                        synonyms+=", "+jsonObject8.getString("text");
                        synonyms_array[1]=jsonObject8.getString("text");
                        synonyms+=", "+jsonObject9.getString("text");
                        synonyms_array[2]=jsonObject9.getString("text");
                        synonyms+=", "+jsonObject10.getString("text");
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
                Intent intent = new Intent(HomeScreen.this, Word_Result.class);
                intent.putExtra("word",word);
                intent.putExtra("meaning",def);
                intent.putExtra("example",def1);
                intent.putExtra("synonyms",synonyms);
                intent.putExtra("visibility",1);
                intent.putExtra("synonyms_array1",synonyms_array[0]);
                intent.putExtra("synonyms_array2",synonyms_array[1]);
                intent.putExtra("synonyms_array3",synonyms_array[2]);
                intent.putExtra("synonyms_array4",synonyms_array[3]);
                startActivity(intent);

            }catch(JSONException e){
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
            }
            System.out.println(result);
        }
    }
}
