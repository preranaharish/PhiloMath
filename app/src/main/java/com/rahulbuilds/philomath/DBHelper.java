package com.rahulbuilds.philomath;

/**
 * Created by rahul on 09/06/19.
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 2;
    public static final String DB_NAME = "RAHUL1";
    private static final String TABLE_Users = "words";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_LOC = "Meaning";
    private static final String KEY_EXM = "Examples";
    private static final String SYN1= "synonym1";
    private static final String SYN2= "synonym2";
    private static final String SYN3= "synonym3";
    private static final String SYN4= "synonym4";
    private static final String NOTE= "Note";
    public DBHelper(Context context){
        super(context,DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(
                "create table words " +
                        "(id integer primary key, name text,Meaning text,Examples text,synonym1 text,synonym2 text,synonym3 text,synonym4 text,Note text)"
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
    void insertUserDetails(String name, String location,String examples,String sy1,String sy2,String sy3,String sy4,String note){
        //Get the Data Repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();
        //Create a new map of values, where column names are the keys
        ContentValues cValues = new ContentValues();
        cValues.put(KEY_NAME, name);
        cValues.put(KEY_LOC, location);
        cValues.put(KEY_EXM, examples);
        cValues.put(SYN1, sy1);
        cValues.put(SYN2, sy2);
        cValues.put(SYN3, sy3);
        cValues.put(SYN4, sy4);
        cValues.put(NOTE, note);
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
            Log.d("Synonym1",cursor.getString(cursor.getColumnIndex(SYN1)));
            user.put("synonym1",cursor.getString(cursor.getColumnIndex(SYN1)));
            Log.d("Synonym2",cursor.getString(cursor.getColumnIndex(SYN2)));
            user.put("synonym2",cursor.getString(cursor.getColumnIndex(SYN2)));
            Log.d("Synonym3",cursor.getString(cursor.getColumnIndex(SYN3)));
            user.put("synonym3",cursor.getString(cursor.getColumnIndex(SYN3)));
            Log.d("Synonym4",cursor.getString(cursor.getColumnIndex(SYN4)));
            user.put("synonym4",cursor.getString(cursor.getColumnIndex(SYN4)));
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