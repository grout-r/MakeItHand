package com.example.roman.makeithand;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Helper for the SQLite database.
 */
class MyDBOpenHelper extends SQLiteOpenHelper
{
    /**
     * Default.
     */
    MyDBOpenHelper(Context context, String name, CursorFactory
            factory, int version) {
        super(context, name, factory, version);
    }

    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(create_table);
    }
    public void onUpgrade(SQLiteDatabase db, int version_old, int version_new)
    {
        db.execSQL(drop_table);
        db.execSQL(create_table);
    }

    /**
     * Creating the fields.
     */
    private static final String create_table = "create table writings(" +
            "ID integer primary key autoincrement, " +
            "TITLE string," +
            "VALUE string, " +
            "IMAGE_PATH string" +
            ")";

    private static final String drop_table = "drop table contacts";
}