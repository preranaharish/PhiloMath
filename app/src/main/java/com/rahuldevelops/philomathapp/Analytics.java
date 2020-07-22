package com.rahuldevelops.philomathapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.rahuldevelops.philomathapp.R;

public class Analytics extends AppCompatActivity {
HorizontalBarChart horizontalChart;
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