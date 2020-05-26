package com.rahulbuilds.philomath;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;

import javax.net.ssl.HttpsURLConnection;



public class HomeScreen extends AppCompatActivity {
    TextView w1, w2, w3, w4;
    String myurl;
    String def;
    CharSequence text;
    String synonyms;
    String def1;
    int importance;
    String word;
    ScrollView scrollView;
    BottomNavigationView bottomNavigationView;
    String[] synonyms_array = new String[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
//        FloatingActionButton fl =(FloatingActionButton)findViewById(R.id.skip);
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
                        Intent intent = new Intent(HomeScreen.this, ListOfWords.class);
                        startActivity(intent);
                        finish();
                        break;

                    case R.id.nav_search:
                        Intent intent1 = new Intent(HomeScreen.this, add.class);
                        startActivity(intent1);
                        finish();
                        break;

                    case R.id.nav_quiz:
                        Intent intent3 = new Intent(HomeScreen.this, MainQuiz.class);
                        startActivity(intent3);
                        finish();
                        break;

                    case R.id.nav_settings:
                        Intent intent4 = new Intent(HomeScreen.this, settings_screen.class);
                        startActivity(intent4);
                        finish();
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
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
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
        w1 = (TextView) findViewById(R.id.w1);
        w2 = (TextView) findViewById(R.id.w2);
        w3 = (TextView) findViewById(R.id.w3);
        w4 = (TextView) findViewById(R.id.w4);
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
        word = w1.getText().toString();
        if (word.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Revealing " + word.toUpperCase() + " Please Wait", Toast.LENGTH_LONG).show();
            new HomeScreen.CallbackTask1().execute(wordimportance(word));
            new HomeScreen.CallbackTask().execute(dictionaryEntries(word));
        }
    }

    public void card2(View view) {
        word = w2.getText().toString();
        if (word.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Revealing " + word.toUpperCase() + " Please Wait", Toast.LENGTH_LONG).show();
            new HomeScreen.CallbackTask1().execute(wordimportance(word));
            new HomeScreen.CallbackTask().execute(dictionaryEntries(word));
        }
    }

    public void card3(View view) {
        word = w3.getText().toString();
        if (word.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Revealing " + word.toUpperCase() + " Please Wait", Toast.LENGTH_LONG).show();
            new HomeScreen.CallbackTask1().execute(wordimportance(word));
            new HomeScreen.CallbackTask().execute(dictionaryEntries(word));
        }
    }

    public void card4(View view) {
        word = w4.getText().toString();
        if (word.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Revealing " + word.toUpperCase() + " Please Wait", Toast.LENGTH_LONG).show();
            new HomeScreen.CallbackTask1().execute(wordimportance(word));
            new HomeScreen.CallbackTask().execute(dictionaryEntries(word));

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
                        Toast.makeText(getApplicationContext(), "Synonoyms not found", Toast.LENGTH_LONG).show();
                        for (int i = 0; i < 4; i++) {
                            synonyms_array[i] = "Not found";
                        }
                    }

                } catch (Exception i) {
                    Toast.makeText(getApplicationContext(), "Example not found", Toast.LENGTH_SHORT).show();
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
                intent.putExtra("imp", importance);
                dialog.dismiss();
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
            super.onPostExecute(result);
            try {
                Log.d("api result:", result);
                result.trim();
                JSONObject jsonObject = new JSONObject(result);
                String i = jsonObject.getString("total");
                float inum = Float.parseFloat(i);
                inum = inum * 100;
                importance = (int) inum;
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
            System.out.println(result);
        }
    }
}
