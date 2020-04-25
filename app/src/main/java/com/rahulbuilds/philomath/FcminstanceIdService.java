package com.rahulbuilds.philomath;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by rahul on 20-08-2017.
 */

public class FcminstanceIdService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh(){
        String fcm_token = FirebaseInstanceId.getInstance().getToken();
        Log.d("FCM_TOKEN",fcm_token);
    }
}
