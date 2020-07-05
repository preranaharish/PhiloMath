package com.rahulbuilds.philomath;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class HotwordsDB extends SQLiteOpenHelper {
    private static final int DB_VERSION = 3;
    public static final String DB_NAME = "RAHUL_HOMESCREEN";
    private static final String TABLE_Users = "hotwords";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "word";
    private static final String KEY_LOC = "Meaning";
    private static final String KEY_EXM = "category";
    private static final String KEY_EXAMPLES = "examples";
    private static final String KEY_PHONETICS = "phonetics";

    public HotwordsDB(Context context){
        super(context,DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(
                "create table hotwords " +
                        "(id integer primary key, word text,Meaning text,category text,examples text,phonetics text)"
        );
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        // Drop older table if exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Users);
        // Create tables again
        onCreate(db);
    }
    // **** CRUD (Create, Read, Update, Delete) Operations ***** //

    // Adding new User Details
    public void insertWordDetails(String name, String location,String examples,String example,String phonetics){
        //Get the Data Repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();
        //Create a new map of values, where column names are the keys
        ContentValues cValues = new ContentValues();
        cValues.put(KEY_NAME, name);
        cValues.put(KEY_LOC, location);
        cValues.put(KEY_EXM, examples);
        cValues.put(KEY_EXAMPLES,example);
        cValues.put(KEY_PHONETICS,phonetics);
        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(TABLE_Users,null, cValues);
        Log.d("ROWid:",""+newRowId);
        db.close();
    }
    // Get User Details

    // Get User Details based on userid
   }