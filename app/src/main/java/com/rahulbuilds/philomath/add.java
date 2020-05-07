package com.rahulbuilds.philomath;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.JsonObjectRequest;
import com.rahulbuilds.philomath.DBHelper;
import com.rahulbuilds.philomath.MainActivity;
import com.rahulbuilds.philomath.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


public class add extends AppCompatActivity {
    EditText name;
    EditText meaning;
    EditText Examples;
    Button saveBtn;
    Intent intent;
    String myurl;
    String def;
    CharSequence text;
    String synonyms;
    String def1;
    ProgressBar Progress;
    String word;
    TextView b1,b2,b3;
    String senderFirstLetter;
    String Recents[]=new String[3];
    Button recent1,recent2,recent3;
    String[] synonyms_array = new String[4];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
         text = getIntent()
                .getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT);

        name = (EditText)findViewById(R.id.txtName);
        name.setText(text);
       b1=(TextView)findViewById(R.id.B);
       b2=(TextView)findViewById(R.id.B1);
       b3=(TextView)findViewById(R.id.B2);
       recent1=(Button)findViewById(R.id.recent1);
       recent2=(Button)findViewById(R.id.recent2);
       recent3=(Button)findViewById(R.id.recent3);
        DBHelper dbHelper = new DBHelper(add.this);
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + "words" + " ORDER by "+ "id" +" DESC LIMIT 3;" , null);
        if(c.getCount()>2) {
            int i = 0;
            if (c.moveToFirst()) {
                do {
                    Recents[i] = c.getString(c.getColumnIndex("name"));
                    i = i + 1;
                } while (c.moveToNext());
            }
            db.close();
            senderFirstLetter = (String) Recents[0].subSequence(0, 1);
            senderFirstLetter = senderFirstLetter.toUpperCase();
            b1.setText(senderFirstLetter);
            recent1.setText(Recents[0]);
            senderFirstLetter = (String) Recents[1].subSequence(0, 1);
            senderFirstLetter = senderFirstLetter.toUpperCase();
            b2.setText(senderFirstLetter);
            recent2.setText(Recents[1]);
            senderFirstLetter = (String) Recents[2].subSequence(0, 1);
            senderFirstLetter = senderFirstLetter.toUpperCase();
            b3.setText(senderFirstLetter);
            recent3.setText(Recents[2]);
        }
        recent1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rec1= (String) recent1.getText();
                name.setText(rec1);
            }
        });
recent2.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String rec2= (String) recent2.getText();
        name.setText(rec2);
    }
});
recent3.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String rec3= (String) recent3.getText();
        name.setText(rec3);
    }
});
    }



    public void clicked(View view){

        String word1 = name.getText().toString();
        if(word1.isEmpty()){
            Toast.makeText(getApplicationContext(),"Provide word to search",Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this,"Searching "+word1.toUpperCase()+ " in Oxford Dictonary",Toast.LENGTH_LONG).show();
            new CallbackTask().execute(dictionaryEntries(word1));
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
                Intent intent = new Intent(add.this, Word_Result.class);
                intent.putExtra("word",name.getText().toString());
                intent.putExtra("meaning",def);
                intent.putExtra("example",def1);
                intent.putExtra("synonyms",synonyms);
                intent.putExtra("visibility",1);
                intent.putExtra("synonyms_array1",synonyms_array[0]);
                intent.putExtra("synonyms_array2",synonyms_array[1]);
                intent.putExtra("synonyms_array3",synonyms_array[2]);
                intent.putExtra("synonyms_array4",synonyms_array[3]);
                startActivity(intent);
                finish();

            }catch(JSONException e){
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
            }
            System.out.println(result);
        }
    }

}
