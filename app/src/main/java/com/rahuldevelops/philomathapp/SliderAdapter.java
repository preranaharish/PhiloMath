package com.rahuldevelops.philomathapp;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rahuldevelops.philomathapp.R;

import pl.droidsonroids.gif.GifImageView;

public class SliderAdapter extends PagerAdapter {
Context context;
LayoutInflater layoutInflater;
    public SliderAdapter(Context context){
        this.context= context;

    }

    public int[] slideimage={
            R.drawable.guides,
           R.drawable.notify,
            R.drawable.quiz
    };

    public String[] heading={
            "PHILOSEARCH",
            "REVISE & REMEMBER",
            "READING COMPREHENSION\n QUIZ"
    };
    public String[] messages={
            "You can search and bookmark for the new word you come across in any app installed in your phone\n its not just an app it's an integrated tool in your android phone",
            "Remembering new english words is hard unless someone repeatedly remind you the word and it's meaning\n Don't worry Philomath is just trained to do that\n Regular notifications of the words you learn to help you retain the words",
            "Take Quiz from our collection of 200+ RCs which includes previous year CAT/IIFT/XAT questions\n 'Practise makes man perfect'\n So Don't forget to take words quiz which will generate a quiz from your bookmarked words list"
    };
    @Override
    public int getCount() {
        return slideimage.length;
    }



    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == (LinearLayout) o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view  = layoutInflater.inflate(R.layout.guidelayout,container,false);
        GifImageView iv = (GifImageView)view.findViewById(R.id.guides);
        TextView slideviewheading = (TextView)view.findViewById(R.id.heading);
        TextView message = (TextView)view.findViewById(R.id.messages);

        iv.setImageResource(slideimage[position]);
        slideviewheading.setText(heading[position]);
        message.setText(messages[position]);

        container.addView(view);
        return  view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);
    }
}
