package com.rahuldevelops.philomathapp;



import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rahuldevelops.philomathapp.R;

public class Guidescreen extends AppCompatActivity {

    private ViewPager viewPager;
    private LinearLayout linearLayout;
    private TextView dots[];
    Button prev,next;
    private int screen;

    private SliderAdapter sliderAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guidescreen);
        Window window =   this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.my_statusbar_color));

        isStoragePermissionGranted();
        SharedPreferences prefer = getSharedPreferences("guide",MODE_PRIVATE);
        SharedPreferences.Editor editor = prefer.edit();
        editor.putInt("guide",1);
        editor.commit();

        viewPager = (ViewPager)findViewById(R.id.pager);
        linearLayout=(LinearLayout) findViewById(R.id.dots);
        prev = (Button)findViewById(R.id.prev);
        next=(Button)findViewById(R.id.next);
        sliderAdapter = new SliderAdapter(this);
        viewPager.setAdapter(sliderAdapter);
        adddots(0);
        viewPager.addOnPageChangeListener(viewlistener);

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(screen-1);
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(screen==dots.length-1){
                    Intent intent = new Intent(Guidescreen.this, splash.class);
                    startActivity(intent);
                    finish();
                }
                viewPager.setCurrentItem(screen+1);
            }
        });

    }

    public void adddots(int position){
        dots = new TextView[3];
        linearLayout.removeAllViews();
        for(int i=0;i<dots.length;i++){
            dots[i]=new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(55);
            dots[i].setTextColor(getResources().getColor(R.color.transparentwhite));

            linearLayout.addView(dots[i]);

        }
    if(dots.length>0){
        dots[position].setTextColor(Color.WHITE);
    }
    }
        ViewPager.OnPageChangeListener viewlistener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
adddots(i);
screen=i;
if(screen==0){
    prev.setEnabled(false);
    next.setEnabled(true);
    prev.setText("PREV");
    prev.setVisibility(View.INVISIBLE);
    next.setText("NEXT");
    next.setVisibility(View.VISIBLE);
}
else if(screen==dots.length-1){
    prev.setEnabled(true);
    next.setEnabled(true);
    prev.setText("PREV");
    prev.setVisibility(View.VISIBLE);
    next.setText("FINISH");
    next.setVisibility(View.VISIBLE);
}
else{
    prev.setEnabled(true);
    next.setEnabled(true);
    prev.setText("PREV");
    prev.setVisibility(View.VISIBLE);
    next.setText("NEXT");
    next.setVisibility(View.VISIBLE);
}
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        };

    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            //resume tasks needing this permission
        }
    }
}
