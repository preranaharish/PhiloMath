package com.rahulbuilds.philomath;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class Analytics extends AppCompatActivity {
BarChart horizontalChart;
String synonym1,synonym2,synonym3,synonym4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analytics);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            synonym1 = extras.getString("synonyms_array1");
            synonym2=extras.getString("synonyms_array2");
            synonym3=extras.getString("synonyms_array3");
            synonym4=extras.getString("synonyms_array4");
        }}}