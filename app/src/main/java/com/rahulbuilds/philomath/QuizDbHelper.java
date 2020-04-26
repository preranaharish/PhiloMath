package com.rahulbuilds.philomath;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rahul on 09/04/18.
 */

public class QuizDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "EduCart2.db";
    private static final int DATABASE_VERSION =MySingleton.databaseversion;

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
        fillQuestionsTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + QuizContract.QuestionsTable.TABLE_NAME);
        onCreate(db);
    }

    private void fillQuestionsTable() {

        Question1 q1 = new Question1(MySingleton.questions[0], MySingleton.options[0][0], MySingleton.options[0][1], MySingleton.options[0][2], MySingleton.answers[0]);
        addQuestion(q1);
        Question1 q2 = new Question1(MySingleton.questions[1], MySingleton.options[1][0], MySingleton.options[1][1], MySingleton.options[1][2], MySingleton.answers[1]);
        addQuestion(q2);
        Question1 q3 = new Question1(MySingleton.questions[2], MySingleton.options[2][0], MySingleton.options[2][1], MySingleton.options[2][2], MySingleton.answers[2]);
        addQuestion(q3);
        Question1 q4 = new Question1(MySingleton.questions[3], MySingleton.options[3][0], MySingleton.options[3][1], MySingleton.options[3][2], MySingleton.answers[3]);
        addQuestion(q4);
        Question1 q5 = new Question1(MySingleton.questions[4], MySingleton.options[4][0], MySingleton.options[4][1], MySingleton.options[4][2], MySingleton.answers[4]);
        addQuestion(q5);
        Question1 q6 = new Question1(MySingleton.questions[5], MySingleton.options[5][0], MySingleton.options[5][1], MySingleton.options[5][2], MySingleton.answers[5]);
        addQuestion(q6);
       Question1 q7 = new Question1(MySingleton.questions[6], MySingleton.options[6][0], MySingleton.options[6][1], MySingleton.options[6][2], MySingleton.answers[6]);
        addQuestion(q7);
        Question1 q8 = new Question1( MySingleton.questions[7], MySingleton.options[7][0], MySingleton.options[7][1], MySingleton.options[7][2], MySingleton.answers[7]);
        addQuestion(q8);
        Question1 q9 = new Question1(MySingleton.questions[8], MySingleton.options[8][0], MySingleton.options[8][1], MySingleton.options[8][2], MySingleton.answers[8]);
        addQuestion(q9);
        Question1 q10 = new Question1(MySingleton.questions[9], MySingleton.options[9][0], MySingleton.options[9][1], MySingleton.options[9][2],MySingleton.answers[9]);
        addQuestion(q10);
        Question1 q11 = new Question1(MySingleton.questions[10], MySingleton.options[10][0], MySingleton.options[10][1], MySingleton.options[10][2], MySingleton.answers[10]);
        addQuestion(q11);
        Question1 q12 = new Question1(MySingleton.questions[11], MySingleton.options[11][0], MySingleton.options[11][1], MySingleton.options[11][2], MySingleton.answers[11]);
        addQuestion(q12);
        Question1 q13 = new Question1(MySingleton.questions[12], MySingleton.options[12][0], MySingleton.options[12][1], MySingleton.options[12][2],MySingleton.answers[12]);
        addQuestion(q13);
        Question1 q14 = new Question1(MySingleton.questions[13], MySingleton.options[13][0], MySingleton.options[13][1], MySingleton.options[13][2],MySingleton.answers[13]);
        addQuestion(q14);
        Question1 q15 = new Question1(MySingleton.questions[14], MySingleton.options[14][0], MySingleton.options[14][1], MySingleton.options[14][2], MySingleton.answers[14]);
        addQuestion(q15);
        Question1 q16 = new Question1(MySingleton.questions[15], MySingleton.options[15][0], MySingleton.options[15][1], MySingleton.options[15][2],MySingleton.answers[15]);
        addQuestion(q16);
        Question1 q17 = new Question1(MySingleton.questions[16], MySingleton.options[16][0], MySingleton.options[16][1], MySingleton.options[16][2], MySingleton.answers[16]);
        addQuestion(q17);
       Question1 q18 = new Question1(MySingleton.questions[17], MySingleton.options[17][0], MySingleton.options[17][1], MySingleton.options[17][2],MySingleton.answers[17]);
        addQuestion(q18);
        Question1 q19 = new Question1(MySingleton.questions[18], MySingleton.options[18][0], MySingleton.options[18][1], MySingleton.options[18][2], MySingleton.answers[18]);
        addQuestion(q19);
        Question1 q20 = new Question1(MySingleton.questions[19], MySingleton.options[19][0], MySingleton.options[19][1], MySingleton.options[19][2], MySingleton.answers[19]);
        addQuestion(q20);
    }

    private void addQuestion(Question1 question) throws SQLiteException {
        ContentValues cv = new ContentValues();
        cv.put(QuizContract.QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuizContract.QuestionsTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuizContract.QuestionsTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuizContract.QuestionsTable.COLUMN_OPTION3, question.getOption3());
        cv.put(QuizContract.QuestionsTable.COLUMN_ANSWER_NR, question.getAnswerNr());
        db.insert(QuizContract.QuestionsTable.TABLE_NAME, null, cv);
    }
    public List<Question1> getAllQuestions() throws SQLiteException {
        Log.d("version of db","version:"+MySingleton.databaseversion);
        List<Question1> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuizContract.QuestionsTable.TABLE_NAME, null);

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
