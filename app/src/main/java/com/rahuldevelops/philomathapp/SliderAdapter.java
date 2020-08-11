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
           R.drawable.notify1,
            R.drawable.quiz1
    };

    public String[] heading={
            "PHILOSEARCH",
            "REVISE & REMEMBER",
            "RC and REVISION\n TESTS"
    };
    public String[] messages={
            "Integrate tool to your android phone : Bookmark words from any app",
            "Regular reminders of the words you learn to help you retain the words",
            "Take Quiz from our collection of 200+ RCs which includes previous year CAT/IIFT/XAT questions and other official guides,\nRevison quiz to revise the words you learnt"};
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
        TextView message = (TextView)view.findViewById(R.id.messages);

        iv.setImageResource(slideimage[position]);
        message.setText(messages[position]);

        container.addView(view);
        return  view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);
    }
}
