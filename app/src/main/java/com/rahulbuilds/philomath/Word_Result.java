package com.rahulbuilds.philomath;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import android.speech.tts.TextToSpeech;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

public class Word_Result extends AppCompatActivity {
    String meaning;
    private Handler handler = new Handler();
    String word;
    int save_visibility;
    String example;
    String senderFirstLetter;
    TextView tv;
    String synonyms;
    String[] synonyms_array ;
    private TextToSpeech mTTS;
    int pStatus=0;
    String note;
    RelativeLayout rl;
    Question1 q;
    String options[]=new String[3];
    EditText additionalnote;
    HorizontalBarChart horizontalChart ;
    String synonym1,synonym2,synonym3,synonym4,note1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word__result);
        rl=(RelativeLayout)findViewById(R.id.relative);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            word = extras.getString("word");
             meaning = extras.getString("meaning");
             example = extras.getString("example");
             synonyms = extras.getString("synonyms");
             save_visibility = extras.getInt("visibility");
             synonym1 = extras.getString("synonyms_array1");
             synonym2=extras.getString("synonyms_array2");
             synonym3=extras.getString("synonyms_array3");
             synonym4=extras.getString("synonyms_array4");
             if (save_visibility==0){
             note1=extras.getString("note");
             note=note1;
             }

            //The key argument here must match that used in the other activity
        }
        horizontalChart = (HorizontalBarChart)findViewById(R.id.barchart);
        TextView Letter = (TextView)findViewById(R.id.letter);
        Letter.setText(word);
        additionalnote=(EditText)findViewById(R.id.Additionalnote);
        if(save_visibility==0){
            additionalnote.setText(note1);

        }
        else{
        if (additionalnote.getText().toString().isEmpty()){
            note="No additional note saved";
        }
        else{
        note=additionalnote.getText().toString();
        }}
        senderFirstLetter = (String) word.subSequence(0, 1);
        senderFirstLetter=senderFirstLetter.toUpperCase();
        Letter.setText(senderFirstLetter);
