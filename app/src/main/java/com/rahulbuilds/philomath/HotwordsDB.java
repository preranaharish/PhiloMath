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
    private static final int DB_VERSION = 2;
    public static final String DB_NAME = "RAHUL_HOMESCREEN";
    private static final String TABLE_Users = "hotwords";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "word";
    private static final String KEY_LOC = "Meaning";
    private static final String KEY_EXM = "category";
    public HotwordsDB(Context context){
        super(context,DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(
                "create table hotwords " +
                        "(id integer primary key, word text,Meaning text,category text)"
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
    public void insertWordDetails(String name, String location,String examples){
        //Get the Data Repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();
        //Create a new map of values, where column names are the keys
        ContentValues cValues = new ContentValues();
        cValues.put(KEY_NAME, name);
        cValues.put(KEY_LOC, location);
        cValues.put(KEY_EXM, examples);
        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(TABLE_Users,null, cValues);
        Log.d("ROWid:",""+newRowId);
        db.close();
    }
    // Get User Details
    public ArrayList<HashMap<String, String>> GetUsers(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = "SELECT name, Meaning ,Examples FROM "+ TABLE_Users;
        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            HashMap<String,String> user = new HashMap<>();
            Log.d("Name",cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            user.put("name",cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            Log.d("Meaning:",cursor.getString(cursor.getColumnIndex(KEY_LOC)));
            user.put("Meaning",cursor.getString(cursor.getColumnIndex(KEY_LOC)));
            Log.d("Examples",cursor.getString(cursor.getColumnIndex(KEY_EXM)));
            user.put("Examples",cursor.getString(cursor.getColumnIndex(KEY_EXM)));
            userList.add(user);
        }
        return  userList;
    }
    // Get User Details based on userid
    public ArrayList<HashMap<String, String>> GetUserByUserId(int userid){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = "SELECT name, location, designation FROM "+ TABLE_Users;
        Cursor cursor = db.query(TABLE_Users, new String[]{KEY_NAME, KEY_LOC}, KEY_ID+ "=?",new String[]{String.valueOf(userid)},null, null, null, null);
        if (cursor.moveToNext()){
            HashMap<String,String> user = new HashMap<>();
            user.put("name",cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            user.put("Meaning",cursor.getString(cursor.getColumnIndex(KEY_LOC)));
            user.put("Examples",cursor.getString(cursor.getColumnIndex(KEY_EXM)));
            userList.add(user);
        }
        return  userList;
    }
    // Delete User Details
    public void DeleteUser(int userid){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_Users, KEY_ID+" = ?",new String[]{String.valueOf(userid)});
        db.close();

    }}