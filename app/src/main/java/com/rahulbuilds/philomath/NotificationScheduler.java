package com.rahulbuilds.philomath;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import java.util.Calendar;
import java.util.concurrent.ThreadLocalRandom;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by rahul on 09/06/19.
 */
public class NotificationScheduler
{
    public static final int DAILY_REMINDER_REQUEST_CODE=100;
    static final int DAILY_REMINDER_REQUEST_CODE1=101;
    static final int DAILY_REMINDER_REQUEST_CODE2=102;
    static final int DAILY_REMINDER_REQUEST_CODE3=103;
    static final int DAILY_REMINDER_REQUEST_CODE4=104;
    static final int DAILY_REMINDER_REQUEST_CODE5=105;
    static final int DAILY_REMINDER_REQUEST_CODE6=106;
    static final int DAILY_REMINDER_REQUEST_CODE7=107;
    static final int DAILY_REMINDER_REQUEST_CODE8=108;
    static final int DAILY_REMINDER_REQUEST_CODE9=109;
    static final int DAILY_REMINDER_REQUEST_CODE10=110;
    static final int DAILY_REMINDER_REQUEST_CODE12=111;
    static final int DAILY_REMINDER_REQUEST_CODE13=112;
    static final int DAILY_REMINDER_REQUEST_CODE14=113;
    public static final String TAG="NotificationScheduler";
    public static  final  int h=8;
    public static final int m=9;
    public static int random ;
    private static final String TABLE_Users = "words";
    public static void setReminder(Context context, Class<?> cls, int hour, int min)
    {
        Calendar calendar = Calendar.getInstance();



        Calendar setcalendar = Calendar.getInstance();
        setcalendar.set(Calendar.HOUR_OF_DAY, 10);
        setcalendar.set(Calendar.MINUTE, 30);
        setcalendar.set(Calendar.SECOND, 0);

        Calendar calendar1 = Calendar.getInstance();
        Calendar setcalendar1 = Calendar.getInstance();
        setcalendar1.set(Calendar.HOUR_OF_DAY,11);
        setcalendar1.set(Calendar.MINUTE,30);
        setcalendar1.set(Calendar.SECOND,0);

        Calendar calendar2 = Calendar.getInstance();
        Calendar setcalendar2 = Calendar.getInstance();
        setcalendar2.set(Calendar.HOUR_OF_DAY,12);
        setcalendar2.set(Calendar.MINUTE,30);
        setcalendar2.set(Calendar.SECOND,0);


        Calendar calendar3 = Calendar.getInstance();
        Calendar setcalendar3 = Calendar.getInstance();
        setcalendar3.set(Calendar.HOUR_OF_DAY,14);
        setcalendar3.set(Calendar.MINUTE,30);
        setcalendar3.set(Calendar.SECOND,0);


        Calendar calendar4 = Calendar.getInstance();
        Calendar setcalendar4 = Calendar.getInstance();
        setcalendar4.set(Calendar.HOUR_OF_DAY,17);
        setcalendar4.set(Calendar.MINUTE,30);
        setcalendar4.set(Calendar.SECOND,0);

        Calendar calendar5 = Calendar.getInstance();
        Calendar setcalendar5 = Calendar.getInstance();
        setcalendar5.set(Calendar.HOUR_OF_DAY,18);
        setcalendar5.set(Calendar.MINUTE,30);
        setcalendar5.set(Calendar.SECOND,0);




        // cancel already scheduled reminders

        if(setcalendar.before(calendar))
            setcalendar.add(Calendar.DATE,1);

        if(setcalendar1.before(calendar1))
            setcalendar1.add(Calendar.DATE,1);

        if(setcalendar2.before(calendar2))
            setcalendar2.add(Calendar.DATE,1);
        if(setcalendar3.before(calendar3))
            setcalendar3.add(Calendar.DATE,1);
        if(setcalendar4.before(calendar4))
            setcalendar4.add(Calendar.DATE,1);

        if(setcalendar4.before(calendar5))
            setcalendar4.add(Calendar.DATE,1);



        // Enable a receiver

        ComponentName receiver = new ComponentName(context, cls);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);


