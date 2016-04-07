package com.example.evgeniy_pc.testvic;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

//Create DataBase
public class DBHelper  extends SQLiteOpenHelper
{
    final String LOG_TAG = "myLogs";

    public DBHelper(Context context)
    {
        super(context, "myDB", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        Log.d(LOG_TAG, " Create Table ");

        db.execSQL("create table data ("
                + "id integer primary key autoincrement,"
                + "email text,"
                + "location text,"
                + "question1 int,"
                + "question2 text,"
                + "question3 int,"
                + "question4 int,"
                + "question5 int,"
                + "question6 int,"
                + "question7 int,"
                + "question8 text,"
                + "question9 int,"
                + "question10 int,"
                + "send boolean"+");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
