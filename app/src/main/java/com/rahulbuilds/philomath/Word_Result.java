package com.rahulbuilds.philomath;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

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
    int pStatus=0;
    String synonym1,synonym2,synonym3,synonym4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word__result);
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
            //The key argument here must match that used in the other activity
        }
        TextView Letter = (TextView)findViewById(R.id.letter);
        Letter.setText(word);
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
        }
        else{
            Save.setVisibility(View.INVISIBLE);
        }
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String word1 = word+"\n";
                String mean1 = meaning;
                if(word1.equals("\n") | mean1.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Provide word and meaning",Toast.LENGTH_LONG).show();
                }
                else {
                    DBHelper dbHandler = new DBHelper(Word_Result.this);
                    dbHandler.insertUserDetails(word1, mean1,example);
                    Intent intent = new Intent(Word_Result.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(getApplicationContext(), "Word Registered", Toast.LENGTH_SHORT).show();
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
    }
    public void TakeToGraph(View view){
        Intent intent = new Intent(this,Analytics.class);
        intent.putExtra("synonyms_array1",synonym1);
        intent.putExtra("synonyms_array2",synonym2);
        intent.putExtra("synonyms_array3",synonym3);
        intent.putExtra("synonyms_array4",synonym4);
        startActivity(intent);
    }
}
