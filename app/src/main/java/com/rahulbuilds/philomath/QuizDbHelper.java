package com.rahulbuilds.philomath;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by rahul on 09/04/18.
 */

public class QuizDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "EduCart2.db";
    public static int DATABASE_VERSION =2;

    private SQLiteDatabase db;

    public QuizDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuizContract.QuestionsTable.TABLE_NAME + " ( " +
                QuizContract.QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuizContract.QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuizContract.QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                QuizContract.QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                QuizContract.QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                QuizContract.QuestionsTable.COLUMN_ANSWER_NR + " INTEGER" +
                ")";

        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
//        fillQuestionsTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + QuizContract.QuestionsTable.TABLE_NAME);
        onCreate(db);
    }

//    public void fillQuestionsTable() {
//
//        Question1 q1 = new Question1(MySingleton.questions[0], MySingleton.options[0][0], MySingleton.options[0][1], MySingleton.options[0][2], MySingleton.answers[0]);
//        addQuestion(q1);
//        Question1 q2 = new Question1(MySingleton.questions[1], MySingleton.options[1][0], MySingleton.options[1][1], MySingleton.options[1][2], MySingleton.answers[1]);
//        addQuestion(q2);
//        Question1 q3 = new Question1(MySingleton.questions[2], MySingleton.options[2][0], MySingleton.options[2][1], MySingleton.options[2][2], MySingleton.answers[2]);
//        addQuestion(q3);
//        Question1 q4 = new Question1(MySingleton.questions[3], MySingleton.options[3][0], MySingleton.options[3][1], MySingleton.options[3][2], MySingleton.answers[3]);
//        addQuestion(q4);
//        Question1 q5 = new Question1(MySingleton.questions[4], MySingleton.options[4][0], MySingleton.options[4][1], MySingleton.options[4][2], MySingleton.answers[4]);
//        addQuestion(q5);
//
//    }

    public void addQuestion(Question1 question) throws SQLiteException {
        db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(QuizContract.QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuizContract.QuestionsTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuizContract.QuestionsTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuizContract.QuestionsTable.COLUMN_OPTION3, question.getOption3());
        cv.put(QuizContract.QuestionsTable.COLUMN_ANSWER_NR, question.getAnswerNr());
        db.insert(QuizContract.QuestionsTable.TABLE_NAME, null, cv);
        db.close();
    }
    public List<Question1> getAllQuestions() throws SQLiteException {
        Log.d("version of db","version:"+MySingleton.databaseversion);
        List<Question1> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuizContract.QuestionsTable.TABLE_NAME + " ORDER by "+QuizContract.QuestionsTable._ID+" DESC LIMIT 5;" , null);

        if (c.moveToFirst()) {
            do {
                Question1 question = new Question1();
                question.setQuestion(c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuizContract.QuestionsTable.COLUMN_ANSWER_NR)));
                questionList.add(question);
            } while (c.moveToNext());
        }

        c.close();
        return questionList;
    }
}
