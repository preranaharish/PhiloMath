package com.rahulbuilds.philomath;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RCDB extends SQLiteOpenHelper {
    private static final int DB_VERSION = 3;
    public static final String DB_NAME = "RAHUL_RC";
    private static final String TABLE_Users = "rcpassage";
    private static final String KEY_ID = "id";
    private static final String PASSAGE_ID="passage_id";
    private static final String KEY_PASSAGE = "passage";
    private static final String KEY_QUESTIONS = "questions";
    private static final String KEY_OPTION1 = "option1";
    private static final String KEY_OPTION2 = "option2";
    private static final String KEY_OPTION3 = "option3";
    private static final String KEY_OPTION4 = "option4";
    private static final String KEY_ANSWERS = "answer";

    public RCDB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table rcpassage " +
                        "(id integer primary key, passage_id int, passage text,questions text,option1 text,option2 text,option3 text,option4 text,answer int)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Users);
        // Create tables again
        onCreate(db);
    }
    // **** CRUD (Create, Read, Update, Delete) Operations ***** //

    // Adding new User Details
    public void insertquestion(int id,String passage, String question, String option1, String option2, String option3, String option4, int answer) {
        //Get the Data Repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();
        //Create a new map of values, where column names are the keys
        ContentValues cValues = new ContentValues();
        cValues.put(PASSAGE_ID,id);
        cValues.put(KEY_PASSAGE, passage);
        cValues.put(KEY_QUESTIONS, question);
        cValues.put(KEY_OPTION1, option1);
        cValues.put(KEY_OPTION2, option2);
        cValues.put(KEY_OPTION3, option3);
        cValues.put(KEY_OPTION4, option4);
        cValues.put(KEY_ANSWERS, answer);
        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(TABLE_Users, null, cValues);
        Log.d("ROWid:", "" + newRowId);
        db.close();
    }
    // Get User Details



    public List<RCQUESTION> getQuestions(int x) throws SQLiteException {
        Log.d("version of db","version:"+MySingleton.databaseversion);
        List<RCQUESTION> questionList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + "rcpassage" + " WHERE passage_id = ? " +";", new String[]{Integer.toString(x)});
        if (c.moveToFirst()) {
            do {
                RCQUESTION question = new RCQUESTION();
                question.setPassage(c.getString(c.getColumnIndex(KEY_PASSAGE)));
                question.setQuestion(c.getString(c.getColumnIndex(KEY_QUESTIONS)));
                question.setOption1(c.getString(c.getColumnIndex(KEY_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(KEY_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(KEY_OPTION3)));
                question.setOption4(c.getString(c.getColumnIndex(KEY_OPTION4)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(KEY_ANSWERS)));
                questionList.add(question);
            } while (c.moveToNext());
        }

        c.close();
        return questionList;
    }

    // Get User Details based on userid
}