package com.rahulbuilds.philomath;

import android.content.Context;
import android.net.Uri;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

/**
 * Created by rahul on 20-08-2017.
 */

public class MySingleton {
    public static int databaseversion;
    public static int lock;
    public static String msg;
    public static String tip;
    public static String uid;
    public static String questions[]=new String[5];
    public static String options[][]=new String[5][3];
    public static int answers[]=new int[5];
    private static MySingleton mInstance;
    public static String name;
    public static String email;
    public static ArrayList<String> arr=new ArrayList<>();
    public static String url;
    public static Uri urls;
    private static String baseUrl;
    private static Context context;
    private RequestQueue requestQueue;
    public static int curCount = 0;
    public static int maxCount = 0;
    public static String user;
    public static int updatedelay = 180;

    private MySingleton(Context context){
        this.context = context;
        requestQueue = getRequestQueue();
    }
    public static String getUrl(){
        return baseUrl;
    }
    public static void setUrl(String Url){
        baseUrl = Url;
    }
    private RequestQueue getRequestQueue()
    {
        if(requestQueue==null)
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        return requestQueue;
    }
    public static synchronized MySingleton getmInstance(Context context){
        if(mInstance==null)
            mInstance = new MySingleton((context));
        return mInstance;
    }
    public static ArrayList<String> getStringArrayList(){
        return arr;
    }
    public <T>void addToRequestQue(Request<T> request)
    {
        getRequestQueue().add(request);
    }
}
