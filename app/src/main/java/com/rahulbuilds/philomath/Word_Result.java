package com.rahulbuilds.philomath;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.data.PieDataSet;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import android.speech.tts.TextToSpeech;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.data.Entry;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;


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
    BarChart horizontalChart ;
    String synonym1,synonym2,synonym3,synonym4,note1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word__result);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
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
        horizontalChart = (BarChart)findViewById(R.id.barchart);
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
        final CardView cardView = (CardView) findViewById(R.id.arrowBtn);
        final ImageView mImgCheck = (ImageView) findViewById(R.id.btntest);
        final ImageView mImgStar = (ImageView)findViewById(R.id.ratingBar3) ;
        mImgStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                TODO: Change tint based on the enable/disable
                DrawableCompat.setTint(
                        mImgStar.getDrawable(),Color.YELLOW

                );
            }
        });


        mImgCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mImgCheck.setImageResource(R.drawable.animated_check_deactivated);
                mImgCheck.setClickable(false);

//                mImgStar.setImageDrawable(ResourcesCompat.getDrawable(getResources(),R.drawable.check_mark_deactivated,getTheme()));
//                DrawableCompat.setTint(
//                        mImgStar.getDrawable(),Color.YELLOW
//
//                );
//                mImgCheck.setBackgroundColor(0x00000000);
                ((Animatable) mImgCheck.getDrawable()).start();


            }
        });

        Button arrowbtn = (Button)findViewById(R.id.btn_arrow);
        arrowbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(cardView.isShown()){
                    Animation animSlideDown = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_up);
                    cardView.startAnimation(animSlideDown);
                    cardView.setVisibility(View.GONE);

                }
                else{
                    Animation animSlideDown = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_down);
                    cardView.startAnimation(animSlideDown);
                    cardView.setVisibility(View.VISIBLE);

                }


            }
        });







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
                xAxis.setDrawGridLines(false);
                xAxis.setValueFormatter(formatter);


                YAxis leftAxis = horizontalChart.getAxisLeft();
                leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
                leftAxis.setSpaceTop(15f);
                leftAxis.setAxisMinimum(0f);

                YAxis rightAxis = horizontalChart.getAxisRight();
                rightAxis.setDrawGridLines(false);
                rightAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
                rightAxis.setSpaceTop(15f);
                rightAxis.setAxisMinimum(0f);


//                XYMarkerView mv = new XYMarkerView(this, IndexAxisValueFormatter);
//                mv.setChartView(chart); // For bounds control
//                chart.setMarker(mv);

                Legend l = horizontalChart.getLegend();
                l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
                l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
                l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
                l.setDrawInside(false);
                l.setXEntrySpace(7f);
                l.setYEntrySpace(0f);
                l.setYOffset(0f);

                horizontalChart.setDrawValueAboveBar(false);
                horizontalChart.setData(barData);
                horizontalChart.setNoDataTextColor(2);
                horizontalChart.setFitBars(true);
                horizontalChart.getAxisLeft().setTextColor(getResources().getColor(R.color.thumb_inactive));
                horizontalChart.getAxisLeft().setAxisLineColor(getResources().getColor(R.color.thumb_inactive));
                horizontalChart.getAxisRight().setTextColor(getResources().getColor(R.color.thumb_inactive));
                horizontalChart.getAxisRight().setAxisLineColor(getResources().getColor(R.color.thumb_inactive));
                horizontalChart.getXAxis().setTextColor(getResources().getColor(R.color.thumb_inactive));
                horizontalChart.getXAxis().setTextSize(30);
                horizontalChart.animateY(5000);
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
                    Intent intent = new Intent(Word_Result.this, ListOfWords.class);
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
        PieChart pieChart = (PieChart)findViewById(R.id.CATS);
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);

        pieChart.setDragDecelerationFrictionCoef(0.95f);

