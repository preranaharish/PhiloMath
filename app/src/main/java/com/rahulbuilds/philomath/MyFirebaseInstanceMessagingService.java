package com.rahulbuilds.philomath;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by rahul on 24-07-2017.
 */

public class MyFirebaseInstanceMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData().size() > 0) {
            String title,message,img_url;
            title = remoteMessage.getData().get("title");
            message = remoteMessage.getData().get("message");
            Intent intent = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
            Uri sounduri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            final NotificationCompat.Builder notification_Builder = new NotificationCompat.Builder(this);
            notification_Builder.setContentTitle(title);
            notification_Builder.setContentText(message);
            notification_Builder.setContentIntent(pendingIntent);
            notification_Builder.setSound(sounduri);
            notification_Builder.setSmallIcon(R.drawable.icon);

        }
    }
    @Override
    public void onNewToken(String token) {
       Log.d("Token:",token);
        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
    }
}