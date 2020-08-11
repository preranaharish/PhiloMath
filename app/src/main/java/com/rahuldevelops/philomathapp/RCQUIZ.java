package com.rahuldevelops.philomathapp;



import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.rahuldevelops.philomathapp.R;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class RCQUIZ extends AppCompatActivity {
    Dialog quizdialog;
    TextView toolbarview;
    Button finishquiz;
    TextView title,scores,accuracy1,timetaken;
    ImageView quizimage,close;
    int questioncounter_solution=0;
    public boolean quizfinished=false;
    private boolean quizcompleted;
    public static final String EXTRA_SCORE = "extraScore";
    private static final long COUNTDOWN_IN_MILLIS = 900000;
    private TextView textViewQuestion,passage;
    private TextView textViewScore;
    private TextView textViewQuestionCount;
    private TextView textViewCountDown;
    private RadioGroup rbGroup;
    int minutes1,seconds1;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private RadioButton rb4;
    private Button buttonConfirmNext;
    private long backPressedTime;
    private ColorStateList textColorDefaultRb;
    private ColorStateList textColorDefaultCds;

    private CountDownTimer countDownTimer;
    private long timeLeftInMillis;
    private int answersfinal[];
    private List<RCQUESTION> questionList;
    private int questionCounter;
    private int questionCountTotal;
    private RCQUESTION currentQuestion;
    private InterstitialAd mInterstitialAd;
    private int score;
    private boolean answered;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_r_c_q_u_i_z);

        Window window =   this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.my_statusbar_color));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbarview = (TextView)findViewById(R.id.toolbarTextView);
        setSupportActionBar(toolbar);
        quizdialog = new Dialog(RCQUIZ.this);
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-2769616951461681/9929566651");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener(){

            @Override
            public void onAdClosed() {
                String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", 14-minutes1, 60-seconds1);

                quizdialog.setContentView(R.layout.customdialog);
                close=(ImageView)quizdialog.findViewById(R.id.close);
                close.setClickable(true);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(RCQUIZ.this, ListOfWords.class);
                        startActivity(intent);
                        finish();
                    }
                });
                finishquiz = (Button)quizdialog.findViewById(R.id.finishquizid);
                finishquiz.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        quizdialog.dismiss();
                        ShowSolution();
                    }
                });
                scores=(TextView)quizdialog.findViewById(R.id.scoresbox);
                accuracy1=(TextView)quizdialog.findViewById(R.id.accuracy);
                timetaken=(TextView)quizdialog.findViewById(R.id.timetaken);
                quizimage = (ImageView)quizdialog.findViewById(R.id.imagerank);
                int accuracy=((score*3*100)/(questionCountTotal*3));
                int pic;
                int beginner=R.drawable.beginner;
                int intermediate=R.drawable.intermediate;
                int master = R.drawable.master_new;
                int champion = R.drawable.champion;
                if(accuracy==100){
                    pic=champion;

                }
                else if(accuracy>=80){
                    pic=master;
                }
                else if(accuracy>=50){
                    pic=intermediate;
                }
                else{
                    pic=beginner;
                }
                quizimage.setImageResource(pic);
                scores.setText("Score: "+((score*3)-(questionCountTotal-score))+"/"+questionCountTotal*3);
                accuracy1.setText("Accuracy: "+((score*3*100)/(questionCountTotal*3))+"%");
                timetaken.setText("Time Taken: "+timeFormatted);

                quizdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                quizdialog.show();

            }
            @Override
            public void onAdFailedToLoad(int errorCode) {
                Log.d("errorcode",""+errorCode);
                // Code to be executed when an ad request fails.
            }



        });
        AdRequest adRequest = new AdRequest.Builder().build();
        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-2769616951461681/4453329957");
        // Display Banner ad
        adView.loadAd(adRequest);
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                Log.d("errorcode",""+errorCode);
Toast.makeText(RCQUIZ.this,"No solutions available without Internet connection",Toast.LENGTH_LONG).show();
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when when the user is about to return
                // to the app after tapping on an ad.

            }
        });
        textViewQuestion = findViewById(R.id.text_view_question);
        textViewQuestion.setMovementMethod(new ScrollingMovementMethod());
        textViewScore = findViewById(R.id.text_view_score);
        textViewScore.setVisibility(View.INVISIBLE);
        textViewQuestionCount = findViewById(R.id.text_view_question_count);
        passage = findViewById(R.id.passage);
        textViewCountDown = findViewById(R.id.text_view_countdown);
        rbGroup = findViewById(R.id.radio_group);
        rb1 = findViewById(R.id.radio_button1);
        rb2 = findViewById(R.id.radio_button2);
        rb3 = findViewById(R.id.radio_button3);
        rb4 = findViewById(R.id.radio_button4);
        buttonConfirmNext = findViewById(R.id.button_confirm_next);

        textColorDefaultRb = rb1.getTextColors();
        textColorDefaultCds = textViewCountDown.getTextColors();
        SharedPreferences prefs2 = getSharedPreferences("rcquiz", MODE_PRIVATE);
        int noofpassages = prefs2.getInt("rcquiz",0);

        SharedPreferences prefs3 = getSharedPreferences("rccurrent", MODE_PRIVATE);
        int current = prefs3.getInt("rccurrent",0);

        int currentpassage=0;
        if(current<noofpassages)
            currentpassage=current;
        else{
            currentpassage=0;
        }
        SharedPreferences prefs4 = getSharedPreferences("rccurrent", MODE_PRIVATE);
        SharedPreferences.Editor editor5 = prefs4.edit();
        editor5.putInt("rccurrent",currentpassage+1);
        editor5.commit();

        RCDB dbHelper = new RCDB(this);
        questionList = dbHelper.getQuestions(currentpassage);
        questionCountTotal = questionList.size();
        answersfinal = new int[questionCountTotal+1];
        Collections.shuffle(questionList);
        timeLeftInMillis = COUNTDOWN_IN_MILLIS;
        startCountDown();
        quizcompleted=false;
        showNextQuestion();

        buttonConfirmNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quizcompleted){
                    if(quizfinished)
                        finishQuiz();
                    else
                   {

                       ShowSolution();
                   }
                }
                else if (!answered) {
                    if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()) {
                        if(rb1.isChecked())
                        answersfinal[questionCounter]=1;
                        else if(rb2.isChecked())
                            answersfinal[questionCounter]=2;
                        else if(rb3.isChecked())
                            answersfinal[questionCounter]=3;
                        else if(rb4.isChecked())
                            answersfinal[questionCounter]=4;
                        checkAnswer();
                        showNextQuestion();

                    } else {
                        Toast.makeText(RCQUIZ.this, "Please select an answer", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void showNextQuestion() {
        rb1.setTextColor(textColorDefaultRb);
        rb2.setTextColor(textColorDefaultRb);
        rb3.setTextColor(textColorDefaultRb);
        rb4.setTextColor(textColorDefaultRb);
        rbGroup.clearCheck();

        if (questionCounter < questionCountTotal) {
            currentQuestion = questionList.get(questionCounter);
            passage.setText(currentQuestion.getPassage());
            textViewQuestion.setText(currentQuestion.getQuestion());
            rb1.setText(currentQuestion.getOption1());
            rb2.setText(currentQuestion.getOption2());
            rb3.setText(currentQuestion.getOption3());
            rb4.setText(currentQuestion.getOption4());
            questionCounter++;
            textViewQuestionCount.setText("Question: " + questionCounter + "/" + questionCountTotal);
            answered = false;
            buttonConfirmNext.setText("Confirm");
        } else {
            if (mInterstitialAd.isLoaded()) {
                quizcompleted=true;
                mInterstitialAd.show();
                countDownTimer.cancel();
            }
            else{
                Toast.makeText(RCQUIZ.this,"No Internet connection to show solutions",Toast.LENGTH_LONG).show();
                finishQuiz();
            }

        }
    }
    private void startCountDown() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis=millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                updateCountDownText();
            }
        }.start();
    }

    private void updateCountDownText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        minutes1=minutes;
        seconds1=seconds;
        textViewCountDown.setText(timeFormatted);
        toolbarview.setText(timeFormatted);

        if (timeLeftInMillis < 10000) {
            toolbarview.setTextColor(Color.RED);
            textViewCountDown.setTextColor(Color.RED);
        } else {
            toolbarview.setTextColor(textColorDefaultCds);
            textViewCountDown.setTextColor(textColorDefaultCds);
        }
    }

    private void checkAnswer() {

        answered = true;

        RadioButton rbSelected = findViewById(rbGroup.getCheckedRadioButtonId());
        int answerNr = rbGroup.indexOfChild(rbSelected) + 1;

        if (answerNr == currentQuestion.getAnswerNr()) {
            score++;
            textViewScore.setText("Score: " + score);
        }

    }

    private void showSolution() {
        rb1.setTextColor(Color.RED);
        rb2.setTextColor(Color.RED);
        rb3.setTextColor(Color.RED);
        rb4.setTextColor(Color.RED);
        switch (currentQuestion.getAnswerNr()) {
            case 1:
                rb1.setTextColor(Color.GREEN);
                textViewQuestion.setText("Answer 1 is correct");
                break;
            case 2:
                rb2.setTextColor(Color.GREEN);
                textViewQuestion.setText("Answer 2 is correct");
                break;
            case 3:
                rb3.setTextColor(Color.GREEN);
                textViewQuestion.setText("Answer 3 is correct");
                break;
            case 4:
                rb4.setTextColor(Color.GREEN);
                textViewQuestion.setText("Answer 4 is correct");
        }

        if (questionCounter < questionCountTotal) {
            buttonConfirmNext.setText("Next");
        } else {
            buttonConfirmNext.setText("Finish");
        }
    }

    private void finishQuiz() {
        BackGround b = new BackGround();
        b.execute();
        Intent resultIntent = new Intent();
        resultIntent.putExtra(EXTRA_SCORE, score);
        setResult(RESULT_OK, resultIntent);
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            Intent intent = new Intent(RCQUIZ.this, ListOfWords.class);
            startActivity(intent);
            finish();

        } else {
            Toast.makeText(this, "Press back again to finish", Toast.LENGTH_SHORT).show();
        }

        backPressedTime = System.currentTimeMillis();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

    }
    class BackGround extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String data="";
            return data;
        }
        @Override
        protected void onPreExecute() {
        }
        @Override
        protected void onPostExecute(String s) {
                Intent intent = new Intent(RCQUIZ.this, ListOfWords.class);
                startActivity(intent);
                finish();

            if(s.equals("")){
                s="You have scored:"+score+" out of 5";

            }
        }
    }


    public void ShowSolution(){
if(questioncounter_solution<questionCountTotal){
    textViewQuestionCount.setText(questioncounter_solution+"/"+questionCountTotal);
    rb1.setTextColor(textColorDefaultRb);
    rb2.setTextColor(textColorDefaultRb);
    rb3.setTextColor(textColorDefaultRb);
    rb4.setTextColor(textColorDefaultRb);
    rbGroup.clearCheck();


    RCQUESTION current = questionList.get(questioncounter_solution);
    int answerselected=answersfinal[questioncounter_solution+1];


    passage.setText(current.getPassage());
    textViewQuestion.setText(current.getQuestion());
    rb1.setText(current.getOption1());
    rb2.setText(current.getOption2());
    rb3.setText(current.getOption3());
    rb4.setText(current.getOption4());
    textViewQuestionCount.setText("Question: " + questionCounter + "/" + questionCountTotal);
    rb1.setTextColor(Color.RED);
    rb2.setTextColor(Color.RED);
    rb3.setTextColor(Color.RED);
    rb4.setTextColor(Color.RED);
if(answerselected==1)
    rb1.setChecked(true);
if(answerselected==2)
    rb2.setChecked(true);
if(answerselected==3)
    rb3.setChecked(true);
if(answerselected==4)
    rb4.setChecked(true);

    switch (current.getAnswerNr()) {
        case 1:
            rb1.setTextColor(Color.GREEN);
            textViewQuestion.setText(current.getQuestion());
            break;
        case 2:
            rb2.setTextColor(Color.GREEN);
            textViewQuestion.setText(current.getQuestion());
            break;
        case 3:
            rb3.setTextColor(Color.GREEN);
            textViewQuestion.setText(current.getQuestion());
            break;
        case 4:
            rb4.setTextColor(Color.GREEN);
            textViewQuestion.setText(current.getQuestion());
    }
    questioncounter_solution++;

    if (questioncounter_solution < questionCountTotal) {
        buttonConfirmNext.setText("Next");
    } else {
        quizfinished=true;
        buttonConfirmNext.setText("Finish");
    }



}

    }


}
