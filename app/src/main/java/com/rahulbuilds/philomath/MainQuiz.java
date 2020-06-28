package com.rahulbuilds.philomath;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class MainQuiz extends AppCompatActivity {
    public static final String EXTRA_SCORE = "extraScore";
    private static final long COUNTDOWN_IN_MILLIS = 30000;
    private TextView textViewQuestion;
    private TextView textViewScore;
    private TextView textViewQuestionCount;
    private TextView textViewCountDown;
    private RadioGroup rbGroup;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private int answer=0;
    Button option1,option2,option3;
    private Button buttonConfirmNext;
    private long backPressedTime;
    private ColorStateList textColorDefaultRb;
    private ColorStateList textColorDefaultCds;

    private CountDownTimer countDownTimer;
    private long timeLeftInMillis;

    private List<Question1> questionList;
    private int questionCounter;
    private int questionCountTotal;
    private Question1 currentQuestion;

    private int score;
    private boolean answered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_quiz);

        textViewQuestion = findViewById(R.id.text_view_question);
        textViewQuestion.setMovementMethod(new ScrollingMovementMethod());
        textViewScore = findViewById(R.id.text_view_score);
        textViewQuestionCount = findViewById(R.id.text_view_question_count);

        textViewCountDown = findViewById(R.id.text_view_countdown);
        option1 = (Button)findViewById(R.id.button_option1);
        option2 = (Button)findViewById(R.id.button_option2);
        option3 = (Button)findViewById(R.id.button_option3);
//        rbGroup = findViewById(R.id.radio_group);
//        rb1 = findViewById(R.id.radio_button1);
//        rb2 = findViewById(R.id.radio_button2);
//        rb3 = findViewById(R.id.radio_button3);
        buttonConfirmNext = findViewById(R.id.button_confirm_next);
        buttonConfirmNext.setVisibility(View.INVISIBLE);

        textColorDefaultRb = option1.getTextColors();
        textColorDefaultCds = textViewCountDown.getTextColors();

        QuizDbHelper dbHelper = new QuizDbHelper(this);
        questionList = dbHelper.getAllQuestions();
        questionCountTotal = questionList.size();
        Collections.shuffle(questionList);

        showNextQuestion();
        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answer=1;
                buttonConfirmNext.setText("Next");
                buttonConfirmNext.setVisibility(View.VISIBLE);
                checkAnswer();
            }
        });
option2.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        answer=2;
        buttonConfirmNext.setText("Next");
        buttonConfirmNext.setVisibility(View.VISIBLE);
        checkAnswer();
    }
});
option3.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        answer=3;
        buttonConfirmNext.setText("Next");
        buttonConfirmNext.setVisibility(View.VISIBLE);
        checkAnswer();

    }
});
        buttonConfirmNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    showNextQuestion();
            }
        });
    }

    private void showNextQuestion() {
        buttonConfirmNext.setVisibility(View.INVISIBLE);
        option1.setBackgroundResource(R.drawable.optionbutton);
        option2.setBackgroundResource(R.drawable.optionbutton);
        option3.setBackgroundResource(R.drawable.optionbutton);

        if (questionCounter < questionCountTotal) {
            currentQuestion = questionList.get(questionCounter);

            textViewQuestion.setText(currentQuestion.getQuestion());
            option1.setText(currentQuestion.getOption1());
            option2.setText(currentQuestion.getOption2());
            option3.setText(currentQuestion.getOption3());

            questionCounter++;
            textViewQuestionCount.setText("Question: " + questionCounter + "/" + questionCountTotal);
            answered = false;
            buttonConfirmNext.setText("Confirm");
            timeLeftInMillis = COUNTDOWN_IN_MILLIS;
            startCountDown();
        } else {
            finishQuiz();
        }
    }
    private void startCountDown() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timeLeftInMillis = 0;
                updateCountDownText();
                checkAnswer();
            }
        }.start();
    }

    private void updateCountDownText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        textViewCountDown.setText(timeFormatted);

        if (timeLeftInMillis < 10000) {
            textViewCountDown.setTextColor(Color.RED);
        } else {
            textViewCountDown.setTextColor(textColorDefaultCds);
        }
    }

    private void checkAnswer() {
        countDownTimer.cancel();
        answered = true;

        if (answer == currentQuestion.getAnswerNr()) {
            score++;
            textViewScore.setText("Score: " + score);
        }

        showSolution();
    }

    private void showSolution() {
        option1.setBackgroundResource(R.drawable.optionbuttonred);
        option2.setBackgroundResource(R.drawable.optionbuttonred);
        option3.setBackgroundResource(R.drawable.optionbuttonred);

        switch (currentQuestion.getAnswerNr()) {
            case 1:
                option1.setBackgroundResource(R.drawable.optionbuttongreen);
                break;
            case 2:
                option2.setBackgroundResource(R.drawable.optionbuttongreen);
                break;
            case 3:
                option3.setBackgroundResource(R.drawable.optionbuttongreen);
                break;
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
        finish();
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            finishQuiz();
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
            if(questionCountTotal==0){
                s="You should learn at least 10 words to unlock the quiz";
            }
            else if(s.equals("")){
                s="You have scored:"+score+" out of "+questionCountTotal;
                Intent intent = new Intent(MainQuiz.this,ListOfWords.class);
                startActivity(intent);
                finish();
            }
            Toast.makeText(MainQuiz.this, s, Toast.LENGTH_LONG).show();
        }
    }
}