//        pieChart.setCenterTextTypeface(tfLight);
        pieChart.setCenterText("CAT");
        pieChart.setCenterTextColor(Color.WHITE);

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(16777215);


        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(80);

        pieChart.setHoleRadius(60f);
        pieChart.setTransparentCircleRadius(90f);

        pieChart.setDrawCenterText(true);

        pieChart.setRotationAngle(0);
        // enable rotation of the pieChart by touch
        pieChart.setRotationEnabled(true);
        pieChart.setHighlightPerTapEnabled(true);

        // pieChart.setUnit(" €");
        // pieChart.setDrawUnitsInChart(true);

        // add a selection listener
//        pieChart.setOnChartValueSelectedListener(findViewById(R.id.CATS));

        // pieChart.spin(2000, 0, 360);

        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        // entry label styling
        pieChart.setEntryLabelColor(Color.WHITE);
//        pieChart.setEntryLabelTypeface(tfRegular);
        pieChart.setEntryLabelTextSize(12f);
        PieDataSet pieDataSet = new PieDataSet(
                getPieData(), "");

        pieDataSet.setDrawIcons(false);

        pieDataSet.setSliceSpace(3f);

        pieDataSet.setIconsOffset(new MPPointF(0, 40));
        pieDataSet.setSelectionShift(10f);
        pieDataSet.setFormLineWidth(50f);


        ArrayList<Integer> colors = new ArrayList<>();

//        for (int c : ColorTemplate.COLORFUL_COLORS)
//            colors.add(c);
//
//        for (int c : ColorTemplate.JOYFUL_COLORS)
//            colors.add(c);
//
//        for (int c : ColorTemplate.COLORFUL_COLORS)
//            colors.add(c);
//
//        for (int c : ColorTemplate.LIBERTY_COLORS)
//            colors.add(c);
//
//        for (int c : ColorTemplate.PASTEL_COLORS)
//            colors.add(c);

        colors.add(Color.WHITE);
        colors.add(getColor(R.color.colorPrimaryDark));


        pieDataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(pieDataSet);
        data.setValueFormatter(new PercentFormatter(pieChart));
        data.setValueTextSize(11f);
        data.setValueTextColor(R.color.colorPrimaryDark);
//        data.setValueTypeface(tfLight);
        pieChart.setData(data);
        pieChart.animateY(1400, Easing.EaseInOutQuad);
        pieChart.highlightValues(null);

        pieChart.invalidate();



        

//        tv = (TextView) findViewById(R.id.tv);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                // TODO Auto-generated method stub
//                while (pStatus < 100) {
//                    pStatus += 1;
//
//                    handler.postDelayed(new Runnable() {
//
//                        @Override
//                        public void run() {
//                            // TODO Auto-generated method stub
//                            mProgress.setProgress(pStatus);
//                            tv.setText(pStatus + "%");
//
//                        }
//                    },1);
//                    try {
//                        // Sleep for 200 milliseconds.
//                        // Just to display the progress slowly
//                        Thread.sleep(2); //thread will take approx 1.5 seconds to finish
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//            }
//        }).start();
//        mTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
//            @Override
//            public void onInit(int status) {
//                if (status == TextToSpeech.SUCCESS) {
//                    int result = mTTS.setLanguage(Locale.ENGLISH);
//
//                    if (result == TextToSpeech.LANG_MISSING_DATA
//                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
//                        Log.e("TTS", "Language not supported");
//                    } else {
//                    }
//                } else {
//                    Log.e("TTS", "Initialization failed");
//                }
//            }
//        });

    }

    private SpannableString generateCenterSpannableText() {

        SpannableString s = new SpannableString("CAT");
        s.setSpan(new RelativeSizeSpan(1.7f), 0, 14, 0);
        s.setSpan(new StyleSpan(Typeface.NORMAL), 3, s.length() - 15, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 3, s.length() - 15, 0);
        s.setSpan(new RelativeSizeSpan(.8f), 3, s.length() - 15, 0);
        s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 3, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 3, s.length(), 0);
        return s;
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

    private ArrayList getPieData(){
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(60f));
        entries.add(new PieEntry(40f));
        return entries;
    }






}
