package com.rahuldevelops.philomathapp;


import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.rahuldevelops.philomathapp.Manifest;
import com.rahuldevelops.philomathapp.R;

import static com.rahuldevelops.philomathapp.SignIn.SHARED_PREFS;

public class settings_screen extends AppCompatActivity {
    String usernametext,emailtext,url;
Switch reminder;
    String TAG = "RemindMe";
    private long backPressedTime;
    LocalData localData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_screen);
        SharedPreferences prefs1 = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        usernametext = prefs1.getString("keyname", "Unknown");
        emailtext = prefs1.getString("Email", "Unknown");
        url = prefs1.getString("url","Unknown");

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        TextView username = (TextView) findViewById(R.id.usernames);
        username.setText(usernametext);
        TextView email = (TextView) findViewById(R.id.emails);
        email.setText(emailtext);
        ImageView imgProfilePic = (ImageView)findViewById(R.id.profilepics);
        try{

            Glide.with(getApplicationContext()).load(url).apply(new RequestOptions().circleCrop())
                    .thumbnail(0.8f)
                    .into(imgProfilePic);
        }catch (Exception e){
            imgProfilePic.setVisibility(View.INVISIBLE);
        }
        final LinearLayout l = (LinearLayout)findViewById(R.id.settings);
        localData = new LocalData(getApplicationContext());
        Button backup = (Button)findViewById(R.id.Backup);
        backup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isStoragePermissionGranted()) {
                    if (Backup.exportDB(settings_screen.this)){
                Backup.exportDB(settings_screen.this);
                Snackbar.make(l,"Backup of words complete",Snackbar.LENGTH_LONG).show();
                }}
                else{
                    Snackbar.make(l, "Something went wrong", Snackbar.LENGTH_LONG).show();
                }
            }
        });
        Button restore = (Button)findViewById(R.id.restore);
        restore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isStoragePermissionGranted()) {
                    if (Backup.importDB(settings_screen.this)) {
                        Backup.importDB(settings_screen.this);
                        Snackbar.make(l, "Restore of words complete, please reopen the app", Snackbar.LENGTH_LONG).show();
                    } else {

                        Snackbar.make(l, "Something went wrong", Snackbar.LENGTH_LONG).show();
                    }
                }}});
        Button contactus = (Button)findViewById(R.id.contactus);
        contactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto","rahulmattihalli@gmail.com", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Contact Developers");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Suggestions/Complaint");
                startActivity(Intent.createChooser(emailIntent, "Send email..."));
            }
        });
        Button logout = (Button)findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        logout();
               Intent intent = new Intent(settings_screen.this, SignIn.class);
               startActivity(intent);
               finish();

            }
        });
        SharedPreferences prefs = getSharedPreferences(TAG, MODE_PRIVATE);
        int checked = prefs.getInt("set", 1);
        Switch reminderSwitch = (Switch)findViewById(R.id.notify);
        if(checked==1)
        reminderSwitch.setChecked(true);
        else
            reminderSwitch.setChecked(false);
        reminderSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                localData.setReminderStatus(isChecked);
                if (isChecked) {
                    SharedPreferences.Editor editor = getSharedPreferences(TAG, MODE_PRIVATE).edit();
                    editor.putInt("set", 1);
                    editor.commit();
                    Log.d(TAG, "onCheckedChanged: true");
                    NotificationScheduler.setReminder(settings_screen.this, AlarmReceiver.class, 10, 30);
                    Log.d(TAG, "Yes: Reminder set");
                    Snackbar.make(l,"Hourly Notifications of words enabled",Snackbar.LENGTH_LONG).show();
                } else {
                    SharedPreferences.Editor editor = getSharedPreferences(TAG, MODE_PRIVATE).edit();
                    editor.putInt("set", 0);
                    editor.commit();
                    Log.d(TAG, "onCheckedChanged: false");
                    NotificationScheduler.cancelReminder(settings_screen.this, AlarmReceiver.class);
                    Snackbar.make(l,"Hourly Notifications of words disabled",Snackbar.LENGTH_LONG).show();
                }

            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(settings_screen.this, HomeScreen.class);
            startActivity(intent);
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted");
                return true;
            } else {

                Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted");
            return true;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Log.v(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);
            //resume tasks needing this permission
        }
    }
    public void logout(){
        SignIn.signOut();
    }
    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            finishAffinity();
            finish();
        } else {
            Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();
        }

        backPressedTime = System.currentTimeMillis();
    }

}