        Intent intent1 = new Intent(context, cls);
        Intent intent2 = new Intent(context,cls);
        Intent intent3 = new Intent(context,cls);
        Intent intent4 = new Intent(context,cls);
        Intent intent5 = new Intent(context,cls);
        Intent intent6 = new Intent(context,cls);


        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, DAILY_REMINDER_REQUEST_CODE, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent pendingIntent1 = PendingIntent.getBroadcast(context,DAILY_REMINDER_REQUEST_CODE1,intent2,PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent pendingIntent2 = PendingIntent.getBroadcast(context,DAILY_REMINDER_REQUEST_CODE2,intent3,PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent pendingIntent3 = PendingIntent.getBroadcast(context,DAILY_REMINDER_REQUEST_CODE3,intent4,PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent pendingIntent4 = PendingIntent.getBroadcast(context,DAILY_REMINDER_REQUEST_CODE4,intent5,PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent pendingIntent5 = PendingIntent.getBroadcast(context,DAILY_REMINDER_REQUEST_CODE5,intent6,PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager am = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        am.setInexactRepeating(AlarmManager.RTC_WAKEUP, setcalendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);


        AlarmManager am2 = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        am2.setInexactRepeating(AlarmManager.RTC_WAKEUP, setcalendar1.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent1);
        AlarmManager am3 = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        am3.setInexactRepeating(AlarmManager.RTC_WAKEUP, setcalendar2.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent2);
        AlarmManager am4 = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        am4.setInexactRepeating(AlarmManager.RTC_WAKEUP, setcalendar3.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent3);


        AlarmManager am1 = (AlarmManager)context.getSystemService(ALARM_SERVICE);
        am1.setInexactRepeating(AlarmManager.RTC_WAKEUP,setcalendar4.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent4);

        AlarmManager am5 = (AlarmManager)context.getSystemService(ALARM_SERVICE);
        am5.setInexactRepeating(AlarmManager.RTC_WAKEUP,setcalendar5.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent5);




    }

    public static void cancelReminder(Context context,Class<?> cls)
    {
        // Disable a receiver

        ComponentName receiver = new ComponentName(context, cls);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);

        Intent intent1 = new Intent(context, cls);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, DAILY_REMINDER_REQUEST_CODE, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        am.cancel(pendingIntent);
        pendingIntent.cancel();
        Intent intent2 = new Intent(context, cls);
        PendingIntent pendingIntent2 = PendingIntent.getBroadcast(context, DAILY_REMINDER_REQUEST_CODE1, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am1 = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        am1.cancel(pendingIntent);
        pendingIntent2.cancel();
        Intent intent3 = new Intent(context, cls);
        PendingIntent pendingIntent3 = PendingIntent.getBroadcast(context, DAILY_REMINDER_REQUEST_CODE2, intent3, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am3 = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        am3.cancel(pendingIntent);
        pendingIntent3.cancel();
        Intent intent4 = new Intent(context, cls);
        PendingIntent pendingIntent4 = PendingIntent.getBroadcast(context, DAILY_REMINDER_REQUEST_CODE3, intent4, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am4 = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        am4.cancel(pendingIntent);
        pendingIntent4.cancel();
        Intent intent5 = new Intent(context, cls);
        PendingIntent pendingIntent5 = PendingIntent.getBroadcast(context, DAILY_REMINDER_REQUEST_CODE4, intent5, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am5 = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        am5.cancel(pendingIntent);
        pendingIntent5.cancel();
    }

    public static void showNotification(Context context,Class<?> cls,String title,String content)
    {

        DBHelper dbHandler = new DBHelper(context);

        String countQuery = "SELECT  * FROM " + TABLE_Users;
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        Cursor cursor1 = db.rawQuery("SELECT * FROM " + "words" + " ORDER by RANDOM()  LIMIT 1;",null);
        int count = cursor1.getCount();
        if (count <= 1){
            random = 1;
        }
        else{
            random = ThreadLocalRandom.current().nextInt(1, count);
        }
        String val=String.valueOf(random);
        Log.d("count:","no:"+count);
        SQLiteDatabase sqlDB = dbHandler.getWritableDatabase();

        Cursor cursor = sqlDB.rawQuery("SELECT * FROM " + "words" + " ORDER by RANDOM()  LIMIT 1;" , null);

        if (cursor != null && cursor.moveToFirst()) {
            String title1 = cursor.getString(cursor.getColumnIndex("name"));
            String content1 = cursor.getString(cursor.getColumnIndex("Meaning"));
            title=title1;
            content=content1;
            Log.d("title",title1);
            Log.d("Content",    content1);
        }

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent notificationIntent = new Intent(context, cls);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(cls);
        stackBuilder.addNextIntent(notificationIntent);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(DAILY_REMINDER_REQUEST_CODE, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        Notification notification = builder.setContentTitle(title)
                .setContentText(content)

                .setAutoCancel(true)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(content))
                .setSmallIcon(R.drawable.icon)
                .setContentIntent(pendingIntent).build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(DAILY_REMINDER_REQUEST_CODE, notification);

    }

}