// To get random color

        TextView Word = (TextView)findViewById(R.id.word);
        Word.setText(word);
        TextView Meaning = (TextView)findViewById(R.id.meaning);
        Meaning.setText(meaning);
        TextView Example = (TextView)findViewById(R.id.examplesentence);
        Example.setText(example);
        TextView Synonyms= (TextView)findViewById(R.id.synonymsword);
        Synonyms.setText(synonyms);
        Button Save = (Button)findViewById(R.id.btnSave1);
        if(save_visibility==1) {
            Save.setVisibility(View.VISIBLE);
            if(!(synonyms.equals("synonyms not found"))){
                BarDataSet barDataSet = new BarDataSet(getData(), "Words");
                barDataSet.setBarBorderWidth(0.9f);
                barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                BarData barData = new BarData(barDataSet);
                XAxis xAxis = horizontalChart.getXAxis();
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                final String[] months = new String[]{synonym1, synonym2, synonym3,synonym4};
                IndexAxisValueFormatter formatter = new IndexAxisValueFormatter(months);
                xAxis.setGranularity(1f);
                xAxis.setValueFormatter(formatter);
                horizontalChart.setData(barData);
                horizontalChart.setNoDataTextColor(2);
                horizontalChart.setFitBars(true);
                horizontalChart.getAxisLeft().setTextColor(getResources().getColor(R.color.thumb_inactive));
                horizontalChart.getAxisLeft().setAxisLineColor(getResources().getColor(R.color.thumb_inactive));
                horizontalChart.getAxisRight().setTextColor(getResources().getColor(R.color.thumb_inactive));
                horizontalChart.getAxisRight().setAxisLineColor(getResources().getColor(R.color.thumb_inactive));
                horizontalChart.getXAxis().setTextColor(getResources().getColor(R.color.thumb_inactive));
                horizontalChart.getXAxis().setTextSize(30);
                horizontalChart.animateXY(5000, 5000);
                horizontalChart.invalidate();

            horizontalChart.setVisibility(View.VISIBLE);
            }
        }
        else{
            Save.setVisibility(View.INVISIBLE);
        }
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String word1 = word+"\n";
                String mean1 = meaning;
               DBHelper dbhelper2 = new DBHelper(Word_Result.this);
               SQLiteDatabase db2= dbhelper2.getReadableDatabase();
                Cursor cur2 = db2.rawQuery("SELECT  * FROM " + "words" + " WHERE "+" Meaning = '" +  meaning +"'" ,null);
                int exists = cur2.getCount();
                if(exists<=0){
                if(word1.equals("\n") | mean1.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Provide word and meaning",Toast.LENGTH_LONG).show();
                }
                else {
                    if(!(synonyms.equals("synonyms not found"))){
                        int random = new Random().nextInt(3);
                        DBHelper dbHelper1 = new DBHelper(Word_Result.this);
                        String countQuery = "SELECT  * FROM " + "words";
                        SQLiteDatabase db1 = dbHelper1.getReadableDatabase();
                        Cursor cursor1 = db1.rawQuery(countQuery, null);
                        int count = cursor1.getCount();
                        if (count > 3){
                        QuizDbHelper quizDbHelper=new QuizDbHelper(Word_Result.this);

                        DBHelper dbHelper = new DBHelper(Word_Result.this);
                        SQLiteDatabase db=dbHelper.getReadableDatabase();
                        Cursor c = db.rawQuery("SELECT * FROM " + "words" + " ORDER by RANDOM()  LIMIT 3;" , null);
                        int i=0;
                        if (c.moveToFirst()) {
                            do {
                                Question1 question = new Question1();
                                options[i] = c.getString(c.getColumnIndex("name"));
                                i = i + 1;
                            } while (c.moveToNext());
                        }
                            db.close();
                        options[random]=word;
                        q=new Question1(meaning,options[0],options[1],options[2],random+1);
                        quizDbHelper.addQuestion(q);
                    }
                    db1.close();
                    }
                    DBHelper dbHandler = new DBHelper(Word_Result.this);
                    note=additionalnote.getText().toString();
                    dbHandler.insertUserDetails(word1, mean1,example,synonym1,synonym2,synonym3,synonym4,note);
                    Intent intent = new Intent(Word_Result.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(getApplicationContext(), "Word Saved", Toast.LENGTH_SHORT).show();
                }}
                else{
                    Snackbar snackbar = Snackbar
                            .make(rl, "Word Already exists", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        });

        Resources res = getResources();
        Drawable drawable = res.getDrawable(R.drawable.circular);
        final ProgressBar mProgress = (ProgressBar) findViewById(R.id.CAT);
        mProgress.setProgress(50);   // Main Progress
        mProgress.setSecondaryProgress(100); // Secondary Progress
        mProgress.setMax(100); // Maximum Progress
        mProgress.setProgressDrawable(drawable);
        

        tv = (TextView) findViewById(R.id.tv);
        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                while (pStatus < 100) {
                    pStatus += 1;

                    handler.postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            mProgress.setProgress(pStatus);
                            tv.setText(pStatus + "%");

                        }
                    },1);
                    try {
                        // Sleep for 200 milliseconds.
                        // Just to display the progress slowly
                        Thread.sleep(2); //thread will take approx 1.5 seconds to finish
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }).start();
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

    }

    public void TakeToGraph(View view){
        Intent intent = new Intent(this,Analytics.class);
        intent.putExtra("synonyms_array1",synonym1);
        intent.putExtra("synonyms_array2",synonym2);
        intent.putExtra("synonyms_array3",synonym3);
        intent.putExtra("synonyms_array4",synonym4);
        startActivity(intent);
    }
  public void Phoneitc(View view){
        speak(word);
  }
    private void speak(String title1) {

        String text = title1;
        mTTS.setPitch(0.9f);
        mTTS.setSpeechRate(0.85f);
        mTTS.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }


    private ArrayList getData(){
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0f, 30f));
        entries.add(new BarEntry(1f, 80f));
        entries.add(new BarEntry(2f, 60f));
        entries.add(new BarEntry(3f, 50f));
        return entries;
    }


}
