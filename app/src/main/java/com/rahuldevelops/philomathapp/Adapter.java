package com.rahuldevelops.philomathapp;

import android.content.Context;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class Adapter extends PagerAdapter {
    private List<Model> models;
    private LayoutInflater layoutInflater;
    private Context context;

    public Adapter(List<Model> models, Context context){
        this.models=models;
        this.context=context;
       }
    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.itemforhomescreen,container,false);


        TextView title;
        TextView meaning;
        TextView example;
        TextView phonetics;
        TextView category;

        title= view.findViewById(R.id.title1);
        meaning=view.findViewById(R.id.meaning1);
        example=view.findViewById(R.id.examplesentence1);
        phonetics=view.findViewById(R.id.phoneticshome);
        category=view.findViewById(R.id.categoryhome);

        title.setText(models.get(position).getWORD());
        meaning.setText(models.get(position).getMeaning());
        example.setText(models.get(position).getExample());
        phonetics.setText(models.get(position).getPhoentics());
        category.setText(models.get(position).getCategory());
        container.addView(view,0);


        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

}
